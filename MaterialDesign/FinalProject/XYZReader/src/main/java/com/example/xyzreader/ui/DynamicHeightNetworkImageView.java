package com.example.xyzreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


public class DynamicHeightNetworkImageView
        extends VolleyImageView
        implements VolleyImageView.ResponseObserver
{

    private float mAspectRatio = 1.5f;

    public DynamicHeightNetworkImageView(Context context) {
        super(context);
        this.setResponseObserver(this);
    }

    public DynamicHeightNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setResponseObserver(this);
    }

    public DynamicHeightNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setResponseObserver(this);
    }

    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (measuredWidth / mAspectRatio));
    }

    @Override
    public void onError() {

    }

    /*
    * after successfully obtain the image from net
    * set the background of text views below the image
    * using the color from image.
    * */
    @Override
    public void onSuccess() {
        {
            Bitmap bitmap = getImageContainerBitmap();
            if (bitmap == null) return;

            ViewGroup parent = (ViewGroup) getParent();
            if (parent == null) return;

            View textsSibling = parent.getChildAt(1);
            if(textsSibling == null) return;

            // get muted color from image,
            // muted color is less lively than vibrant color
            Palette.Builder builder = new Palette.Builder(bitmap);
            Palette p = builder.generate();
            final int defaultBgColor = 0x77FFFFFF;
            int mutedColorFromImage = p.getMutedColor(defaultBgColor);
            // add a little white
            int mixedWithDefaultColor = ColorUtils.blendARGB( mutedColorFromImage, defaultBgColor, 0.2f);

            // build a gradient drawable from 2 color based on image
            GradientDrawable gradientDrawable = new GradientDrawable();
            int colors [] ={
                    mutedColorFromImage,
                    mixedWithDefaultColor
            };
            gradientDrawable.setColors(colors);
            textsSibling.setBackground(gradientDrawable);
            }
        }
    }

