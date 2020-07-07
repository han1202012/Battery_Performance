package kim.hsl.bp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;

import java.util.List;

public class JobScheduleManager {
    public static final String TAG = "JobScheduleManager";

    /**
     * 将不紧急的任务调度到更合适的时机进行处理
     * 如充电时 , 如 WIFI 连接时
     * 1. 避免频繁由于执行单次任务 , 唤醒硬件模块 , 造成电量浪费
     * 2. 避免在不合适的时机执行耗电任务 , 如使用蜂窝网络在不合适的时候更新软件
     */
    private JobScheduler mJobScheduler;

    /**
     * 上下文对象
     */
    private Context mContext;

    /*
        单例模式
     */
    private static JobScheduleManager mInstance;
    private JobScheduleManager(){}
    public static JobScheduleManager getInstance(){
        if(mInstance == null){
            mInstance = new JobScheduleManager();
        }
        return mInstance;
    }

    public void init(Context context){
        this.mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        }
    }

    public void addJob(String currentJobData){
        if(mJobScheduler == null){
            return;
        }

        // 查找 id 为 0 的 任务
        JobInfo pendingJob = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            pendingJob = mJobScheduler.getPendingJob(0);

        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<JobInfo> allPendingJobs = mJobScheduler.getAllPendingJobs();
            for(JobInfo info : allPendingJobs){
                if(info.getId() == 0){
                    pendingJob = info;
                    break;
                }
            }
        }

        // 获取任务执行数据
        String historyJobData = "";
        if(pendingJob != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                PersistableBundle extras = pendingJob.getExtras();
                historyJobData = extras.getString("JOB_DATA");
                mJobScheduler.cancel(0);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PersistableBundle extras = new PersistableBundle();
            extras.putString("JOB_DATA", currentJobData + "$" + historyJobData);

            //创建一个任务
            JobInfo jobInfo = new
                    JobInfo.Builder(0,  // 任务 id 为 0
                    new ComponentName(mContext, BpJobService.class))
                    .setRequiresCharging(true)  // 要求在充电时执行
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // 非蜂窝网络执行
                    .setExtras(extras).build();

            // 将任务提交到队列中
            mJobScheduler.schedule(jobInfo);
        }
    }
}

