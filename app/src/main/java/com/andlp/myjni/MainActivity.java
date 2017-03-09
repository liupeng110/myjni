package com.andlp.myjni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andlp.myjni.jni.JniUtil;
import com.getkeepsafe.relinker.ReLinker;

import cn.dxjia.ffmpeg.library.FFmpegNativeHelper;

public class MainActivity extends AppCompatActivity {

    TextView txt; Button btn; EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        setContentView(R.layout.activity_main);
        txt =(TextView)findViewById(R.id.txt);
        btn =(Button)findViewById(R.id.btn);
        edit=(EditText)findViewById(R.id.edit);
        initData();
    }
    private void initData(){
        ReLinker.loadLibrary(MainActivity.this, "hellojni");
        txt.setText(JniUtil.test());
        String gif = "ffmpeg -i /sdcard/DCIM/1.mp4 -vframes 30 -y -f gif /sdcard/abc.gif";
        edit.setText(gif);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String common=edit.getText().toString();
                if (common==null||common.equals("")){
                String result= FFmpegNativeHelper.runCommand("ffmpeg -version");
                  txt.setText(result);
                }else{
                    String result=  FFmpegNativeHelper.runCommand(common);
                    txt.setText(result);
                }


            }
        });
//        t.setText(JniUtil.callJava());
//        t.setText(JniUtil.getCpu());

    }
}
