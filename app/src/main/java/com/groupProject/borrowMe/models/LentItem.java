package com.groupProject.borrowMe.models;

/**
 * Created by Enache on 26/04/2018.
 */

public class LentItem {

    private String Bemail;
    private String borrow_id;



    public LentItem(String borrow_id,String Bemail) {
        this.borrow_id= borrow_id;
        this.Bemail = Bemail;


    }

    public String getBorrowId() {
        return borrow_id;
    }

    public String getEmailBorrower() {
        return Bemail;
    }

}
