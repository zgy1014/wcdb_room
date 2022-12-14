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
import com.wcdb.tool.event.BusProvider;
import com.wcdb.tool.model.ModelInfo;
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
        tvTitle.setText("???????????????");
        if(!TextUtils.isEmpty(subIds) && subFlagModelDao !=null){
            tvTitle.setText("???????????????");
            subFlagModel = subFlagModelDao.getBySubId(subId);
            if(subFlagModel !=null){
                ed_flag_titile.setText(subFlagModel.title);
                ed_flag_titile.setSelection(subFlagModel.title.length());
                if(TextUtils.isEmpty(subFlagModel.repeatDay)){
                    tvWeek.setText("?????????");
                    tvWeek.setTextColor(Color.parseColor("#3097FF"));
                    releateFlagEndDate.setVisibility(View.GONE);
                }else {
                    releateFlagEndDate.setVisibility(View.VISIBLE);
                    String[] weeks = subFlagModel.repeatDay.split(" ");
                    tvWeek.setTextColor(Color.parseColor("#333333"));
                    if(weeks.length != 7){
                        tvWeek.setText(subFlagModel.repeatDay);
                    }else {
                        tvWeek.setText("??????");
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
     * ?????????????????????
     */
    private void initSubFlagData() {
        subFlagModel = new SmallTable();
        cal = Calendar.getInstance();
        today = cal.getTime();
        cal.add(Calendar.DATE, 29);
        Date endDays = cal.getTime();
        subFlagModel.startTime = formatterDay.format(today);
        subFlagModel.endTime = formatterDay.format(endDays);
        subFlagModel.repeatDay = "?????? ?????? ?????? ?????? ??????";
        mContext.getWindow().setStatusBarColor(mContext.getResources().getColor(R.color.color_white));
        subFlagModel.repeatTime = "19:00-20:00";
        ed_flag_titile.setText("");
        tvFlagStartDate.setText(subFlagModel.startTime);
        tvFlagEndDate.setText(subFlagModel.endTime);
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

        tvRepeatDate.setText("??????&??????");

        InputFilter[] filter = {new ChineseInputFilter(28)};
        ed_flag_titile.setFilters(filter);
        ed_flag_titile.setHint(" ??????????????????????????????");
        ed_flag_titile.requestFocus();
        ed_flag_titile.setFocusable(true);   //????????????????????????
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
                    ed_flag_titile.setHint(" ??????????????????????????????");
                }else{
                    ed_flag_titile.setHintTextColor(mContext.getResources().getColor(R.color.color_CCCCCC));
                    ed_flag_titile.setHint("??????????????????????????????");
                }
            }
        });

        //????????????????????????????????????
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
                            //???????????????
                            // changeKeyboardHeight(heightDifference);
                        } else {
                            //???????????????
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
            }else { //??????
                insertDayDBTable("");
            }
        }

        //??????????????????????????????????????????????????????Flag????????????
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
                int random = (int)(Math.random()*100);
                subIds = Long.toString(System.currentTimeMillis()) +random +"";
                subFlagModel.subId = subIds;
                subFlagModelDao.insert(subFlagModel);
            }else {
                subFlagModelDao.update(subFlagModel);
            }

            ModelInfo modelInfo = new ModelInfo();
            modelInfo.updateFlag = true;
            modelInfo.subId = subIds;
            BusProvider.getBus().post(modelInfo);
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
