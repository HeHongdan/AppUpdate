package com.hehongdan.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListenerAdapter;
import com.azhon.appupdate.manager.DownloadManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);




	}


	private DownloadManager manager;
	private String url = "https://imtt.dd.qq.com/16891/apk/FA48766BA12A41A1D619CB4B152889C6.apk?fsname=com.estrongs.android.pop_4.2.3.3_10089.apk&csr=1bbd";
	public void startUpdate1(View view) {


		new UpdateCDialog.Builder()
				.getApkDescription("ApkDescription")
				.getApkSize("ApkSize")
				.getApkVersionName("ApkVersionName")
				.isForcedUpgrade(true)
				.getOnButtonClickListener(new OnButtonClickListener() {
					@Override
					public void onButtonClick(int id) {

					}
				})
				.build();


		if (true){



//			@Override
//			public void start() {
//
//			}
//
//			@Override
//			public void downloading(int max, int progress) {
//				if (max != -1 && progressBar.getVisibility() == View.VISIBLE) {
//					int curr = (int) (progress / (double) max * 100.0);
//					progressBar.setProgress(curr);
//				} else {
//					progressBar.setVisibility(View.GONE);
//				}
//			}
//
//			@Override
//			public void done(File apk) {
//				this.apk = apk;
//				if (forcedUpgrade) {
//					update.setTag(install);
//					update.setEnabled(true);
//					update.setText(R.string.click_hint);
//				}
//			}
//
//			@Override
//			public void error(Exception e) {
//
//			}

			return;
		}

		/*
		 * 整个库允许配置的内容
		 * 非必选
		 */
		UpdateConfiguration configuration = new UpdateConfiguration()
				//输出错误日志
				.setEnableLog(true)
				//设置自定义的下载
				//.setHttpManager()
				//下载完成自动跳动安装页面
				.setJumpInstallPage(true)
				//设置对话框背景图片 (图片规范参照demo中的示例图)
				//.setDialogImage(R.drawable.ic_dialog)
				//设置按钮的颜色
				//.setDialogButtonColor(Color.parseColor("#E743DA"))
				//设置对话框强制更新时进度条和文字的颜色
				.setDialogProgressBarColor(Color.parseColor("#E743DA"))
				//设置按钮的文字颜色
				.setDialogButtonTextColor(Color.WHITE)
				//设置是否显示通知栏进度
				.setShowNotification(true)
				//设置是否提示后台下载toast
				.setShowBgdToast(false)
				//设置是否上报数据
				.setUsePlatform(true)
				//设置强制更新
				.setForcedUpgrade(false)
				//设置下载过程的监听
				.setOnDownloadListener(listenerAdapter)
				//设置对话框按钮的点击监听
				.setButtonClickListener(new OnButtonClickListener() {
					@Override
					public void onButtonClick(int id) {
						Log.e("TAG", String.valueOf(id));
					}
				})
				;

		manager = DownloadManager.getInstance(this);
		manager.setApkName("ESFileExplorer.apk")
				.setApkUrl(url)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setShowNewerToast(true)
				.setConfiguration(configuration)
				.setApkVersionCode(2)
				.setApkVersionName("2.1.8")
				.setApkSize("20.4")
				.setApkDescription("1.支持Android M N O P Q\\n2.支持自定义下载过程\\n3.支持 设备>=Android M 动态权限的申请\\n4.支持通知栏进度条展示\\n5.支持文字国际化")
//                .setApkMD5("DC501F04BBAA458C9DC33008EFED5E7F")
				.download();


	}


	private OnDownloadListenerAdapter listenerAdapter = new OnDownloadListenerAdapter() {
		/**
		 * 下载中
		 *
		 * @param max      总进度
		 * @param progress 当前进度
		 */
		@Override
		public void downloading(int max, int progress) {
			int curr = (int) (progress / (double) max * 100.0);
//			progressBar.setMax(100);
//			progressBar.setProgress(curr);
		}
	};

}
