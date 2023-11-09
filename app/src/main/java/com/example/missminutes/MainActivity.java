package com.example.missminutes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.Log;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText input;
    ImageButton sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        My Code for logic functioning

        input = (EditText) findViewById(R.id.input);
        sendBtn = (ImageButton) findViewById(R.id.sendBtn);

//        On Clicking the send Button
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String inputStr = input.getText().toString().trim();

                if(inputStr.equals("")){
                    Toast.makeText(getApplicationContext(),"Please type something first",Toast.LENGTH_SHORT).show();
                }
                else{
//                    Creating and Sending the Request to the BARD AI using the Volley Library
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = "https://bard.google.com/";
                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("accept", "application/json");
                        postData.put("Content-Type", "application/json");
                        postData.put("session_id", "cQgN4XaH_3dURfFUApJXcGlsw9mlweNiLzIbdFAU3tKtwk7K_4VUkBuuyYzhXzj_ll0Jpw");
                        postData.put("message", "What is the meaning of life?");
                        // Add more key-value pairs as needed
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            postData,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("resp",response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }
                    );
                    requestQueue.add(jsonObjectRequest);
                }
            }
        });

    }
}