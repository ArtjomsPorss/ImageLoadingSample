package com.artjomsporss.imageloadingsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Holds references to drawables to be shown in GridView
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private int mImageHeight = 140;
    private int mImageWidth = 140;
    private Integer[] mThumbnails = {
            R.drawable.one, R.drawable.two,
            R.drawable.three,R.drawable.four,
            R.drawable.five,R.drawable.six,
            R.drawable.seven,R.drawable.eight,
            R.drawable.nine,R.drawable.ten,
            R.drawable.eleven,R.drawable.twelve,
            R.drawable.thirteen,R.drawable.fourteen,
            R.drawable.fifteen,R.drawable.sixteen,
            R.drawable.seventeen,R.drawable.eighteen,
            R.drawable.nineteen,R.drawable.twenty,
            R.drawable.twentyone,R.drawable.twnentytwo,
            R.drawable.one, R.drawable.two,
            R.drawable.three,R.drawable.four,
            R.drawable.five,R.drawable.six,
            R.drawable.seven,R.drawable.eight,
            R.drawable.nine,R.drawable.ten,
            R.drawable.eleven,R.drawable.twelve,
            R.drawable.thirteen,R.drawable.fourteen,
            R.drawable.fifteen,R.drawable.sixteen,
            R.drawable.seventeen,R.drawable.eighteen,
            R.drawable.nineteen,R.drawable.twenty,
            R.drawable.twentyone,R.drawable.twnentytwo,
            R.drawable.one, R.drawable.two,
            R.drawable.three,R.drawable.four,
            R.drawable.five,R.drawable.six,
            R.drawable.seven,R.drawable.eight,
            R.drawable.nine,R.drawable.ten,
            R.drawable.eleven,R.drawable.twelve,
            R.drawable.thirteen,R.drawable.fourteen,
            R.drawable.fifteen,R.drawable.sixteen,
            R.drawable.seventeen,R.drawable.eighteen,
            R.drawable.nineteen,R.drawable.twenty,
            R.drawable.twentyone,R.drawable.twnentytwo,
    };

    private Bitmap[] mBitmaps;

    public ImageAdapter(Context context){
        this.mContext = context;

        //loads images in bulk when Adapter gets Created
        new BitmapBulkLoaderTask().execute(mThumbnails);
    }

    @Override
    public int getCount() {
        return mThumbnails.length;
    }

    //is called by GridView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(mImageHeight, mImageWidth));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }else{
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbnails[position]);                    //un-scaled image

        // works only if images are loaded in bulk
        if(mBitmaps != null && mBitmaps[position] != null){
            imageView.setImageBitmap(mBitmaps[position]);
        }

        return imageView;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * To run:
     * create instance of BitmapBulkLoaderTask, call execute(Bitmap[])
     *
     * Parameter list:
     * Iteger[] takes in for execution of the task
     * Bitmap[] is passeed to onProgressUpdate by calling publishProgress(Bitmap[])
     * Btimap[] is passed to onPostExecute via return statement
     */
    class BitmapBulkLoaderTask extends AsyncTask<Integer[], Bitmap[], Bitmap[]> {
        @Override
        protected Bitmap[] doInBackground(Integer[]... params) {
            Integer[] thumbnails = params[0];
            Bitmap[] bitmaps = new Bitmap[thumbnails.length];
            for (int i = 0; i < thumbnails.length; i++) {
                bitmaps[i] = ImageUtils.scaleBitmapFromResource(mContext.getResources(), thumbnails[i], mImageWidth, mImageHeight);
                if(i % 2 == 0){
                    //calls onProgressUpdate
                    publishProgress(bitmaps);
                }
            }
            return bitmaps;
        }

        //executes when task finishes
        @Override
        protected void onPostExecute(Bitmap[] bitmaps){
            mBitmaps = bitmaps;

            //notifies GridView that data has changed and needs to be reloaded by calling getView
            notifyDataSetChanged();
        }

        //executes via publishProgress(Bitmap[]) during task execution
        @Override
        protected void onProgressUpdate(Bitmap[]... bitmaps) {
            mBitmaps = bitmaps[0];
            notifyDataSetChanged();
        }
    }
}


