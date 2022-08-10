package com.wcdb.tool.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ToastUtils 防止吐司多次弹出
 */
public class ToastUtils {
    private static String oldMsg;
    private static Toast toast   = null;
    private static long oneTime=0;
    private static long twoTime=0;

    private static long   time;
    private static int resOldMsg;

//    public static void showMessage(Context context, String text) {
//        if(toast==null){
//            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//            toast.show();
//            oneTime= System.currentTimeMillis();
//        }else{
//            twoTime= System.currentTimeMillis();
//            if(text.equals(oldMsg)){
//                if(twoTime-oneTime> Toast.LENGTH_SHORT){
//                    toast.show();
//                }
//            }else{
//                oldMsg = text;
//                toast.setText(text);
//                toast.show();
//            }
//        }
//        oneTime=twoTime;
//    }


    public static void showMessage(Context context, String msg) {
        if (!msg.equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            time = System.currentTimeMillis();
            toast.setText(msg);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            // 显示内容一样时，只有间隔时间大于2秒时才显示
            if (System.currentTimeMillis() - time > 2000) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.setText(msg);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = msg;


    }

    public static void showMessageBottom(Context context, String msg) {
        if (!msg.equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            time = System.currentTimeMillis();
            toast.setText(msg);
            toast.show();
        } else {
            // 显示内容一样时，只有间隔时间大于2秒时才显示
            if (System.currentTimeMillis() - time > 2000) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.setText(msg);
                toast.show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = msg;


    }



}
