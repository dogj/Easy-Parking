package com.jiangxin.packing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Packing extends AppCompatActivity {
    String username_string;
    String balance_string;
    String park_code_string;
    String start_time_string;
    String end_time_string;
    String info;
    SharedPreferences sharedata;
    Button confirm;
    EditText packing_code;
    RadioGroup radioGroup;
    RadioButton onehour;
    RadioButton twohour;
    TextView payment;
    TextView balance;
    Button packing_cancel;
    int time_check=0;
    static double charge = 1.50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing);
        sharedata = getSharedPreferences("data", 0);
        String data = sharedata.getString("username",null);
        username_string = data;
        balance_string = sharedata.getString("balance",null);
        confirm= (Button) findViewById(R.id.confirm);
        packing_code= (EditText) findViewById(R.id.packing_code);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        onehour= (RadioButton) findViewById(R.id.oneHour);
        twohour= (RadioButton) findViewById(R.id.twoHour);
        payment = (TextView) findViewById(R.id.payment);
        balance = (TextView) findViewById(R.id.account_balance);
        packing_cancel= (Button) findViewById(R.id.cancel_packing);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        if(dayOfTheWeek.contains("六")){
            charge=0;
        }

        payment.setText(String.valueOf(charge));
        balance.setText(balance_string);



        Toast.makeText(getApplicationContext(),dayOfTheWeek, Toast.LENGTH_SHORT).show();



        packing_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Packing.this,HomePage.class));
            }
        });



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int buttonId = group.getCheckedRadioButtonId();
                if(buttonId==R.id.oneHour){
                    payment.setText(String.valueOf(charge));
                    time_check=0;
                }else if(buttonId==R.id.twoHour){
                    payment.setText(String.valueOf(charge*2));
                    time_check=1;
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Time time = new Time();
                String[] packing_time = time.getTime(time_check);
//                payment.setText(packing_time[0]);
//                balance.setText(packing_time[1]);

                park_code_string = packing_code.getText().toString();
                start_time_string = packing_time[0];
                end_time_string = packing_time[1];
                balance_string = sharedata.getString("balance",null);


                new packing_update().execute();


                startActivity(new Intent(Packing.this,HomePage.class));


            }
        });

    }

    class packing_update extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://dogj.000webhostapp.com/assignment2/update.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            double balance_double;
            balance_double = Double.valueOf(balance_string);
            balance_double = balance_double - (time_check +1)*1.5;
            balance_string = String.valueOf(balance_double);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("balance",balance_string));
            nameValuePairs.add(new BasicNameValuePair("park_code",park_code_string));
            nameValuePairs.add(new BasicNameValuePair("start_time",start_time_string));
            nameValuePairs.add(new BasicNameValuePair("end_time",end_time_string));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                info = EntityUtils.toString(entity);


                Thread.sleep(2000);


                if(!(info.contains("fail"))){

                    SharedPreferences.Editor sharedata = getSharedPreferences("data", 0).edit();
                    sharedata.putString("start",start_time_string);
                    sharedata.putString("end",end_time_string);
                    sharedata.putString("code",park_code_string);

                    sharedata.putString("balance",balance_string);
                    sharedata.commit();
                    Thread.sleep(2000);
                    startActivity(new Intent(Packing.this,HomePage.class));
                }


//                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
