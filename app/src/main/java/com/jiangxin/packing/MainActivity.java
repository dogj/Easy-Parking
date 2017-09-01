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

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button sign_in;
    Button register;
    String username_string;
    String password_string;
    Button button;

    TextView tip;
    String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedata3 = getSharedPreferences("data", 0);
        String data = sharedata3.getString("username",null);
        if(data!=null){
            startActivity(new Intent(MainActivity.this,HomePage.class));
        }


        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.psw);
        sign_in = (Button) findViewById(R.id.sign_in);
        register = (Button) findViewById(R.id.register);
        tip = (TextView) findViewById(R.id.tip);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HomePage.class));
            }
        });


        register.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,Register.class)));

        sign_in.setOnClickListener(v -> {
            username_string = username.getText().toString();
            password_string = password.getText().toString();
            new LoginDatabase().execute();

        });

    }

    class LoginDatabase extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://dogj.000webhostapp.com/assignment2/login.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("password",password_string));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                info = EntityUtils.toString(entity);


                runOnUiThread(() -> tip.setText(info));

                Thread.sleep(2000);


                if(!(info.contains("fail"))){
                    startActivity(new Intent(MainActivity.this,HomePage.class));
                    SharedPreferences.Editor sharedata = getSharedPreferences("data", 0).edit();
                    sharedata.putString("username",username_string);
                    String balance;
                    balance = info;
                    sharedata.putString("balance",balance);
                    sharedata.commit();
                }


//                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}

