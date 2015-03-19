package com.dormvote.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dormvote.R;
import com.dormvote.app.AppConfig;
import com.dormvote.bean.User;
import com.dormvote.db.UserDao;
import com.dormvote.net.Macros;
import com.dormvote.net.NetRequestClient;
import com.dormvote.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/17.
 */
public class UpPicActivity extends BaseActivity implements View.OnClickListener {

    String roomId;
    @InjectView(R.id.iv_pic)
    ImageView mIvPic;
    @InjectView(R.id.et_ps)
    EditText mEtPs;
    private int crop = 600;

    private AlertDialog dialog;
    private File myAvatarFile;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Macros.MSG_UPLOAD_PHOTO_TO_QINIU:
                    if(msg.arg1==Macros.SUCCESS){
                        try {
                            String fileKey = ((JSONObject)msg.obj).getString("key");
                            Log.e("UpPic",fileKey);
                            NetRequestClient.sharedNetRequestClient().uploadUserPhotos(AppConfig.sharedAppConfig(UpPicActivity.this).getAccessToken(),roomId,mEtPs.getText().toString(),fileKey,handler);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Macros.MSG_UPLOAD_MINE_PHOTO:
                    Log.e("upPic",msg.obj.toString());
                    if(msg.arg1== Macros.SUCCESS){
                        ToastUtils.ToastUtil("图片上传成功");
                        finish();
                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_pic_item);
        ButterKnife.inject(this);
        ButterKnife.inject(this);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("上传图片");
        roomId = getIntent().getStringExtra("roomId");
        mIvPic.setImageResource(R.drawable.ic_launcher);
        mIvPic.setOnClickListener(this);

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory();
            // 得到一个路径，内容是sdcard的文件夹路径和名字
            String path = sdcardDir.getPath() + "/dormvote";
            File path1 = new File(path);
            if (!path1.exists()) {
                // 若不存在，创建目录，可以在应用启动的时候创建
                if (path1.mkdirs()) {
                    System.out.println("创建成功");

                } else {
                    System.out.println("创建失败");
                }

            }
        } else {
            Toast.makeText(getApplicationContext(), "无SD卡",Toast.LENGTH_SHORT).show();
        }

        myAvatarFile = new File("/mnt/sdcard/dormvote/", "minehead.jpg");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_up_pic, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.item_done:
                User user = new UserDao(UpPicActivity.this).query();
                String uuid_key = UUID.randomUUID().toString();
                int start = myAvatarFile.getName().indexOf(".");
                String ext = myAvatarFile.getName().substring(start);
                NetRequestClient.sharedNetRequestClient().uploadUserPhotosToQiniu(uuid_key+ext,
                        AppConfig.sharedAppConfig(UpPicActivity.this).
                        getPhotoAccessToken(),myAvatarFile,handler);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_pic:
                if (dialog == null) {

                    dialog = new AlertDialog.Builder(this).setItems(
                            new String[] { "相机", "相册" },
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    if (which == 0) {
                                        Intent intent_camera = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        // 下面这句指定调用相机拍照后的照片存储的路径
                                        intent_camera.putExtra(
                                                MediaStore.EXTRA_OUTPUT,
                                                Uri.fromFile(myAvatarFile));
                                        startActivityForResult(intent_camera, 2);
                                        // Intent intent = new Intent(
                                        // "android.media.action.IMAGE_CAPTURE");
                                        //
                                        // intent.putExtra("output",
                                        // Uri.fromFile(myAvatarFile));
                                        //
                                        // intent.putExtra("crop", "true");
                                        //
                                        // intent.putExtra("aspectX", 1);// 裁剪框比例
                                        //
                                        // intent.putExtra("aspectY", 1);
                                        //
                                        // intent.putExtra("outputX", crop);//
                                        // 输出图片大小
                                        //
                                        // intent.putExtra("outputY", crop);
                                        //
                                        // startActivityForResult(intent, 101);

                                    } else {
                                        Intent intent_media = new Intent(
                                                Intent.ACTION_PICK, null);

                                        /**
                                         * 下面这句话，与其它方式写是一样的效果，如果：
                                         * intent.setData(MediaStore
                                         * .Images.Media.EXTERNAL_CONTENT_URI);
                                         * intent.setType(""image/*");设置数据类型
                                         * 如果朋友们要限制上传到服务器的图片类型时可以直接写如
                                         * ："image/jpeg 、 image/png等的类型"
                                         * 这个地方小马有个疑问，希望高手解答下
                                         * ：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
                                         */

                                        intent_media
                                                .setDataAndType(
                                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                        "image/*");

                                        startActivityForResult(intent_media, 1);
                                        // Intent intent = new Intent(
                                        // "android.intent.action.PICK");
                                        //
                                        // intent.setDataAndType(
                                        // MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                                        // "image/*");
                                        //
                                        // intent.putExtra("output",
                                        // Uri.fromFile(myAvatarFile));
                                        //
                                        // intent.putExtra("crop", "true");
                                        //
                                        // intent.putExtra("aspectX", 1);// 裁剪框比例
                                        //
                                        // intent.putExtra("aspectY", 1);
                                        //
                                        // intent.putExtra("outputX", crop);//
                                        // 输出图片大小
                                        //
                                        // intent.putExtra("outputY", crop);
                                        //
                                        // startActivityForResult(intent, 100);

                                    }

                                }

                            }).create();
                }
                if (!dialog.isShowing()) {
                    dialog.show();
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case 2:
                if (data == null) {
                    System.out.println("图片");
                    startPhotoZoom(Uri.fromFile(myAvatarFile));
                }
                break;
            // 取得裁剪后的图片
            case 3:
                /**
                 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
                 *
                 */
                if (data != null) {
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bmp = BitmapFactory.decodeFile(
                            myAvatarFile.getAbsolutePath(), opts);

                    mIvPic.setImageBitmap(bmp);

                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
		 */
        Intent intent_crop = new Intent("com.android.camera.action.CROP");
        intent_crop.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent_crop.putExtra("output", Uri.fromFile(myAvatarFile));
        intent_crop.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent_crop.putExtra("aspectX", 1);
        intent_crop.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent_crop.putExtra("outputX", crop);
        intent_crop.putExtra("outputY", crop);
        startActivityForResult(intent_crop, 3);
    }
}
