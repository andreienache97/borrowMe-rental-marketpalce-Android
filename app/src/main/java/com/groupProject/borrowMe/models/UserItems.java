package com.groupProject.borrowMe.models;

/**
 * Created by Enache on 23/04/2018.
 */

public class UserItems {


    private String title;
    private String item_id;


    public UserItems(String item_id,String title) {
        this.item_id= item_id;
        this.title = title;

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


}
