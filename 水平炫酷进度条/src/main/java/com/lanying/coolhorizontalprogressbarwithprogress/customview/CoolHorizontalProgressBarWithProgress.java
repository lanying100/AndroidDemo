package com.lanying.coolhorizontalprogressbarwithprogress.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.lanying.coolhorizontalprogressbarwithprogress.R;

/**
 * Created by lanying on 2016/11/12.
 * 炫酷水平进度条
 */
public class CoolHorizontalProgressBarWithProgress extends ProgressBar{

    // 默认值
    private static final int DEFAULT_REACH_COLOR = 0xFFFF0000;
    private static final int DEFAULT_REACH_HEIGHT = 2;// dp
    private static final int DEFAULT_UNREACH_COLOR = 0x44FF0000;
    private static final int DEFAULT_UNREACH_HEIGHT = 2;//dp
    private static final int DEFAULT_TEXT_COLOR = DEFAULT_REACH_COLOR;
    private static final int DEFAULT_TEXT_SIZE = 15;//sp
    private static final int DEFAULT_TEXT_OFFSET = 10;//dp

    protected int mReachColor = DEFAULT_REACH_COLOR;
    protected int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    protected int mUnReachColor = DEFAULT_UNREACH_COLOR;
    protected int mUnReachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    protected Paint mPaint = new Paint();
    private int mRealWidth;// progressbar 去掉左右边距后的宽度


    public CoolHorizontalProgressBarWithProgress(Context context) {
        this(context,null);// 一定要改成this
    }

    public CoolHorizontalProgressBarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);// 一定要改成this
    }

    public CoolHorizontalProgressBarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyableAttrs(attrs);// 另外两个构造方法已经修改为调用该方法了
    }


    // 解析xml布局的属性值，并设置到控件上
    private void obtainStyableAttrs(AttributeSet attrs){
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CoolHorizontalProgressBarWithProgress);

        mReachColor = ta.getColor(R.styleable.CoolHorizontalProgressBarWithProgress_progress_reach_color,mReachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.CoolHorizontalProgressBarWithProgress_progress_reach_height,mReachHeight);
        mUnReachColor = ta.getColor(R.styleable.CoolHorizontalProgressBarWithProgress_progress_unreach_color,mUnReachColor);
        mUnReachHeight = (int) ta.getDimension(R.styleable.CoolHorizontalProgressBarWithProgress_progress_unreach_height,mUnReachHeight);
        mTextColor = ta.getColor(R.styleable.CoolHorizontalProgressBarWithProgress_progress_text_color,mTextColor);
        mTextSize = (int) ta.getDimension(R.styleable.CoolHorizontalProgressBarWithProgress_progress_text_size,mTextSize);
        mTextOffset = (int) ta.getDimension(R.styleable.CoolHorizontalProgressBarWithProgress_progress_text_offset,mTextOffset);

        ta.recycle();

        mPaint.setTextSize(mTextSize);// 给画笔设置文字大小，便于后面测量宽高
        //mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 笔尖是圆头的，为啥没效果？
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setAntiAlias(true);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 不要父类实现方法，自己决定控件的宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);// 进度条的宽度比较特殊，必须要父类指定一个值，所以没必要区分Mode了
        int height = measureHeight(heightMeasureSpec);

        setMeasuredDimension(width,height);

        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();// 进度条所占据的真实宽度
    }


    // 计算出进度条的高度
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if(mode == MeasureSpec.EXACTLY){// match_parent 或 具体的数值
            result = size;

        }else{
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());// 文字的高度
            result = getPaddingTop() + getPaddingBottom()
                    + Math.max(Math.max(mReachHeight,mUnReachHeight),textHeight);

            if(mode == MeasureSpec.AT_MOST){// 如果是wrap_content，则取最小值
                result = Math.min(size,result);
            }
        }

        return result;
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // 也不要父类的实现方法，完全自己绘制
        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);// 平移到进度条绘制的起始位置，高度约么着在中间位置，忽略了画笔的粗细（笔触宽度）

        // 开始绘制
        // boolean isNeedUnReach = true;// 是否需要unReach部分（进度是否快满了）——优化掉它

        float ratio = 1.0F * getProgress() / getMax();// 进度条当前进度的比例 0 ~ 1
        String text = getProgress()+"%";
        int reachWidth = (int) ((mRealWidth - mPaint.measureText("100%") - mTextOffset) * ratio);// 测量100%的宽度是为了避免99%跳到100%的时候，进度条反而退了一个字符的距离

        // 绘制reach部分
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        canvas.drawLine(0,0,reachWidth,0,mPaint);// 因为已经平移过，所以此处y为0

        // 绘制text部分
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        canvas.drawText(text,reachWidth + mTextOffset,mPaint.descent(),mPaint);// 起点已经在基准线上了，所以y值为mPaint.descent()，正值，向下移动

        // 绘制unreach部分
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeight);

        // 判断进度条是否已满 (起点大于终点时竟然也会绘制)
        int progressX = (int) (reachWidth + mPaint.measureText(text) + mTextOffset*2);// 左侧和右侧两个间距
        Log.d("lanying",progressX +" --> "+mRealWidth);
        if(progressX < mRealWidth) {
            canvas.drawLine(progressX, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();

    }

    protected int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,
                getResources().getDisplayMetrics());// 根据“显示度量”来得到屏幕的分辨率，从而进行dp-->px的转换
    }


    protected int sp2px(int spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal,
                getResources().getDisplayMetrics());// 根据“显示度量”来得到屏幕的分辨率，从而进行sp-->px的转换
    }
}
