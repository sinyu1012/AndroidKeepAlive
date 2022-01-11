package io.iftech.android.androidkeepalive

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import android.util.Log
import java.lang.IllegalArgumentException

class OnepxActivity : Activity() {
    private var br: BroadcastReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        window.setGravity(Gravity.LEFT or Gravity.TOP)
        val params = window.attributes
        params.x = 0
        params.y = 0
        params.height = 1
        params.width = 1
        window.attributes = params
        //结束该页面的广播
        br = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d("OnepxActivity", "OnepxActivity finish == ==============")
                finish()
            }
        }
        registerReceiver(br, IntentFilter("finish activity"))

        //检查屏幕状态
        checkScreenOn("onCreate")
    }

    /**
     * 检查屏幕状态  isScreenOn为true  屏幕“亮”结束该Activity
     */
    private fun checkScreenOn(methodName: String) {
        Log.d("OnepxActivity", "from call method: $methodName")
        val pm = this@OnepxActivity.getSystemService(POWER_SERVICE) as PowerManager
        val isScreenOn = pm.isScreenOn
        Log.d("OnepxActivity", "isScreenOn: $isScreenOn")
        if (isScreenOn) {
            finish()
        }
    }

    override fun onDestroy() {
        Log.d("OnepxActivity", "===onDestroy===")
        try {
            unregisterReceiver(br)
        } catch (e: IllegalArgumentException) {
            Log.d("OnepxActivity", "receiver is not resisted: $e")
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        checkScreenOn("onResume")
    }
}