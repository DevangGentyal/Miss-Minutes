package com.example.missminutes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.media3.common.util.Log;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
    String inputStr;

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
                inputStr = input.getText().toString().trim();

                if(inputStr.equals("")){
                    Toast.makeText(getApplicationContext(),"Please type something first",Toast.LENGTH_SHORT).show();
                }
                else{
//                   Creating Prompt
                    createPrompt();

//                   Creating Response
                    api_call(inputStr);

                }
            }
        });

    }
    public void createPrompt(){

//        Creating the new CardView for Prompt
        CardView new_cardView = new CardView(this);
        int newId = getResources().getIdentifier("cardView" + (cardNo), "id", getPackageName());
        new_cardView.setId(newId);

        LinearLayout.LayoutParams new_cardViewLayoutParams = new LinearLayout.LayoutParams(
                dp_to_pixels(305), LinearLayout.LayoutParams.WRAP_CONTENT
        );
        new_cardViewLayoutParams.setMargins(0,0,dp_to_pixels(10),dp_to_pixels(20));
        new_cardViewLayoutParams.gravity = Gravity.END;
        new_cardView.setLayoutParams(new_cardViewLayoutParams);
        new_cardView.setCardBackgroundColor(Color.argb(80,60,135,245));
        new_cardView.setRadius(dp_to_pixels(10));

//        Creating a Linear Layout inside CardView
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linear_layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT
        );
        linearLayout.setGravity(Gravity.RIGHT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(dp_to_pixels(10),dp_to_pixels(10),dp_to_pixels(10),dp_to_pixels(10));

//        Creating a Image View inside LinearLayout
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.user);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                dp_to_pixels(30),dp_to_pixels(38)
        ));

//        Creating a Text View inside LinearLayout
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        );
        textView.setPadding(dp_to_pixels(10),dp_to_pixels(10),dp_to_pixels(10),dp_to_pixels(10));
        textView.setText(inputStr);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(dp_to_pixels(5));

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        new_cardView.addView(linearLayout);
        container.addView(new_cardView);
        this.cardNo++;

    }
    public void createResponse(String responseString){
//      Creating the new CardView for Response
            CardView new_cardView = new CardView(this);
            int newId = getResources().getIdentifier("cardView" + (cardNo), "id", getPackageName());
            new_cardView.setId(newId);

            LinearLayout.LayoutParams new_cardViewLayoutParams = new LinearLayout.LayoutParams(
                    dp_to_pixels(305), LinearLayout.LayoutParams.WRAP_CONTENT
            );
            new_cardViewLayoutParams.setMargins(dp_to_pixels(10), 0, 0, dp_to_pixels(20));
            new_cardViewLayoutParams.gravity = Gravity.START;
            new_cardView.setLayoutParams(new_cardViewLayoutParams);
            new_cardView.setCardBackgroundColor(Color.argb(80, 255, 122, 0));
            new_cardView.setRadius(dp_to_pixels(10));

//        Creating a Linear Layout inside CardView
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams linear_layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            );
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(dp_to_pixels(10), dp_to_pixels(10), dp_to_pixels(10), dp_to_pixels(10));

//        Creating a Image View inside LinearLayout
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.missminutessmall);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    dp_to_pixels(30), dp_to_pixels(38)
            ));

//        Creating a Text View inside LinearLayout
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            );
            textView.setPadding(dp_to_pixels(10), dp_to_pixels(10), dp_to_pixels(10), dp_to_pixels(10));
            textView.setText(responseString);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(dp_to_pixels(5));

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            new_cardView.addView(linearLayout);
            container.addView(new_cardView);
            this.cardNo++;

    }

    public void api_call(String input){
        String respString = "";
        //                    Creating and Sending the Request to the BARD AI using the Volley Library
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://generativelanguage.googleapis.com/v1beta3/models/chat-bison-001:generateMessage?key=AIzaSyDb49SM3NK7yEHnvA70ShpnzCQlgSLw5WE";


        JSONObject prompt = new JSONObject();
        JSONObject context = new JSONObject();
        JSONObject input1 = new JSONObject();
        JSONObject output = new JSONObject();
        JSONObject examples = new JSONObject();
        JSONObject messages = new JSONObject();

        try {
            input1.put("content", "who are you");
            output.put("content", "I am Miss Minutes, your friendly AI assistant. I am here to help you with anything you need, from scheduling appointments to finding information. I am always learning and growing, so please feel free to ask me anything.");

            examples.put("input", input1);
            examples.put("output", output);

            context.put("context", "Act as you are Miss Minutes an Assistant made by Devang Gentyal");
            context.put("examples", new JSONObject().put("examples", examples));
            context.put("messages", new JSONObject().put("messages", input));

            prompt.put("prompt", context);
            prompt.put("temperature", 0.25);
            prompt.put("top_k", 40);
            prompt.put("top_p", 0.95);
            prompt.put("candidate_count", 2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject postData = new JSONObject();
        try {
            postData.put("prompt",prompt);
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
//                                  //Parsing the JSON response data
                        try {
                            String respString;
                            JSONArray candidates = response.getJSONArray("candidates");
                            JSONObject firstobj = candidates.getJSONObject(0);
                            respString = firstobj.getString("output");
                            createResponse(respString);
                            Log.d("resp",respString);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("resp","Error :"+error.toString());
                        createResponse("Sorry! I can't answer that");
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