package com.taotao.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import com.taotao.service.JedisClient;
import com.taotao.utils.IDUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    private final int max = 1200;
    private final int min = 600;
    private Random random = new Random();
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamMapper itemParamMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination destination;
    @Autowired
    private JedisClient jedisClient;

    /**
     * 根据商品id查询商品信息
     * @param itemId
     * @return
     */
    @Override
    public TbItem findTbItemById(Long itemId) {
        int seconds = random.nextInt(max)%(max-min+1) + min;
        String tbItemBasicData = jedisClient.get("tbItemBasicData");
        if (StringUtils.isNotBlank(tbItemBasicData)){
            if (tbItemBasicData.equals("null")){
                return null;
            }
            TbItem tbItem = JsonUtils.jsonToPojo(tbItemBasicData, TbItem.class);
            jedisClient.expire("tbItemBasicData",seconds);
            return tbItem;
        }
        TbItem tbItem = itemMapper.findTbItemById(itemId);
        if (tbItem == null){
            jedisClient.set("tbItemBasicData","null");
            jedisClient.expire("tbItemBasicData",180);
        }else {
            jedisClient.set("tbItemBasicData",JsonUtils.objectToJson(tbItem));
            jedisClient.expire("tbItemBasicData",seconds);
        }
        return tbItem;
    }

    /**
     * 查询所有商品信息并分页
     * @param pageno 当前页码
     * @param pagesize  每页显示的数据条数
     * @return
     */
    @Override
    public LayuiTbItem findAllTbItem(String pageno, String pagesize) {
        /*当前页码*/
        Integer page = Integer.parseInt(pageno);
        /*每页显示数据条数*/
        Integer size = Integer.parseInt(pagesize);
        /*每页开始数据的索引*/
        Integer start = (page - 1) * size;
        List<TbItem> tbItems = itemMapper.findAllTbItem(start,size);
        Integer count = itemMapper.findTbItemsCount();
        LayuiTbItem layui = new LayuiTbItem();
        layui.setCode(0);
        layui.setCount(count);
        layui.setMsg("");
        layui.setData(tbItems);
        return layui;
    }

    /**
     * 修改商品的状态
     * @param tbItem  需要修改的商品
     * @param type  商品的状态
     * @param update   更新时间
     * @return
     */
    @Override
    public TaotaoResult updateTbItem(List<TbItem> tbItem, int type, Date update) {
        if (tbItem.size() <= 0){
            return TaotaoResult.build(500,"请先选择商品，在操作",null);
        }
        List<Long> ids = new ArrayList<Long>();
        for (TbItem tbItems : tbItem) {
            ids.add(tbItems.getId());
        }
        int count = itemMapper.updateTbItem(ids,type,update);
        if (count > 0 && type == 0){
            return TaotaoResult.build(200,"商品下架成功",null);
        } else if (count > 0 && type == 1){
            return TaotaoResult.build(200,"商品上架成功",null);
        } else if (count > 0 && type == 2){
            return TaotaoResult.build(200,"商品删除成功",null);
        }
        return TaotaoResult.build(500,"商品修改失败",null);
    }

    /**
     * 多条件搜索商品
     * @param page 当前页码
     * @param limit 每页显示商品数量
     * @param title 商品标题信息
     * @param priceMin 价格区间最低
     * @param priceMax 价格区间最高
     * @param cId 商品的分类id
     * @return
     */
    @Override
    public LayuiTbItem searchItem(Integer page, Integer limit, String title, Integer priceMin, Integer priceMax, Long cId) {
        if (priceMin == null){
            priceMin = 0;
        }
        if (priceMax == null){
            priceMax = 1000000000;
        }
        Integer start = (page - 1) * limit;
        int count = itemMapper.searchItemLikeCount(title,priceMin,priceMax,cId);
        List<TbItem> tbItems = itemMapper.searchItemLike(start,limit,title,priceMin,priceMax,cId);
        LayuiTbItem layui = new LayuiTbItem();
        layui.setCode(0);
        layui.setCount(count);
        layui.setMsg("");
        layui.setData(tbItems);
        return layui;
    }

    /**
     * 上传图片
     * @param filename
     * @param bytes
     * @return
     */
    @Override
    public ImageDataResult addImage(String filename, byte[] bytes) {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("E:\\tool\\阿里云OSS.txt");
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String endpoint = properties.getProperty("endpoint");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String bucketName = properties.getProperty("bucketName");
        String objectName = properties.getProperty("objectName");
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //随机生成一个字符串   本身的名字 只要.jpg 随机字符串.jpg
        String fileName = IDUtils.genImageName()+filename.substring(filename.lastIndexOf("."));
        ossClient.putObject(bucketName,objectName+fileName,bis);
        ImageDataResult result = new ImageDataResult();
        result.setCode(0);
        result.setMsg("");
        ImageData data = new ImageData();
        data.setSrc("https://"+bucketName+".oss-cn-chengdu.aliyuncs.com/images/"+fileName);
        result.setData(data);
        return result;
    }

    /**
     * 添加商品信息、商品描述信息以及商品规格参数信息
     * @param tbItem
     * @param itemDesc
     * @return
     */
    @Override
    public TaotaoResult addItem(TbItem tbItem, String itemDesc, String[] paramKeyIds, String[] paramValues) {
        /*添加商品基本信息*/
        final long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItem.setStatus((byte)1);
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        int i = itemMapper.addTbItem(tbItem);
        if (i <= 0){
            return TaotaoResult.build(500,"添加商品基本信息失败");
        }
        /**
         * 意味着前面都没有报错 添加商品成功了 我们应该做solr索引同步了
         * 意味着他应该发布一个消息到消息队列里面去
         * 提供给 search-service 来使用
         * 为什么发送id过去呢 ？
         * 因为 我发送了id过去  search-service可以根据id查询到商品信息
         * 就会得到商品对象  使用solrService。add（商品对象）;
         *
         */
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(itemId+"");
                return textMessage;
            }
        });
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(itemDesc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        int j = tbItemDescMapper.addTbItemDesc(tbItemDesc);
        if (j <= 0){
            return TaotaoResult.build(500,"添加商品描述信息失败");
        }
        /*添加商品对应的规格参数信息*/
        int count = 0;
        for (int k = 0; k < paramKeyIds.length; k++) {
            String paramKeyId = paramKeyIds[k];
            int paramId = Integer.parseInt(paramKeyId);
            String paramValue = paramValues[k];
            count = itemParamMapper.addTbItemParamValue(itemId,paramId,paramValue);
        }
        if (count <= 0){
            return TaotaoResult.build(500,"添加商品规格参数信息失败");
        }
        return TaotaoResult.build(200,"添加商品信息成功");
    }

    @Override
    public TbItemDesc getItemDescByItemId(Long itemId) {
        int seconds = random.nextInt(max)%(max-min+1) + min;
        String itemDesc = jedisClient.get("itemDesc");
        if (StringUtils.isNotBlank(itemDesc)){
            if (itemDesc.equals("null")){
                return null;
            }
            TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(itemDesc, TbItemDesc.class);
            jedisClient.expire("itemDesc",seconds);
            return tbItemDesc;
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.getItemDescByItemId(itemId);
        if (tbItemDesc == null){
            jedisClient.set("itemDesc","null");
            jedisClient.expire("itemDesc",180);
        }else {
            jedisClient.set("itemDesc",JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire("itemDesc",seconds);
        }
        return tbItemDesc;
    }
}
