package com.lanying.coolhorizontalprogressbarwithprogress.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.lanying.coolhorizontalprogressbarwithprogress.R;

/**
 * Created by lanying on 2016/11/14.
 * 圆形炫酷进度条
 */
public class CoolRoundProgressBarWithProgress extends CoolHorizontalProgressBarWithProgress{

    private int mRadius = dp2px(30);// 默认30dp的半径
    private int mMaxPaintWidth ;// ReachHeight和UnReachHeight中较大的值，便于计算控件大小

    public CoolRoundProgressBarWithProgress(Context context) {
        this(context,null);
    }

    public CoolRoundProgressBarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CoolRoundProgressBarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mReachHeight = (int) (mUnReachHeight * 2.5f);// 默认2.5倍，好看

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CoolRoundProgressBarWithProgress);

        mRadius = (int) ta.getDimension(R.styleable.CoolRoundProgressBarWithProgress_radius,mRadius);

        ta.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);// 抖动显示，用于以较少的颜色提供更多颜色的显示假象
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 笔尖为圆头

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 完全自己实现该方法


        mMaxPaintWidth = Math.max(mReachHeight,mUnReachHeight);

        // 默认宽高值一致
        int expect = mRadius * 2 + mMaxPaintWidth/*左右各一半*/+ getPaddingLeft() +getPaddingRight();

        int width = resolveSize(expect,widthMeasureSpec);// 根据MeasureSpec和约束和期望值求出可得的合理值
        int height = resolveSize(expect,heightMeasureSpec);

        int realWidth = Math.min(width,height);// 宽高中以小的为准

        mRadius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;

        setMeasuredDimension(realWidth,realWidth);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(getPaddingLeft()+mMaxPaintWidth/2,getPaddingTop()+mMaxPaintWidth/2);

        mPaint.setStyle(Paint.Style.STROKE);// 切换到绘制线的风格

        // draw unReach bar
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeight);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);

        // draw reach bar
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        int sweepAngle = (int) (getProgress() * 1.0f / getMax() * 360);// 划过的角度值
        canvas.drawArc(new RectF(0,0,mRadius*2,mRadius*2),0,sweepAngle,false,mPaint);

        // draw text
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        //float textHeight = mPaint.descent()-mPaint.ascent();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);// 绘制文字需要Style.FILL风格！
        canvas.drawText(text,mRadius-textWidth/2,mRadius+mPaint.descent(),mPaint);

        canvas.restore();
    }
}


