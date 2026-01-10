@file:Suppress("UnstableApiUsage")

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val iconsDir = File("icons")
    val outputDir = File("app/src/main/res/drawable")
    
    if (!outputDir.exists()) {
        outputDir.mkdirs()
    }
    
    val iconMappings = mapOf(
        "gauge.svg" to "ic_camera.xml",
        "calculator.svg" to "ic_view_module.xml",
        "leaf.svg" to "ic_nature.xml",
        "lightbulb.svg" to "ic_lightbulb.xml",
        "settings.svg" to "ic_settings.xml",
        "moon.svg" to "ic_dark_mode.xml",
        "book-open.svg" to "ic_book.xml",
        "sofa.svg" to "ic_home.xml",
        "bed.svg" to "ic_bed.xml",
        "utensils.svg" to "ic_restaurant.xml",
        "laptop.svg" to "ic_computer.xml",
        "baby.svg" to "ic_child_care.xml",
        "heart-pulse.svg" to "ic_favorite.xml",
        "chevron-right.svg" to "ic_keyboard_arrow_right.xml",
        "x.svg" to "ic_close.xml",
        "pause.svg" to "ic_pause.xml",
        "save.svg" to "ic_done.xml",
        "trash-2.svg" to "ic_delete.xml",
        "play.svg" to "ic_play_arrow.xml",
        "info.svg" to "ic_info.xml",
        "chevron-left.svg" to "ic_expand_less.xml",
        "triangle-alert.svg" to "ic_warning.xml",
        "building.svg" to "ic_building.xml",
        "coffee.svg" to "ic_coffee.xml",
        "download.svg" to "ic_download.xml",
        "external-link.svg" to "ic_external_link.xml",
        "gift.svg" to "ic_gift.xml",
        "message-circle-question-mark.svg" to "ic_help.xml",
        "share-2.svg" to "ic_share.xml",
        "sun.svg" to "ic_sun.xml",
        "trending-up.svg" to "ic_trending_up.xml"
    )
    
    iconMappings.forEach { (svgFile, xmlFile) ->
        val svgPath = Paths.get(iconsDir.absolutePath, svgFile)
        val xmlPath = Paths.get(outputDir.absolutePath, xmlFile)
        
        if (Files.exists(svgPath)) {
            println("Processing $svgFile -> $xmlFile")
            // 这里需要实际的 SVG 到 Vector Drawable 转换逻辑
            // 由于 SVG 解析复杂，建议使用 Android Studio 的 Import Vector Asset 功能
            // 或者使用在线工具：https://inloop.github.io/svg2android/
        }
    }
    
    println("\n请使用 Android Studio 的 Import Vector Asset 功能导入以下 SVG 文件：")
    iconMappings.keys.forEach { svgFile ->
        println("  - $svgFile")
    }
}