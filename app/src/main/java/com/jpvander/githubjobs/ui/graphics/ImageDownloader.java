package com.jpvander.githubjobs.ui.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final static int TIMEOUT_IN_MILLISECONDS = 7 * 1000;

    private final ImageDownloaderCallback callback;
    private final float displayScale;
    private final float imageWidth;

    public ImageDownloader(float displayScale, float imageWidth, ImageDownloaderCallback callback) {
        this.callback = callback;
        this.displayScale = displayScale;
        this.imageWidth = imageWidth;
    }

    protected Bitmap doInBackground(String... urls) {
        if (0 >= urls.length) {
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }

        String url = urls[0];
        HttpURLConnection queryConnection = null;
        HttpURLConnection inputConnection = null;
        Bitmap bitmap = null;
        InputStream queryStream;
        InputStream inputStream;

        try {
            URL imageURL = new URL(url);
            queryConnection = (HttpURLConnection) imageURL.openConnection();
            queryConnection.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);
            queryConnection.setReadTimeout(TIMEOUT_IN_MILLISECONDS);
            inputConnection = (HttpURLConnection) imageURL.openConnection();
            inputConnection.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);
            inputConnection.setReadTimeout(TIMEOUT_IN_MILLISECONDS);

            // This is my current best attempt at (1) scaling down the bitmap sizes to a uniform
            // width while (2) keeping the aspect ratio and (3) preventing OOM crashes. I am not
            // happy that this requires two separate network calls but it was the only option
            // (thus far) that gave me all three of my criteria.  --Jennifer Vanderputten
            BitmapFactory.Options queryOptions = new BitmapFactory.Options();
            queryOptions.inJustDecodeBounds = true;
            queryStream = new BufferedInputStream(queryConnection.getInputStream());
            BitmapFactory.decodeStream(queryStream, null, queryOptions);
            queryStream.close();
            float originalWidth = queryOptions.outWidth;
            float originalHeight = queryOptions.outHeight;
            int wantedWidth = (int) (imageWidth * displayScale);
            int scale = (int) (originalWidth / wantedWidth);
            if ( 0 >= scale) { scale = 1; }
            BitmapFactory.Options inputOptions = new BitmapFactory.Options();
            inputOptions.inSampleSize = scale;
            inputStream = new BufferedInputStream(inputConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(inputStream, null, inputOptions);
            inputStream.close();
            int wantedHeight = (int) ((wantedWidth * originalHeight) / originalWidth);
            bitmap = Bitmap.createScaledBitmap(bitmap, wantedWidth, wantedHeight, true);

        } catch (SocketTimeoutException exception){
            Log.e("GitHubJobs", "Socket timeout on URL " + url);

        } catch (Exception exception) {
            Log.e("GitHubJobs", "Exception on url " + url + ": " + exception.getMessage());
        }
        finally {
            if (null != queryConnection) {
                queryConnection.disconnect();
            }

            if (null != inputConnection) {
                inputConnection.disconnect();
            }
        }

        return bitmap;
    }

    protected void onPostExecute(Bitmap bitmap) {
        callback.onDownloadComplete(bitmap);
    }
}