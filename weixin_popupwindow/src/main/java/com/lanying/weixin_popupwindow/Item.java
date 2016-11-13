package com.lanying.weixin_popupwindow;

/**
 * Created by lanying on 2016/11/4.
 */
public class Item {
    private int imageRes;
    private String title;

    public Item(int imageRes, String title) {
        this.imageRes = imageRes;
        this.title = title;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
