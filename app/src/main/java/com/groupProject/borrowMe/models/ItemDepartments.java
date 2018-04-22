package com.groupProject.borrowMe.models;

/**
 * Created by Enache on 19/04/2018.
 */

public class ItemDepartments {


    private String title;
    private String price;
    private String item_id;


    public ItemDepartments(String item_id,String title, String price) {
        this.item_id= item_id;
        this.title = title;
        this.price = price;

    }

    public String getItemTitle() {
        return title;
    }

    public String getItemId() {
        return item_id;
    }


    public void setItemTitle(String title) {
        this.title = title;
    }

    public String getItemPrice() {
        return price;
    }

    public void setItemPrice(String price) {
        this.price = price;
    }


}
