package kim.hsl.bp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class WiflUtils {
    /**
     * 主动获取当前是否正在使用 WIFI
     * @return
     */
    public static boolean isWifi(Context context){
        boolean isWifi = false;

        // 获取连接管理器
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // 获取网络信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // 判定是否是 WIFI
        if(networkInfo != null && networkInfo.isConnected() &&
                networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            isWifi = true;
        }

        return isWifi;
    }
}
