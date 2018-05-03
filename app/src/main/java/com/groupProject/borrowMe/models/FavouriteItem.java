package com.groupProject.borrowMe.models;

public class FavouriteItem {
    private String Like_email;
    private String item_id;
    private String name;



    public FavouriteItem(String item_id,String Like_email,String name) {
        this.item_id= item_id;
        this.Like_email = Like_email;
        this.name = name;


    }

    public String getItemId() {
        return item_id;
    }

    public String getEmailLiker() {
        return Like_email;
    }

    public String getItemName() {
        return name;
    }




}
