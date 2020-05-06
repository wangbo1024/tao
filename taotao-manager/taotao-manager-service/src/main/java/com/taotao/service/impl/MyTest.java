
package com.taotao.service.impl;

import com.taotao.pojo.SearchItem;
import com.taotao.service.ItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MyTest {
    @Test
    public void show() throws IOException, SolrServerException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ItemService itemServiceImpl = (ItemServiceImpl) ac.getBean("itemServiceImpl");
        List<SearchItem> searchItemAll = itemServiceImpl.findSearchItemAll();
        SolrServer solrServer = new HttpSolrServer("http://192.168.67.131:8080/solr/");
        for (SearchItem searchItem : searchItemAll) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSellPoint());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategoryName());
            document.addField("item_desc", searchItem.getItemDesc());
            solrServer.add(document);
        }
        solrServer.commit(); 
    }

    @Test
    public void show1() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.67.131:8080/solr/");
        /*不是set，就是add*/
        SolrQuery query = new SolrQuery();
        /*若设置了默认域，就不能用 域名:域值 的形式来查询solr文档，而应该直接传查询的内容*/
        query.setQuery("手机");
        /*给指定域排序*/
        query.setSort("item_price", SolrQuery.ORDER.desc);
        /*设置显示数据数，类似于分页*/
        query.setStart(0);
        query.setRows(5);
        /*设置默认查找的域*/
        query.set("df","item_keywords");
        /*设置显示高亮*/
        query.setHighlight(true);
        /*设置显示高亮的域*/
        query.addHighlightField("item_title");
        /*设置前缀*/
        query.setHighlightSimplePre("<font style='color:red'>");
        /*设置后缀*/
        query.setHighlightSimplePost("</font>");
        QueryResponse response = solrServer.query(query);
        /*solr文档库集合对象*/
        SolrDocumentList list = response.getResults();
        /*总记录条数*/
        System.out.println("总条数："+list.getNumFound());
        for (SolrDocument document:list) {
            /*System.out.println("标题："+document.get("item_title"));
            System.out.println("价格："+document.get("item_price"));
            System.out.println("========================================");*/
            //设置显示高亮后的获取数据方法
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> lists = highlighting.get(document.get("id")).get("item_title");
            String itemTitle = null;
            if (lists != null && lists.size() > 0) {
                itemTitle = lists.get(0);
            } else {
                itemTitle = (String) document.get("item_title");
            }
            System.out.println(itemTitle);
            System.out.println(document.get("item_price"));

        }
    }
}
