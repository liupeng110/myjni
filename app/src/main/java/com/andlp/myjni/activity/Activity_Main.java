package com.andlp.myjni.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andlp.myjni.R;
import com.andlp.myjni.jni.JniUtil;
import com.getkeepsafe.relinker.ReLinker;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.dxjia.ffmpeg.library.FFmpegNativeHelper;
@ContentView(R.layout.activity_main)
public class Activity_Main extends Activity_Base {

    @ViewInject(R.id.txt) TextView txt;
    @ViewInject(R.id.btn)Button btn;
    @ViewInject(R.id.edit)EditText edit;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){


        initData();
    }
    private void initData(){
        ReLinker.loadLibrary(Activity_Main.this, "hellojni");
        txt.setText(JniUtil.test());//test
        String gif = "ffmpeg -i /sdcard/DCIM/1.mp4 -vframes 40 -y -f gif /sdcard/DCIM/abc.gif";
        edit.setText(gif);

    }

   @Event(R.id.btn) private void btn(View view){


//       if (true){
//           txt.setText(JniUtil.callCpp());//callJava
//           return;
//       }

       String common=edit.getText().toString();
       if (common==null||common.equals("")){
           String result= FFmpegNativeHelper.runCommand("ffmpeg -version");
           txt.setText(result);
       }else{
           common="ffmpeg -version";
           String result="n/a";
           try {
             result = FFmpegNativeHelper.runCommand(common);
           }catch (Throwable t){t.printStackTrace();result="执行命令异常";}
           txt.setText(result);
       }

    }




}
