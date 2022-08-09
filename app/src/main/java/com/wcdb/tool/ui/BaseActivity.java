package com.wcdb.tool.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;


/**
 *
 */
public class BaseActivity extends Activity  {


    protected Activity context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

    }




    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void finish() {
        super.finish();
    }



}
