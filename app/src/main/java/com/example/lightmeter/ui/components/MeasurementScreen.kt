package com.example.lightmeter.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.nativeCanvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import com.example.lightmeter.data.DataPoint
import com.example.lightmeter.data.LightMeasurement
import com.example.lightmeter.ui.theme.*
import com.example.lightmeter.ui.icons.CustomIcons

@Composable
fun MeasurementScreen(
    currentLux: Float,
    isRecording: Boolean,
    historyData: List<DataPoint>,
    measurements: List<LightMeasurement>,
    selectedRecordIndex: Int?,
    onToggleRecording: () -> Unit,
    onSaveRecord: (String) -> Unit,
    onDeleteRecord: (Int) -> Unit,
    onSelectRecord: (Int) -> Unit,
    onClearRecordSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    val luxLevel = getLuxLevel(currentLux)
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RealtimeLuxDisplay(
            lux = currentLux,
            level = luxLevel
        )
        
        MeasurementControls(
            isRecording = isRecording,
            onToggleRecording = onToggleRecording
        )
        
        val displayHistoryData = if (selectedRecordIndex != null && measurements.isNotEmpty()) {
            measurements[selectedRecordIndex!!].historyData ?: emptyList()
        } else {
            historyData
        }
        
        if (displayHistoryData.isNotEmpty()) {
            HistoryChart(
                historyData = displayHistoryData,
                selectedRecordIndex = selectedRecordIndex,
                selectedRecordLabel = if (selectedRecordIndex != null && measurements.isNotEmpty()) {
                    measurements[selectedRecordIndex!!].label
                } else {
                    null
                },
                isRecording = isRecording,
                onClearSelection = onClearRecordSelection
            )
        }
        
        SavedRecords(
            measurements = measurements,
            selectedRecordIndex = selectedRecordIndex,
            onSaveRecord = onSaveRecord,
            onDeleteRecord = onDeleteRecord,
            onSelectRecord = onSelectRecord
        )
    }
}

@Composable
fun RealtimeLuxDisplay(
    lux: Float,
    level: LuxLevel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = level.bgColor
        ),
        // elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = CustomIcons.Sun,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = level.iconColor
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = String.format("%.0f", lux),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = level.iconColor
            )
            
            Text(
                text = "Lux",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = level.statusColor.copy(alpha = 0.2f)
            ) {
                Text(
                    text = level.text,
                    color = level.statusColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MeasurementControls(
    isRecording: Boolean,
    onToggleRecording: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onToggleRecording,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        ),
        contentPadding = PaddingValues(16.dp)
    ) {
        Icon(
            imageVector = if (isRecording) CustomIcons.Pause else CustomIcons.Play,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (isRecording) "停止记录" else "开始记录",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun HistoryChart(
    historyData: List<DataPoint>,
    selectedRecordIndex: Int?,
    selectedRecordLabel: String?,
    isRecording: Boolean,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    var scrollOffset by remember { mutableStateOf(0) }
    val maxVisiblePoints = 10
    val maxScrollOffset = maxOf(0, historyData.size - maxVisiblePoints)
    
    LaunchedEffect(isRecording) {
        if (isRecording) {
            scrollOffset = 0
        }
    }
    
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
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedRecordIndex != null && selectedRecordLabel != null) {
                        "$selectedRecordLabel - 历史曲线"
                    } else {
                        "历史曲线"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isRecording && historyData.size > maxVisiblePoints) {
                        IconButton(
                            onClick = { if (scrollOffset > 0) scrollOffset-- },
                            enabled = scrollOffset > 0
                        ) {
                            Icon(
                                imageVector = CustomIcons.ChevronLeft,
                                contentDescription = "向左滚动"
                            )
                        }
                    }
                    
                    if (selectedRecordIndex != null) {
                        IconButton(onClick = onClearSelection) {
                            Icon(
                                    imageVector = CustomIcons.Close,
                                    contentDescription = "关闭"
                            )
                        }
                    }
                    
                    if (!isRecording && historyData.size > maxVisiblePoints) {
                        IconButton(
                            onClick = { if (scrollOffset < maxScrollOffset) scrollOffset++ },
                            enabled = scrollOffset < maxScrollOffset
                        ) {
                            Icon(
                                imageVector = CustomIcons.ChevronRight,
                                contentDescription = "向右滚动"
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val boxModifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
            
            Box(
                modifier = if (!isRecording && historyData.size > maxVisiblePoints) {
                    boxModifier.pointerInput(Unit) {
                        var totalDragX = 0f
                        detectHorizontalDragGestures { change, dragAmount ->
                            change.consume()
                            totalDragX += dragAmount
                            
                            val dragThreshold = 100f
                            if (totalDragX > dragThreshold) {
                                if (scrollOffset > 0) {
                                    scrollOffset--
                                }
                                totalDragX = 0f
                            } else if (totalDragX < -dragThreshold) {
                                if (scrollOffset < maxScrollOffset) {
                                    scrollOffset++
                                }
                                totalDragX = 0f
                            }
                        }
                    }
                } else {
                    boxModifier
                }
            ) {
                LuxLineChart(
                    data = historyData,
                    scrollOffset = scrollOffset,
                    isRecording = isRecording,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            StatisticsCards(historyData)
        }
    }
}

@Composable
fun LuxLineChart(
    data: List<DataPoint>,
    scrollOffset: Int,
    isRecording: Boolean,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    val axisColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    
    val maxVisiblePoints = 10
    val visibleData = if (isRecording) {
        if (data.size > maxVisiblePoints) {
            data.takeLast(maxVisiblePoints)
        } else {
            data
        }
    } else {
        if (data.size > maxVisiblePoints) {
            data.drop(scrollOffset).take(maxVisiblePoints)
        } else {
            data
        }
    }
    
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val padding = 16.dp.toPx()
        
        if (visibleData.isNotEmpty()) {
            val dataMaxLux = visibleData.maxOfOrNull { it.lux } ?: 0f
            val minLux = 0f
            val maxLux = ((dataMaxLux / 100).toInt() * 100 + 100).coerceAtLeast(100).toFloat()
            val luxRange = if (maxLux == minLux) 100f else maxLux - minLux
            
            val points = visibleData.mapIndexed { index, point ->
                val x = if (visibleData.size == 1) {
                    padding + (width - 2 * padding) / 2
                } else {
                    padding + (width - 2 * padding) * index / (visibleData.size - 1)
                }
                val y = height - padding - (height - 2 * padding) * (point.lux - minLux) / luxRange
                Offset(x, y)
            }
            
            val path = Path().apply {
                points.forEachIndexed { index, point ->
                    if (index == 0) {
                        moveTo(point.x, point.y)
                    } else {
                        lineTo(point.x, point.y)
                    }
                }
            }
            
            drawPath(
                path = path,
                color = primaryColor,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
            
            points.forEach { point ->
                drawCircle(
                    color = primaryColor,
                    radius = 4.dp.toPx(),
                    center = point
                )
            }
            
            drawLine(
                start = Offset(padding, padding),
                end = Offset(padding, height - padding),
                color = axisColor,
                strokeWidth = 1.dp.toPx()
            )
            
            drawLine(
                start = Offset(padding, padding),
                end = Offset(width - padding, padding),
                color = axisColor,
                strokeWidth = 1.dp.toPx()
            )
            
            drawLine(
                start = Offset(padding, height - padding),
                end = Offset(width - padding, height - padding),
                color = axisColor,
                strokeWidth = 1.dp.toPx()
            )
            
            val yLabelCount = 5
            for (i in 0..yLabelCount) {
                val luxValue = minLux + (maxLux - minLux) * i / yLabelCount
                val y = height - padding - (height - 2 * padding) * (luxValue - minLux) / luxRange
                
                val textPaint = android.text.TextPaint()
                textPaint.color = android.graphics.Color.argb(
                    (textColor.alpha * 255).toInt(),
                    (textColor.red * 255).toInt(),
                    (textColor.green * 255).toInt(),
                    (textColor.blue * 255).toInt()
                )
                textPaint.textSize = 10.sp.toPx()
                textPaint.isAntiAlias = true
                
                val text = String.format("%.0f", luxValue)
                val textBounds = android.graphics.Rect()
                textPaint.getTextBounds(text, 0, text.length, textBounds)
                
                drawContext.canvas.nativeCanvas.drawText(
                    text,
                    padding - textBounds.width() - 2.dp.toPx(),
                    y + 5.dp.toPx(),
                    textPaint
                )
            }
            
            val xLabelCount = minOf(visibleData.size, 10)
            for (i in 0 until xLabelCount) {
                val pointIndex = if (visibleData.size == 1) {
                    0
                } else {
                    i
                }
                val x = if (visibleData.size == 1) {
                    padding + (width - 2 * padding) / 2
                } else {
                    padding + (width - 2 * padding) * pointIndex / (visibleData.size - 1)
                }
                val y = height - padding + 15.dp.toPx()
                
                val textPaint = android.text.TextPaint()
                textPaint.color = android.graphics.Color.argb(
                    (textColor.alpha * 255).toInt(),
                    (textColor.red * 255).toInt(),
                    (textColor.green * 255).toInt(),
                    (textColor.blue * 255).toInt()
                )
                textPaint.textSize = 10.sp.toPx()
                textPaint.isAntiAlias = true
                
                val text = if (isRecording) {
                    "${data.size - visibleData.size + i + 1}"
                } else {
                    "${scrollOffset + i + 1}"
                }
                val textBounds = android.graphics.Rect()
                textPaint.getTextBounds(text, 0, text.length, textBounds)
                
                drawContext.canvas.nativeCanvas.drawText(
                    text,
                    x - textBounds.width() / 2,
                    y,
                    textPaint
                )
            }
        }
    }
}

@Composable
fun StatisticsCards(
    historyData: List<DataPoint>
) {
    if (historyData.isEmpty()) return
    
    val luxValues = historyData.map { it.lux }
    val minLux = luxValues.minOrNull() ?: 0f
    val maxLux = luxValues.maxOrNull() ?: 0f
    val avgLux = luxValues.average().toFloat()
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard("最小值", String.format("%.0f", minLux), MaterialTheme.colorScheme.primary, Modifier.weight(1f))
        StatCard("平均值", String.format("%.0f", avgLux), MaterialTheme.colorScheme.secondary, Modifier.weight(1f))
        StatCard("最大值", String.format("%.0f", maxLux), MaterialTheme.colorScheme.tertiary, Modifier.weight(1f))
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = "lux",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun SavedRecords(
    measurements: List<LightMeasurement>,
    selectedRecordIndex: Int?,
    onSaveRecord: (String) -> Unit,
    onDeleteRecord: (Int) -> Unit,
    onSelectRecord: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val (label, setLabel) = remember { mutableStateOf("") }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "保存记录",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = label,
                    onValueChange = { setLabel(it) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("输入标签（如：客厅等）") },
                    shape = RoundedCornerShape(12.dp)
                )
                
                Button(
                    onClick = { 
                        onSaveRecord(label)
                        setLabel("")
                    },
                    enabled = label.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Icon(
                        imageVector = CustomIcons.Save,
                        contentDescription = null
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (measurements.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "暂无保存的记录",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            } else {
                measurements.forEachIndexed { index, record ->
                    RecordItem(
                        record = record,
                        isSelected = selectedRecordIndex == index,
                        onClick = { onSelectRecord(index) },
                        onDelete = { onDeleteRecord(index) }
                    )
                    
                    if (index < measurements.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecordItem(
    record: LightMeasurement,
    isSelected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val avgLux = record.historyData?.map { it.lux }?.average()?.toFloat() ?: record.lux
    val dataCount = record.historyData?.size ?: 1
    val timeFormat = java.text.SimpleDateFormat("M/d HH:mm", java.util.Locale.getDefault())
    val timeStr = timeFormat.format(java.util.Date(record.timestamp))
    
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.label ?: "未命名",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = timeStr,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "平均: ${String.format("%.0f", avgLux)} lux",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "数据: $dataCount 个",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = CustomIcons.Trash,
                    contentDescription = "删除",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

data class LuxLevel(
    val text: String,
    val statusColor: Color,
    val bgColor: Color,
    val iconColor: Color
)

@Composable
fun getLuxLevel(lux: Float): LuxLevel {
    return when {
        lux < 100 -> LuxLevel(
            text = "极暗",
            statusColor = Gray600,
            bgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            iconColor = MaterialTheme.colorScheme.tertiary
        )
        lux < 300 -> LuxLevel(
            text = "较暗",
            statusColor = Blue500,
            bgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            iconColor = MaterialTheme.colorScheme.tertiary
        )
        lux < 500 -> LuxLevel(
            text = "适中",
            statusColor = Green500,
            bgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            iconColor = MaterialTheme.colorScheme.tertiary
        )
        lux < 750 -> LuxLevel(
            text = "明亮",
            statusColor = Amber500,
            bgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            iconColor = MaterialTheme.colorScheme.tertiary
        )
        else -> LuxLevel(
            text = "很亮",
            statusColor = Orange500,
            bgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            iconColor = MaterialTheme.colorScheme.tertiary
        )
    }
}
