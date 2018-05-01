package com.groupProject.borrowMe.Chat;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChatMessage {

    private String emailFrom;

    private String emailTo;

    private String message;

    private String timestamp;

    private String itemId;

    public ChatMessage(String emailFrom, String emailTo, String message, String timestamp, String itemId) {
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.message = message;
        this.timestamp = timestamp;
        this.itemId = itemId;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ChatMessage) {
            ChatMessage other = (ChatMessage) obj;

            return this.timestamp.equals(other.timestamp);
        }

        return false;
    }

    public static Calendar getFormattedTimeText(ChatMessage message) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.DAY_OF_MONTH, 20);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.HOUR, 4);
        calendar.set(Calendar.MINUTE, 20);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        try {
            calendar.setTime(df.parse(message.timestamp));
            Log.d("original", message.timestamp);

            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return calendar;
        }
    }
}
