package com.wcdb.tool.ui

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.wcdb.tool.R
import com.wcdb.tool.constant.SimpleListener
import com.wcdb.tool.dao.AppDataUtils
import com.wcdb.tool.dao.AppDatabase
import com.wcdb.tool.dao.FlagInfoDao
import com.wcdb.tool.dao.SubFlagModelDao
import com.wcdb.tool.dialog.UICreateSonFlagDialog
import com.wcdb.tool.model.SmallTable
import com.wcdb.tool.util.AppUtil
import com.wcdb.tool.util.ToastUtils
import com.wcdb.tool.util.ViewClickUtils

import kotlinx.android.synthetic.main.activity_son_flag_detail.*
import java.text.SimpleDateFormat
import java.util.*

class SonFlagDetailActivity : BaseActivity(), View.OnClickListener {

    private var subId: String? = null
    private var flagId: String? = null
    private var status: String? = null

    private var database: AppDatabase? = null
    private  var subFlagDao : SubFlagModelDao? =null
    var flagModelDao: FlagInfoDao? = null

    private var subFlagModel: SmallTable? = null
    var cal: Calendar? = null

    private  var day : Long? =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view =  LayoutInflater.from(this).inflate(R.layout.activity_son_flag_detail, null)
        setContentView(view)

        subId = intent.getStringExtra("subId")
        status = intent.getStringExtra("status")

        database = AppDataUtils.initRoom(context)
        ViewClickUtils.setViewClick(this, tvBack)
        ViewClickUtils.setViewClick(this, tvDeltele)
        ViewClickUtils.setViewClick(this, btn_edit_flag)
        tvRepeatDate.text = "重复&时间"
        cal = Calendar.getInstance()
        cal!!.time =  Date()
        initDataShow(subId!!)
    }


    private fun  initDataShow(subIds: String){
        if(database !=null){
            flagModelDao = database!!.flagInfoDao()
            subFlagDao = database!!.subFlagDao()

            subFlagModel = subFlagDao!!.getBySubId(subIds)
            flagId = subFlagModel!!.fId
            tvFlagTitile.text = subFlagModel!!.title

            tvFlagStartDate.text = subFlagModel!!.startTime

            if(TextUtils.isEmpty(subFlagModel!!.endTime)){
                releateFlagEndDate.visibility = View.GONE
            }else{
                releateFlagEndDate.visibility = View.VISIBLE
                tvFlagEndDate.text =  subFlagModel!!.endTime
            }

            if(TextUtils.isEmpty(subFlagModel!!.repeatDay)){
                tvWeek.text = "不重复"
                tvWeek.setTextColor(context.resources.getColor(R.color.color_3097FF))
            }else{
                tvWeek.setTextColor(context.resources.getColor(R.color.color_333333))
                val weeks = subFlagModel!!.repeatDay.split(" ").toTypedArray()
                if (weeks.size != 7) {
                    tvWeek.text = subFlagModel!!.repeatDay
                } else {
                    tvWeek.text = "每天"
                }
            }

            tvWeekDate.text = subFlagModel!!.repeatTime

        }
    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvBack -> {
                if(updateStates){
                     showFlagDialog(subFlagModels)
                }
                finish()
            }
            R.id.tvDeltele -> {
                deleteFlag()
            }

            R.id.btn_edit_flag -> {
                initCreateSonFalgView(flagId!!)
            }

        }
    }

    private var  createSonFlagDialog : UICreateSonFlagDialog? =null

    private val mHandler = Handler()

    private var  updateStates : Boolean = false
    private var  subFlagModels : SmallTable = SmallTable()

    /**
     * 创建子目标
     */
    private fun initCreateSonFalgView(flagId: String) {

     //   if (createSonFlagDialog == null) {
            createSonFlagDialog = UICreateSonFlagDialog(context, flagId, database)
            createSonFlagDialog!!.setSimpleListener(object : SimpleListener<SmallTable>() {
                override fun onClick(subFlagModel: SmallTable) {
                    subFlagModels = subFlagModel
                    updateStates = false
                    if (subFlagModel.status == "提前完成") {
                        updateStates = true
                        createSonFlagDialog!!.dismiss()
                    }
                }
            })
   //     }
          createSonFlagDialog!!.setOnKeyBackListener(false)
        createSonFlagDialog!!.updateFlag(subFlagModel!!.subId, flagId)
        createSonFlagDialog!!.show()

        mHandler.postDelayed(Runnable {
            AppUtil.showSoftInput(context)
        }, 500)

    }

    override fun onBackPressed() {
        if(updateStates){
            showFlagDialog(subFlagModels)
        }
        finish()
    }


    private fun showFlagDialog(subFlagModel: SmallTable) {
        var flagLists =  subFlagDao!!.getAllByFlagId(subFlagModel.fId)

        var flagProgressList = subFlagDao!!.getAllByFlagIdAndProgress(subFlagModel.fId, 1.0f, "提前完成")
        var flagInfo =  flagModelDao!!.getById(subFlagModel.fId)

        if(flagLists.size == flagProgressList.size){
            flagInfo.isComplete =  true
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            var  completeToday = sdf.format(cal!!.time)
            flagInfo.completeDay = completeToday
            flagModelDao!!.update(flagInfo)
        }else{
            flagInfo.isComplete =  false
            flagInfo.completeDay = ""
            flagModelDao!!.update(flagInfo)

        }

    }





    private fun deleteFlag() {
        if(subFlagDao !=null){
            subFlagDao!!.delete(subFlagModel)
            ToastUtils.showMessage(context, "删除成功")
            finish()
        }
    }


}
