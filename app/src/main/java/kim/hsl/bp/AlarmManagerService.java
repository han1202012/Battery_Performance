package kim.hsl.bp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

public class AlarmManagerService extends Service {
    public static final String TAG = "Battery_Performance.AlarmManagerService";

    /**
     * 闹钟意图
     */
    private Intent mAlarmIntent;

    /**
     * 闹钟管理器
     */
    private AlarmManager mAlarmManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "AlarmManagerService onCreate");

        // 使用闹钟管理器保持 CPU 唤醒
        alarmKeep();

        // 初始化 JobSchedule 管理器单例对象
        JobScheduleManager.getInstance().init(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void alarmKeep(){
        // 注册广播接受者
        IntentFilter intentFilter = new IntentFilter("ACTION");
        registerReceiver( receiver, intentFilter);

        // 创建延迟意图
        mAlarmIntent = new Intent();
        mAlarmIntent.setAction("ACTION");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mAlarmIntent, 0);

        // 获取闹钟管理器, 并设置每隔 5 秒发送一次广播
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 5000, pendingIntent);
    }

    /**
     * 接收上面每隔 5 秒发送的广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("LongLogTag")
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取广播事件
            String action = intent.getAction();
            if(TextUtils.equals("ACTION", action)){
                Log.i(TAG, "receiver ACTION");
                JobScheduleManager.getInstance().addJob("ACTION(" + System.currentTimeMillis() + ")");
            }
        }
    };

}
