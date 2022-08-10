package com.wcdb.tool.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.wcdb.tool.R;
import com.wcdb.tool.util.StatusBarUtil;

/**
 *
 */
public class BaseActivity extends Activity  {

    protected Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStatusColor(true, 1);
//        setStatusBlack();
        context = this;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }



    protected void setStatusBlack() {
      //  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.StatusBarLightMode(this);
    }

    protected void setStatusBlack(boolean flag) {
        if(flag){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |View.SYSTEM_UI_FLAG_VISIBLE);
        }
        StatusBarUtil.StatusBarLightMode(this,flag);
    }


    //需要弹出Dialog的时候用这个，底部是透明色
    protected void setStatusColor(boolean status) {
        if (status) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //如果sdk版本大于21
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    protected void setStatusColor(boolean status, int color) {
        if (status) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //如果sdk版本大于21
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                if (color == 1) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.color_white));
                } else if (color == 2) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.color_300000));
                }else if(color == 3){
                    getWindow().setStatusBarColor(getResources().getColor(R.color.color_e3e3e3));
                }else if(color == 4){
                    getWindow().setStatusBarColor(getResources().getColor(R.color.pop_banner_ground));
                }
                else if(color == 5){
                    getWindow().setStatusBarColor(getResources().getColor(R.color.color_ECECEC));
                }

            }
        }
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
