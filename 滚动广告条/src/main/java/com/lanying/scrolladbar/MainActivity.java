package com.lanying.scrolladbar;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 2.模拟滚动广告条 4分
 ①	每隔两秒自动切换下一张
 ②	用户可以无限左滑右滑
 ③	用户滑动时不可以自动切换
 ④	附加功能：点击底部的圆点，可以切换到相应页面，小心bug…… +2分

 分析：
 1、数据源及容器
    1）文字——TextView
    2）图片——ImageView——ViewPager
    3）圆点——ImageView——LinearLayout
 2、

 */
public class MainActivity extends AppCompatActivity {
    private static final int SIGNAL_NEXT = 0;
    // 数据源
    private String[] titles = {
            "武媚娘传奇 - 大结局",
            "神曲《小苹果》",
            "疯狂漫画",
            "痛的领悟！每年过节必心塞",
            "加拿大登山家攀冰史诗"
    };
    private int[] imageIds = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5
    };

    private TextView tvTitle;

    private ImageView[] images = new ImageView[titles.length];
    private ViewPager mViewPager;

    private ImageView[] dots = new ImageView[titles.length];
    private LinearLayout dots_container;

    private MyPagerAdapter mPagerAdapter;

    private MyHandler mHandler;

    private int currentPage;
    private boolean byMe = false;// 是否由我setCurrentItem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setListener();
    }

    private void init() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        dots_container = (LinearLayout) findViewById(R.id.dot_container);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        // 初始化圆点
        for(int i = 0;i < dots.length;i ++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 15;
            dots[i] = new ImageView(MainActivity.this);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.dot_selector);
            dots[i].setEnabled(true);
            dots[i].setTag(i);
            dots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击圆点，跳转到相应Page
                    //1、算出当前页面所处的那一波循环的第一页
                    int temp = currentPage - currentPage%imageIds.length;// 所处循环的第一页的下标
                    //2、计算目标页
                    temp += (Integer)v.getTag();

                    System.out.println(temp +" -- "+currentPage);

                    //3、判断点击的是当前页的前面还是后面，一页页跳过去
                    byMe = true;
                    if(currentPage < temp){
                        for(;temp - currentPage>0;){
                            mViewPager.setCurrentItem(++currentPage,false);
                            System.out.println("++++++");
                        }
                    }else{
                        for(;currentPage - temp >0;){
                            mViewPager.setCurrentItem(--currentPage,false);
                            System.out.println("------");
                        }

                    }
                    byMe = false;
                }
            });
            dots_container.addView(dots[i]);
        }

        // 初始化图片
        for(int i = 0; i < imageIds.length ; i ++){
            images[i] = new ImageView(MainActivity.this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,220);
//            images[i].setLayoutParams(params);

            images[i].setImageResource(imageIds[i]);
            images[i].setBackgroundColor(Color.RED);
        }

        mPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        changeDot(0);// 初始化为第一个圆点被选中

        //无限循环版修改处——3
        currentPage = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageIds.length;
        mViewPager.setCurrentItem(currentPage,false);

        mHandler = new MyHandler(MainActivity.this);

        startMove();
        //startTimer();
    }

    private void startMove() {
        mHandler.sendEmptyMessageDelayed(SIGNAL_NEXT,2000);
    }

//    /**
//     * 开启定时器
//     */
//    private void startTimer() {
//        new Thread(){
//            @Override
//            public void run() {
//                while(true) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    mHandler.sendEmptyMessage(SIGNAL_NEXT);
//                }
//            }
//        }.start();
//    }


    private void setListener(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(titles[position%titles.length]);
                changeDot(position%dots.length);
                if(!byMe) {
                    currentPage = position;
                }
                System.out.println("======onPageSelected======");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch(state){
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        // 用户正在拖拽
                        mHandler.removeMessages(SIGNAL_NEXT);
                        System.out.println("====SCROLL_STATE_DRAGGING===");
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if(!mHandler.hasMessages(SIGNAL_NEXT)){
                            startMove();
                        }
                        System.out.println("====SCROLL_STATE_IDLE===");
                        break;
                }
            }
        });
    }

    /**
     * 选中目标位置的圆点
     * @param position
     */
    private void changeDot(int position){
        for(ImageView dot : dots){
            dot.setEnabled(true);
        }
        dots[position%titles.length].setEnabled(false);
        tvTitle.setText(titles[position%titles.length]);
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            //不循环
            //return images.length;

            //无限循环版修改处——1
            return Integer.MAX_VALUE;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //无限循环版修改处——2
            container.addView(images[position%images.length]);
            return images[position%images.length];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images[position%images.length]);
        }
    }


    private static class MyHandler extends Handler {
        WeakReference<MainActivity> mReference;
        public MyHandler(MainActivity activity){
            mReference = new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mReference.get();
            if(activity != null){
                activity.mViewPager.setCurrentItem(++activity.currentPage,false);
            }
            sendEmptyMessageDelayed(SIGNAL_NEXT,2000);
        }
    }

}
