package lesson9_custom_ht1633.com.lanying.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by lanying on 2016/11/16.
 */
public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    /**
     * 注意，必须要有一个无参构造方法
     */
    public MyIntentService(){
        super("");// 必须填入一个字符串
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("lanying","onHandleIntent");
    }
}
