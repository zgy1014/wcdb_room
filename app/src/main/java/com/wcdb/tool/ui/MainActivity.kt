package com.wcdb.tool.ui

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.wcdb.tool.R
import com.wcdb.tool.dao.DayDBDao
import com.wcdb.tool.dao.SubFlagModelDao
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val view =  LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        setContentView(view)
    }


    var dayDBDao: DayDBDao? = null
    var subFlagModelDao: SubFlagModelDao? = null
//    private val formatterDay = SimpleDateFormat("yyyy-MM-dd")



    private val formatterDay = SimpleDateFormat("yyyy-MM-dd")


    override fun onActivityResult(
            requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (supportFragmentManager.fragments != null && supportFragmentManager.fragments.isNotEmpty()) {
//            val fragments: List<Fragment> = supportFragmentManager.fragments
//            for (i in fragments.indices) {
//                if (i == 2) {
//                    fragments[i].onActivityResult(requestCode, resultCode, data)
//                }
//            }
//        }
    }



    override fun onDestroy() {
//        stopService(intentIm)
        super.onDestroy()
    }




}
