package com.example.missminutes;

import androidx.appcompat.app.AppCompatActivity;
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

        input = findViewById(R.id.input);
        sendBtn = findViewById(R.id.sendBtn);

//        On Clicking the send Button
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"Please type something first",Toast.LENGTH_SHORT);
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
                                    System.out.println(response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }
                    );
                }
            }
        });

    }
}