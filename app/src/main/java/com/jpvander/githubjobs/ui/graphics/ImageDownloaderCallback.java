package com.jpvander.githubjobs.ui.graphics;

import android.graphics.Bitmap;

public interface ImageDownloaderCallback {
    void onDownloadComplete(Bitmap bitmap);
}
