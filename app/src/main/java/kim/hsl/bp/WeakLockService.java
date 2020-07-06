package kim.hsl.bp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class WeakLockService extends Service {
    public static final String TAG = "WeakLockService";

    /**
     * 唤醒锁
     */
    private PowerManager.WakeLock mWakeLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("InvalidWakeLockTag")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        // 跨进程获取 PowerManager 服务
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // 判断是否支持 CPU 唤醒
        boolean isWakeLockLevelSupported = powerManager.
                isWakeLockLevelSupported(PowerManager.PARTIAL_WAKE_LOCK);

        // 支持 CPU 唤醒 , 才保持唤醒
        if(isWakeLockLevelSupported){
            // 创建只唤醒 CPU 的唤醒锁
            mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WAKE_LOCK");

            // 开始唤醒 CPU
            mWakeLock.acquire();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放 CPU 唤醒锁
        if(mWakeLock != null){
            mWakeLock.release();
        }
    }
}
