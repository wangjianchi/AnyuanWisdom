package com.ayfp.anyuanwisdom.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author:: wangjianchi
 * @time: 2017/12/11  15:20.
 * @description:
 */

public class FileUtils {
//    public static String File2StrByBase64(File file){
//        FileInputStream inputFile = new FileInputStream(file);
//        byte[] buffer = new byte[(int)file.length()];
//        inputFile.read(buffer);
//        inputFile.close();
//        return new Base64.encodeToString();
//    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    public static String Bitmap2StrByBase64(Bitmap bit){
        if (bit != null){
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            //参数100表示不压缩
            byte[] bytes=bos.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }else {
            return "";
        }
    }
    /**
     * 将Bitmap写入SD卡中的一个文件中,并返回写入文件的Uri
     * @param bm
     * @param dirPath
     * @return
     */
    private static void saveBitmap(Bitmap bm, String dirPath) {
        //新建文件存储裁剪后的图片
        File img = new File(dirPath);
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    /**
     * 将content类型的Uri转化为文件类型的Uri
     * @param uri
     * @return
     */
    public static String convertUri(Context context,Uri uri){
        InputStream is;
        try {
            //Uri ----> InputStream
            is = context.getContentResolver().openInputStream(uri);
            //InputStream ----> Bitmap
            Bitmap bm = BitmapFactory.decodeStream(is);
            //关闭流
            is.close();
            String path = CommonUtils.getImageFileName();
            saveBitmap(bm,path);
            return path ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
