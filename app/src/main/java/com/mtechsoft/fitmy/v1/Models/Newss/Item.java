package com.mtechsoft.fitmy.v1.Models.Newss;

import java.util.List;

public class Item {

    private String category_name;
    private List<SubItem> subItemList;


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<SubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }
}
