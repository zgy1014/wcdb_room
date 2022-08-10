package com.wcdb.tool.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wcdb.tool.R
import com.wcdb.tool.adapter.FlagAdapter
import com.wcdb.tool.constant.SimpleListener
import com.wcdb.tool.dao.AppDataUtils
import com.wcdb.tool.dao.AppDatabase
import com.wcdb.tool.dialog.UICreateFlagDialog
import com.wcdb.tool.dialog.UICreateSonFlagDialog
import com.wcdb.tool.model.FlagTable
import com.wcdb.tool.util.AppUtil
import com.wcdb.tool.util.ViewClickUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view =  LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        setContentView(view)
        database = AppDataUtils.initRooms(this)
        showTopData()
    }


    //显示顶部数据资源
    private fun showTopData() {
        ViewClickUtils.setViewClick(this, iv_create_flag)
        initDataShow()
    }


    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: FlagAdapter? = null
    private var flagInfoLists: MutableList<FlagTable> = ArrayList()
    private var database: AppDatabase? = null

    private fun  initDataShow(){

        flagInfoLists.clear()
        if(database !=null){
            var infoDao = database!!.flagInfoDao()
            flagInfoLists = infoDao!!.getAll(false)
            if(flagInfoLists.size !=0){
                adapter = FlagAdapter(flagInfoLists)

                linearLayoutManager = LinearLayoutManager(context)
                mRecyclerView.layoutManager = linearLayoutManager
                adapter!!.setSubFlagModelDao(database)

                mRecyclerView.adapter = adapter

                adapter!!.setListener(object : SimpleListener<String>() {
                    override fun onClick(flagId: String) {
                        initCreateSonFalgView(flagId)
                    }

                    override fun onClick(flagId: String, str: String) {
                        val intent = Intent(context, FlagDetailActivity::class.java)
                        intent.putExtra("flagId", flagId)
                        startActivity(intent)
                    }
                })

            }

        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_create_flag -> {
                initCreateFalgView()
            }
        }
    }


    private var  createSonFlagDialog : UICreateSonFlagDialog? =null

    /**
     * 创建子目标
     */
    private fun initCreateSonFalgView(flagId: String) {
        createSonFlagDialog = UICreateSonFlagDialog(context, flagId, database)
        createSonFlagDialog!!.setSimpleListener(object : SimpleListener<Int>() {
            override fun onClick(type: String) {

            }
        })
        createSonFlagDialog!!.updateFlag(null, flagId)
        createSonFlagDialog!!.show()

        mHandler.postDelayed(Runnable {
            AppUtil.showSoftInput(context)
        }, 500)


    }

    private var  createFlagDialog : UICreateFlagDialog? =null
    private val mHandler = Handler()

    /**
     * 创建Flag
     */
    private fun initCreateFalgView() {

//        if (createFlagDialog == null) {
        createFlagDialog = UICreateFlagDialog(context, database)
        createFlagDialog!!.setSimpleListener(object : SimpleListener<Int>() {
            override fun onClick(type: String) {
//                    if (type == "save") {
//                        initDataShow()
//                        createFlagDialog!!.dismiss()
//                    }
            }
        })
//        }
        createFlagDialog!!.setOnKeyBackListener(false)
        createFlagDialog!!.show()
        mHandler.postDelayed({
            AppUtil.showSoftInput(context)
        }, 500)

    }

}
