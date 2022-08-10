package com.wcdb.tool.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wcdb.tool.R
import com.wcdb.tool.constant.SimpleListener
import com.wcdb.tool.model.SmallTable


class SubFlagAdapter(lists: List<SmallTable>) :
    BaseQuickAdapter<SmallTable, BaseViewHolder>(
        R.layout.item_sub_flag,
        lists
    ) {

    private var listener: SimpleListener<*>? = null

    fun setListener(listener: SimpleListener<*>?) {
        this.listener = listener
    }

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder?, item: SmallTable) {
        var flagend = helper?.getView<TextView>(R.id.flagend)
        var flagSonContent = helper?.getView<TextView>(R.id.flagSonContent)

        var flagstart = helper?.getView<TextView>(R.id.flagstart)
        var sub_detail = helper?.getView<LinearLayout>(R.id.sub_detail)

        flagSonContent!!.setTextColor(mContext.resources.getColor(R.color.color_333333))
        flagSonContent!!.text = item.title
        flagstart!!.text = item.startTime
        flagend!!.text = item.endTime

        var position = helper!!.adapterPosition
        sub_detail!!.setOnClickListener {
            if(listener !=null){
                listener!!.onClick(position)
            }
        }

    }
}