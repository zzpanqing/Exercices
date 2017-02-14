package com.qing.imagetests.imagetests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.qing.imagetests.imagetests.utilities.FastBlur;

import io.fabric.sdk.android.Fabric;

public class BlurActivity extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_blur);
        mImageView = (ImageView) findViewById(R.id.imageView);

    }

    public void doBlur(View view) {

        BitmapDrawable btmDrawable = (BitmapDrawable) mImageView.getDrawable();
        Bitmap bmp = btmDrawable.getBitmap();

        // rescale image, smaller image, less computation
        int scaleRatio = 2;
        int blurRadius = 8;

        if( bmp.getWidth() < 2 ||  bmp.getHeight() < 2)
            return;

        Bitmap rescaledBtp = Bitmap.createScaledBitmap(
                bmp,
                bmp.getWidth()/scaleRatio,
                bmp.getHeight()/scaleRatio,
                false);

        Bitmap newBmp = FastBlur.doBlur(rescaledBtp, blurRadius, false);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setImageBitmap(newBmp);

    }

    public void reset(View view) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.girl_eyes_makeup_xexy_60883);
        mImageView.setImageBitmap(bmp);
    }
}
