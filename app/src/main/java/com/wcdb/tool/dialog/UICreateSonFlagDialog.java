package com.wcdb.tool.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wcdb.tool.R;
import com.wcdb.tool.constant.ChineseInputFilter;
import com.wcdb.tool.dao.AppDatabase;
import com.wcdb.tool.dao.SubFlagModelDao;
import com.wcdb.tool.model.SmallTable;
import com.wcdb.tool.util.AppUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import butterknife.BindView;


public class UICreateSonFlagDialog extends DialogHelper {

    @BindView(R.id.ed_flag_titile)
    EditText ed_flag_titile;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvBack)
    TextView tvBack;

    @BindView(R.id.tvSave)
    TextView tvSave;

    @BindView(R.id.tvRepeatDate)
    TextView tvRepeatDate;

    @BindView(R.id.tvFlagStartDate)
    TextView tvFlagStartDate;

    @BindView(R.id.tvFlagEndDate)
    TextView tvFlagEndDate;

    @BindView(R.id.tvWeek)
    TextView tvWeek;

    @BindView(R.id.tvWeekDate)
    TextView tvWeekDate;

    @BindView(R.id.releateFlagEndDate)
    LinearLayout releateFlagEndDate;

    @BindView(R.id.linearEditText)
    LinearLayout linearEditText;

    @BindView(R.id.mLineaCreateFlag)
    LinearLayout mLineaCreateFlag;

    private String flagId;

    private String subId;

    private SubFlagModelDao subFlagModelDao ;

    public UICreateSonFlagDialog(Activity context, String flagId, AppDatabase database) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        this.flagId = flagId;
        this.subFlagModelDao = database.subFlagDao();
        setFullWidthScreen();
        setGravity(Gravity.BOTTOM);
        setAnimSet(R.style.popBottomPopAnim);
        initSubFlagData();

    }


    public void updateFlag(String subIds,String flagIds) {
        subId = subIds;
        flagId = flagIds;
        ed_flag_titile.requestFocus();
        ed_flag_titile.setFocusable(true);
        tvTitle.setText("创建子目标");
        if(!TextUtils.isEmpty(subIds) && subFlagModelDao !=null){
            tvTitle.setText("编辑子目标");
            subFlagModel = subFlagModelDao.getBySubId(subId);
            if(subFlagModel !=null){
                ed_flag_titile.setText(subFlagModel.title);
                ed_flag_titile.setSelection(subFlagModel.title.length());
                if(TextUtils.isEmpty(subFlagModel.repeatDay)){
                    tvWeek.setText("不重复");
                    tvWeek.setTextColor(Color.parseColor("#3097FF"));
                    releateFlagEndDate.setVisibility(View.GONE);
                }else {
                    releateFlagEndDate.setVisibility(View.VISIBLE);
                    String[] weeks = subFlagModel.repeatDay.split(" ");
                    tvWeek.setTextColor(Color.parseColor("#333333"));
                    if(weeks.length != 7){
                        tvWeek.setText(subFlagModel.repeatDay);
                    }else {
                        tvWeek.setText("每天");
                    }
                }
                tvWeekDate.setText(subFlagModel.repeatTime);

            }
        }
    }

    private SimpleDateFormat formatterDay = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal;
    Date today;

    @Override
    protected void initData() {

    }

    /**
     * 初始化界面信息
     */
    private void initSubFlagData() {
        subFlagModel = new SmallTable();
        cal = Calendar.getInstance();
        today = cal.getTime();
        cal.add(Calendar.DATE, 29);
        Date endDays = cal.getTime();
        subFlagModel.startTime = formatterDay.format(today);
        subFlagModel.endTime = formatterDay.format(endDays);
        subFlagModel.repeatDay = "周一 周二 周三 周四 周五";
        mContext.getWindow().setStatusBarColor(mContext.getResources().getColor(R.color.color_white));
        subFlagModel.repeatTime = "19:00-20:00";
        ed_flag_titile.setText("");
        tvFlagStartDate.setText("今天");
        tvFlagEndDate.setText("明天");
        tvWeekDate.setText(subFlagModel.repeatTime);
        tvWeek.setText(subFlagModel.repeatDay);
        tvWeek.setTextColor(Color.parseColor("#333333"));
        releateFlagEndDate.setVisibility(View.VISIBLE);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_ui_create_son_flag;
    }


    @Override
    protected void initView() {

        tvRepeatDate.setText("重复&时间");

        InputFilter[] filter = {new ChineseInputFilter(28)};
        ed_flag_titile.setFilters(filter);
        ed_flag_titile.setHint(" 打算立个什么子目标？");
        ed_flag_titile.requestFocus();
        ed_flag_titile.setFocusable(true);   //设置可以获取焦点
        ed_flag_titile.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        ed_flag_titile.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI|EditorInfo.IME_ACTION_DONE);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCreateSonFlag();
            }
        });

        ed_flag_titile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    ed_flag_titile.setHintTextColor(mContext.getResources().getColor(R.color.color_3097FF));
                    ed_flag_titile.setHint(" 打算立个什么子目标？");
                }else{
                    ed_flag_titile.setHintTextColor(mContext.getResources().getColor(R.color.color_CCCCCC));
                    ed_flag_titile.setHint("打算立个什么子目标？");
                }
            }
        });

        //监听软键盘是否显示或隐藏
        linearEditText.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        linearEditText.getWindowVisibleDisplayFrame(r);
                        int screenHeight = linearEditText.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);
                        if (heightDifference > 200) {
                            if(!flag){
                                ed_flag_titile.requestFocus();
                                flag = true;
                            }
                            //软键盘显示
                            // changeKeyboardHeight(heightDifference);
                        } else {
                            //软键盘隐藏
                            ed_flag_titile.clearFocus();

                        }
                    }

                });

    }

    private boolean flag = false;

    private SmallTable subFlagModel = new SmallTable();

    private String eventId = "";

    private void saveCreateSonFlag() {
        String title = ed_flag_titile.getText().toString();

        boolean flagEdit = AppUtil.isKeyboardShown(mLineaCreateFlag);
        if(TextUtils.isEmpty(title)){
            AppUtil.setVibrator(mContext);
            ed_flag_titile.requestFocus();
            if(!flagEdit){
                AppUtil.showSoftInput(mContext);
            }

            return;
        }
        subFlagModel.title = title;
        subFlagModel.fId = flagId;
        subFlagModel.completeDay = "";

        if(subFlagModelDao !=null){
            if(!TextUtils.isEmpty(subId)){
                insertDayDBTable(subId);
            }else { //新增
                insertDayDBTable("");
            }
        }

        //提前完成的时候会有完成子目标或者完成Flag的弹出框
        if (listener != null) {
            listener.onClick(subFlagModel);
        }

        dismiss();

    }

    /**
     *
     */
    private  void insertDayDBTable(String subIds){
        if(subFlagModelDao !=null){
            if(TextUtils.isEmpty(subIds)){
                subFlagModel.subId = eventId;
                subFlagModelDao.insert(subFlagModel);
            }else {
                subFlagModel.subId = eventId;
                subFlagModelDao.update(subFlagModel);
            }
        }
    }


    @Override
    protected void dialogDismiss() {
        dismiss();
    }

    @Override
    public void dismiss() {
        initSubFlagData();
        super.dismiss();

    }


}
