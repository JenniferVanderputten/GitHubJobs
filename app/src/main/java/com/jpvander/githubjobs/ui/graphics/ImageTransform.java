package com.jpvander.githubjobs.ui.graphics;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class ImageTransform implements Transformation {

    private final float density;
    private final float width;

    public ImageTransform(float density, float width) {
        this.density = density;
        this.width = width;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        float originalWidth = bitmap.getWidth();
        float originalHeight = bitmap.getHeight();
        int wantedWidth = (int) (width * density);
        if (0 >= wantedWidth) { wantedWidth = 1; }
        int wantedHeight = (int) ((wantedWidth * originalHeight) / originalWidth);
        if (0 >= wantedHeight) { wantedHeight = 1; }
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, wantedWidth, wantedHeight, true);

        if (bitmap != newBitmap) {
            bitmap.recycle();
        }

        return newBitmap;
    }

    @Override
    public String key() {
        return "ImageTransform_" + density + "_" + width;
    }
}
