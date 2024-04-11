# 杀不死的App

Android 保活实践，如有相关需求可联系：sinyuper@gmail.com

## 前言

- 什么是保活？保活就是在用户主动杀进程，或者系统基于当前内存不足状态而触发清理进程后，该进程设法让自己免于被杀的命运或者被杀后能立刻重生的手段。
- 为什么保活：赢得在线时长、实时需求

# 思路

#### 进程的优先级分为5级（https://zhuanlan.zhihu.com/p/108312672）：

- **前台进程**
- **可见进程**
- **服务进程** 
- **后台进程**
- **空进程**

#### 主流思路有两个：

- 提升进程优先级，降低被杀概率
- 进程被杀后，重新拉起进程

## 方案（不互斥

- startForeground 启动前台服务（官方提供，会有通知，保活性不高
  - 进阶点：开启前台Service，再开启另一个Service将通知栏移除
- 多任务列表窗口加锁
- 多任务列表窗口隐藏App
- 多个app关联唤醒
- 忽略电池优化开关
- 无障碍服务，https://developer.android.com/guide/topics/ui/accessibility/service?hl=zh-cn
- 应用自启动权限(各个系统不一样)
- QQ 的 1 像素（**可以使进程的优先级在屏幕锁屏时间由4提升为最高优先级1**
- 后台无声音乐
- [双进程保活](https://github.com/Marswin/MarsDaemon)，6.0之前
- [Tim 流氓保活方案](https://segmentfault.com/a/1190000021579231)，开源库：https://github.com/tiann/Leoric
- 终极方案：跟各大系统厂商建立合作关系，OOM_ADJ https://www.jianshu.com/p/8897b7e47466

## 实现

https://github.com/sinyu1012/AndroidKeepAlive

## 总结

**没有规矩，不成方圆**。保活方案有些太 hack 了，还是尊重用户、提升用户体验才是最重要的。


![](pic/screen.jpg)
