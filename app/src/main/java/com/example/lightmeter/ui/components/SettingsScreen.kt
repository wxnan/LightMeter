package com.example.lightmeter.ui.components

import android.content.pm.PackageInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import com.example.lightmeter.data.*
import com.example.lightmeter.ui.theme.*
import com.example.lightmeter.ui.icons.CustomIcons

@Composable
fun SettingsScreen(
    settings: AppSettings,
    onThemeChange: (ThemeMode) -> Unit,
    onCalibrationChange: (Float, Float) -> Unit,
    onPPFDChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCalibration by remember { mutableStateOf(false) }
    var showPPFD by remember { mutableStateOf(false) }
    var showGuide by remember { mutableStateOf(false) }
    var showFAQ by remember { mutableStateOf(false) }
    var tempMultiplier by remember { mutableStateOf(settings.calibrationMultiplier.toString()) }
    var tempOffset by remember { mutableStateOf(settings.calibrationOffset.toString()) }
    var tempPPFDFactor by remember { mutableStateOf(settings.ppfdConversionFactor.toString()) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // SettingsHeader()
        
        ThemeSettings(
            currentTheme = settings.theme,
            onThemeChange = onThemeChange
        )
        
        CalibrationSettings(
            showCalibration = showCalibration,
            multiplier = tempMultiplier,
            offset = tempOffset,
            onToggleShow = { showCalibration = !showCalibration },
            onMultiplierChange = { tempMultiplier = it },
            onOffsetChange = { tempOffset = it },
            onSave = {
                val multiplier = tempMultiplier.toFloatOrNull() ?: 1f
                val offset = tempOffset.toFloatOrNull() ?: 0f
                onCalibrationChange(multiplier, offset)
            }
        )
        
        PPFDSettings(
            showPPFD = showPPFD,
            factor = tempPPFDFactor,
            onToggleShow = { showPPFD = !showPPFD },
            onFactorChange = { tempPPFDFactor = it },
            onSave = {
                val factor = tempPPFDFactor.toFloatOrNull() ?: 0.0185f
                onPPFDChange(factor)
            }
        )
        
        GuideSettings(
            showGuide = showGuide,
            onToggleShow = { showGuide = !showGuide }
        )
        
        FAQSettings(
            showFAQ = showFAQ,
            onToggleShow = { showFAQ = !showFAQ }
        )
        
        OtherSettings()
        
        VersionInfo()
    }
}

@Composable
fun SettingsHeader(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val gradient = Brush.linearGradient(
                    colors = listOf(
                        Gray50,
                        Slate50
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height)
                )
                drawRect(gradient)
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = CustomIcons.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Gray700
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "设置",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Gray900
                )
            }
            Text(
                text = "个性化您的照度测量体验",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray600
            )
        }
    }
}

@Composable
fun ThemeSettings(
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "外观设置",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ThemeButton(
                        text = "浅色",
                        icon = CustomIcons.Sun,
                        selected = currentTheme == ThemeMode.LIGHT,
                        onClick = { onThemeChange(ThemeMode.LIGHT) },
                        modifier = Modifier.weight(1f)
                    )
                    ThemeButton(
                        text = "深色",
                        icon = CustomIcons.Moon,
                        selected = currentTheme == ThemeMode.DARK,
                        onClick = { onThemeChange(ThemeMode.DARK) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun ThemeButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Blue50 else MaterialTheme.colorScheme.surface,
            contentColor = if (selected) Blue600 else Gray600
        ),
        border = if (selected) {
            ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.horizontalGradient(listOf(Blue500, Blue500))
            )
        } else {
            ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.horizontalGradient(listOf(Gray200, Gray200))
            )
        },
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun CalibrationSettings(
    showCalibration: Boolean,
    multiplier: String,
    offset: String,
    onToggleShow: () -> Unit,
    onMultiplierChange: (String) -> Unit,
    onOffsetChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onToggleShow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CustomIcons.Gauge,
                            contentDescription = null,
                            tint = Gray600
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "校准设置",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Icon(
                        imageVector = if (showCalibration) CustomIcons.ExpandLess else CustomIcons.ExpandMore,
                        contentDescription = null,
                        tint = Gray400
                    )
                }
            }
            
            if (showCalibration) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "📐 校准公式：",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "实际照度 = 测量值 × 校准倍数 + 校准偏移",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                            )
                        }
                    }
                    
                    OutlinedTextField(
                        value = multiplier,
                        onValueChange = onMultiplierChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("校准倍数（默认 1.0）") },
                        placeholder = { Text("1.0") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Text(
                        text = "范围：0.5 - 2.0，用于整体比例调整",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray500
                    )
                    
                    OutlinedTextField(
                        value = offset,
                        onValueChange = onOffsetChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("校准偏移（默认 0 lux）") },
                        placeholder = { Text("0") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Text(
                        text = "正值表示当前测量偏低，负值表示偏高",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray500
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onSave,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("保存校准")
                        }
                        OutlinedButton(
                            onClick = {
                                onMultiplierChange("1")
                                onOffsetChange("0")
                                onSave()
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("重置")
                        }
                    }
                    
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "💡 校准示例：",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "• 专业照度计显示500 lux，手机显示450 lux\n• 校准倍数 = 500 ÷ 450 ≈ 1.11\n• 或使用偏移 = 500 - 450 = 50 lux",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PPFDSettings(
    showPPFD: Boolean,
    factor: String,
    onToggleShow: () -> Unit,
    onFactorChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onToggleShow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CustomIcons.Sun,
                            contentDescription = null,
                            tint = Orange500
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "PPFD设置",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Icon(
                        imageVector = if (showPPFD) CustomIcons.ExpandLess else CustomIcons.ExpandMore,
                        contentDescription = null,
                        tint = Gray400
                    )
                }
            }
            
            if (showPPFD) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "🌱 PPFD换算公式：",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "PPFD (μmol/m²·s) = Lux × 换算系数",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                            )
                        }
                    }
                    
                    OutlinedTextField(
                        value = factor,
                        onValueChange = onFactorChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("换算系数（默认 0.0185）") },
                        placeholder = { Text("0.0185") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Text(
                        text = "阳光直射：0.0185，室内LED：0.012-0.015",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray500
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onSave,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("保存设置")
                        }
                        OutlinedButton(
                            onClick = {
                                onFactorChange("0.0185")
                                onSave()
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("重置")
                        }
                    }
                    
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "💡 说明：",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "PPFD（光合有效辐射密度）是衡量植物可利用光能的指标。不同光源的换算系数不同，阳光直射约为0.0185，室内LED灯约为0.012-0.015。\n植物页面，点击实时照度显示卡片，可以进行切换。",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GuideSettings(
    showGuide: Boolean,
    onToggleShow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onToggleShow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CustomIcons.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "使用指导",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Icon(
                        imageVector = if (showGuide) CustomIcons.ExpandLess else CustomIcons.ExpandMore,
                        contentDescription = null,
                        tint = Gray400
                    )
                }
            }
            
            if (showGuide) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GuideSection(
                        title = "📱 测量技巧",
                        items = listOf(
                            "将手机传感器朝向光源方向",
                            "保持手机稳定，避免遮挡传感器",
                            "等待数值稳定后再记录",
                            "多点测量取平均值更准确"
                        )
                    )
                    GuideSection(
                        title = "⚙️ 计算器使用",
                        items = listOf(
                            "准确测量房间尺寸（长×宽×高）",
                            "根据场景选择推荐照度值",
                            "根据实际情况，选择合适的利用系数",
                            "深色装修需增加15-20%流明"
                        )
                    )
                    GuideSection(
                        title = "🌿 植物养护",
                        items = listOf(
                            "在植物位置测量实际照度",
                            "观察植物生长状态调整位置",
                            "南窗光照最强，北窗最弱",
                            "补光灯距离30-50cm效果最佳"
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun GuideSection(
    title: String,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        items.forEach { item ->
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = "• ",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun FAQSettings(
    showFAQ: Boolean,
    onToggleShow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onToggleShow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CustomIcons.Question,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "常见问题",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Icon(
                        imageVector = if (showFAQ) CustomIcons.ExpandLess else CustomIcons.ExpandMore,
                        contentDescription = null,
                        tint = Gray400
                    )
                }
            }
            
            if (showFAQ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FAQItem(
                        question = "Q: 手机测量准确吗？",
                        answer = "A: 手机传感器可提供参考数据，误差约±20%，经专业照度计校准后误差约±5%。专业场合建议使用专业照度计。"
                    )
                    FAQItem(
                        question = "Q: 手机测量数值偏低？",
                        answer = "A: 查看手机传感器是否有遮挡，如污染物、手机贴膜等。使用校准功能可提高准确度。"
                    )
                    FAQItem(
                        question = "Q: 数值一直在变动？",
                        answer = "A: 这是正常现象。光线会随时间、角度变化。建议记录稳定时的平均值。"
                    )
                    FAQItem(
                        question = "Q: 如何提高准确度？",
                        answer = "A: 1) 清洁传感器 2) 使用深色主题 3) 降低屏幕亮度 4) 使用校准功能 5) 多点测量取平均 6) 避免遮挡传感器。"
                    )
                    FAQItem(
                        question = "Q: lux和lm的区别？",
                        answer = "A: lux是照度单位（光照强度），lm是光通量单位（光源发光总量）。"
                    )
                    FAQItem(
                        question = "Q: 利用系数如何选择？",
                        answer = "A: 利用系数受多种因素影响，包括但不限于灯具的类型、灯具的安装方式、房间的尺寸、墙壁和天花板的反射率等。通常，吸顶灯的利用系数范围为0.6~0.85；吊灯的利用系数范围为0.5~0.8；筒射灯的利用系数范围为0.4~0.7；若保守计算，利用系数可取0.4左右。"
                    )
                    FAQItem(
                        question = "Q: 维护系数如何选择？",
                        answer = "A: 对于普通住宅室内房间（客厅、卧室、餐厅等），选择清洁(0.8)即可。每年需至少清洁2次灯具。"
                    )
                    FAQItem(
                        question = "Q: 照度标准值或推荐值？",
                        answer = "A: 标准值数据来源于国标《建筑照明设计标准》，推荐值来源于知网文献。对于常见房间（客厅、卧室、餐厅等），参考平面及高度为0.75米的水平面或桌面；对于走廊，参考平面为地面。照度值为平均照度，而非某一点的照度。"
                    )
                    FAQItem(
                        question = "Q: 场景照度推荐值范围？",
                        answer = "A: 房间照度不能低于推荐值范围的最小值，可以适当高于推荐值范围的最大值。"
                    )
                    FAQItem(
                        question = "Q: 植物照度推荐值范围？",
                        answer = "A: 推荐值数据来源于AI和网络，仅供参考。如果您有权威数据来源，请通过本应用的GitHub官方页面或“反馈建议”联系我。"
                    )
                }
            }
        }
    }
}

@Composable
fun FAQItem(
    question: String,
    answer: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = question,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = answer,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun OtherSettings(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    val handleFeedback = {
        android.widget.Toast.makeText(
            context,
            "感谢您的反馈！请发送邮件至 xiaon_ooossltsbk@aka.yeah.net",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
    
    val handleShare = {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "照度测量仪") 
            putExtra(Intent.EXTRA_TEXT, "照度测量仪 | 专业级手机照度测量计算工具\n\n官网链接：https://github.com/wxnan/LightMeter \n网盘下载：https://pan.baidu.com/s/12cQ3gz14A646O3R8O-puQA?pwd=smr9")
        }
        context.startActivity(Intent.createChooser(shareIntent, "分享应用"))
    }
    
    val handleCoupons = {
        val uri = Uri.parse("https://www.yuque.com/dawnan/sheng/shengqian?singleDoc# 《省钱优惠》")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
    
    val handleReferences = {
        val uri = Uri.parse("https://ima.qq.com/wiki/?shareId=d99610653c901ee6f9540ba79b159f11e94871bdf51052798df960c41b09f529")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SettingItem(
                icon = CustomIcons.Message,
                text = "反馈建议",
                onClick = handleFeedback,
                showDivider = true
            )
            SettingItem(
                icon = CustomIcons.Share,
                text = "分享应用",
                onClick = handleShare,
                showDivider = true
            )
            SettingItem(
                icon = CustomIcons.Gift,
                text = "省钱优惠",
                onClick = handleCoupons,
                badge = "HOT",
                showDivider = true
            )
            SettingItem(
                icon = CustomIcons.Book,
                text = "参考文献",
                onClick = handleReferences,
                showDivider = false
            )
        }
    }
}

@Composable
fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit,
    badge: String? = null,
    showDivider: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isHovered) Gray50 else MaterialTheme.colorScheme.surface
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = when (text) {
                        "反馈建议" -> Purple500
                        "分享应用" -> Blue500
                        "省钱优惠" -> Red500
                        else -> Gray600
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (badge != null) {
                    Surface(
                        color = Red500,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = badge,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                if (text == "参考文献") {
                    Icon(
                        imageVector = CustomIcons.ExternalLink,
                        contentDescription = null,
                        tint = Gray400,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Icon(
                        imageVector = CustomIcons.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Gray400
                    )
                }
            }
        }
    }
    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Gray200,
            thickness = 1.dp
        )
    }
}

@Composable
fun VersionInfo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val versionName = packageInfo.versionName
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "版本 $versionName",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "© 2026 照度测量仪",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
