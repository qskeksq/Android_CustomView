package com.example.administrator.customview3.composite;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 복합 위젯1.
 * 글자수를 자동으로 세어주는 위젯
 */

public class NumEdit extends LinearLayout implements TextWatcher {

    EditText editText;
    TextView textView;

    public NumEdit(Context context) {
        super(context);
        init(context);
    }

    public NumEdit(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setListener();
    }

    private void init(Context context){
        setOrientation(LinearLayout.VERTICAL);
        editText = new EditText(context);
        textView = new TextView(context);

        editText.setHint("문자수 리턴 EditText");

        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        addView(editText, params);
        addView(textView, params);
    }

    private void setListener(){
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        textView.setText("글자수 : "+charSequence.length());
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
