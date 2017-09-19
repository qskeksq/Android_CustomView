package com.example.administrator.customview3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 뷰 위젯2.
 * 레인보우 프로그레스바
 */

public class RainBowProgress extends View {

    private int MAX = 100;
    private int curPos = 0;
    private Paint paint;
    private Shader shader;


    public RainBowProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint();
    }

    /**
     * 전체 값
     * @return
     */
    public int getMAX() {
        return MAX;
    }

    public void setMAX(int MAX) {
        this.MAX = MAX;
        invalidate();
    }

    /**
     * 현재 값
     * @return
     */
    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        if(curPos > MAX || curPos < 0){
            return;
        }
        this.curPos = curPos;
        invalidate();
    }

    /**
     * Paint 값 설정
     */
    private void setShader(int progressHeight) {
        int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
        shader = new LinearGradient(0, 0, 100, progressHeight, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    /**
     * '전체' 크기값만 설정한다 정하기
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 100;
        int height = 300;

        switch (MeasureSpec.getMode(widthMeasureSpec)){
            case MeasureSpec.AT_MOST:
                width = Math.min(MeasureSpec.getSize(widthMeasureSpec), width);
                break;
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getMode(widthMeasureSpec);
                break;
        }

        switch (MeasureSpec.getMode(heightMeasureSpec)){
            case MeasureSpec.AT_MOST:
                height = Math.min(MeasureSpec.getSize(heightMeasureSpec), height);
                break;
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * Pos 에 맞게 그려주기
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 그려줄 크기만큼 셰이더 설정
        int progressHeight = getHeight()-(getPaddingTop()+getPaddingBottom());
        setShader(progressHeight);

        // 그려질 영역 설정
        Rect rect = new Rect();
        rect.left = getPaddingLeft();
        rect.right = getWidth() - getPaddingRight();
        // 좌표는 왼쪽 맨 위가 (0,0) 임을 잊지 말자!! 헷갈릴 수 있다.
        rect.bottom = getPaddingTop()+progressHeight;
        // 위에서부터 아래로 올라와야 하기 때문에 뺴주는 것. 위치 조심하자
        rect.top = rect.bottom - progressHeight*curPos/MAX;
        canvas.drawRect(rect, paint);
    }
}
