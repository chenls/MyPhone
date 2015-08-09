package com.chenls.myphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent service = new Intent(this, PhoneService.class);
        this.startService(service);
        Log.i("hh", "MainActivity");
    }
    public void open(View view){

        Intent service = new Intent(this, PhoneService.class);
        this.startService(service);
        Toast.makeText(this, "已开启录音功能，哈哈哈", Toast.LENGTH_LONG).show();
    }
    public void close(View view){

        Intent service = new Intent(this, PhoneService.class);
        stopService(service);
        Toast.makeText(this, "已关闭录音功能，嘿嘿嘿", Toast.LENGTH_LONG).show();
    }


}
