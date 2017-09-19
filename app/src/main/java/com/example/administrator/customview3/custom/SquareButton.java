package com.example.administrator.customview3.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 커스텀 위젯3.
 * 정사각형
 */

public class SquareButton extends AppCompatButton {

    public SquareButton(Context context) {
        super(context);
    }

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        int mMODE = MeasureSpec.getMode(widthMeasureSpec);
        int hMODE = MeasureSpec.getMode(heightMeasureSpec);

        switch (mMODE){
            case MeasureSpec.AT_MOST:
                Log.e("mMODE", "AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("mMODE", "EXACTLY");
                break;
        }

        switch (hMODE){
            case MeasureSpec.AT_MOST:
                Log.e("hMODE", "AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("hMODE", "EXACTLY");
                break;
        }
        /**
         * getWidth -- 그려지고 난 후의 크기
         * getMeasuredWidth(); -- 뷰 자체의 크기
         * getDefaultSize -- 부모가 정해주는 여유 있는 크기
         */

        Log.e("getwidth", getWidth()+"");
        Log.e("getHeight", getHeight()+"");
        Log.e("Measurespec.getsize(w", MeasureSpec.getSize(widthMeasureSpec)+"");
        Log.e("Measurespec.getsize(h", MeasureSpec.getSize(heightMeasureSpec)+"");
        Log.e("getMeasuedwidth", getMeasuredWidth()+"");
        Log.e("getMeasuedHeight", getMeasuredHeight()+"");
        Log.e("getdefaultwidthsize", getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec)+"");
        Log.e("getdefaulthegithsize", getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec)+"");

        // 정사각형 그리기
        if(width > height){
            width = height;
        } else if(height > width){
            height = width;
        }

        setMeasuredDimension(width, height);
    }
}
