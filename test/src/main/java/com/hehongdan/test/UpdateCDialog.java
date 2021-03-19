package com.hehongdan.test;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.azhon.appupdate.service.DownloadService;
import com.azhon.appupdate.utils.ApkUtil;
import com.azhon.appupdate.utils.Constant;
import com.azhon.appupdate.utils.DensityUtil;
import com.azhon.appupdate.utils.ScreenUtil;

import java.io.File;

/**
 * 项目名:    AppUpdate
 * 包名       com.azhon.appupdate.dialog
 * 文件名:    UpdateDialog
 * 创建时间:  2018/1/30 on 15:13
 * 描述:     显示升级对话框
 *
 * @author 阿钟
 */


public class UpdateCDialog extends Dialog implements View.OnClickListener {


    public static class Builder {
        private OnButtonClickListener buttonClickListener;
        private OnDownloadListener downloadListener;

        private String apkVersionName = "";
        private String apkSize = "";
        private String apkDescription = "";


        private ImageView ivBg ;
        private TextView title ;
        private TextView size;
        private TextView description;

        private int dialogImage, dialogButtonTextColor, dialogButtonColor, dialogProgressBarColor;

        private Activity activity;
        private boolean forcedUpgrade;
        private Button update;
        private TextRoundCornerProgressBar progressBar;
        private File apk;
        private final int install = 0x45F;


        /**
         * 是否是post请求，默认是get
         *
         * @param activity 当前提示的Activity
         * @return Builder
         */
        public Builder setActivity(Activity activity) {
            activity = activity;
            return this;
        }

        public Builder getApkDescription(String apkDescription) {
            this.apkDescription = apkDescription;
            if (description != null){
                description.setText(apkDescription);
            }
            return this;
        }

        public Builder getApkSize(String apkSize) {
            this.apkSize = apkSize;
            return this;
        }

        public Builder getApkVersionName(String apkVersionName) {
            this.apkVersionName = apkVersionName;
            return this;
        }

        public Builder isForcedUpgrade(boolean forcedUpgrade) {
            this.forcedUpgrade = forcedUpgrade;
            return this;
        }
        public Builder getDialogImage(int dialogImage) {
            this.dialogImage = dialogImage;
            return this;
        }

        public Builder getDialogButtonColor(int dialogButtonColor) {
            this.dialogButtonColor = dialogButtonColor;
            return this;
        }
        public Builder getDialogProgressBarColor(int dialogProgressBarColor) {
            this.dialogProgressBarColor = dialogProgressBarColor;
            return this;
        }
        public Builder getDialogButtonTextColor(int dialogButtonTextColor) {
            this.dialogButtonTextColor = dialogButtonTextColor;
            return this;
        }

        public Builder getOnButtonClickListener(OnButtonClickListener buttonClickListener) {
            this.buttonClickListener = buttonClickListener;
            return this;
        }

        public Builder setOnDownloadListener(OnDownloadListener downloadListener) {
            this.downloadListener = downloadListener;
            return this;
        }


        /**
         * @return 生成app管理器
         */
        public UpdateCDialog build() {
            //校验
            if (activity == null){
                throw new NullPointerException("必要参数不能为空");
            }

            return new UpdateCDialog(this);
        }
    }


    private OnButtonClickListener buttonClickListener;
    private OnDownloadListener downloadListener;

    private String apkVersionName = "";
    private String apkSize = "";
    private String apkDescription = "";


    private ImageView ivBg ;
    private TextView title ;
    private TextView size;
    private TextView description;


    private Context context;
    private boolean forcedUpgrade;
    private Button update;
    private TextRoundCornerProgressBar progressBar;
    private int dialogImage, dialogButtonTextColor, dialogButtonColor, dialogProgressBarColor;
    private File apk;
    private final int install = 0x45F;




    public UpdateCDialog(@NonNull Builder builder) {
        super(builder.activity, R.style.UpdateDialog);
        init(context);
    }







    public UpdateCDialog getApkDescription(String apkDescription) {
        this.apkDescription = apkDescription;
        if (description != null){
            description.setText(apkDescription);
        }
        return this;
    }

    public UpdateCDialog getApkSize(String apkSize) {
        this.apkSize = apkSize;
        return this;
    }

    public UpdateCDialog getApkVersionName(String apkVersionName) {
        this.apkVersionName = apkVersionName;
        return this;
    }

    public UpdateCDialog isForcedUpgrade(boolean forcedUpgrade) {
        this.forcedUpgrade = forcedUpgrade;
        return this;
    }
    public UpdateCDialog getDialogImage(int dialogImage) {
        this.dialogImage = dialogImage;
        return this;
    }

    public UpdateCDialog getDialogButtonColor(int dialogButtonColor) {
        this.dialogButtonColor = dialogButtonColor;
        return this;
    }
    public UpdateCDialog getDialogProgressBarColor(int dialogProgressBarColor) {
        this.dialogProgressBarColor = dialogProgressBarColor;
        return this;
    }
    public UpdateCDialog getDialogButtonTextColor(int dialogButtonTextColor) {
        this.dialogButtonTextColor = dialogButtonTextColor;
        return this;
    }

    public UpdateCDialog getOnButtonClickListener(OnButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
        return this;
    }

    public UpdateCDialog setOnDownloadListener(OnDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
        return this;
    }
    /**
     * 初始化布局
     */
    private void init(Context context) {
        this.context = context;

//        forcedUpgrade = configuration.isForcedUpgrade();
//        dialogImage = configuration.getDialogImage();
//        dialogButtonColor = configuration.getDialogButtonColor();
//        dialogProgressBarColor = configuration.getDialogProgressBarColor();
//        dialogButtonTextColor = configuration.getDialogButtonTextColor();
//        buttonClickListener = configuration.getOnButtonClickListener();
//        configuration.setOnDownloadListener(this);



        View view = LayoutInflater.from(context).inflate(R.layout.dialog_c_update, null);
        setContentView(view);
        setWindowSize(context);
        initView(view);
    }

    private void initView(View view) {
        View ibClose = view.findViewById(R.id.ib_close);
        ivBg = view.findViewById(R.id.iv_bg);
        title = view.findViewById(R.id.tv_title);
        size = view.findViewById(R.id.tv_size);
        description = view.findViewById(R.id.tv_description);
        progressBar = view.findViewById(R.id.np_bar);
        progressBar.setVisibility(forcedUpgrade ? View.VISIBLE : View.GONE);
        update = view.findViewById(R.id.btn_update);
        update.setTag(0);
        View line = view.findViewById(R.id.line);
        update.setOnClickListener(this);
        ibClose.setOnClickListener(this);
        //自定义
        if (dialogImage != -1) {
            ivBg.setBackgroundResource(dialogImage);
        }
        if (dialogButtonTextColor != -1) {
            update.setTextColor(dialogButtonTextColor);
        }
        if (dialogButtonColor != -1) {
            StateListDrawable drawable = new StateListDrawable();
            GradientDrawable colorDrawable = new GradientDrawable();
            colorDrawable.setColor(dialogButtonColor);
            colorDrawable.setCornerRadius(DensityUtil.dip2px(context, 3));
            drawable.addState(new int[]{android.R.attr.state_pressed}, colorDrawable);
            drawable.addState(new int[]{}, colorDrawable);
            update.setBackgroundDrawable(drawable);
        }
        if (dialogProgressBarColor != -1) {
//            progressBar.rcBackgroundColo(dialogProgressBarColor);
//            progressBar.setProgressTextColor(dialogProgressBarColor);
        }
        //强制升级
        if (forcedUpgrade) {
            line.setVisibility(View.GONE);
            ibClose.setVisibility(View.GONE);
            setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    //屏蔽返回键
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });
        }
        //设置界面数据
        if (!TextUtils.isEmpty(apkVersionName)) {
            String newVersion = context.getResources().getString(R.string.dialog_new);
            title.setText(String.format(newVersion, apkVersionName));
        }
        if (!TextUtils.isEmpty(apkSize)) {
            String newVersionSize = context.getResources().getString(R.string.dialog_new_size);
            size.setText(String.format(newVersionSize, apkSize));
            size.setVisibility(View.VISIBLE);
        }
        description.setText(apkDescription);

        if (downloadListener != null) {

        }

    }




    private void setWindowSize(Context context) {
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenUtil.getWith(context) * 0.7f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ib_close) {
            if (!forcedUpgrade) {
                dismiss();
            }
            //回调点击事件
            if (buttonClickListener != null) {
                buttonClickListener.onButtonClick(OnButtonClickListener.CANCEL);
            }
        } else if (id == R.id.btn_update) {
            if ((int) update.getTag() == install) {
                installApk();
                return;
            }
            if (forcedUpgrade) {
                update.setEnabled(false);
                update.setText(R.string.background_downloading);
            } else {
                dismiss();
            }
            //回调点击事件
            if (buttonClickListener != null) {
                buttonClickListener.onButtonClick(OnButtonClickListener.UPDATE);
            }
            context.startService(new Intent(context, DownloadService.class));
        }
    }

    /**
     * 强制更新，点击进行安装
     */
    private void installApk() {
        ApkUtil.installApk(context, Constant.AUTHORITIES, apk);
    }


}
