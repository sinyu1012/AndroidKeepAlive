package io.iftech.android.androidkeepalive


import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService

@SuppressLint("StaticFieldLeak")
object ForegroundNotification {
    private const val CHANNEL_FOREGROUND = "foreground-notification"
    const val NOTICE_ID = 233
    private var service: Service? = null

    private fun createChannelIfNeeded(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val foregroundChannel =
            NotificationChannel(CHANNEL_FOREGROUND, "前台服务", NotificationManager.IMPORTANCE_MIN)
                .apply {
                    setShowBadge(false)
                    enableLights(false)
                    enableVibration(false)
                    lockscreenVisibility = Notification.VISIBILITY_SECRET
                }
        context.getSystemService<NotificationManager>()
            ?.createNotificationChannel(foregroundChannel)
    }

    fun startForeground(service: Service) {
        this.service = service
        createChannelIfNeeded(service)
        val pendingIntent = PendingIntent.getActivity(
            service,
            0,
            Intent(service, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(service, CHANNEL_FOREGROUND)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("提示内容")
            .setContentIntent(pendingIntent)
            .setLocalOnly(true)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setOngoing(true)
            .setShowWhen(false)
            .build()
        service.startForeground(NOTICE_ID, notification)
    }

    fun stopForeground(service: Service) {
        val manager = service.getSystemService(Service.NOTIFICATION_SERVICE) as? NotificationManager
        manager?.cancel(NOTICE_ID)
        service.stopForeground(true)
    }

    fun cancelNotice(service: Service) {
        val manager = service.getSystemService(Service.NOTIFICATION_SERVICE) as? NotificationManager
        manager?.cancel(NOTICE_ID)
    }

    fun stopForeground() {
        val manager = service?.getSystemService(Service.NOTIFICATION_SERVICE) as? NotificationManager
        manager?.cancel(NOTICE_ID)
        service?.stopForeground(true)
    }

    fun startForegroundIfNeed(service: Service) {
        val manager = service.getSystemService(Service.NOTIFICATION_SERVICE) as? NotificationManager
        var needStart = true
        manager?.activeNotifications?.forEach {
            needStart = (it.id == NOTICE_ID).not()
        }
        if (needStart) {
            startForeground(service)
        }
    }


}
