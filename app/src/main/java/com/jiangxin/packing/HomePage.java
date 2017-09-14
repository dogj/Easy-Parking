package com.jiangxin.packing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomePage extends AppCompatActivity {

    Button start_button;
    Button extension;
    Button my_account;
    String start_time;
    String end_time;
    String my_balance;
    String my_code;
    String username_string;
    CountDownTimer countDownTimer;
    Date start;
    Date end;
    long start_time_long ;
    long end_time_long;

    double account;
    TextView textView_start;
    TextView textView_end;
    TextView textView_balance;
    TextView textView_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        start_button = (Button) findViewById(R.id.start);
        extension = (Button) findViewById(R.id.addtime);
        my_account= (Button) findViewById(R.id.my_account);
        textView_start = (TextView) findViewById(R.id.start_time);
        textView_end = (TextView) findViewById(R.id.end_time);
        textView_balance= (TextView) findViewById(R.id.current_balance_home);
        textView_code = (TextView) findViewById(R.id.place);

        SharedPreferences sharedata = getSharedPreferences("data", 0);


        username_string = sharedata.getString("username",null);
        start_time = sharedata.getString("start",null);
        end_time=sharedata.getString("end",null);
        my_balance=sharedata.getString("balance",null);
        my_code=sharedata.getString("code",null);
        textView_start.setText(start_time);
        textView_end.setText(end_time);
        textView_balance.setText(my_balance);
        textView_code.setText(my_code);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,Parking.class));
            }
        });


        extension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                try {
                     start = df1.parse(start_time);
                     end = df1.parse(end_time);
                     start_time_long = start.getTime();
                     end_time_long = end.getTime();

                    if((end_time_long-start_time_long)==0){
                        Toast.makeText(HomePage.this,"you don't have parking yet", Toast.LENGTH_SHORT).show();
                    }else if((end_time_long-start_time_long)<70 * 60 * 1000){
                        end_time_long = end_time_long + 60 * 60 * 1000;
                        end = new Date(end_time_long);
                        end_time = df1.format(end);
                        textView_end.setText(end_time);
                        account=  Double.parseDouble(my_balance);
                        account = account - Parking.charge;
                        my_balance = String.valueOf(account);


                        SharedPreferences.Editor sharedata2 = sharedata.edit();
                        sharedata2.putString("balance",my_balance);
                        sharedata2.commit();
                        new Update().execute();

                        countDownTimer = new CountDownTimer((end_time_long-start_time_long),(end_time_long-start_time_long)) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                sharedata.edit().remove("start").remove("end").remove("code").commit();

                                startActivity(new Intent(HomePage.this,HomePage.class));
                            }
                        };
                        countDownTimer.start();
                        Toast.makeText(getApplicationContext(),"extension accepted",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"The maximum packing hour is 2",Toast.LENGTH_SHORT).show();
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });



        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,Account.class));
            }
        });


        if((end_time_long-start_time_long)>3000){

            SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

            try {
                start = df2.parse(start_time);
                end = df2.parse(end_time);
                start_time_long = start.getTime();
                end_time_long = end.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            countDownTimer = new CountDownTimer((end_time_long-start_time_long),(end_time_long-start_time_long)) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    sharedata.edit().remove("start").remove("end").remove("code").commit();
                    startActivity(new Intent(HomePage.this,HomePage.class));
                }
            };
            countDownTimer.start();

        }




    }



    class Update extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://dogj.000webhostapp.com/assignment2/update.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("balance",my_balance));
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
                        textView_balance.setText(my_balance);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
