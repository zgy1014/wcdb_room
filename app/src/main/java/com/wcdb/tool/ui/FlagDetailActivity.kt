package com.wcdb.tool.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.rxbus.RxBus
import com.wcdb.tool.R
import com.wcdb.tool.adapter.SubFlagAdapter
import com.wcdb.tool.constant.SimpleListener
import com.wcdb.tool.dao.AppDataUtils
import com.wcdb.tool.dao.AppDatabase
import com.wcdb.tool.dao.FlagInfoDao
import com.wcdb.tool.dao.SubFlagModelDao
import com.wcdb.tool.dialog.UICreateFlagDialog
import com.wcdb.tool.dialog.UICreateSonFlagDialog
import com.wcdb.tool.event.BusProvider
import com.wcdb.tool.model.FlagTable
import com.wcdb.tool.model.ModelInfo
import com.wcdb.tool.model.SmallTable
import com.wcdb.tool.util.AppUtil
import com.wcdb.tool.util.ToastUtils
import com.wcdb.tool.util.ViewClickUtils
import kotlinx.android.synthetic.main.activity_flag_detail.*

class FlagDetailActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBlack()
        val view =  LayoutInflater.from(this).inflate(R.layout.activity_flag_detail, null)
        setContentView(view)
        database = AppDataUtils.initRooms(this)
        flagId = intent.getStringExtra("flagId")

        ViewClickUtils.setViewClick(this, tvBack)
        ViewClickUtils.setViewClick(this, tvDeltele)
        ViewClickUtils.setViewClick(this, ivSonFlag)
        ViewClickUtils.setViewClick(this, btn_create_son_flag)
        ViewClickUtils.setViewClick(this, btn_edit_flag)
        showSonFlagList()

        BusProvider.getBus().subscribe(context,
            RxBus.Callback<ModelInfo> { modelInfo ->

                if (modelInfo.updateFlag) {
                    showSonFlagList()
                }

            })

    }


    private var database: AppDatabase? = null
    private  var subFlagDao : SubFlagModelDao? =null
    private  var flagDao : FlagInfoDao? =null

    private  var flagInfo : FlagTable? =null
    private var flagId: String? = null
    private var subFlagLists: MutableList<SmallTable> = ArrayList()

    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: SubFlagAdapter? = null


    private fun showSonFlagList() {

        if(database !=null){
            flagDao = database!!.flagInfoDao()
            subFlagDao = database!!.subFlagDao()
            flagInfo = flagDao!!.getById(flagId)

            subFlagLists.clear()
            subFlagLists = subFlagDao!!.getAllByFlagId(flagId)
            tvFlagTitile.text = flagInfo!!.title
            tvFlagContent.text = flagInfo!!.subTitle

            if(subFlagLists.size !=0){
                if(flagInfo!!.isComplete){
                    btn_edit_flag.visibility = View.GONE
                    ivSonFlag.visibility = View.GONE
                }else{
                    ivSonFlag.visibility = View.VISIBLE
                    btn_edit_flag.visibility = View.VISIBLE
                }
                tvNosan.visibility = View.GONE
                btn_create_son_flag.visibility = View.GONE
                linearLayoutManager = LinearLayoutManager(context)
                mRecyclerView.layoutManager = linearLayoutManager
                adapter = SubFlagAdapter(subFlagLists)
                adapter!!.setListener(object : SimpleListener<Int>() {
                    override fun onClick(index: Int) {
                        val intent = Intent(context, SonFlagDetailActivity::class.java)
                        intent.putExtra("subId", subFlagLists[index].subId)
                        startActivity(intent)
                    }

                })
                mRecyclerView.adapter = adapter
            }else{
                ivSonFlag.visibility = View.GONE
                tvNosan.visibility = View.VISIBLE
                btn_create_son_flag.visibility = View.VISIBLE
            }
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvBack -> {
                finish()
            }
            R.id.tvDeltele -> {
                deleteFlag()
            }
            R.id.ivSonFlag -> {
                initCreateSonFalgView()
            }
            R.id.btn_create_son_flag -> {
                initCreateSonFalgView()
            }
            R.id.btn_edit_flag -> {
                initCreateFalgView()
            }

        }
    }

    private var  createSonFlagDialog : UICreateSonFlagDialog? =null
    private var  createFlagDialog : UICreateFlagDialog? =null
    private val mHandler = Handler()



    /**
     * 创建Flag
     */
    private fun initCreateFalgView() {

            createFlagDialog = UICreateFlagDialog(context, database)
            createFlagDialog!!.setSimpleListener(object : SimpleListener<Int>() {
                override fun onClick(type: String) {

                }
            })

        if(!TextUtils.isEmpty(flagId)){
            createFlagDialog!!.updateFlag(flagId)
        }
        createFlagDialog!!.setOnKeyBackListener(false)

        createFlagDialog!!.show()

        mHandler.postDelayed({
            AppUtil.showSoftInput(context)
        }, 500)

    }

    /**
     * 创建子目标
     */
    private fun initCreateSonFalgView() {
            createSonFlagDialog = UICreateSonFlagDialog(context, flagId, database)
            createSonFlagDialog!!.setSimpleListener(object : SimpleListener<Int>() {
                override fun onClick(type: String) {

                }
            })
        createSonFlagDialog!!.setOnKeyBackListener(false)

        createSonFlagDialog!!.show()

        mHandler.postDelayed(Runnable {
            AppUtil.showSoftInput(context)
        }, 500)

    }


    private fun deleteFlag() {
        if(flagDao !=null){
             flagDao!!.delete(flagInfo)
             subFlagDao!!.deleteAllByFId(flagInfo!!.flagId)
            ToastUtils.showMessage(context, "删除成功")
            var modelInfo = ModelInfo()
            modelInfo.updateFlag = true
            BusProvider.getBus().post(modelInfo)

            finish()
        }

    }


}
