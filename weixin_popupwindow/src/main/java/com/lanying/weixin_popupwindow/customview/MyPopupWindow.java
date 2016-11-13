package com.lanying.weixin_popupwindow.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanying.weixin_popupwindow.Item;
import com.lanying.weixin_popupwindow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanying on 2016/11/4.
 */
public class MyPopupWindow extends PopupWindow{
    private List<Item> mList;
    private ListView mListView;
    private MyAdapter mAdapter;


    public MyPopupWindow(Context context) {

        final View popupView = LayoutInflater.from(context).inflate(R.layout.my_popupwindow,null);
        mListView = (ListView) popupView.findViewById(R.id.lv_popupwindow);

        fillData();
        mAdapter = new MyAdapter(context);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
            }
        });

        // 设置PopupWindow的布局对应的View
        setContentView(popupView);

        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        setFocusable(true);

        // 点击外部可以取消PopupWindow
        setOutsideTouchable(true);
        setTouchable(true);
        setBackgroundDrawable(new BitmapDrawable(context.getResources(),(Bitmap)null));

    }

    private void fillData() {
        mList = new ArrayList<>();
        mList.add(new Item(R.drawable.mm_title_btn_compose_normal,"发起聊天"));
        mList.add(new Item(R.drawable.mm_title_btn_receiver_normal,"听筒模式"));
        mList.add(new Item(R.drawable.mm_title_btn_keyboard_normal,"登录网页"));
        mList.add(new Item(R.drawable.mm_title_btn_qrcode_normal,"扫一扫"));
    }

    class MyAdapter extends BaseAdapter {
        private Context mContext;

        public MyAdapter(Context context){
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_listview_popupwindow,parent, false);
            ImageView image = (ImageView) view.findViewById(R.id.image_item_listview);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_listview);

            Item item = mList.get(position);
            image.setImageResource(item.getImageRes());
            tv.setText(item.getTitle());
            return view;
        }
    }
}
