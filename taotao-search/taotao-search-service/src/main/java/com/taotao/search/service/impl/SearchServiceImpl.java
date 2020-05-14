package com.taotao.search.service.impl;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.SearchItem;
import com.taotao.pojo.SearchItemResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public TaotaoResult importSolr() {
        try {
            List<SearchItem> searchItemAll = itemMapper.findSearchItemAll();
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
            return TaotaoResult.build(200,"商品信息初始化成功");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TaotaoResult.build(500,"商品信息初始化失败");
    }

    @Override
    public SearchItemResult findSearchItemByQuery(String query, Integer page) {
        SearchItemResult result = new SearchItemResult();
        try {
            //service 逻辑
            SolrQuery solrQuery = new SolrQuery();
            //设置默认域为复制域
            solrQuery.set("df","item_keywords");
            //设置查询条件
            solrQuery.setQuery(query);
            //设置显示高亮
            solrQuery.setHighlight(true);
            //设置高亮的域
            solrQuery.addHighlightField("item_title");
            //设置前缀
            solrQuery.setHighlightSimplePre("<font style='color:red'>");
            //设置后缀
            solrQuery.setHighlightSimplePost("</font>");
            //设置每页开始的索引
            solrQuery.setStart((page - 1) * 60);
            //设置每页显示的数据条数
            solrQuery.setRows(60);


            /*dao层代码*/
            QueryResponse queryResponse = solrServer.query(solrQuery);
            SolrDocumentList documentList = queryResponse.getResults();
            long totalCount = documentList.getNumFound();
            long totalPages = totalCount % 60 == 0 ? totalCount / 60 : totalCount /60 + 1;
            //设置总数据条数
            result.setTotalCount(totalCount);
            //设置总页数
            result.setTotalPages(totalPages);
            List<SearchItem> itemList = new ArrayList<SearchItem>();
            for (SolrDocument document : documentList) {
                SearchItem item = new SearchItem();
                item.setId((String) document.get("id"));
                item.setCategoryName((String) document.get("item_category_name"));
                //你取出来的图片也是多张图片
                String item_image = (String) document.get("item_image");
                item.setImage((String) document.get("item_image"));
                item.setPrice((Long) document.get("item_price"));
                item.setSellPoint((String) document.get("item_sell_point"));
                Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
                List<String> list = highlighting.get(document.get("id")).get("item_title");
                String item_title = "";
                if (list != null && list.size() > 0){
                    item_title = list.get(0);
                } else {
                    item_title = (String) document.get("item_title");
                }
                item.setTitle(item_title);
                itemList.add(item);
            }
            result.setItemList(itemList);
            return result;
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addSearchItem(SearchItem item) {
        try {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSellPoint());
            //存储成为 Lucene结构的图片 也是多张图片
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategoryName());
            document.addField("item_desc", item.getItemDesc());
            solrServer.add(document);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
