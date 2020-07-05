package kim.hsl.bp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * 被动获取网络状态
 * 网络状态发生变化时 , 系统发出的广播 , 使用广播接受者接收这些广播
 */
public class WifiReceiver extends BroadcastReceiver {
    public static final String TAG = "WifiReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isWifi = WiflUtils.isWifi(context);

        if(isWifi){
            Log.i(TAG, "WIFI 启用");
        }else{
            Log.i(TAG, "WIFI 停用");
        }
    }
}