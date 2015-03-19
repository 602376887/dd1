package com.dormvote.utils;
/**
 * 
 * <p>Description:七牛url参数 </p>
 * @author Nob
 * @date 2014-6-18
 */
public class QiniuImageUtils {
	
//	imageView2/<mode>
//    /w/<LongEdge>
//    /h/<ShortEdge>
//    /q/<Quality>
//    /format/<Format>
//    /interlace/<Interlace>
    
	//http://qiniuphotos.qiniudn.com/gogopher.jpg?imageView2/0/w/200/h/200
	public final static int IMAGEVIEW2_MODE0_KEEPMAXEDGE = 0;//限定缩略图的长边最多为<LongEdge>，短边最多为<ShortEdge>，进行等比缩放，不裁剪。如果只指定 w 参数则表示限定长边（短边自适应），只指定 h 参数则表示限定短边（长边自适应）。
	public final static int IMAGEVIEW1_MODE0_KEEPCENTER = 1;//裁剪正中部分，等比缩小生成200x200缩略图：
	public final static int IMAGEVIEW2_MODE2_FIXWIDTH = 2;//宽度固定为200px，高度等比缩小，生成200x133缩略图
	public final static int IMAGEVIEW2_MODE3_FIXHEIGHT = 3;//高度固定为200px，宽度等比缩小，生成300x200缩略图
	public final static int IMAGEVIEW2_MODE4_FULLSCREEN = 4;//渐进显示图片,适合做全屏显示
	
	public static String wrapUrl(int mode,int w,int h,int q,String format,String interlace) {
		// TODO Auto-generated method stub
		String urlParam = "?imageView2/";
		urlParam = urlParam+mode+"/w/"+w+"/h/"+h;
		if (q!=0) {
			urlParam = urlParam+mode+"/q/"+q;
		}
		if (format!=null&&!format.equals("")) {
			urlParam = urlParam+"/format/"+format;
		}
		if (interlace!=null&&!interlace.equals("")) {
			urlParam = urlParam+"/interlace/"+interlace;
		}
		return urlParam;
	}
	
	//获取缩略图
	public static String getThumb(){
		return wrapUrl(IMAGEVIEW2_MODE0_KEEPMAXEDGE, 100, 100, 0, null, null);
	}
	
}
