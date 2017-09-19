package com.example.administrator.customview3.composite;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.customview3.R;

/**
 * 복합 위젯2.
 * 레이팅바
 */

public class RatingStar extends LinearLayout implements View.OnClickListener {

    int starNums;
    LinearLayout view;
    Button star1, star2, star3, star4, star5;

    public RatingStar(Context context) {
        super(context);
    }

    public RatingStar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setListener();
        getAttrs(attrs);

        // 초기 별점 세팅
        setSelected(starNums);

        // 현재 뷰에 LinearLayout addView()
        addView(view);
    }

    public void init(){
        view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.ratingbar, this, false);
        star1 = (Button) view.findViewById(R.id.button);
        star2 = (Button) view.findViewById(R.id.button2);
        star3 = (Button) view.findViewById(R.id.button3);
        star4 = (Button) view.findViewById(R.id.button4);
        star5 = (Button) view.findViewById(R.id.button5);
    }

    public void setListener(){
        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);
        star4.setOnClickListener(this);
        star5.setOnClickListener(this);
    }


    /**
     * attrs 설정
     * @param attrs
     */
    private void getAttrs(AttributeSet attrs){
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.RatingStar);
        setTypedArray(ta);
    }

    private void setTypedArray(TypedArray ta){
        starNums = ta.getInt(R.styleable.RatingStar_starNum, 0);
    }


    /**
     * 선택한 만큼 바꿔주기
     * @param num
     */
    public void setSelected(int num){
        switch (num){
            case 1:
                star1.setBackgroundColor(Color.CYAN);
                break;
            case 2:
                star1.setBackgroundColor(Color.CYAN);
                star2.setBackgroundColor(Color.CYAN);
                break;
            case 3:
                star1.setBackgroundColor(Color.CYAN);
                star2.setBackgroundColor(Color.CYAN);
                star3.setBackgroundColor(Color.CYAN);
                break;
            case 4:
                star1.setBackgroundColor(Color.CYAN);
                star2.setBackgroundColor(Color.CYAN);
                star3.setBackgroundColor(Color.CYAN);
                star4.setBackgroundColor(Color.CYAN);
                break;
            case 5:
                star1.setBackgroundColor(Color.CYAN);
                star2.setBackgroundColor(Color.CYAN);
                star3.setBackgroundColor(Color.CYAN);
                star4.setBackgroundColor(Color.CYAN);
                star5.setBackgroundColor(Color.CYAN);
                break;
        }
    }

    public void clear(){
        star1.setBackgroundColor(Color.WHITE);
        star2.setBackgroundColor(Color.WHITE);
        star3.setBackgroundColor(Color.WHITE);
        star4.setBackgroundColor(Color.WHITE);
        star5.setBackgroundColor(Color.WHITE);
    }


    @Override
    public void onClick(View v) {
        clear();
        switch (v.getId()){
            case R.id.button:
                setSelected(1);
                break;
            case R.id.button2:
                setSelected(2);
                break;
            case R.id.button3:
                setSelected(3);
                break;
            case R.id.button4:
                setSelected(4);
                break;
            case R.id.button5:
                setSelected(5);
                break;
        }
    }
}
