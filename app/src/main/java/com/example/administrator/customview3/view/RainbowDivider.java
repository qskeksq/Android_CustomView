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
 * 뷰 위젯3.
 * 레인보우 디바이더
 */
public class RainbowDivider extends View {

    private Paint paint;
    private Shader shader;

    private int MAX = 100;
    private int curPos = 0;

    public RainbowDivider(Context context, @Nullable AttributeSet attrs) {
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
     * 크기 설정
     * @param widthMeasureSpec  match_parent
     * @param heightMeasureSpec wrap_content 들어와야 함.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 300;
        int height = 5;

        switch (MeasureSpec.getMode(widthMeasureSpec)){
            case MeasureSpec.AT_MOST:
                width = Math.min(MeasureSpec.getSize(widthMeasureSpec), width);
                break;
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getSize(widthMeasureSpec);
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
     * 셰이더 설정
     * @param width 값 만큼 셰이더가 퍼진다.
     */
    private void setShader(int width){
        int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
        shader = new LinearGradient(0, 0, width, 0, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    /**
     * 그려주기
     * @param canvas
     */
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        // 전체 길이
        int width = getWidth();
        setShader(width);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        // 색칠될 영역 사각형으로 설정
        Rect rect = new Rect();
        rect.left = paddingLeft;
        rect.right = getWidth() - paddingRight;
        rect.bottom = getPaddingTop() + getHeight();
        rect.top = getPaddingTop();

        // 먼저 흰색 바탕으로 도화지를 만들고
        Paint blankPaint = new Paint();
        blankPaint.setColor(Color.WHITE);
        canvas.drawRect(rect, blankPaint);

        // 중심지점
        int center = rect.centerX();

        // 중심으로부터 오른쪽, 왼쪽으로 일정 값만큼씩 그려준다
        rect.right = center+(width/2)*curPos/MAX;
        rect.left = center-(width/2)*curPos/MAX;
        canvas.drawRect(rect, paint);
    }
}
