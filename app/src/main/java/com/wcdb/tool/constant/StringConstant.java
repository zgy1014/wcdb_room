package com.wcdb.tool.constant;

import android.os.Environment;

import java.io.File;

public class StringConstant {

    /**
     * SDCARD根目录
     */
    public final static String SDCARD = Environment.getExternalStorageDirectory().getPath();

    /**
     * 项目文件夹
     */
    public final static String STORAGE_DIR = SDCARD + File.separatorChar + "flag" + File.separatorChar;


    /**
     * 奔溃缓存文件夹
     */
    public final static String CACHE = "cache" + File.separatorChar;

    /**
     * log缓存文件夹
     */
    public final static String LOGE = "log" + File.separatorChar;

    /**
     * 项目简报缓存文件夹
     */
    public final static String FILE = "file" + File.separatorChar;

    /**
     * 政策下载文件夹
     */
    public final static String POLICYFILE = "policyFile" + File.separatorChar;

    /**
     * webView缓存文件夹
     */
    public final static String APP_CACAHE_DIRNAME = "/appcache";

    /**
     * 图片缓存文件夹
     */
    public final static String IMAGE = "images" + File.separatorChar;

    public static final String DbBase = "wcdb.db";


    /**
     * 图片加后缀
     */
    public final static String IMAGE_DOWNLOAD = "?download=0";


    public static String getLogDir() {
        File file = Environment.getExternalStorageDirectory();
        if (file.canWrite()) {
            file = new File(STORAGE_DIR + LOGE);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return STORAGE_DIR + LOGE;
    }

    public static String getCacheDir() {
        File file = Environment.getExternalStorageDirectory();
        if (file.canWrite()) {
            file = new File(STORAGE_DIR + CACHE);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return STORAGE_DIR + CACHE;
    }

    public static String getImageDir() {
        File file = Environment.getExternalStorageDirectory();
        if (file.canWrite()) {
            file = new File(STORAGE_DIR + IMAGE);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return STORAGE_DIR + IMAGE;
    }

    public static String getImageDirs() {
        File file = Environment.getExternalStorageDirectory();
        if (file.canWrite()) {
            file = new File(STORAGE_DIR + IMAGE);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return STORAGE_DIR + IMAGE;
    }

    public static String getFileDir() {
        File file = Environment.getExternalStorageDirectory();
        if (file.canWrite()) {
            file = new File(STORAGE_DIR + FILE);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return STORAGE_DIR + FILE;
    }

    public static String getPolicyFileDir() {
        File file = Environment.getExternalStorageDirectory();
        if (file.canWrite()) {
            file = new File(STORAGE_DIR + POLICYFILE);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return STORAGE_DIR + POLICYFILE;
    }


    /**
     * 发送短信类型（1：注册 2：忘记密码）
     */
    public static final class sendSM {

        public static final String FROGET = "2";

        public static final String REGISTER = "1";

    }


    /**
     * 补卡界面
     */
    public static final class clockCard {

        //我发起的
        public static final String LAUNCHEBYME = "1";
        //抄送我的
        public static final String COPYME = "2";
        //审批完成
        public static final String APPROVEDONE = "3";
        //等待审批列表
        public static final String APPROVEWAIT = "4";
        //别人的补卡记录
        public static final String OTHERCLOCK = "5";

    }

}
