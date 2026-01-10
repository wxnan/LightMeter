package com.example.lightmeter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lightmeter.data.*
import com.example.lightmeter.ui.theme.*
import com.example.lightmeter.ui.icons.CustomIcons

@Composable
fun CalculatorScreen(
    result: CalculationResult?,
    onCalculate: (
        roomLength: Float,
        roomWidth: Float,
        roomHeight: Float,
        numLights: Int,
        lightType: LightType,
        utilizationFactor: Float,
        maintenanceFactor: MaintenanceFactor,
        luxMode: String,
        customLux: Float,
        sceneType: RoomType
    ) -> Unit,
    onClearResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    var roomLength by remember { mutableStateOf("") }
    var roomWidth by remember { mutableStateOf("") }
    var roomHeight by remember { mutableStateOf("2.8") }
    var luxMode by remember { mutableStateOf("recommended") }
    var customLux by remember { mutableStateOf("") }
    var numLights by remember { mutableStateOf("1") }
    var selectedLightType by remember { mutableStateOf(DataRepository.lightTypes[0]) }
    var utilizationFactor by remember { mutableStateOf("0.7") }
    var selectedMaintenanceFactor by remember { mutableStateOf(DataRepository.maintenanceFactors[0]) }
    var selectedRoomType by remember { mutableStateOf(DataRepository.roomTypes[0]) }
    
    val currentLux = when (luxMode) {
        "recommended" -> selectedRoomType.recommendedLux.toFloat()
        "standard" -> selectedRoomType.standardLux.toFloat()
        else -> customLux.toFloatOrNull() ?: 0f
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CalculatorHeader()
        
        CalculatorForm(
            roomLength = roomLength,
            roomWidth = roomWidth,
            roomHeight = roomHeight,
            luxMode = luxMode,
            customLux = customLux,
            currentLux = currentLux,
            numLights = numLights,
            selectedLightType = selectedLightType,
            utilizationFactor = utilizationFactor,
            selectedMaintenanceFactor = selectedMaintenanceFactor,
            selectedRoomType = selectedRoomType,
            onRoomLengthChange = { roomLength = it },
            onRoomWidthChange = { roomWidth = it },
            onRoomHeightChange = { roomHeight = it },
            onLuxModeChange = { luxMode = it },
            onCustomLuxChange = { customLux = it },
            onNumLightsChange = { numLights = it },
            onLightTypeChange = { 
                selectedLightType = it
                utilizationFactor = when (it.value) {
                    "ceiling" -> "0.7"
                    "pendant" -> "0.65"
                    "downlight" -> "0.45"
                    else -> "0.7"
                }
            },
            onUtilizationFactorChange = { utilizationFactor = it },
            onMaintenanceFactorChange = { selectedMaintenanceFactor = it },
            onRoomTypeChange = { selectedRoomType = it }
        )
        
        Button(
            onClick = {
                val length = roomLength.toFloatOrNull() ?: 0f
                val width = roomWidth.toFloatOrNull() ?: 0f
                val height = roomHeight.toFloatOrNull() ?: 0f
                val lights = numLights.toIntOrNull() ?: 1
                val utilFactor = utilizationFactor.toFloatOrNull() ?: when (selectedLightType.value) {
                    "ceiling" -> 0.7f
                    "pendant" -> 0.65f
                    "downlight" -> 0.45f
                    else -> 0.7f
                }
                onCalculate(length, width, height, lights, selectedLightType, utilFactor, selectedMaintenanceFactor, luxMode, customLux.toFloatOrNull() ?: 0f, selectedRoomType)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                text = "开始计算",
                style = MaterialTheme.typography.titleMedium
            )
        }
        
        if (result != null) {
            CalculationResultCard(
                result = result,
                lightType = selectedLightType,
                roomHeight = roomHeight,
                maintenanceFactor = selectedMaintenanceFactor,
                onClear = onClearResult
            )
        }
    }
}

@Composable
fun CalculatorHeader(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        ),
        // elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = CustomIcons.Calculator,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "照度计算器",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorForm(
    roomLength: String,
    roomWidth: String,
    roomHeight: String,
    luxMode: String,
    customLux: String,
    currentLux: Float,
    numLights: String,
    selectedLightType: LightType,
    utilizationFactor: String,
    selectedMaintenanceFactor: MaintenanceFactor,
    selectedRoomType: RoomType,
    onRoomLengthChange: (String) -> Unit,
    onRoomWidthChange: (String) -> Unit,
    onRoomHeightChange: (String) -> Unit,
    onLuxModeChange: (String) -> Unit,
    onCustomLuxChange: (String) -> Unit,
    onNumLightsChange: (String) -> Unit,
    onLightTypeChange: (LightType) -> Unit,
    onUtilizationFactorChange: (String) -> Unit,
    onMaintenanceFactorChange: (MaintenanceFactor) -> Unit,
    onRoomTypeChange: (RoomType) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "场景类型",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            var roomTypeExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = roomTypeExpanded,
                onExpandedChange = { roomTypeExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedRoomType.name,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = roomTypeExpanded) },
                    shape = RoundedCornerShape(12.dp)
                )
                
                ExposedDropdownMenu(
                    expanded = roomTypeExpanded,
                    onDismissRequest = { roomTypeExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DataRepository.roomTypes.forEach { room ->
                        DropdownMenuItem(
                            text = { Text(room.name) },
                            onClick = {
                                onRoomTypeChange(room)
                                roomTypeExpanded = false
                            }
                        )
                    }
                }
            }
            
            Text(
                text = "房间尺寸（米）",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RoomDimensionInput(
                    value = roomLength,
                    onValueChange = onRoomLengthChange,
                    label = "长度",
                    modifier = Modifier.weight(1f)
                )
                RoomDimensionInput(
                    value = roomWidth,
                    onValueChange = onRoomWidthChange,
                    label = "宽度",
                    modifier = Modifier.weight(1f)
                )
                RoomDimensionInput(
                    value = roomHeight,
                    onValueChange = onRoomHeightChange,
                    label = "高度",
                    modifier = Modifier.weight(1f)
                )
            }
            
            Text(
                text = "期望照度（lux）",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LuxModeButton(
                    text = "推荐值",
                    selected = luxMode == "recommended",
                    onClick = { onLuxModeChange("recommended") },
                    modifier = Modifier.weight(1f)
                )
                LuxModeButton(
                    text = "标准值",
                    selected = luxMode == "standard",
                    onClick = { onLuxModeChange("standard") },
                    modifier = Modifier.weight(1f)
                )
                LuxModeButton(
                    text = "自定义",
                    selected = luxMode == "custom",
                    onClick = { onLuxModeChange("custom") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            if (luxMode == "custom") {
                OutlinedTextField(
                    value = customLux,
                    onValueChange = onCustomLuxChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("输入照度值") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp)
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${currentLux.toInt()} lux",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Blue500
                    )
                }
            }
            
            Text(
                text = "灯具参数",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = numLights,
                    onValueChange = onNumLightsChange,
                    modifier = Modifier.weight(1f),
                    label = { Text("数量") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp)
                )
                
                var lightTypeExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = lightTypeExpanded,
                    onExpandedChange = { lightTypeExpanded = it },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedLightType.label,
                        label = { Text("类型") },
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = lightTypeExpanded) },
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    ExposedDropdownMenu(
                        expanded = lightTypeExpanded,
                        onDismissRequest = { lightTypeExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DataRepository.lightTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.label) },
                                onClick = {
                                    onLightTypeChange(type)
                                    lightTypeExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            
            Text(
                text = "利用系数（0.1~0.9）",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            OutlinedTextField(
                value = utilizationFactor,
                onValueChange = onUtilizationFactorChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("0.1~0.9") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(12.dp)
            )
            
            Text(
                text = "维护系数",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            var maintenanceExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = maintenanceExpanded,
                onExpandedChange = { maintenanceExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedMaintenanceFactor.label,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = maintenanceExpanded) },
                    shape = RoundedCornerShape(12.dp)
                )
                
                ExposedDropdownMenu(
                    expanded = maintenanceExpanded,
                    onDismissRequest = { maintenanceExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DataRepository.maintenanceFactors.forEach { factor ->
                        DropdownMenuItem(
                            text = { Text(factor.label) },
                            onClick = {
                                onMaintenanceFactorChange(factor)
                                maintenanceExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RoomDimensionInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun LuxModeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        ),
        border = if (!selected) ButtonDefaults.outlinedButtonBorder else null,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            softWrap = false
        )
    }
}

@Composable
fun CalculationResultCard(
    result: CalculationResult,
    lightType: LightType,
    roomHeight: String,
    maintenanceFactor: MaintenanceFactor,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = CustomIcons.Lightbulb,
                    contentDescription = null,
                    tint = Amber500,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "计算结果",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "单个灯具所需光通量",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "${result.requiredLumensPerLight} lm",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ResultStatCard(
                    label = "总光通量",
                    value = "${result.totalLumens} lm",
                    color = MaterialTheme.colorScheme.primary,
                    bgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    modifier = Modifier.weight(1f)
                )
                ResultStatCard(
                    label = "单灯照度",
                    value = "${result.luxPerLight} lux",
                    color = MaterialTheme.colorScheme.secondary,
                    bgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                    modifier = Modifier.weight(1f)
                )
            }
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "💡 选购建议：",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "• 选择光通量 ≥ ${result.requiredLumensPerLight}lm 的灯具\n• LED灯泡参考：800lm≈10W，1200lm≈15W，1600lm≈20W\n• 建议预留10-15%余量，应对灯具光衰\n• 可调光灯具能适应不同使用场景",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "📐 计算说明：",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "• 已考虑${lightType.label}的光效系数\n• 已根据层高${roomHeight}米进行修正\n• 维护系数${maintenanceFactor.label}已计入计算\n• 深色装修建议增加15-20%流明值",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            Button(
                onClick = onClear,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("清除结果")
            }
        }
    }
}

@Composable
fun ResultStatCard(
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color,
    bgColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
        }
    }
}
