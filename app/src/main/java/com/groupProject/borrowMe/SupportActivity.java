/* Author:
 * This is Support page, users will submit theirs support tickets in here,
 * admin will get the question on admin page and repsonse to these tickets
 */
package com.groupProject.borrowMe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
//Fields
        final Spinner spinner1 = findViewById(R.id.spinner1);
        final EditText editText4 = findViewById(R.id.editText4);
        final Button button = findViewById(R.id.button);

//Submit Button
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String text = editText4.getText().toString();
                Log.d("hi",text);
            }
        } );

    }


}