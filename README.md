# 杀不死的App

Android 保活实践


![](pic/screen.png)

项目里的文档同时也在 [iftech-android-doc](https://github.com/iftechio/iftech-android-doc/blob/main/projects/match/README.md) 下备份，本地更新时记得也更新那里。

### 项目配置

* [CHANGE_LOG](CHANGELOG.md)
* [guide_track](docs/guide_track.md)
* [match_guide](docs/match_guide.md)
* [guide_resource](docs/guide_resource.md)
* [guide_publish](docs/guide_publish.md)
* [dependency_use](docs/dependency_use.md)
* [package_use](docs/package_use.md)
* [guide_flipper_flavor](docs/guide_flipper_flavor.md)
* [guide_flipper_under_release](docs/guide_flipper_under_release.md)

### 注意事项
* debug RN 时需要本地跑下 yarn android
* 检查依赖更新库：https://github.com/ben-manes/gradle-versions-plugin，使用 gradle dependencyUpdates -Drevision=release