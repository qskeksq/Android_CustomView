package com.example.administrator.customview3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.customview3.R;

import java.util.Arrays;

import static android.graphics.Color.BLACK;

/**
 * Created by Administrator on 2017-09-21.
 */

public class Piano2 extends View {

    /**
     *  필요한 자원 선언, 초기화
     */
    // 흰건반, 검은건반, 허용손가락 수, 검은건반 비율
    public static final int MAX_FINGERS = 5;
    public static final int WHITE_KEYS_COUNT = 7;
    public static final int BLACK_KEYS_COUNT = 5;
    public static final float BLACK_WIDTH_RATIO = 0.625f;
    public static final float BLACK_HEIGHT_RATIO = 0.54f;

    // 흰, 검은 건반 영역
    private Rect[] whiteRects = new Rect[WHITE_KEYS_COUNT];
    private Rect[] blackRects = new Rect[BLACK_KEYS_COUNT];

    // 눌러진 포인트(건반), 눌러진 건반 소리 임시 저장소
    private Point[] mFingersPoints = new Point[MAX_FINGERS];
    private int[] mFingerTones = new int[MAX_FINGERS];
    private int[] whiteSounds = new int[7];
    private int[] blackSounds = new int[5];

    // 소리풀
    private SoundPool soundPool;

    // 포인트 구분
    private MotionEvent.PointerCoords mPointerCoords;

    // 페인트
    private Paint[] whitePaints = new Paint[WHITE_KEYS_COUNT];
    private Paint[] blackPaints = new Paint[BLACK_KEYS_COUNT];

    // 전체 가로 세로
    private int totalWidth;
    private int totalHeight;
    // 흰건반 가로
    private int whiteKeyWidth;
    // 검은건반 가로 세로
    private int blackKeyWidth;
    private int blackKeyHeight;


    private SparseIntArray mToneToIndexMap = new SparseIntArray();

    /**
     * 생성자
     */
    public Piano2(Context context) {
        super(context);
    }

    public Piano2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mPointerCoords = new MotionEvent.PointerCoords();
        // 각 배열 초기화
        setRects();
        setPaints();
        loadSound();
        Arrays.fill(mFingersPoints, null);
        Arrays.fill(mFingerTones, -1);
    }

    /**
     * 사각형 초기화
     */
    private void setRects(){
        for (int i = 0; i < WHITE_KEYS_COUNT; i++) {
            whiteRects[i] = new Rect();
        }
        for (int i = 0; i < BLACK_KEYS_COUNT; i++) {
            blackRects[i] = new Rect();
        }
    }

    /**
     * 페인트 설정
     */
    private void setPaints(){
        for (int i = 0; i < WHITE_KEYS_COUNT; i++) {
            whitePaints[i] = new Paint();
            whitePaints[i].setColor(Color.WHITE);
        }
        for (int i = 0; i < BLACK_KEYS_COUNT; i++) {
            blackPaints[i] = new Paint();
            blackPaints[i].setColor(BLACK);
        }
    }

    /**
     * 소리 설정
     */
    private void loadSound(){
        soundPool = new SoundPool(0, AudioTrack.MODE_STREAM, 0); //1

        mToneToIndexMap.put(R.raw.a, whiteSounds[0] = soundPool.load(getContext(), R.raw.a, 1));
        mToneToIndexMap.put(R.raw.b, whiteSounds[1] = soundPool.load(getContext(), R.raw.b, 1));
        mToneToIndexMap.put(R.raw.c, whiteSounds[2] = soundPool.load(getContext(), R.raw.c, 1));
        mToneToIndexMap.put(R.raw.d, whiteSounds[3] = soundPool.load(getContext(), R.raw.d, 1));
        mToneToIndexMap.put(R.raw.e, whiteSounds[4] = soundPool.load(getContext(), R.raw.e, 1));
        mToneToIndexMap.put(R.raw.f, whiteSounds[5] = soundPool.load(getContext(), R.raw.f, 1));
        mToneToIndexMap.put(R.raw.g, whiteSounds[6] = soundPool.load(getContext(), R.raw.g, 1));
        mToneToIndexMap.put(R.raw.aa, blackSounds[0] = soundPool.load(getContext(), R.raw.aa, 1));
        mToneToIndexMap.put(R.raw.bb, blackSounds[1] = soundPool.load(getContext(), R.raw.bb, 1));
        mToneToIndexMap.put(R.raw.cc, blackSounds[2] = soundPool.load(getContext(), R.raw.cc, 1));
        mToneToIndexMap.put(R.raw.dd, blackSounds[3] = soundPool.load(getContext(), R.raw.dd, 1));
        mToneToIndexMap.put(R.raw.ee, blackSounds[4] = soundPool.load(getContext(), R.raw.ee, 1));
    }

    /**
     * soundPool 해제
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        soundPool.release();
    }

    /**
     * 크기 설정
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 전체 가로 세로
        totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        totalHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 흰건반 가로
        whiteKeyWidth = totalWidth/WHITE_KEYS_COUNT;
        // 검은건반 가로 세로
        blackKeyWidth = (int) (whiteKeyWidth*BLACK_WIDTH_RATIO);
        blackKeyHeight = (int) (totalHeight*BLACK_HEIGHT_RATIO);
    }

    /**
     * 건반 위치 설정
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        blackRects[0].set(whiteKeyWidth*1-blackKeyWidth/2, 0, whiteKeyWidth*1+blackKeyWidth/2, blackKeyHeight);
        blackRects[1].set(whiteKeyWidth*2-blackKeyWidth/2, 0, whiteKeyWidth*2+blackKeyWidth/2, blackKeyHeight);
        blackRects[2].set(whiteKeyWidth*4-blackKeyWidth/2, 0, whiteKeyWidth*4+blackKeyWidth/2, blackKeyHeight);
        blackRects[3].set(whiteKeyWidth*5-blackKeyWidth/2, 0, whiteKeyWidth*5+blackKeyWidth/2, blackKeyHeight);
        blackRects[4].set(whiteKeyWidth*6-blackKeyWidth/2, 0, whiteKeyWidth*6+blackKeyWidth/2, blackKeyHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < WHITE_KEYS_COUNT; i++) {
            canvas.drawRect(whiteRects[i], whitePaints[i]);
        }
        for (int i = 0; i < BLACK_KEYS_COUNT; i++) {
            canvas.drawRect(blackRects[i], blackPaints[i]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 전체 눌려진 개수
        int pointerCount = event.getPointerCount();
        // 5개를 넘어가면 5를 리턴
        int cappedPointerCount = pointerCount > MAX_FINGERS ? MAX_FINGERS : pointerCount;

        // 액션 종류
        int action = event.getActionMasked();
        // 바뀌는 포인터 값
        int actionIndex = event.getActionIndex();
        // 고정된 포인터 값
        int id = event.getPointerId(actionIndex);

        // 눌려졌을 경우
        if((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_POINTER_DOWN) && id < MAX_FINGERS){
            // 바뀌는 포인터 값으로 현재 눌린 포인터를 구하고, 바뀌지 않는 인덱스 번호로 맵핑을 시켜 저장한다.
            mFingersPoints[id] = new Point((int)event.getX(actionIndex), (int) event.getY(actionIndex));
            Log.e("터치다운", event.getX(actionIndex)+":"+event.getY(actionIndex));
        // 뗐을 경우
        } else if((action == MotionEvent.ACTION_UP) || (action == MotionEvent.ACTION_POINTER_UP) && id < MAX_FINGERS){
            mFingersPoints[id] = null;
            invalidateKey(mFingerTones[id]);
            mFingerTones[id] = -1;
        }


        for (int i = 0; i < cappedPointerCount; i++) {
            int index = event.findPointerIndex(i);
            if(mFingersPoints[i] != null && index != -1){
                mFingersPoints[i].set((int) event.getX(), (int) event.getY());
                int tone = getToneForPoint(mFingersPoints[i]);
                if(tone != mFingerTones[i] && tone != -1){
                    invalidateKey(mFingerTones[i]);
                    mFingerTones[i] = tone;
                    invalidateKey(mFingerTones[i]);
                    if(!isKeyDown(i)){
                        int poolIndex = mToneToIndexMap.get(mFingerTones[i]);
                        event.getPointerCoords(poolIndex, mPointerCoords);
                        float volume = mPointerCoords.getAxisValue(MotionEvent.AXIS_PRESSURE);
                        volume = volume > 1f ? 1f : volume;
                        soundPool.play(poolIndex, volume, volume, 0, 0, 1f);
                    }
                }
            }
        }
        updatePaints();
        return true;
    }

    private boolean isKeyDown(int finger){
        int key = getToneForPoint(mFingersPoints[finger]);

        for (int i = 0; i < mFingersPoints.length; i++) {
            if(i != finger){
                Point fingerPoint = mFingersPoints[i];
                if(fingerPoint != null){
                    int otherKey = getToneForPoint(fingerPoint);
                    if(otherKey == key){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void updatePaints(){
        for (int i = 0; i < WHITE_KEYS_COUNT; i++) {
            whitePaints[i].setColor(Color.WHITE);
        }
        for (int i = 0; i < BLACK_KEYS_COUNT; i++) {
            blackPaints[i].setColor(Color.BLACK);
        }

        for(Point fingerPoint : mFingersPoints){
            if(fingerPoint != null){
                for (int i = 0; i < WHITE_KEYS_COUNT; i++) {
                    if(whiteRects[i].contains(fingerPoint.x, fingerPoint.y)){
                        whitePaints[i].setColor(Color.CYAN);
                    }
                }
                for (int i = 0; i < BLACK_KEYS_COUNT; i++) {
                    if(blackRects[i].contains(fingerPoint.x, fingerPoint.y)){
                        blackPaints[i].setColor(Color.CYAN);
                    }
                }
            }
        }
        invalidate();
    }


    private void invalidateKey(int tone){
        switch (tone){
            case R.raw.a:
                invalidate(whiteRects[0]);
                break;
            case R.raw.b:
                invalidate(whiteRects[1]);
                break;
            case R.raw.c:
                invalidate(whiteRects[2]);
                break;
            case R.raw.d:
                invalidate(whiteRects[3]);
                break;
            case R.raw.e:
                invalidate(whiteRects[4]);
                break;
            case R.raw.f:
                invalidate(whiteRects[5]);
                break;
            case R.raw.g:
                invalidate(whiteRects[6]);
                break;
            case R.raw.aa:
                invalidate(blackRects[0]);
                break;
            case R.raw.bb:
                invalidate(blackRects[1]);
                break;
            case R.raw.cc:
                invalidate(blackRects[2]);
                break;
            case R.raw.dd:
                invalidate(blackRects[3]);
                break;
            case R.raw.ee:
                invalidate(blackRects[4]);
                break;
        }
    }

    private int getToneForPoint(Point point){
        for (int i = 0; i < WHITE_KEYS_COUNT; i++) {
            if(whiteRects[i].contains(point.x, point.y)){
                return whiteSounds[i];
            }
        }
        for (int i = 0; i < BLACK_KEYS_COUNT; i++) {
            if(blackRects[i].contains(point.x, point.y)){
                return blackSounds[i];
            }
        }
        return -1;
    }
}
