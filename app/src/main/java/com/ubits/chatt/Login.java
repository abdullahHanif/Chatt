package com.ubits.chatt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Abdullah
 */

public class Login extends AppCompatActivity {

    public String UserName= "";
    Button submit;
    TextView tvname;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        submit = (Button) findViewById(R.id.btnChatt);
        tvname = (TextView) findViewById(R.id.displayName);
        tvname.setText("192.168.1.106");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 UserName = tvname.getText().toString();
                 if(UserName.equals("")){
                     Toast.makeText(Login.this,"Please Enter Valid User Name",Toast.LENGTH_SHORT).show();
                  }
                 else{
                     Intent intent = new Intent(Login.this, MainActivity.class);
                     intent.putExtra("USERNAME",UserName);
                     startActivity(intent);
                     finish();
                 }
            }
        });
    }
}