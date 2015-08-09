package com.chenls.myphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, PhoneService.class);
        context.startService(service);
        Log.i("hh", "BootBroadcastReceiver");
        Toast.makeText(context, "已开启录音功能，哈哈哈", Toast.LENGTH_LONG).show();
    }

}
