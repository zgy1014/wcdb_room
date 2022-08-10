package com.wcdb.tool.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wcdb.tool.R;
import com.wcdb.tool.constant.SimpleListener;
import com.wcdb.tool.util.KnifeKit;

import butterknife.ButterKnife;


public abstract class DialogHelper {

    private FullTopDialog dialog;
    public Activity mContext;
    private View rootView;
    protected SimpleListener listener;

    public DialogHelper(Activity context, int style) {
        if (getLayoutId() != 0) {
            rootView = View.inflate(context, getLayoutId(), null);
        } else {
            try {
                throw new Exception("layout is empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mContext = context;
        dialog = new FullTopDialog(mContext, style);
        dialog.setContentView(rootView);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        KnifeKit.bind(this, rootView);
        initView();
        initData();
    }

    //设置点击屏幕Dialog不消失false
    public void setCanceledOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    //设置dialog点击系统返回键，不消失dialog
    public void setOnKeyBackListener(boolean flag) {
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode == event.KEYCODE_BACK) {
                    if(flag) {
                        // dialog.dismiss()
                        return  true;
                    }else {
                        dialogDismiss();
                        return false;
                    }
                }
                return false;

            }
        });

    }

    public void setBackgroundDrawable() {
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawableResource(R.color.color_300000);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(lp);
    }

    public void setFullWidthScreen() {
        Display display = mContext.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth());
        dialog.getWindow().setAttributes(lp);
    }

    public void setFullWidthScreen_80() {
        Display display = mContext.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth() * 0.8);
        dialog.getWindow().setAttributes(lp);
    }


    public void setFullHightScreen() {
        Display display = mContext.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.height = (display.getHeight());
        dialog.getWindow().setAttributes(lp);
    }

    public void setSoftInputMode(int softInputMode) {
        dialog.getWindow().setSoftInputMode(softInputMode);
    }

    public void setGravity(int gravity) {
        dialog.getWindow().setGravity(gravity);
    }

    public void setAnimSet(int animId) {
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(animId);
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dismissListener != null) {
            dismissListener.dismiss();
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public Dialog getDialog() {
        return dialog;
    }

    public DismissListener dismissListener;

    public DialogHelper setOnDismissListener(DismissListener listener) {
        this.dismissListener = listener;
        return this;
    }

    public interface DismissListener {
        void dismiss();
    }

    public void setSimpleListener(SimpleListener listener) {
        this.listener = listener;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void dialogDismiss();

    protected <T extends View> T viewById(int viewId) {
        return (T) rootView.findViewById(viewId);
    }
}
