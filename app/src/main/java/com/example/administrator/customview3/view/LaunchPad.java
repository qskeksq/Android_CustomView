package com.example.administrator.customview3.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.customview3.R;

/**
 * 뷰 위젯1.
 * 런치패트
 */

public class LaunchPad extends View {

    /**
     * 전역 변수에서
     * padPaintList = new Paint[PAD_NUM];
     * rects = new Rect[PAD_NUM];
     * sounds = new int[PAD_NUM];
     *
     * 해주면 전역 변수에서 초기화 한 PAD_NUM 값으로 초기화가 되고
     * PAD_NUM 가 바뀌더라도 생성자보다 먼저 메모리에 올라가기 때문에 계속 0 값으로 초기화 될 수밖에 없는 것이다.
     */

    // 입력된 색상, 길이
    int padColor;
    int selectedColor;

    // 길이값, 개수 설정
    private int PAD_HORI_NUM ;           // 패드 가로 개수
    private int PAD_VERT_NUM ;           // 패트 세로 개수
    private int PAD_NUM = PAD_HORI_NUM*PAD_VERT_NUM;    // 총 패드 개수
    private float PAD_PADDING_RATIO = 0.0625f;          // 패드와 패드 사이 간격
    private Rect[] rects;

    // 실제 길이
    private int width, padWidth;
    private int height, padHeight;
    private int wPadding, paddingWidth;
    private int hPadding, paddingHeight;

    // 페인트
    Paint padPaint = new Paint(Paint.ANTI_ALIAS_FLAG);      // 패드 디폴트 색
    Paint selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 선택시 패트 색
    Paint[] padPaintList;              // 각 패드 페인드

    // 소리
    int[] sounds;         // 각 패드의 소리 리스트
    SoundPool mSoundPool;                     // 소리 풀

    Point point;

    /**
     * 이 생성자는 호출 안 됨.
     * @param context
     */
    public LaunchPad(Context context) {
        super(context);
    }

    public LaunchPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LaunchPad);
        PAD_HORI_NUM = ta.getInt(R.styleable.LaunchPad_rowCount, 4);
        PAD_VERT_NUM = ta.getInt(R.styleable.LaunchPad_columnCount, 4);
        padColor = ta.getColor(R.styleable.LaunchPad_padColor, Color.CYAN);
        selectedColor = ta.getColor(R.styleable.LaunchPad_selectedColor, Color.BLUE);

        PAD_NUM = PAD_HORI_NUM*PAD_VERT_NUM;

        initPaint();
        initRect();
    }

    /**
     * 페인트 초기값 기본 페인트로 세팅
     */
    private void initPaint(){
        padPaintList = new Paint[PAD_NUM];
        padPaint.setColor(padColor);
        selectedPaint.setColor(selectedColor);
        for (int i = 0; i < PAD_NUM; i++) {
            padPaintList[i] = padPaint;
        }
    }

    /**
     * 사각형 초기화
     */
    private void initRect(){
        rects = new Rect[PAD_NUM];
        for (int i = 0; i < PAD_NUM; i++) {
            rects[i] = new Rect();
        }
    }

    /**
     * 초기값 세팅
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSoundPool();
    }

    /**
     * 사운드 풀 설정
     *
     * 미디어 플레이어와 다른 점은 미리 소리를 설정해 두고 호출시 값만 불러오는 것
     * 미디어 플에이어는 호출시 값을 읽어온다.
     */
    private void setSoundPool(){
        sounds = new int[PAD_NUM];
        mSoundPool = new SoundPool(0, AudioTrack.MODE_STREAM, 0); //1
        sounds[0] = mSoundPool.load(getContext(), R.raw.zz, 0);
        sounds[1] = mSoundPool.load(getContext(), R.raw.a, 0);
        sounds[2] = mSoundPool.load(getContext(), R.raw.b, 0);
        sounds[3] = mSoundPool.load(getContext(), R.raw.c, 0);
        sounds[4] = mSoundPool.load(getContext(), R.raw.d, 0);
        sounds[5] = mSoundPool.load(getContext(), R.raw.e, 0);
        sounds[6] = mSoundPool.load(getContext(), R.raw.f, 0);
        sounds[7] = mSoundPool.load(getContext(), R.raw.g, 0);
        sounds[8] = mSoundPool.load(getContext(), R.raw.hh, 0);
        sounds[9] = mSoundPool.load(getContext(), R.raw.ii, 0);
        sounds[10] = mSoundPool.load(getContext(), R.raw.jj, 0);
        sounds[11] = mSoundPool.load(getContext(), R.raw.kk, 0);
        sounds[12] = mSoundPool.load(getContext(), R.raw.ll, 0);
        sounds[13] = mSoundPool.load(getContext(), R.raw.mm, 0);
        sounds[14] = mSoundPool.load(getContext(), R.raw.nn, 0);
        sounds[15] = mSoundPool.load(getContext(), R.raw.oo, 0);
    }


    /**
     * 크기 설정
     * @param widthMeasureSpec  부모 뷰가 허락한 크기, 스펙 모드가 넘어옴
     * @param heightMeasureSpec 부모 뷰가 허락한 크기, 스펙 모드가 넘어옴
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 전체 가로 세로
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        // 패딩값 가로 세로
        wPadding = (int) (width*PAD_PADDING_RATIO);
        hPadding = (int) (height*PAD_PADDING_RATIO);
        // 패딩 1개 값 가로 세로
        paddingWidth = wPadding/(PAD_HORI_NUM+1);
        paddingHeight = hPadding/(PAD_VERT_NUM+1);
        // 패드 가로 세로
        padWidth = (width-wPadding)/PAD_HORI_NUM;
        padHeight = (height-hPadding)/PAD_VERT_NUM;
    }

    /**
     * 좌표값 설정
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < PAD_NUM; i++) {

            int curRow = (i+1)%PAD_HORI_NUM;
            int curCol = (i)/PAD_HORI_NUM;

            Rect rect = rects[i];
            rect.left = ((curCol+1)*paddingWidth)+(curCol*padWidth);
            rect.right = ((curCol+1)*paddingWidth)+((curCol+1)*padWidth);
            rect.top = ((curRow+1)*paddingHeight)+(curRow*padHeight);
            rect.bottom = ((curRow+1)*paddingHeight)+((curRow+1)*padHeight);
        }
    }

    /**
     * 패드 그리기
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < PAD_NUM; i++) {
            canvas.drawRect(rects[i], padPaintList[i]);
        }
    }

    /**
     * 터치 이벤트
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 포인터가 없는 순수 액션값만 리턴
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // 포인트는 x, y를 저장해 두는 하나의 객체이다(선은 Path, 점은 Point)
                point = new Point((int)event.getX(),(int) event.getY());
                invalidatePad(point, true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                invalidatePad(point, false);
                break;
        }
        // false 는 up을 인식하지 않음.
        return true;
    }

    /**
     * 선택한 자리가 포함되어있는지 확인 후 색을 바꿔줌
     * @param point
     * @param isDown
     */
    private void invalidatePad(Point point, boolean isDown){
        for (int i = 0; i < PAD_NUM; i++) {
            if(rects[i].contains(point.x, point.y)){
                // 눌러졌을 경우만 색을 바꾸고
                if(isDown){
                    padPaintList[i] = selectedPaint;
                    mSoundPool.play(sounds[i],1f,1f,0,0,1);
                } else {
                    // 떼었을 경우 다시 색을 바꿔준다
                    padPaintList[i] = padPaint;
                }
            }
        }
        invalidate();
    }

    /**
     * 풀 해제
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mSoundPool.release();
    }
}
