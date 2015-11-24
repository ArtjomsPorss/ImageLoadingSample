package com.artjomsporss.imageloadingsample;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by User1 on 24/11/2015.
 */
public class ImageUtils {

    /**
     * Determines the scale size
     * @param options parametes of Original image
     * @param reqWidth needed width
     * @param reqHeight needed height
     * @return
     */
    public static int calculateScaleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth){
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap scaleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        //holds the info about image
        final BitmapFactory.Options options = new BitmapFactory.Options();

        //only decode image, don't allocate mem for image because
        // sets decoding method to return null
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options); //reads the info about image

        //get scaled bitmap (if inSampleSize > 1)
        options.inSampleSize = calculateScaleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;


        return BitmapFactory.decodeResource(res, resId, options);
    }
}
