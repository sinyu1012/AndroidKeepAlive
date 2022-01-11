package io.iftech.android.androidkeepalive

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*

//前台服务
class ForegroundService : Service() {
    private var logInt = 0
    private var timer: Timer? = null
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        ForegroundNotification.startForeground(this)
        timer = Timer()

        timer?.schedule(object : TimerTask() {
            override fun run() {
                Log.d("ForegroundService", "Timer task ${logInt++}")
            }
        }, 0L, 300L)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (null == intent) {
            //服务被系统kill掉之后重启进来的
            return START_NOT_STICKY
        }
        ForegroundNotification.startForegroundIfNeed(this)
        if (ServiceHelper.cancelNotice) {
            Log.d("ForegroundService", "onStartCommand: CancelNoticeService")
            val intent = Intent(this, CancelNoticeService::class.java)
            startService(intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ForegroundService", "onDestroy: $timer")

        timer?.cancel()
        ForegroundNotification.stopForeground(this)
        // 重启自己
//        startForegroundService()
    }
}
