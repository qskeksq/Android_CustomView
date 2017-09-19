package com.example.administrator.customview3.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.administrator.customview3.R;

/**
 * 커스텀 위젯4.
 * 컴파운드 텍스트뷰
 */

public class CompoundDrawableTextView extends AppCompatTextView {

    int compoundPlace;
    int compoundDrawable;

    public CompoundDrawableTextView(Context context) {
        super(context);
    }

    public CompoundDrawableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if(attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CompoundDrawableTextView);
            // 이미지
            compoundDrawable = ta.getResourceId(R.styleable.CompoundDrawableTextView_compoundImgage, 0);
            // 이미지 방향
            compoundPlace = ta.getInt(R.styleable.CompoundDrawableTextView_where, 0);
        }

        // 입력값에 따라 이미지 방향이 달라짐
        switch (compoundPlace){
            case 0:
                setCompoundDrawablesWithIntrinsicBounds(compoundDrawable, 0, 0, 0);
                break;
            case 1:
                setCompoundDrawablesWithIntrinsicBounds(0, compoundDrawable, 0, 0);
                break;
            case 2:
                setCompoundDrawablesWithIntrinsicBounds(0, 0, compoundDrawable, 0);
                break;
            case 3:
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, compoundDrawable);
                break;
        }
    }
}
