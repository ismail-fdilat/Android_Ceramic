package com.shrinkcom.alsaadceramicapp.firebase;

import android.content.Context;

import java.io.File;

/**
 * Created by win 7 on 3/20/2018.
 */

public class Constants {

    public static final String CHANNEL_ID = "my_channel_01";
    public static final String CHANNEL_NAME = "Simplified Coding Notification";
    public static final String CHANNEL_DESCRIPTION = "www.simplifiedcoding.net";


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
