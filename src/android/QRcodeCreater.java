﻿package org.apache.cordova.gpPrint;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import android.graphics.Bitmap;
import android.graphics.Matrix;
/**
 * 根据内容生成二维码
 * @author B2
 *
 */
public class QRcodeCreater {
    private static final int QRCODE_SIZE = 240;
	//根据字符生成二维码图片
	public static Bitmap createQRCodeBitmap(String text) {
		//用于设置QR二维码的参数
		Hashtable<EncodeHintType, Object> qrParam= new Hashtable<EncodeHintType, Object>();  
		//设置二维码的纠错级别-这里是最高级别
		qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
		//设置二维码的外边框
		qrParam.put(EncodeHintType.MARGIN, 4);
		 // 设置编码方式  
	    qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8"); 
	  
	    // 生成QR二维码数据——这里只是得到一个由true和false组成的数组  
	    // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数  
	    try {  
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,  
	                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, qrParam);  
	  
	        // 开始利用二维码数据创建Bitmap图片，分别设为黑白两色  
	        int w = bitMatrix.getWidth();  
	        int h = bitMatrix.getHeight(); 
	       
	        int[] data = new int[w * h];  
	  
	        for (int y = 0; y < h; y++) {  
	            for (int x = 0; x < w; x++) {  
	                if (bitMatrix.get(x, y)){
	                	
	                    data[y * w + x] = 0xff000000;// 黑色  
	                }else{  
	                    data[y * w + x] = -1;// -1 相当于0xffffffff 白色  
	                }
	                }  
	        }  
	  
	        // 创建一张bitmap图片，采用最高的效果显示  
	        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);  
	        // 将上面的二维码颜色数组传入，生成图片颜色  
	        bitmap.setPixels(data, 0, w, 0, 0, w, h);  
	        
	        return small(bitmap);  
	    } catch (WriterException e) {  
	        e.printStackTrace();  
	    } 
		
		return null; 
		
	}
	
	public static Bitmap small(Bitmap bitmap) {
		  Matrix matrix = new Matrix(); 
		  matrix.postScale(0.6f,0.6f); //长和宽放大缩小的比例
		  Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		  return resizeBmp;

		 }
}
