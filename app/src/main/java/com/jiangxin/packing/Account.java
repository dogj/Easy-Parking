package com.jiangxin.packing;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class Account extends AppCompatActivity {

    TextView username;

    EditText account_balance;
    EditText psw;
    TextView friends;
    Button update;
    Button cancel;
    String username_string;
    String start_time;
    String end_time;
    String my_balance;
    String my_code;


    String account_balance_string;
    TextView tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        SharedPreferences sharedata = getSharedPreferences("data", 0);
        String data = sharedata.getString("username",null);
        username_string = data;
        setTitle("Account");
        data = "Current account = "+data;
        username = (TextView) findViewById(R.id.username);

        account_balance = (EditText) findViewById(R.id.account_balance);
        psw = (EditText) findViewById(R.id.psw);
        tip = (TextView) findViewById(R.id.tip);
        update = (Button) findViewById(R.id.register);
        cancel = (Button) findViewById(R.id.sign_in);
        friends = (TextView) findViewById(R.id.friends);

        setTitle("Account");
        username.setText(data);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedata = getSharedPreferences("data", 0);
                start_time = sharedata.getString("start",null);
                end_time=sharedata.getString("end",null);
                my_balance=sharedata.getString("balance",null);
                my_code=sharedata.getString("code",null);

                account_balance_string = account_balance.getText().toString();
                SharedPreferences.Editor sharedata2 = getSharedPreferences("data", 0).edit();
                sharedata2.putString("balance",account_balance_string);
                new Update().execute();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(Account.this,HomePage.class));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedata.edit().clear().commit();
                startActivity(new Intent(Account.this,MainActivity.class));
            }
        });



    }



    class Update extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://dogj.000webhostapp.com/assignment2/update.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("balance",account_balance_string));
            nameValuePairs.add(new BasicNameValuePair("park_code",my_code));
            nameValuePairs.add(new BasicNameValuePair("start_time",start_time));
            nameValuePairs.add(new BasicNameValuePair("end_time",end_time));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String info = EntityUtils.toString(entity);



                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        tip.setText(info);
                    }
                });


//                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
