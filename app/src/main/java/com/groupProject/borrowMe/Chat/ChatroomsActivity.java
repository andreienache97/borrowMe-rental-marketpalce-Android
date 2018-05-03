package com.groupProject.borrowMe.Chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.GetMessagesRequest;
import com.groupProject.borrowMe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ChatroomsActivity extends AppCompatActivity {

    public static String EXTRA_EMAIL = "email";

    private ListView listView;

    private String userEmail;

    HashMap<String, List<ChatMessage>> messagesMap = new HashMap<>();

    private ConversationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatrooms);

        //return button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent incomingIntent = getIntent();

        //Get from the previous page
        final String email = incomingIntent.getStringExtra(EXTRA_EMAIL);
        this.userEmail = email;

        getAllUserMessages(email);
    }

    private void getAllUserMessages(String email) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    //Getting responses from php file
                    JSONObject jsonResponse = new JSONObject(response);
                    parseJSON(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetMessagesRequest messagesRequest = new GetMessagesRequest(email, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(messagesRequest);
    }

    private void parseJSON(JSONObject jsonResponse) throws JSONException {
        boolean success = jsonResponse.getBoolean("success");
        if (!success) {
            Toast.makeText(this, "You don't have any messages", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONArray messages = jsonResponse.getJSONArray("messages");
        for (int i = 0 ; i < messages.length(); i++) {
            JSONObject obj = messages.getJSONObject(i);
            String fromEmail = obj.getString("from");
            String toEmail = obj.getString("to");
            String message = obj.getString("message");
            String timestamp = obj.getString("timestamp");
            String itemId = obj.getString("item_id");

            if (messagesMap.containsKey(itemId)) {
                messagesMap.get(itemId).add(new ChatMessage(fromEmail, toEmail, message, timestamp, itemId));
            } else {
                List<ChatMessage> conversation = new ArrayList<>();
                conversation.add(new ChatMessage(fromEmail, toEmail, message, timestamp, itemId));

                messagesMap.put(itemId, conversation);
            }
            Log.d("ceva", "got message");
        }

        initConversations(messagesMap.values());
    }

    private void initConversations(Collection<List<ChatMessage>> threads) {
        listView = findViewById(R.id.lv_messages);

        ArrayList<ChatMessage> different = new ArrayList<>();
        for (List<ChatMessage> conversation : threads) {
            different.add(conversation.get(conversation.size() - 1));
        }

        mAdapter = new ConversationsAdapter(this, different);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //open new activity here
                ChatMessage message = mAdapter.getItem(position);
                if (message == null) {
                    return;
                }

                Intent intent = new Intent(ChatroomsActivity.this, ChatActivity.class);

                intent.putExtra(ChatActivity.EXTRA_EMAIL_TO, message.getEmailTo());
                intent.putExtra(ChatActivity.EXTRA_EMAIL_FROM, message.getEmailFrom());
                intent.putExtra(ChatActivity.EXTRA_EMAIL_ITEM_ID, message.getItemId());

                startActivity(intent);
            }
        });
    }

    public class ConversationsAdapter extends ArrayAdapter<ChatMessage> {
        ConversationsAdapter(Context context, ArrayList<ChatMessage> users) {
            super(context, 0, users);
        }

        @Override
        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Get the data item for this position
            ChatMessage message = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_item_chat_room, parent, false);
            }
            // Lookup view for data population
            TextView emailText = convertView.findViewById(R.id.conversation_email);
            TextView messageText = convertView.findViewById(R.id.conversation_message);

            // Populate the data into the template view using the data object
            if (message != null) {
                emailText.setText(getOtherEmail(message));
                messageText.setText(message.getMessage());
            }

            // Return the completed view to render on screen
            return convertView;
        }

        private String getOtherEmail(ChatMessage message) {
            return message.getEmailFrom().equals(userEmail) ? message.getEmailTo() : message.getEmailFrom();
        }
    }
}
