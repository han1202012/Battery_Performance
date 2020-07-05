package kim.hsl.bp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * 被动获取充电状态
 * 充电状态发生变化时 , 系统发出的广播 , 使用广播接受者接收这些广播
 */
public class BatteryReceiver extends BroadcastReceiver {
    public static final String TAG = "BatteryReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取广播事件
        String action = intent.getAction();

        if(TextUtils.equals(Intent.ACTION_POWER_CONNECTED, action)){
            // 数据线插入
            Log.i(TAG, "数据线插入");
        }else if(TextUtils.equals(Intent.ACTION_POWER_DISCONNECTED, action)){
            // 数据先拔出
            Log.i(TAG, "数据线拔出");
        }
    }
}