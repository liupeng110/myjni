package com.andlp.myjni.activity;

import android.app.Activity;
import android.os.Bundle;

import org.xutils.x;

/**
 * 717219917@qq.com  2017/3/10 11:14
 */
public class Activity_Base extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

}
