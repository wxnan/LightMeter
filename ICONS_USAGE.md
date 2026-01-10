# 自定义图标使用说明

## 概述
本项目使用自定义的 Vector Drawable 图标来替代 Material Icons 中不存在的图标。

## 图标文件位置
所有图标文件位于：`app/src/main/res/drawable/`

## 图标包装器
图标包装器位于：`app/src/main/java/com/example/lightmeter/ui/icons/CustomIcons.kt`

## 已创建的图标列表

| 图标名称 | 文件名 | 用途 |
|---------|--------|------|
| ic_camera.xml | Camera | 测光标签页 |
| ic_view_module.xml | ViewModule | 计算标签页 |
| ic_nature.xml | Nature | 植物标签页 |
| ic_lightbulb.xml | Lightbulb | 场景标签页 |
| ic_settings.xml | Settings | 设置标签页 |
| ic_dark_mode.xml | DarkMode | 深色主题 |
| ic_book.xml | Book | 阅读场景 |
| ic_home.xml | Home | 客厅场景 |
| ic_bed.xml | Bed | 卧室场景 |
| ic_restaurant.xml | Restaurant | 餐厅场景 |
| ic_computer.xml | Computer | 书房场景 |
| ic_child_care.xml | ChildCare | 儿童房场景 |
| ic_favorite.xml | Favorite | 老人房场景 |
| ic_keyboard_arrow_right.xml | KeyboardArrowRight | 右箭头 |
| ic_close.xml | Close | 关闭按钮 |
| ic_pause.xml | Pause | 暂停按钮 |
| ic_done.xml | Done | 保存按钮 |
| ic_delete.xml | Delete | 删除按钮 |
| ic_play_arrow.xml | PlayArrow | 播放按钮 |
| ic_info.xml | Info | 信息图标 |
| ic_expand_less.xml | ExpandLess | 收起箭头 |
| ic_warning.xml | Warning | 警告图标 |
| ic_building.xml | Building | 建筑图标 |
| ic_coffee.xml | Coffee | 咖啡图标 |
| ic_download.xml | Download | 下载图标 |
| ic_external_link.xml | ExternalLink | 外部链接 |
| ic_gift.xml | Gift | 礼物图标 |
| ic_help.xml | Help | 帮助图标 |
| ic_share.xml | Share | 分享图标 |
| ic_sun.xml | Sun | 太阳图标 |
| ic_trending_up.xml | TrendingUp | 趋势上升 |
| ic_cancel.xml | Cancel | 取消按钮 |

## 使用方法

### 在 Composable 中使用

```kotlin
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.lightmeter.ui.icons.CustomIcons

// 使用自定义图标
Icon(
    imageVector = CustomIcons.Camera,
    contentDescription = null
)
```

### 在枚举中使用

```kotlin
enum class TabType(val displayName: String, val icon: ImageVector) {
    MEASUREMENT("测光", CustomIcons.Camera),
    CALCULATOR("计算", CustomIcons.ViewModule),
    // ...
}
```

## 添加新图标

1. 创建 SVG 图标文件并放在 `icons/` 目录
2. 使用 Android Studio 的 Import Vector Asset 功能：
   - 右键点击 `res/drawable` 文件夹
   - 选择 New -> Vector Asset
   - 选择 Local file (SVG)
   - 选择 SVG 文件并命名
3. 在 `CustomIcons.kt` 中添加对应的属性

## 注意事项

- 所有图标都是 24dp × 24dp 的 Vector Drawable
- 图标颜色可以通过 `tint` 参数修改
- 确保图标路径数据正确，否则可能显示异常
