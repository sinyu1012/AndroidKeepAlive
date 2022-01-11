package io.iftech.android.androidkeepalive.utils

import android.os.Build
import java.util.*

@Suppress("unused")
object IfRom {

    @Suppress("MemberVisibilityCanBePrivate")
    @JvmStatic
    val romInfo by lazy {
        Build.BRAND.toLowerCase(Locale.US)
    }

    @JvmStatic
    val isHuawei by lazy {
        romInfo in listOf("huawei", "honor")
    }

    @JvmStatic
    val isXiaomi by lazy {
        romInfo in listOf("xiaomi", "redmi")
    }

    @JvmStatic
    val isOppo by lazy {
        romInfo in listOf("oppo", "realme")
    }

    @JvmStatic
    val isOnePlus by lazy {
        romInfo == "oneplus"
    }

    @JvmStatic
    val isVivo by lazy {
        romInfo == "vivo"
    }

    @JvmStatic
    val isMeizu by lazy {
        romInfo == "meizu"
    }

}
