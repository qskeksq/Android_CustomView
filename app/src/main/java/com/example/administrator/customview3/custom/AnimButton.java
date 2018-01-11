package com.example.administrator.customview3.custom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.customview3.R;

/**
 * 커스텀 위젯1.
 * 누르면 애니메이션이 나타나는 커스텀 버튼
 */

public class AnimButton extends AppCompatButton implements View.OnTouchListener{

    boolean animation;

    public AnimButton(Context context){
        super(context);
    }


    // 생성자 호출할 때 속성들이 전부 AttributeSet 에 담겨온다.
    public AnimButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        getAttrs(attrs);
    }
    

    /**
     * attrs.xml 에서 속성값을 TypedArray 로 가져온다
     * @param attrs
     */
    private void getAttrs(AttributeSet attrs){
        // 1. attrs.xml 에 정의된 속성을 가져온다
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AniButton);
        setTypedArray(ta);
    }

    /**
     * attrs.xml 에서 속성값을 가져온다
     * @param ta
     */
    private void setTypedArray(TypedArray ta){
        // 2. 해당 이름으로 가져온 속성의 개수를 가져온다
        int size = ta.getIndexCount();
        Log.d("AniButton size", "size : "+size);
        // 3. 반복문을 돌면서 해당 속성에 대한 처리를 해준다.
        for (int i = 0; i < size; i++) {
            // 3.1 현재 배열에 있는 속성 아이디 가져오기
            int cur_attr = ta.getIndex(i);
            switch (cur_attr) {
                case R.styleable.AniButton_animation:
                    animation = ta.getBoolean(cur_attr, false);
                    break;
            }
        }
    }

    /**
     * 애니메이션 설정&시작
     */
    private void animateButton(){                       // ObjectAnimator 인자로 여러 값이 들어가 다양한 형태를 보여줄 수 있다.
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 1.5f, 1.0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 1.5f, 1.0f);
        final AnimatorSet set = new AnimatorSet();
        set.playTogether(animator, animator2);
        set.setDuration(1000);
        set.start();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        if(mode == MeasureSpec.AT_MOST){
            Log.e("mode 확인", "AT_MOST");
        } else if(mode == MeasureSpec.EXACTLY){
            Log.e("mode", "EXACTLY");
        } else if(mode == MeasureSpec.UNSPECIFIED){
            Log.e("mode", "UNSPECIFIED");
        }

        Log.e("onMeasure확인", width+":"+height+":"+mode);
        Log.e("getwidth", getWidth()+"");
        Log.e("getHeight", getHeight()+"");
        Log.e("getMeasuedwidth", getMeasuredWidth()+"");
        Log.e("getMeasuedHeight", getMeasuredHeight()+"");
        Log.e("getdefaultwidthsize", getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec)+"");
        Log.e("getdefaulthegithsize", getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec)+"");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("언제 뜨는가", "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("언제 뜨는가", "onDraw");
    }

    /**
     * 클릭을 사용하지 않고 터치를 사용할 경우 코드에서 따로 클릭 이벤트를 등록 가능하다.
     \     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        animateButton();
        return true;
    }

}
