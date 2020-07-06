package kim.hsl.bp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private Intent mAlarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void alarmKeep(){
        mAlarmIntent = new Intent();
        mAlarmIntent.setAction("ACTION");
        // 延迟意图
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mAlarmIntent, 0);

        // 闹钟管理器
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // 注册广播接受者
        IntentFilter intentFilter = new IntentFilter("ACTION");
        registerReceiver( receiver, intentFilter);

        // 每隔 5 秒发送一次广播
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 5000, pendingIntent);


    }

    /**
     * 接收上面每隔 5 秒发送的广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取广播事件
            String action = intent.getAction();
            if(TextUtils.equals("ACTION", action)){
                Log.i(TAG, "receiver ACTION");
            }
        }
    };

}
