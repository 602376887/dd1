package com.dormvote.app;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import com.dormvote.net.Macros;
import com.dormvote.ui.LoginActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * <p>
 * Description:全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * </p>
 * 
 * @author dengdun
 * @date 2014-11-26
 */
public class AppContext extends Application {

	public static final String ROOM_LIST_TYPE_DONE = "100";
	public static final String ROOM_LIST_TYPE_UNDONE = "200";
	//新消息来了，发送消息提醒
	private NotificationManager notificationManager;
	
	private static AppContext myApp;
	//private User loginUser;
	private boolean login = false; // 登录状态
	private String loginUid = ""; // 登录用户的id



	private Handler unLoginHandler = new Handler() {
		// 用户登录接口不可用是触发，如不可用access_token、注销等，返回到登录界面
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Macros.MSG_LOGOUT:
				// 清空数据,MSG_LOGOUT消息为accessToken不可用时发送
				Logout();
//				if(AppManager.getAppManager().currentActivity().getClass() == LoginActivity.class){
//					break;
//				}
				AppManager.getAppManager().finishAllActivity();
				Intent intent = new Intent(myApp, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				break;
//			case Macros.MSG_LOGIN:
//					User re_user = UserMapper
//							.parseUserInfo((JSONObject) msg.obj);
//					UserDao userDao = new UserDao(getApplicationContext());
//					int ret = userDao.update(re_user);
//					AppContext.sharedAppContext().initLoginInfo();// 初始化应用上下文
//				break;
			default:
				break;
			}
		}
	};
@Override
     public void onCreate() {
        super.onCreate();
        // To Do 注册一些东西
        this.myApp = (AppContext) getApplicationContext();
        setNotificationManager((NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE));

        if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }


        initImageLoader(getApplicationContext());
    }
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

	public static AppContext sharedAppContext() {
		return myApp;
	}

	/**
	 * 初始化用户登录信息,用户登录后必须被调用
	 */
//	public void initLoginInfo() {
////		User loginUser = getLoginInfo();
//		if (loginUser != null && !loginUser.getId().equals("")) {
//			this.loginUid = loginUser.getId();
//			this.login = true;
//			this.setLoginUser(loginUser);
//		} else {
//			System.out.println("获取登录信息失败！！！");
//		}
//	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 用户是否登录
	 * 
	 * @return
	 */

	public boolean isLogin() {
		return login;
	}

//	public User getLoginUser() {
//		return loginUser;
//	}
//
//	public void setLoginUser(User loginUser) {
//		this.loginUser = loginUser;
//	}

	/**
	 * 获取登录用户id
	 * 
	 * @return
	 */
	public String getLoginUid() {
		return this.loginUid;
	}

	/**
	 * 用户注销
	 */
	public void Logout() {
		AppConfig.sharedAppConfig(getApplicationContext()).setAccessToken("");

		this.login = false;
		this.loginUid = "";
	}

	/**
	 * 未登录或修改密码后的处理
	 */

	public Handler getUnLoginHandler() {
		return this.unLoginHandler;
	}

	/**
	 * 获取登录信息
	 * 
	 * @return
	 */
//	private User getLoginInfo() {
//		// 从数据库中取出
//		User loginUser = null;
//		UserDao userDao = new UserDao(getApplicationContext());
//		loginUser = userDao.query();
//		if (loginUser != null) {
//			return loginUser;
//
//		} else {
//			Intent intent = new Intent(this, LoginActivity.class);
//			startActivity(intent);
//			return null;
//		}
//	}



//	public Handler getUserMessageChange() {
//		return userMessageChange;
//	}
//
//	public void setUserMessageChange(Handler userMessageChange) {
//		this.userMessageChange = userMessageChange;
//	}
//
//	public Handler getNoReadCountChangeHandler() {
//		return noReadCountChangeHandler;
//	}
//
//	public void setNoReadCountChangeHandler(Handler noReadCountChangeHandler) {
//		this.noReadCountChangeHandler = noReadCountChangeHandler;
//	}

	public NotificationManager getNotificationManager() {
		return notificationManager;
	}

	public void setNotificationManager(NotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

}
