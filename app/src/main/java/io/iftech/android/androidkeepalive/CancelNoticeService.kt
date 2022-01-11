package io.iftech.android.androidkeepalive

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log


// 同时启动两个service，共享同一个NotificationID，并且将他们同时置为前台状态，
// 此时会出现两个前台服务，但通知管理器里只有一个关联的通知。
// 这时我们在其中一个服务中调用 stopForeground(true)，
// 这个服务前台状态会被取消，同时状态栏通知也被移除。另外一个服务并没有受到影响，还是前台服务状态，但是此时，状态栏通知已经没了！
// 其oom_adj值还是没变的
class CancelNoticeService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (null == intent) {
            //服务被系统kill掉之后重启进来的
            return START_NOT_STICKY
        }
        ForegroundNotification.startForeground(this)
        Thread {
            SystemClock.sleep(1000)
//            stopForeground(true)
            Log.d("ForegroundService", "CancelNoticeService onStartCommand: CancelNoticeService" )
            ForegroundNotification.stopForeground(this)
//            stopSelf()
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ForegroundService", "onDestroy: CancelNoticeService")

    }
}
