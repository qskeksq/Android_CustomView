package com.example.administrator.customview3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 뷰 위젯4.
 * 피아노
 */

public class Piano extends View {

    // 건반 수, 간격
    private int WHITE_KEY_NUM = 7;
    private int BLACK_KEY_NUM = 5;
    private float KEY_PADDING = 0.02F;
    private Rect[] whiteKeys = new Rect[WHITE_KEY_NUM];
    private Rect[] blackKeys = new Rect[BLACK_KEY_NUM];

    // 건반 색
    private Paint whiteKeyPaint = new Paint();
    private Paint blackKeyPaint = new Paint();
    private Paint pressedWhiteKey = new Paint();
    private Paint pressedBlackKey = new Paint();
    private Paint[] whitePaints = new Paint[WHITE_KEY_NUM];
    private Paint[] blackPaints = new Paint[BLACK_KEY_NUM];

    // 실제 크기
    int width, keyWidth;
    int height;
    int padding, paddings;

    public Piano(Context context) {
        super(context);
    }

    public Piano(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRect();
        initPaint();
    }

    /**
     * Rect 초기화
     */
    private void initRect(){
        for (int i = 0; i < WHITE_KEY_NUM; i++) {
            whiteKeys[i] = new Rect();
        }
        for (int i = 0; i < BLACK_KEY_NUM; i++) {
            blackKeys[i] = new Rect();
        }
    }

    /**
     * Paint 초기화
     */
    private void initPaint(){
        for (int i = 0; i < WHITE_KEY_NUM; i++) {
            whitePaints[i] = new Paint();
            whitePaints[i].setColor(Color.WHITE);
        }
        for (int i = 0; i < BLACK_KEY_NUM; i++) {
            blackPaints[i] = new Paint();
            blackPaints[i].setColor(Color.BLACK);
        }
    }

    /**
     * 각 건반 크기 측정
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getDefaultSize(getSuggestedMinimumHeight(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        padding = ((int) (width*KEY_PADDING))/(WHITE_KEY_NUM+1);
        paddings = (int) (width*KEY_PADDING);
        keyWidth = (width-paddings)/(WHITE_KEY_NUM);

        setMeasuredDimension(width, height);
    }

    /**
     * 건반 위치 지정
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int column = 0; column < WHITE_KEY_NUM; column++) {
            Rect rect = whiteKeys[column];
            rect.left = padding*(column+1) + keyWidth*column;
            rect.top = getPaddingTop();
            rect.bottom = height-getPaddingBottom();
            rect.right = padding*(column+1) + keyWidth*(column+1);
        }
    }

    /**
     * 그려줌
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 임시 구분용
        setBackgroundColor(Color.BLACK);
        for (int i = 0; i < WHITE_KEY_NUM; i++) {
            canvas.drawRect(whiteKeys[i], whitePaints[i]);
        }

    }
}
