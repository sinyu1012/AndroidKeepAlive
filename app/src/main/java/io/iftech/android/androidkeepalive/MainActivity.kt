package io.iftech.android.androidkeepalive

import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mOnepxReceiver: OnepxReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStartForeground.setOnClickListener {
            startForegroundService()
        }
        btnCancelNotice.setOnClickListener {
            ServiceHelper.cancelNotice = true
            startForegroundService()
        }

        btnLockAndHide.setOnClickListener {
            hideBackground(true)
        }

        btnIgnoreBattery.setOnClickListener {
            ignoreBattery()
        }

        btnAutoStart.setOnClickListener {
            startAutostartSetting()
        }
        btnAccessibility.setOnClickListener {
            startAccessibilitySetting()
        }
        btnOnepx.setOnClickListener {
            mOnepxReceiver = OnepxReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.intent.action.SCREEN_OFF")
            intentFilter.addAction("android.intent.action.SCREEN_ON")
            intentFilter.addAction("android.intent.action.USER_PRESENT")
            registerReceiver(mOnepxReceiver, intentFilter)
        }

        btnRevive.setOnClickListener {
            Toast.makeText(this, "账号同步+死亡监听保活链路已启动（演示）", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mOnepxReceiver)
    }
}