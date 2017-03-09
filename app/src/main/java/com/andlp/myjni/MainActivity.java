package com.andlp.myjni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andlp.myjni.jni.JniUtil;
import com.getkeepsafe.relinker.ReLinker;

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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        t.setText(JniUtil.callJava());
//        t.setText(JniUtil.getCpu());

    }
}
