package com.example.missminutes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.media3.common.util.Log;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    int cardNo;
    EditText input;
    ImageButton sendBtn;
    LinearLayout container;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        My Code for logic functioning
        cardNo = 1;
        input = (EditText) findViewById(R.id.input);
        sendBtn = (ImageButton) findViewById(R.id.sendBtn);
        container = (LinearLayout) findViewById(R.id.containerLayout);

//        On Clicking the send Button
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputStr = input.getText().toString().trim();

                if(inputStr.equals("")){
                    Toast.makeText(getApplicationContext(),"Please type something first",Toast.LENGTH_SHORT).show();
                }
                else{
//                   Creating CardView for user Prompt
                    createPrompt();

//                    createResponse();
//                   Gettting Response from API
//                    api_call(inputStr);
                }
            }
        });

    }
    public void createPrompt(){

//        Dynamically generate the resource ID for Previous CardView
        int prevId = getResources().getIdentifier("cardView" + (cardNo-1), "id", getPackageName());
        CardView prev_cardView = findViewById(prevId);

//        Creating the new CardView
        CardView new_cardView = new CardView(this);
        int newId = getResources().getIdentifier("cardView" + (cardNo), "id", getPackageName());
        new_cardView.setId(newId);

        LinearLayout.LayoutParams new_cardViewLayoutParams = new LinearLayout.LayoutParams(
                dp_to_pixels(305), dp_to_pixels(105)
        );
        new_cardViewLayoutParams.setMargins(0,0,dp_to_pixels(10),dp_to_pixels(20));
        new_cardViewLayoutParams.gravity = Gravity.END;
        new_cardView.setLayoutParams(new_cardViewLayoutParams);
        new_cardView.setCardBackgroundColor(Color.argb(80,60,135,245));
        new_cardView.setRadius(dp_to_pixels(10));

        container.addView(new_cardView);
        this.cardNo++;
    }
    public void createResponse(){

    }

    public void api_call(String input){
        //                    Creating and Sending the Request to the BARD AI using the Volley Library
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://generativelanguage.googleapis.com/v1beta3/models/text-bison-001:generateText?key=AIzaSyDb49SM3NK7yEHnvA70ShpnzCQlgSLw5WE";

        JSONObject text = new JSONObject();
        try {
            text.put("text", input);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject postData = new JSONObject();
        try {
            postData.put("prompt",text);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String respString = "";
//                                  //Parsing the JSON response data
                        try {
                            JSONArray candidates = response.getJSONArray("candidates");
                            JSONObject firstobj = candidates.getJSONObject(0);
                            respString = firstobj.getString("output");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        Log.d("resp",respString);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("resp",error.toString());

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    int dp_to_pixels(int dp){
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()
        );
        return px;
    }
}