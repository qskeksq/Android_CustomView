package com.example.administrator.customview3.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.example.administrator.customview3.R;

/**
 * 커스텀 위젯2.
 * 타이핑 소리나는 위젯
 */

public class SoundEdit extends AppCompatEditText {

    SoundPool mPool;
    float volume, speed;
    int sound;

    public SoundEdit(Context context) {
        super(context);
        init(context);
    }

    public SoundEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        getAttrs(attrs);
    }

    /**
     * 초기화
     */
    private void init(Context context){
        mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mPool.load(context, R.raw.a, 1);
        mPool.load(context, R.raw.b, 2);
    }

    /**
     * attrs 설정
     * @param set
     */
    private void getAttrs(AttributeSet set){
        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.SoundEdit);
        setTypedArray(ta);
    }

    private void setTypedArray(TypedArray ta){
        volume = ta.getFloat(R.styleable.SoundEdit_volume, 1);
        speed = ta.getFloat(R.styleable.SoundEdit_volume, 1);
        sound = ta.getInt(R.styleable.SoundEdit_sound, 1);
        ta.recycle();
    }

    /**
     * 텍스트가 변경될 경우 소리
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(mPool != null){
            mPool.play(sound, volume, volume, 0, 0, speed);
        }
    }
}
