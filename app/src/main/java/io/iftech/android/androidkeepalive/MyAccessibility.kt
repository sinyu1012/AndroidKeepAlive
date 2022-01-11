package io.iftech.android.androidkeepalive

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import java.util.*


class MyAccessibility : AccessibilityService() {
    private var logInt = 0
    private var timer: Timer? = null

    override fun onCreate() {
        super.onCreate()
        // 也可启动前台通知，效果更佳
        ForegroundNotification.startForeground(this)

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                Log.d("MyAccessibility", "Timer task ${logInt++}")
            }
        }, 0L, 300L)
    }

    override fun onServiceConnected() {
        Log.d("MyAccessibility", "onServiceConnected")
    }

    override fun onAccessibilityEvent(enent: AccessibilityEvent) {
        //辅助功能事件监听 及 运行
    }

    override fun onInterrupt() {
        //辅助功能中断时
    }

    override fun onDestroy() {
        super.onDestroy()
        ForegroundNotification.stopForeground(this)

        timer?.cancel()

    }
}