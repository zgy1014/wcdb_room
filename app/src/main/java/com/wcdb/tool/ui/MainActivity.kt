package com.wcdb.tool.ui

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tencent.wcdb.database.SQLiteCipherSpec
import com.tencent.wcdb.room.db.WCDBOpenHelperFactory
import com.wcdb.tool.R
import com.wcdb.tool.constant.StringConstant
import com.wcdb.tool.dao.AppDatabase
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
        //初始化wcdb数据库
        initRoom()
    }


    var dayDBDao: DayDBDao? = null
    var subFlagModelDao: SubFlagModelDao? = null
//    private val formatterDay = SimpleDateFormat("yyyy-MM-dd")
private var database: AppDatabase? = null

    private fun initRoom() {

        val cipherSpec = SQLiteCipherSpec()
            .setPageSize(4096)
            .setKDFIteration(64000)
        val factory =
            WCDBOpenHelperFactory() //  .passphrase("passphrase".getBytes())  // passphrase to the database, remove this line for plain-text
                .cipherSpec(cipherSpec) // cipher to use, remove for default settings
                // .writeAheadLoggingEnabled(false)       // enable WAL mode, remove if not needed
                .asyncCheckpointEnabled(true) // enable asynchronous checkpoint, remove if not needed
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, StringConstant.DbBase
        )
            .allowMainThreadQueries()
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .openHelperFactory(factory) // specify WCDBOpenHelperFactory when opening database
            .build()
        dayDBDao = database!!.dayDBDao()
        subFlagModelDao = database!!.subFlagDao()


    }



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
