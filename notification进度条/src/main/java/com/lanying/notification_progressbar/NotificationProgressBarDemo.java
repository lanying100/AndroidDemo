package com.lanying.notification_progressbar;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Notification中带有进度条
 * 参考网址：http://blog.csdn.net/cstarbl/article/details/7200757
 * 例题需求提供者：孙林
 * @author lanying
 *
 */
public class NotificationProgressBarDemo extends Activity {
	/** Called when the activity is first created. */
	int notification_id = 19172439;
	NotificationManager nm;
	Handler handler = new Handler();
	Notification notification;
	int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		//通过系统服务，获得一个Notification管理者
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		//用推荐的方法创建一个Notification对象（注意，该方法支持的最小版本为API 16）
		notification = new Notification.Builder(this)
				.setContentTitle("content title")
				.setContentText("content text")
				.setSmallIcon(R.mipmap.ic_launcher).build();

		//此处涉及新知识：RemoteViews（描述了一个View对象能够显示在其他进程中，可以融合从一个 layout资源文件实现布局。）
		//参考网址：http://laolang.xtmm.cn/?p=14439
		//http://www.cnblogs.com/over140/archive/2011/11/28/2265736.html
		// 使用notification.xml文件作VIEW
		notification.contentView = new RemoteViews(getPackageName(),
				R.layout.notification_progressbar);

		// 设置进度条，最大值 为100,当前值为0，最后一个参数为true时显示条纹
		notification.contentView.setProgressBar(R.id.noti_progressbar, 100, 0, false);
		notification.contentView.setTextViewText(R.id.tv_percent, "...");

//		//创建意图对象
//		Intent notificationIntent = new Intent(this,
//				NotificationProgressBarDemo.class);
//		//对Intent对象进行包装，从而不必立刻执行，等到一定条件触发时执行。
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//				notificationIntent, 0);
//		notification.contentIntent = contentIntent;
	}

		public void myClick(View v) {
			showNotification();// 显示notification
			handler.post(run);
		}

	Runnable run = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			count++;
			notification.contentView.setProgressBar(R.id.noti_progressbar, 100, count, false);
			notification.contentView.setTextViewText(R.id.tv_percent, count+"%");
			// 设置当前值为count
			showNotification();// 这里是更新notification,就是更新进度条
			if (count < 100)
				handler.postDelayed(run, 200);
			// 每200毫秒开一个子线程，从而使count加1
		}

	};

	public void showNotification() {
		nm.notify(notification_id, notification);
	}
}
