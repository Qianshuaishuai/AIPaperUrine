package com.xinxin.aicare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xinxin.aicare.R;

public class HalfCircleProgressView extends View {

    Paint mPaint;
    RectF mRectF;
    //颜色以及宽度
    private int mBackgroudColor;
    private int mProgressColor;
    private float mCircleWidth;
    private int mTextSize;
    private int mTextColor;
    private int mProgress = 0;
    private int mValue = 0;
    private String mName;
    private int mPadding;

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);//绘图为描边模式
        mPaint.setStrokeWidth(30);//画笔宽度
        mPaint.setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        //得到画布一半的宽度
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        //定义圆的半径
        float radius = Math.min(halfWidth, height) - mCircleWidth;
        //移动坐标
        canvas.translate(halfWidth, height - mPadding);
        //定义一个圆  (float left, float top, float right, float bottom)
        mRectF = new RectF(-radius, -radius, radius, radius);
        mPaint.setColor(mBackgroudColor);
        //绘制圆弧，从9点方向（-180度）开始绘制，偏移角度为进度
        canvas.drawArc(mRectF, -180, 180, false, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorCommon));
        //绘制圆弧，从9点方向（-180度）开始绘制，偏移角度为进度
        canvas.drawArc(mRectF, -180, mProgress, false, mPaint);

        Paint textPaint = new Paint();          // 创建画笔
        textPaint.setColor(mTextColor);        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(mTextSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mName == null ? "XXXXXX" : mName, 0, 0, textPaint);
        canvas.translate(0, -height / 3);
//        canvas.drawText(mValue + "%", 0, 0, textPaint);
    }

    public HalfCircleProgressView(Context context) {
        this(context, null);
        init();
    }

    public HalfCircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public HalfCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray at = context.obtainStyledAttributes(attrs, R.styleable.HalfCircleProgressView, defStyleAttr, 0);
//        获取自定义属性和默认值
//        getColor方法的第一个参数是我们在XML文件中定义的颜色，如果我们没有给我们自定义的View定义颜色，他就会使用第二个参数中的默认值
        mBackgroudColor = at.getColor(R.styleable.HalfCircleProgressView_backgroudColor, Color.argb(32, 10, 10, 10));
        mProgressColor = at.getColor(R.styleable.HalfCircleProgressView_progressColor, Color.BLUE);
        mCircleWidth = at.getDimensionPixelSize(R.styleable.HalfCircleProgressView_circleWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        mTextColor = at.getColor(R.styleable.HalfCircleProgressView_textColors, Color.BLACK);
        mTextSize = at.getDimensionPixelSize(R.styleable.HalfCircleProgressView_textSizes,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        mName = at.getString(R.styleable.HalfCircleProgressView_name);
        mPadding = at.getDimensionPixelSize(R.styleable.HalfCircleProgressView_padding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));

    }

    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }

    public void setValue(int value) {
        mValue = value;
        postInvalidate();
    }

}