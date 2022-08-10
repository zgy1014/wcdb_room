package com.wcdb.tool.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wcdb.tool.R
import com.wcdb.tool.constant.SimpleListener
import com.wcdb.tool.dao.AppDatabase
import com.wcdb.tool.dao.SubFlagModelDao
import com.wcdb.tool.model.FlagTable
import com.wcdb.tool.ui.SonFlagDetailActivity


class FlagAdapter(lists: List<FlagTable>) :
    BaseQuickAdapter<FlagTable, BaseViewHolder>(
        R.layout.item_flag,
        lists
    ) {

    fun setSubFlagModelDao(database: AppDatabase?) {
        this.subFlagModelDao = database!!.subFlagDao()
    }

    var subFlagModelDao: SubFlagModelDao? = null

    private var listener: SimpleListener<*>? = null

   // private var subFlagLists: MutableList<SmallTable> = ArrayList()

    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: SubFlagAdapter? = null

    fun setListener(listener: SimpleListener<*>?) {
        this.listener = listener
    }

    override fun convert(helper: BaseViewHolder?, item: FlagTable) {
        var iv_flag_item = helper?.getView<RelativeLayout>(R.id.iv_flag_item)

        var flagContent = helper?.getView<TextView>(R.id.flagContent)
        var ivSonFlag = helper?.getView<ImageView>(R.id.ivSonFlag)
        var flagDetail = helper?.getView<RelativeLayout>(R.id.flagDetail)
        var mRecyclerView = helper?.getView<RecyclerView>(R.id.mRecyclerView)
        iv_flag_item!!.visibility = View.VISIBLE

        if(item.isComplete){
            iv_flag_item!!.visibility = View.GONE
        }else{
            iv_flag_item!!.visibility = View.VISIBLE
        }
        if(subFlagModelDao !=null){
            //提前完成的不需要展示在flag列表
             var subFlagLists = subFlagModelDao!!.getAllByFlagId(item.flagId)
            linearLayoutManager = LinearLayoutManager(mContext)
            mRecyclerView?.layoutManager = linearLayoutManager
            adapter = SubFlagAdapter(subFlagLists)
            adapter!!.setListener(object : SimpleListener<Int>() {
                override fun onClick(index: Int) {
                    val intent = Intent(mContext, SonFlagDetailActivity::class.java)
                    intent.putExtra("subId", subFlagLists[index].subId)
                    mContext.startActivity(intent)
                }

            })
            mRecyclerView?.adapter = adapter

        }
        flagContent?.text = item.title
        ivSonFlag!!.setOnClickListener {
            if (listener !=null){
                listener!!.onClick(item.flagId)
            }
        }
        flagDetail!!.setOnClickListener {
            if (listener !=null){
                listener!!.onClick(item.flagId,"detail")
            }
        }




    }
}