package kim.hsl.bp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

/**
 * 白名单
 */
public class WhiteListUtils {
    public static final String TAG = "WhiteListUtils";

    /**
     * 引导用户添加白名单
     * @param context
     */
    public static void addWhiteList(Context context){
        // 获取电量管理器
        PowerManager powerManager = (PowerManager) context.
                getSystemService(Context.POWER_SERVICE);

        // Android 6.0 以上才能使用该功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 查看当前应用是否则电量白名单中
            boolean isInWhiteList = powerManager.
                    isIgnoringBatteryOptimizations(context.getPackageName());

            // 如果没有在白名单中 , 弹出对话框 , 引导用户设置白名单
            if(!isInWhiteList){
                // 弹出白名单设置对话框
                Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 引导用户添加白名单
     * @param context
     */
    public static void addWhiteList2(Context context){
        // 获取电量管理器
        PowerManager powerManager = (PowerManager) context.
                getSystemService(Context.POWER_SERVICE);

        // Android 6.0 以上才能使用该功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 查看当前应用是否则电量白名单中
            boolean isInWhiteList = powerManager.
                    isIgnoringBatteryOptimizations(context.getPackageName());

            // 如果没有在白名单中 , 弹出对话框 , 引导用户设置白名单
            if(!isInWhiteList){
                // 弹出白名单设置对话框
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                // 带上要设置的包名
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            }
        }
    }

}
