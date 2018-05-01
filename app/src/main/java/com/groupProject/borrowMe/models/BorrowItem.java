package com.groupProject.borrowMe.models;

/**
 * Created by Enache on 25/04/2018.
 */

public class BorrowItem {

    private String Lemail;
    private String borrow_id;



    public BorrowItem(String borrow_id,String Lemail) {
        this.borrow_id= borrow_id;
        this.Lemail = Lemail;


    }

    public String getBorrowId() {
        return borrow_id;
    }

    public String getEmailLender() {
        return Lemail;
    }




}
