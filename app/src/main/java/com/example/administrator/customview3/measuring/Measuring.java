package com.example.administrator.customview3.measuring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017-09-18.
 */

public class Measuring extends View {

    String mResult;

    public Measuring(Context context) {
        super(context);
    }

    public Measuring(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode, hMode;
        int wSpec, hSpec;
        int Width, Height;
        Width = 150;
        Height = 80;



        wMode = MeasureSpec.getMode(widthMeasureSpec);
        hMode = MeasureSpec.getMode(heightMeasureSpec);
        wSpec = MeasureSpec.getSize(widthMeasureSpec);
        hSpec = MeasureSpec.getSize(heightMeasureSpec);

        switch (wMode){
            case MeasureSpec.AT_MOST:
                Width = Math.min(wSpec, Width);
                break;
            case MeasureSpec.EXACTLY:
                Width = wSpec;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        switch (hMode){
            case MeasureSpec.AT_MOST:
                Height = Math.min(hSpec, Height);
                break;
            case MeasureSpec.EXACTLY:
                Height = hSpec;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        setMeasuredDimension(Width, Height);

        mResult += (SpecToString(widthMeasureSpec)+", "+SpecToString(heightMeasureSpec)+" -> ("+Width+", "+Height+")\n");

    }

    String SpecToString(int Spec){
        String str = "";
        switch (MeasureSpec.getMode(Spec)){
            case MeasureSpec.AT_MOST:
                str = "AT_MOST";
                break;
            case MeasureSpec.EXACTLY:
                str = "EXACTLY";
                break;
            case MeasureSpec.UNSPECIFIED:
                str = "UNSPECIFIED";
                break;
        }

        str += " "+MeasureSpec.getSize(Spec);
        return str;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }


}
