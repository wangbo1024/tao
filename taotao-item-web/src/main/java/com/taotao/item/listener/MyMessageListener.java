package com.taotao.item.listener;

import com.taotao.constant.FreeMakerConstant;
import com.taotao.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class MyMessageListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private Writer writer;

    @Override
    public void onMessage(Message message) {
        /*TextMessage textMessage = (TextMessage) message;
        try {
            String id = textMessage.getText();
            Long itemId = Long.valueOf(id);
            TbItem tbItem = itemService.findTbItemById(itemId);
            TbItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
            String itemParam = itemService.findItemGroupAndKeyAndValue(itemId);
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map map = new HashMap();
            map.put("item", new Item(tbItem));
            map.put("itemDesc", itemDesc);
            map.put("itemParam", itemParam);
            writer = new FileWriter(new File(FreeMakerConstant.HTML_OUT_PATH + itemId + ".html"));
            template.process(map, writer);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

    }
}
