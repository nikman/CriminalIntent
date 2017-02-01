package com.andrew.niku.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by niku on 31.01.2017.
 */

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {

        // Чтение размеров изображения
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        // вычислим масштаб
        int scaleFactor = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                scaleFactor = Math.round(srcHeight / destHeight);
            } else {
                scaleFactor = Math.round(srcWidth / destWidth);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        // Чтение данных и создание итогового изображения
        return BitmapFactory.decodeFile(path, options);

    }

    public static Bitmap getScaledBitmap(String path, Activity activity) {

        Point size = new Point();

        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return getScaledBitmap(path, size.x, size.y);

    }

}
