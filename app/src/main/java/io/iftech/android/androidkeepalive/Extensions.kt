package io.iftech.android.androidkeepalive

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import io.iftech.android.androidkeepalive.utils.IfRom

fun Context.startForegroundService() {
    Intent(this, ForegroundService::class.java).also { intent ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intent)
        } else {
            this.startService(intent)
        }
    }
}


fun Context.hideBackground(hide: Boolean) {
    var appTasks: List<ActivityManager.AppTask>? = null
    val activityManager = getSystemService(
        Context.ACTIVITY_SERVICE
    ) as? ActivityManager
    if (activityManager != null && activityManager.appTasks.also {
            appTasks = it
        } != null && appTasks?.isNotEmpty() == true) {
        appTasks?.get(0)?.setExcludeFromRecents(hide)
    }
}


fun Activity.ignoreBattery() {
    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
    intent.data = Uri.parse("package:$packageName")
    startActivityForResult(intent, 1)
}

fun Activity.startAccessibilitySetting() {
    runCatching {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}


fun Context.startAutostartSetting() {
    kotlin.runCatching {
        startActivity(getAutostartSettingIntent())
    }.getOrElse {
        it.printStackTrace()
    }
}

/**
 * 获取自启动管理页面的Intent
 * @return 返回自启动管理页面的Intent
 */
fun Context.getAutostartSettingIntent(): Intent {
    var componentName: ComponentName? = null
    val brand = Build.MANUFACTURER
    val intent = Intent()
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    when {
        brand.toLowerCase() == "samsung" -> componentName = ComponentName(
            "com.samsung.android.sm",
            "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity"
        )

        brand.toLowerCase() == "yulong" || brand.toLowerCase() == "360" -> componentName =
            ComponentName(
                "com.yulong.android.coolsafe",
                "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity"
            )
        brand.toLowerCase() == "oneplus" -> componentName = ComponentName(
            "com.oneplus.security",
            "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
        )
        brand.toLowerCase() == "letv" -> {
            intent.action = "com.letv.android.permissionautoboot"
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", packageName, null)
        }
        IfRom.isHuawei ->             //荣耀V8，EMUI 8.0.0，Android 8.0上，以下两者效果一样
            componentName = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"
            )
        IfRom.isXiaomi -> componentName = ComponentName(
            "com.miui.securitycenter",
            "com.miui.permcenter.autostart.AutoStartManagementActivity"
        )
        IfRom.isVivo -> //            componentName = new ComponentName("com.iqoo.secure", "com.iqoo.secure.safaguard.PurviewTabActivity");
            componentName = ComponentName(
                "com.iqoo.secure",
                "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
            )
        IfRom.isOppo -> //            componentName = new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity");
            componentName = ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"
            )
        IfRom.isMeizu -> componentName =
            ComponentName("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity")
        else -> {
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", packageName, null)
        }
    }
    intent.component = componentName
    return intent
}