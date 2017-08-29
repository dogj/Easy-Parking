package com.jiangxin.packing;

import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Packing extends AppCompatActivity {
    String username_string;
    Button confirm;
    EditText packing_code;
    RadioGroup radioGroup;
    RadioButton onehour;
    RadioButton twohour;
    TextView payment;
    TextView balance;
    static int charge = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing);
        SharedPreferences sharedata = getSharedPreferences("data", 0);
        String data = sharedata.getString("username",null);
        username_string = data;
        confirm= (Button) findViewById(R.id.confirm);
        packing_code= (EditText) findViewById(R.id.packing_code);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        onehour= (RadioButton) findViewById(R.id.oneHour);
        twohour= (RadioButton) findViewById(R.id.twoHour);
        payment = (TextView) findViewById(R.id.payment);
        balance = (TextView) findViewById(R.id.account_balance);

        payment.setText(String.valueOf(charge));


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int buttonId = group.getCheckedRadioButtonId();
                if(buttonId==R.id.oneHour){
                    payment.setText(String.valueOf(charge));
                }else if(buttonId==R.id.twoHour){
                    payment.setText(String.valueOf(charge*2));
                }
            }
        });



    }
}
