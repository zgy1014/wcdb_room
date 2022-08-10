package com.wcdb.tool.util


import android.view.View

class ViewClickUtils {

    companion object {//用companion object包裹方法，实现java中static的效果
        fun setViewClick(listener: View.OnClickListener, vararg views: View) {
            for (it in views) {
                it.setOnClickListener(listener)
            }
        }





    }




}