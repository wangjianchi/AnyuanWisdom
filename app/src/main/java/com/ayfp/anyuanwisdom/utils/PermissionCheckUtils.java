package com.ayfp.anyuanwisdom.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Desction:权限检查工具
 * Author:pengjianbo  Dujinyang
 * Author:KARL-dujinyang
 * Date:16/6/1 下午7:40
 */
public class PermissionCheckUtils {
    public static final int REQUEST_PERMISSION_LOCATION = 10004;

    /**
     * 数组
     */
    public static boolean checkPermission(Activity activity, String permission, String permissionDesc, int requestCode) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    /**
     * 检查是否对sd卡读取授权
     */
    @TargetApi(16)
    public static boolean checkReadExternalPermission(Activity activity, String permissionDesc, int requestCode) {
        return checkPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE, permissionDesc, requestCode);
    }


    /**
     * 检查是否对sd卡读取授权
     */
    @TargetApi(16)
    public static boolean checkWriteExternalPermission(Activity activity, String permissionDesc, int requestCode) {
        return checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, permissionDesc, requestCode);
    }

    /**
     * 检查是否对相机读取授权
     */
    @TargetApi(16)
    public static boolean checkCameraPermission(Activity activity, String permissionDesc, int requestCode) {
        return checkPermission(activity, Manifest.permission.CAMERA, permissionDesc, requestCode);
    }
    /**
     * 检查是否对录音读取授权
     */
    @TargetApi(16)
    public static boolean checkRecordPermission(Activity activity, String permissionDesc, int requestCode) {
        return checkPermission(activity, Manifest.permission.RECORD_AUDIO, permissionDesc, requestCode);
    }

    /**
     * 检查是否对定位读取授权
     */
    @TargetApi(16)
    public static boolean checkLocationPermission(Activity activity, String permissionDesc, int requestCode) {
        return checkPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION, permissionDesc, requestCode);
    }
    /**
     * 检查是否对定位读取授权
     */
    @TargetApi(16)
    public static boolean checkFineLocationPermission(Activity activity, String permissionDesc, int requestCode) {
        return checkPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION, permissionDesc, requestCode);
    }


    /**
     * 录音权限
     * @return
     */
    public static boolean checkRecordAudioPermissions(Activity activity, int requestCode){
        String[] permissions = new String[]{
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        return checkPermissions(activity,permissions,requestCode);
    }
    /**
     * 录音权限
     * @return
     */
    public static boolean checkLocationPermissions(Activity activity, int requestCode){
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        return checkPermissions(activity,permissions,requestCode);
    }
    /**
     * 录像权限
     * @return
     */
    public static boolean checkVideoPermissions(Activity activity, int requestCode){
        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        return checkPermissions(activity,permissions,requestCode);
    }

    /**
     * 照相权限
     * @return
     */
    public static boolean checkCameraPermissions(Activity activity, int requestCode){
        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        return checkPermissions(activity,permissions,requestCode);
    }


    /**
     * 检测多个权限
     */
    public static boolean checkPermissions(Activity activity, String[] permissions, int requestCode) {

        List<String> deniedPermissions = new ArrayList<>();
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            for (String p : permissions) {
                if (ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(p);
                }
            }
        }
        if (deniedPermissions.size() == 0){
            return true;
        }else {
            ActivityCompat.requestPermissions(activity,deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            return false;
        }
    }



}
