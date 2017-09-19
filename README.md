# CustomView

## 1. 기본 위젯 상속(extends 위젯)

 - attrs.xml 에 declare-styleable 지정
 - extends 위젯
 - TypedArray 값 설정
 - 원하는 기능 추가


 #### 애니메이션 버튼

 ```java
 // AttributeSet으로부터 TypedArray 가져오기
 private void getAttrs(AttributeSet attrs){
       // 1. attrs.xml 에 정의된 속성을 가져온다
       TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AniButton);
       setTypedArray(ta);
   }
```
```java
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
 ```

 #### CompoundDrawable TextView
 ```java
 // attrs로부터 방향과 이미지를 받아옴
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

 ```

 #### 정사각형 버튼
 ```java
 @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);

      int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
      int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

      int mMODE = MeasureSpec.getMode(widthMeasureSpec);
      int hMODE = MeasureSpec.getMode(heightMeasureSpec);

      switch (mMODE){
          // 부모 크기가 여유를 부여하는 경우
          case MeasureSpec.AT_MOST:
              Log.e("mMODE", "AT_MOST");
              break;
          // 부모가 정해진 크기를 부여하는 경우
          case MeasureSpec.EXACTLY:
              Log.e("mMODE", "EXACTLY");
              break;
      }

      switch (hMODE){
          case MeasureSpec.AT_MOST:
              Log.e("hMODE", "AT_MOST");
              break;
          case MeasureSpec.EXACTLY:
              Log.e("hMODE", "EXACTLY");
              break;
      }
      /**
       * getWidth -- 그려지고 난 후의 크기
       * getMeasuredWidth(); -- 뷰 자체의 크기
       * getDefaultSize -- 부모가 정해주는 여유 있는 크기
       */

      // 정사각형 그리기
      if(width > height){
          width = height;
      } else if(height > width){
          height = width;
      }

      setMeasuredDimension(width, height);
  }

 ```

 #### 소리나는 EditText
 ```java
 // attrs에서 볼륨, 출력속도, 소리를 받아옴
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
 ```


## 2. 복합 위젯(extends 레이아웃)
  - 레이아웃 상속
  - 방법1. 위젯을 new 한 후 param&위치 설정
  - 방법2. LayoutInflater로 xml에 정의한 복합 위젯을 인플레이션
  - 리스너 등록, 커스텀 메소드 작성

#### 문자 개수를 리턴하는 EditText(EditText+TextView)
- extends LinearLayout

```java
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
```

#### 레이팅바
```java
public void init(){
     view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.ratingbar, this, false);
     star1 = (Button) view.findViewById(R.id.button);
     star2 = (Button) view.findViewById(R.id.button2);
     star3 = (Button) view.findViewById(R.id.button3);
 }

 public void setListener(){
     star1.setOnClickListener(this);
     star2.setOnClickListener(this);
     star3.setOnClickListener(this);
 }
```

## 3. View 상속
  - onAttachToWindow와 생성자에서 초기값 설정
  - onMeasure에서 뷰 크기 측정
  - onLayout에서 뷰 안에 그릴 위치 설정
  - onDraw에서 원하는 모양을 그려줌
  - onTouch에서 사용자 인터페이스 등록

#### LaunchPad

> (1) 생성자

```java
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
```

>  (2) 초기값

```java
@Override
protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    setSoundPool();
}
//onAttachedToWindow의 경우 onDetachedFromWindow와 연동하자
```
```java
@Override
protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mSoundPool.release();
}
```

> (3) onMeasure()

```java

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
```

> (4) onLayout

```java

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
```

> (5) onDraw()

```java
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    for (int i = 0; i < PAD_NUM; i++) {
        canvas.drawRect(rects[i], padPaintList[i]);
    }
}
```

> (6) onTouch() - 부가

```java
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
```

#### RainbowDivider

> 외부 값 세팅

```java
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
```

> onMeasure()

```java
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
```

> onDraw()

```java
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
```

#### Piano
```java

```

#### Calendar
```java

```
