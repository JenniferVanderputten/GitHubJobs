package com.jpvander.githubjobs.ui.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.util.Log;

import java.io.InputStream;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final ImageView imageView;
    private final float displayScale;

    public ImageDownloader(ImageView imageView, float displayScale) {
        this.imageView = imageView;
        this.displayScale = displayScale;
    }

    protected Bitmap doInBackground(String... urls) {

        if (0 >= urls.length) {
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }

        String url = urls[0];
        Bitmap scaledBitmap = null;

        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            int originalWidth = bitmap.getScaledWidth((int) displayScale);
            int originalHeight = bitmap.getScaledHeight((int)displayScale);
            //int wantedHeight = (int) (50.0f * displayScale);
            //int wantedWidth = (int) ((wantedHeight * originalWidth) / originalHeight);
            int wantedWidth = (int) (100.0f * displayScale);
            int wantedHeight = (wantedWidth * originalHeight) / originalWidth;
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, wantedWidth, wantedHeight, true);
        } catch (Exception exception) {
            Log.e("Error", exception.getMessage());
        }

        return scaledBitmap;
    }

    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}