package com.taotao.pojo;

public class Item extends TbItem {
    public String[] getImages() {
        String image2 = this.getImage();
        if (image2 != null && !"".equals(image2)) {
            String[] split = image2.split("http");
            String[] strings = new String[split.length - 1];
            for (int i = 1; i < split.length; i++) {
                String s = "http"+split[i];
                strings[i - 1] = s;
            }
            return strings;
        }
        return null;
    }

    public Item() {

    }

    public Item(TbItem tbItem) {
        this.setBarcode(tbItem.getBarcode());
        this.setcId(tbItem.getcId());
        this.setCreated(tbItem.getCreated());
        this.setId(tbItem.getId());
        this.setImage(tbItem.getImage());
        this.setNum(tbItem.getNum());
        this.setPrice(tbItem.getPrice());
        this.setSellPoint(tbItem.getSellPoint());
        this.setStatus(tbItem.getStatus());
        this.setTitle(tbItem.getTitle());
        this.setUpdated(tbItem.getUpdated());
    }

}
