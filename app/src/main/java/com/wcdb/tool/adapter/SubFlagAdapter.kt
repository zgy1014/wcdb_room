package com.wcdb.tool.adapter

import android.annotation.SuppressLint
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wcdb.tool.R
import com.wcdb.tool.constant.SimpleListener
import com.wcdb.tool.model.SmallTable


class SubFlagAdapter(lists: List<SmallTable>, color:Int, type:Int) :
    BaseQuickAdapter<SmallTable, BaseViewHolder>(
        R.layout.item_sub_flag,
        lists
    ) {

    private var colors :Int = color

    //0从flag界面，1从flag详情，2从成就界面
    private var status :Int = type


    private var listener: SimpleListener<*>? = null


    fun setListener(listener: SimpleListener<*>?) {
        this.listener = listener
    }

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder?, item: SmallTable) {
        var mItemSubFlag = helper?.getView<LinearLayout>(R.id.mItemSubFlag)
        var flagSonContent = helper?.getView<TextView>(R.id.flagSonContent)

        var flagProgress = helper?.getView<TextView>(R.id.flagProgress)
        var sub_detail = helper?.getView<LinearLayout>(R.id.sub_detail)

        flagSonContent!!.setTextColor(mContext.resources.getColor(R.color.color_333333))
        flagSonContent?.text = item.title
        var position = helper!!.adapterPosition


    }
}