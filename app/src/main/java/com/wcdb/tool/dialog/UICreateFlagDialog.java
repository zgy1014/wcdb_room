package com.wcdb.tool.dialog;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;
import com.wcdb.tool.R;
import com.wcdb.tool.constant.ChineseInputFilter;
import com.wcdb.tool.dao.AppDatabase;
import com.wcdb.tool.dao.FlagInfoDao;
import com.wcdb.tool.model.FlagTable;
import com.wcdb.tool.util.AppUtil;
import java.util.List;
import butterknife.BindView;


public class UICreateFlagDialog extends DialogHelper {

    @BindView(R.id.ed_flag_titile)
    AppCompatEditText ed_flag_titile;

    @BindView(R.id.tvBack)
    TextView tvBack;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvSave)
    TextView tvSave;

    @BindView(R.id.ed_flag_content)
    AppCompatEditText ed_flag_content;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.linearEditText)
    LinearLayout linearEditText;
    @BindView(R.id.mLineaCreateFlag)
    LinearLayout mLineaCreateFlag;


    List<Integer> colorList;

    private int selectPosition = 0;

    private FlagInfoDao infoDao ;

    private String flagId;

    private FlagTable flagInfo = new FlagTable();

    public UICreateFlagDialog(Activity context, AppDatabase database) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        this.infoDao = database.flagInfoDao();
        setFullWidthScreen();

        setGravity(Gravity.BOTTOM);
        setAnimSet(R.style.popBottomPopAnim);

    }

    public void updateFlag(String flagId) {
        this.flagId = flagId;
        if(!TextUtils.isEmpty(flagId)){
            tvTitle.setText("编辑Flag");
            initShowData();
        }
    }


    /**
     * 编辑获取的信息
     */
    public void initShowData() {
        flagInfo =  infoDao.getById(flagId);
        ed_flag_titile.setText(flagInfo.title);
        ed_flag_titile.requestFocus();
        ed_flag_titile.setFocusable(true);
        ed_flag_titile.setSelection(flagInfo.title.length());
        ed_flag_content.setText(flagInfo.subTitle);
        selectPosition = flagInfo.color;

    }


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_ui_create_flag;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initData() {
        InputFilter[] filter = {new ChineseInputFilter(28)};
        ed_flag_titile.setFilters(filter);
        ed_flag_titile.setHint(" 打算立个什么Flag？");
        ed_flag_titile.requestFocus();
        ed_flag_titile.setFocusable(true);   //设置可以获取焦点
        ed_flag_titile.setSingleLine();
        ed_flag_titile.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        ed_flag_titile.setImeOptions(EditorInfo.IME_ACTION_DONE);

        InputFilter[] filters = {new ChineseInputFilter(144)};
        ed_flag_content.setFilters(filters);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCreateFlag();
            }
        });


        ed_flag_titile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    ed_flag_titile.setHintTextColor(mContext.getResources().getColor(R.color.color_3097FF));
                    ed_flag_titile.setHint(" 打算立个什么Flag？");
                }else{
                    ed_flag_titile.setHintTextColor(mContext.getResources().getColor(R.color.color_CCCCCC));
                    ed_flag_titile.setHint("打算立个什么Flag？");
                }
            }
        });

        ed_flag_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    ed_flag_content.setHintTextColor(mContext.getResources().getColor(R.color.color_3097FF));
                    ed_flag_content.setHint(" 为什么要立这个Flag？");
                }else{
                    ed_flag_content.setHintTextColor(mContext.getResources().getColor(R.color.color_CCCCCC));
                    ed_flag_content.setHint("为什么要立这个Flag？");
                }
            }
        });

        ed_flag_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable.toString())){
                    ed_flag_content.setTypeface(null, Typeface.NORMAL);
                    ed_flag_content.invalidate();
                }else {
                    ed_flag_content.setTypeface(Typeface.create(ed_flag_content.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                    ed_flag_content.invalidate();
                    int lines = ed_flag_content.getLineCount();
//                    XLog.e("------"+lines);
                    if(lines > 5){
                        String string = editable.toString();
                        // 获取当前输入位置
                        int cursorStart = ed_flag_content.getSelectionStart();
                        int cursorEnd = ed_flag_content.getSelectionEnd();
                        if (cursorStart == cursorEnd && cursorStart < string.length() && cursorStart >= 1) {
                            // 输入位置在字符串中间，前后拼接
                            string = string.substring(0, cursorStart - 1) + string.substring(cursorStart);
                        } else {
                            // 输入位置在末尾则去掉最后一个字符串
                            string = string.substring(0, editable.length() - 1);
                        }
                        ed_flag_content.setText(string);
                        ed_flag_content.setSelection(ed_flag_content.getText().length());
                    }
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
                                boolean flagShow = AppUtil.isKeyboardShown(mLineaCreateFlag);
                                ed_flag_titile.requestFocus();
                                if(!flagShow){
                                    AppUtil.showSoftInput(mContext);
                                }
                                flag = true;
                            }
                            //软键盘显示
                            // changeKeyboardHeight(heightDifference);
                        } else {
                            //软键盘隐藏
                            ed_flag_content.clearFocus();
                            ed_flag_titile.clearFocus();

                        }
                    }

                });
    }

    private boolean flag = false;

    @Override
    protected void dialogDismiss() {
        dismiss();
    }

    @Override
    public void dismiss() {
        mContext.getWindow().setStatusBarColor(mContext.getResources().getColor(R.color.color_white));
        ed_flag_titile.setText("");
        ed_flag_content.setText("");
        ed_flag_titile.requestFocus();
        flag = false;
        flagId = "";
        super.dismiss();

    }


    private void saveCreateFlag() {
        String title = ed_flag_titile.getText().toString();
        String content = ed_flag_content.getText().toString();
        boolean flag = AppUtil.isKeyboardShown(mLineaCreateFlag);

        if(TextUtils.isEmpty(title)){
            AppUtil.setVibrator(mContext);
            ed_flag_titile.requestFocus();

            if(!flag){
              AppUtil.showSoftInput(mContext);
            }
            return;
        }
        if(TextUtils.isEmpty(content)){
            AppUtil.setVibrator(mContext);
            ed_flag_content.requestFocus();

            if(!flag){
                AppUtil.showSoftInput(mContext);
            }
            return;
        }

        flagInfo.subTitle = content;
        flagInfo.title = title;
        flagInfo.color = selectPosition;
        if(TextUtils.isEmpty(flagId)){
            int random = (int)(Math.random()*100);
           long createTime =  System.currentTimeMillis();
            String flagId = Long.toString(createTime) + '_' +random;
            flagInfo.flagId = flagId;
            flagInfo.createTime = new Long(createTime).intValue();
            infoDao.insert(flagInfo);
        }else {
            infoDao.update(flagInfo);
        }

        dismiss();
    }

}
