package com.leday.Util;

/**
 * Created by Administrator on 2016/6/24.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.leday.R;
import com.leday.application.MyApplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class UpdateUtil {

    private ProgressBar pb;
    private Dialog mDownLoadDialog;

    private final String URL_SERVE = "http://www.iyuce.com/Scripts/andoird.json";
    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 0;

    private String mVersion;
    private String mVersionURL;
    private String mMessage;
    private String mSavePath;
    private int mProgress;
    private boolean mIsCancel = false;

    private Context mcontext;

    public UpdateUtil(Context context) {
        mcontext = context;
    }

    @SuppressLint("HandlerLeak")
    private Handler mGetVersionHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                String jsonString = (String) msg.obj;
                String parseString = new String(jsonString.getBytes("ISO-8859-1"), "utf-8");
                JSONObject jsonObject;
                jsonObject = new JSONObject(parseString);
                int result = jsonObject.getInt("code");
                if (result == 0) {
                    JSONArray data = jsonObject.getJSONArray("data");
                    jsonObject = data.getJSONObject(0);
                    mVersion = jsonObject.getString("version");
                    mVersionURL = jsonObject.getString("apkurl");
                    mMessage = jsonObject.getString("detail");
                    LogUtil.e("mVersionURL", "VersionURL = " + mVersionURL);
                }
//				LogUtil.e("version", "远程version = " + mVersion);
                if (isUpdate()) {
                    showNoticeDialog();
                } else {
//                    ToastUtil.showMessage(mcontext, "当前是最新版本" + PreferenUtil.get(mcontext, "localVersion", ""));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mUpdateProgressHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOADING:
                    pb.setProgress(mProgress);
                    break;
                case DOWNLOAD_FINISH:
                    mDownLoadDialog.dismiss();
                    installAPK();
                    break;
            }
        }

        ;
    };

    public void checkUpdate() {
        StringRequest request = new StringRequest(Method.GET, URL_SERVE, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                Message msg = Message.obtain();
                msg.obj = response;
                mGetVersionHandler.sendMessage(msg);
            }
        }, null);
        request.setTag("post");
        MyApplication.getHttpQueue().add(request);
    }

    //boolean比较本地版本是否需要更新
    public boolean isUpdate() {
        float serverVersion = Float.parseFloat(mVersion);
        //将该数据保存如sharepreference，留用
        PreferenUtil.put(mcontext, "serverVersion", String.valueOf(serverVersion));
        LogUtil.e("serverVersion", "serverVersion = " + serverVersion);
        String localVersion = null;

        try {
            //获取versionName作比较
            localVersion = mcontext.getPackageManager().getPackageInfo("com.leday", 0).versionName;
            //将该数据保存如sharepreference，留用
            PreferenUtil.put(mcontext, "localVersion", mcontext.getPackageManager().getPackageInfo("com.leday", 0).versionName);
            LogUtil.e("localVersion", "localVersion = " + localVersion);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (serverVersion > Float.parseFloat(localVersion)) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("InlinedApi")
    @SuppressWarnings("deprecation")
    protected void showNoticeDialog() {     //show 弹窗供选择是否更新
        AlertDialog.Builder builder = new Builder(mcontext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("发现新版本");
//        builder.setMessage(mMessage);
        builder.setPositiveButton("更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
                //下载新版本后，消除欢迎页的key，再次安装则可以有引导动画
                PreferenUtil.remove(mcontext, "welcome");
            }
        });
        builder.setNegativeButton("下次再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"InflateParams", "InlinedApi"})
    protected void showDownloadDialog() {     //显示下载进度
        AlertDialog.Builder builder = new Builder(mcontext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("下载中");

        View view = LayoutInflater.from(mcontext).inflate(R.layout.dialog_progress, null);
        pb = (ProgressBar) view.findViewById(R.id.update_progress);
        builder.setView(view);
        builder.setNegativeButton("取消下载", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mIsCancel = true;
            }
        });
        mDownLoadDialog = builder.create();
        mDownLoadDialog.show();

        //下载文件
        downloadAPK();
    }

    //文件下载的操作(1.存储卡/2.输入流)
    private void downloadAPK() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";// sd卡根目录
                        mSavePath = sdPath + "le";

                        File dir = new File(mSavePath);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }

                        HttpURLConnection conn = (HttpURLConnection) new URL("http://www.iyuce.com/uploadfiles/app/le.apk").openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, mVersion);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];

                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            mProgress = (int) (((float) count / length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(DOWNLOADING);
                            // 下载完成
                            if (numread < 0) {
                                mUpdateProgressHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //安装下载好的APK
    private void installAPK() {
        File apkFile = new File(mSavePath, mVersion);
        if (!apkFile.exists()) {
            return;
        }
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        it.setDataAndType(uri, "application/vnd.android.package-archive");
        mcontext.startActivity(it);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}