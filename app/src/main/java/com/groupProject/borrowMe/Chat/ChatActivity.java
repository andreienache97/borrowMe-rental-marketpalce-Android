package com.groupProject.borrowMe.Chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.groupProject.borrowMe.JSONRequests.AddMessageRequest;
import com.groupProject.borrowMe.JSONRequests.GetConversationRequest;
import com.groupProject.borrowMe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public static String EXTRA_EMAIL_FROM = "email_from";
    public static String EXTRA_EMAIL_TO = "email_to";
    public static String EXTRA_EMAIL_ITEM_ID = "item_id";

    private ChatView mChatView;

    private List<ChatMessage> messages = new ArrayList<>();
    private List<ChatMessage> newMessages = new ArrayList<>();

    private String emailFrom;
    private String emailTo;
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent incomingIntent = getIntent();

        final String emailFrom = incomingIntent.getStringExtra(EXTRA_EMAIL_FROM);
        final String emailTo = incomingIntent.getStringExtra(EXTRA_EMAIL_TO);
        final String itemId = incomingIntent.getStringExtra(EXTRA_EMAIL_ITEM_ID);

        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.itemId = itemId;

        if (emailFrom == null || emailTo == null) {
            Toast.makeText(this, "You need both emails to opn up conversation", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        getConversation(emailFrom, emailTo, itemId);
        setupChatView();
    }

    private void getConversation(String email1, String email2, String itemId) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    //Getting responses from php file
                    JSONObject jsonResponse = new JSONObject(response);
                    parseJSON(jsonResponse);
                    Log.d("asl", "got this shit");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetConversationRequest messagesRequest = new GetConversationRequest(email1, email2, itemId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(messagesRequest);
    }

    private void parseJSON(JSONObject jsonResponse) throws JSONException {
        boolean success = jsonResponse.getBoolean("success");
        if (!success) {
            Toast.makeText(this, "You don't have any messages", Toast.LENGTH_SHORT).show();
            return;
        }

        this.newMessages.clear();

        JSONArray messages = jsonResponse.getJSONArray("messages");
        for (int i = 0 ; i < messages.length(); i++) {
            JSONObject obj = messages.getJSONObject(i);
            String fromEmail = obj.getString("from");
            String toEmail = obj.getString("to");
            String message = obj.getString("message");
            String timestamp = obj.getString("timestamp");
            String userId = obj.getString("item_id");

            this.newMessages.add(new ChatMessage(fromEmail, toEmail, message, timestamp, userId));

            Log.d("ceva", "got message");
        }

        Iterator<ChatMessage> iterator = newMessages.iterator();
        while (iterator.hasNext()) {
            ChatMessage message = iterator.next();

            if (this.messages.contains(message)) {
                iterator.remove();
            } else {
                this.messages.add(message);
            }
        }

        setMessages();
    }

    private void setupChatView() {
        mChatView = (ChatView) findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.colorAccent));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                //Reset edit text
                mChatView.setInputText("");
            }
        });
    }

    private void setMessages() {
        //User id
        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = messages.get(0).getEmailFrom();

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = messages.get(0).getEmailTo();

        final User me = new User(myId, myName, myIcon);
        final User you = new User(yourId, yourName, yourIcon);


        for (ChatMessage message : newMessages) {
            if (message.getEmailFrom().equals(myName)) {
                //new message
                final Message mess = new Message.Builder()
                        .setUser(me)
                        .setRight(true)
                        .setText(message.getMessage())
                        .setSendTime(ChatMessage.getFormattedTimeText(message))
                        .hideIcon(true)
                        .build();

                //Set to chat view
                mChatView.send(mess);
            } else {
                //Receive message
                final Message receivedMessage = new Message.Builder()
                        .setUser(you)
                        .setRight(false)
                        .setText(message.getMessage())
                        .setSendTime(ChatMessage.getFormattedTimeText(message))
                        .build();

                mChatView.receive(receivedMessage);
            }

        }
    }

    private void sendMessage() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //response from php code
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );

                    if (!success) {
                        Toast.makeText(ChatActivity.this, "Couldn't send message", Toast.LENGTH_SHORT).show();
                    }

                    getConversation(emailFrom, emailTo, itemId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //Register request to connect the database by php code
        AddMessageRequest addMessageRequest = new AddMessageRequest( emailFrom, emailTo, mChatView.getInputText().trim(), itemId, responseListener );
        RequestQueue queue = Volley.newRequestQueue( this );
        queue.add(addMessageRequest);
    }
}
