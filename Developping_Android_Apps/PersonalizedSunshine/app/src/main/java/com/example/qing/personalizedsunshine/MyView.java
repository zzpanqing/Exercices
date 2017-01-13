package com.example.qing.personalizedsunshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Qing on 13/01/17.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = 0;
        int w = 0;

        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        if (wSpecMode == MeasureSpec.EXACTLY)
            w = wSpecSize;
        else if(wSpecMode == MeasureSpec.AT_MOST)
            w = 100;


        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (hSpecMode == MeasureSpec.EXACTLY)
            h = hSpecSize;
        else if(hSpecMode == MeasureSpec.AT_MOST)
            h = 100;


        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffff0000);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(50, 50, 25, paint);
    }
}
