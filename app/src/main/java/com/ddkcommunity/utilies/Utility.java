package com.ddkcommunity.utilies;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;

public class Utility {

    public static final String TAG = Utility.class.getSimpleName();
    public static File file;

    public static boolean isExternalStorageAvailable() {
        return (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
    }

    public static Uri getExternalFilesDirForVersion24Above(@NonNull Context context, @NonNull String directoryPath,
                                                           @NonNull String folderName, @NonNull String fileName) {
        Uri uri = null;
        File mediaStorageDir = null;
        file = null;

        if (directoryPath.equals(Environment.DIRECTORY_PICTURES)) {

            mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName);

            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdir()) {
            }
        }
        if (mediaStorageDir != null) {

            file = new File(mediaStorageDir.getPath() + File.separator + fileName);

            uri = FileProvider.getUriForFile(context, "com.ddkcommunity", file);
        }
        return uri;
    }

    public static Uri getExternalFilesDirForVersion24Below(@NonNull Context context, @NonNull String directoryPath, @NonNull String folderName, @NonNull String fileName) {
        Uri uri = null;
        File mediaStorageDir = null;
        if (directoryPath.equals(Environment.DIRECTORY_PICTURES)) {
            mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName);
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdir()) {
            }
        }
        if (mediaStorageDir != null) {
            file = new File(mediaStorageDir.getPath() + File.separator + fileName);
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static File getFile() {
        return file;
    }
}
