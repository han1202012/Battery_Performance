package kim.hsl.bp;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BpJobService extends JobService {
    public static final String TAG = "Battery_Performance.BpJobService";

    /**
     *
     * @param params
     * @return
     *      true 任务正要被执行, 需要开始执行任务
     *      false 任务执行完毕
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        // 启动 AsyncTask 异步任务处理工作
        new JobAsyncTask().execute(params);
        return false;
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * AsyncTask<JobParameters, Void, Void> 三个泛型解析
     * -- 1. 异步任务开始时 , execute 方法传入的参数类型
     * -- 2. 异步任务执行时 , 进度值类型
     * -- 3. 异步任务结束时 , 结果类型
     */
    class JobAsyncTask extends AsyncTask<JobParameters, Void, Void> {

        /**
         * doInBackground 之前执行的方法, 一般在该方法中执行初始化操作
         * ( 主线程, 可以更新 UI )
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 主要的耗时操作是在该方法中执行的
         * ( 非主线程, 不能更新 UI )
         * @param jobParameters
         * @return
         */
        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(JobParameters... jobParameters) {
            JobParameters parameters = jobParameters[0];
            PersistableBundle extras = parameters.getExtras();
            String jobData = extras.getString("JOB_DATA");
            Log.i(TAG, "JobAsyncTask 执行 : " + jobData);
            return null;
        }

        /**
         * 在 doInBackground 中调用了 publishProgress 方法, 就会回调该方法
         * 一般情况下是在该方法中执行更新 UI 的操作
         * ( 主线程, 可以更新 UI )
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * doInBackground 执行完毕后 , 调用 return 方法后 , 该方法会被调用
         * ( 主线程, 可以更新 UI )
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
