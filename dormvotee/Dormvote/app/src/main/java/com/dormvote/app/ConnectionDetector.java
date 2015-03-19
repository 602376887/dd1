package com.dormvote.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络检测，单例类
 * @author Ma
 *
 */
public class ConnectionDetector {
	
	private static ConnectionDetector detector = new ConnectionDetector();

	private ConnectionDetector() {

	}

	/**
	 * 获得单例
	 * @return
	 */
	public static ConnectionDetector sharedConnectionDector() {
		return detector;
	}
	
	
	/**
	 * 检测是否联网,获得单例后调用
	 * @param context
	 * @return
	 */
	public boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
}
