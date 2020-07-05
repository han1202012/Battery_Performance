package kim.hsl.bp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 电量被动获取
 * 电量发生变化时 , 系统发出的广播 , 使用广播接受者接收这些广播
 */
public class BatteryReceiver extends BroadcastReceiver {
    public static final String TAG = "BatteryReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
