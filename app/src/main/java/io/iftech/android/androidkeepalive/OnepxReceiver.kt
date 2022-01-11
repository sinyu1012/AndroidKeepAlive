package io.iftech.android.androidkeepalive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.iftech.android.androidkeepalive.OnepxActivity
import io.iftech.android.androidkeepalive.OnepxReceiver
import android.content.IntentFilter
import android.util.Log

class OnepxReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF) {    //屏幕关闭的时候接受到广播
            val it = Intent(context, OnepxActivity::class.java)
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
            Log.d("OnepxReceiver", "-------screen off")
        } else if (intent.action == Intent.ACTION_SCREEN_ON) {   //屏幕打开的时候发送广播  结束一像素
            context.sendBroadcast(Intent("finish activity"))
            Log.d("OnepxReceiver", "------screen on")
            val home = Intent(Intent.ACTION_MAIN)
            home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            home.addCategory(Intent.CATEGORY_HOME)
            context.startActivity(home)
        }
    }
}