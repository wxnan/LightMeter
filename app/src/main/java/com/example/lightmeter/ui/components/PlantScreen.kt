package com.example.lightmeter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.BackHandler
import com.example.lightmeter.data.*
import com.example.lightmeter.ui.theme.*
import com.example.lightmeter.ui.icons.CustomIcons

@Composable
fun PlantScreen(
    currentLux: Float,
    selectedPlant: Plant,
    showSelector: Boolean,
    onToggleSelector: () -> Unit,
    onSelectPlant: (Plant) -> Unit,
    onUpdatePlant: (Plant) -> Unit,
    displayMode: String = "lux",
    ppfdConversionFactor: Float = 0.0185f,
    onToggleDisplayMode: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val status = getPlantLuxStatus(currentLux, selectedPlant, displayMode, ppfdConversionFactor)
    var showEditDialog by remember { mutableStateOf(false) }
    var editingPlant by remember { mutableStateOf(selectedPlant) }
    
    if (showSelector) {
        PlantSelector(
            selectedPlant = selectedPlant,
            onSelectPlant = onSelectPlant,
            onBack = onToggleSelector,
            modifier = modifier
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RealtimeLuxDisplayPlant(
                lux = currentLux,
                status = status,
                displayMode = displayMode,
                ppfdConversionFactor = ppfdConversionFactor,
                onToggleDisplayMode = onToggleDisplayMode
            )
            
            PlantSelectorCard(
                selectedPlant = selectedPlant,
                onShowSelector = onToggleSelector
            )
            
            PlantDetailsCard(
                plant = selectedPlant,
                onEditClick = { 
                    editingPlant = selectedPlant
                    showEditDialog = true 
                }
            )
        }
    }
    
    if (showEditDialog) {
        PlantEditDialog(
            plant = editingPlant,
            onDismiss = { showEditDialog = false },
            onSave = { updatedPlant ->
                onUpdatePlant(updatedPlant)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun RealtimeLuxDisplayPlant(
    lux: Float,
    status: PlantLuxStatus,
    displayMode: String = "lux",
    ppfdConversionFactor: Float = 0.0185f,
    onToggleDisplayMode: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val ppfd = lux * ppfdConversionFactor
    val displayValue = if (displayMode == "ppfd") String.format("%.1f", ppfd) else String.format("%.0f", lux)
    val displayUnit = if (displayMode == "ppfd") "μmol/m²·s" else "Lux"
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onToggleDisplayMode),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = status.bgColor
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
                tint = status.iconColor
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = displayValue,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = status.iconColor
            )
            
            Text(
                text = displayUnit,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = status.statusColor.copy(alpha = 0.2f)
            ) {
                Text(
                    text = status.text,
                    color = status.statusColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PlantSelectorCard(
    selectedPlant: Plant,
    onShowSelector: () -> Unit,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "当前植物",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = selectedPlant.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = selectedPlant.nameEn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Button(
                onClick = onShowSelector,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text("选择植物")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(CustomIcons.ChevronRight, contentDescription = null)
            }
        }
    }
}

@Composable
fun PlantDetailsCard(
    plant: Plant,
    onEditClick: () -> Unit,
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = CustomIcons.Leaf,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = plant.name,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = plant.nameEn,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "编辑",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CustomIcons.Sun,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "理想照度范围",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "${plant.minLux} - ${plant.maxLux} lux",
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "每日光照时长：${plant.hoursPerDay} 小时",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CustomIcons.Sun,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "理想PPFD范围",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "${plant.minPPFD} - ${plant.maxPPFD}",
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "光合有效辐射密度（μmol/m²·s）",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            SymptomCard("缺光症状识别", plant.lackSymptoms, MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.error.copy(alpha = 0.2f))
            SymptomCard("过光症状识别", plant.excessSymptoms, MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "💡 养护建议",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    )
                    Text(
                        text = plant.tips,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun SymptomCard(
    title: String,
    symptoms: List<String>,
    color: androidx.compose.ui.graphics.Color,
    bgColor: androidx.compose.ui.graphics.Color
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = CustomIcons.Warning,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            symptoms.forEach { symptom ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "• ",
                        color = color,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = symptom,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun PlantSelector(
    selectedPlant: Plant,
    onSelectPlant: (Plant) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(CustomIcons.ChevronLeft, contentDescription = "返回")
            }
            Text(
                text = "选择植物",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        DataRepository.getAllPlants().chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { plant ->
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = { onSelectPlant(plant) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = plant.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = plant.nameEn,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "${plant.minLux}-${plant.maxLux} lux",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

data class PlantLuxStatus(
    val text: String,
    val statusColor: androidx.compose.ui.graphics.Color,
    val bgColor: androidx.compose.ui.graphics.Color,
    val iconColor: androidx.compose.ui.graphics.Color
)

@Composable
fun getPlantLuxStatus(
    lux: Float,
    plant: Plant,
    displayMode: String = "lux",
    ppfdConversionFactor: Float = 0.0185f
): PlantLuxStatus {
    val ppfd = lux * ppfdConversionFactor
    return when {
        displayMode == "ppfd" -> {
            when {
                ppfd < plant.minPPFD -> PlantLuxStatus(
                    text = "光照不足",
                    statusColor = Red500,
                    bgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    iconColor = MaterialTheme.colorScheme.secondary
                )
                ppfd > plant.maxPPFD -> PlantLuxStatus(
                    text = "光照过强",
                    statusColor = Orange500,
                    bgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    iconColor = MaterialTheme.colorScheme.secondary
                )
                else -> PlantLuxStatus(
                    text = "光照适宜",
                    statusColor = Green500,
                    bgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    iconColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
        else -> {
            when {
                lux < plant.minLux -> PlantLuxStatus(
                    text = "光照不足",
                    statusColor = Red500,
                    bgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    iconColor = MaterialTheme.colorScheme.secondary
                )
                lux > plant.maxLux -> PlantLuxStatus(
                    text = "光照过强",
                    statusColor = Orange500,
                    bgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    iconColor = MaterialTheme.colorScheme.secondary
                )
                else -> PlantLuxStatus(
                    text = "光照适宜",
                    statusColor = Green500,
                    bgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    iconColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun PlantEditDialog(
    plant: Plant,
    onDismiss: () -> Unit,
    onSave: (Plant) -> Unit
) {
    var name by remember { mutableStateOf(plant.name) }
    var nameEn by remember { mutableStateOf(plant.nameEn) }
    var minLux by remember { mutableStateOf(plant.minLux.toString()) }
    var maxLux by remember { mutableStateOf(plant.maxLux.toString()) }
    var minPPFD by remember { mutableStateOf(plant.minPPFD.toString()) }
    var maxPPFD by remember { mutableStateOf(plant.maxPPFD.toString()) }
    var hoursPerDay by remember { mutableStateOf(plant.hoursPerDay) }
    
    val defaultPlant = DataRepository.plants.find { it.id == plant.id } ?: plant
    
    fun resetToDefault() {
        name = defaultPlant.name
        nameEn = defaultPlant.nameEn
        minLux = defaultPlant.minLux.toString()
        maxLux = defaultPlant.maxLux.toString()
        minPPFD = defaultPlant.minPPFD.toString()
        maxPPFD = defaultPlant.maxPPFD.toString()
        hoursPerDay = defaultPlant.hoursPerDay
    }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "编辑植物参数",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("中文名称") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = nameEn,
                    onValueChange = { nameEn = it },
                    label = { Text("英文名称") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = minLux,
                    onValueChange = { minLux = it },
                    label = { Text("最小照度 (Lux)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                OutlinedTextField(
                    value = maxLux,
                    onValueChange = { maxLux = it },
                    label = { Text("最大照度 (Lux)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                OutlinedTextField(
                    value = minPPFD,
                    onValueChange = { minPPFD = it },
                    label = { Text("最小PPFD (μmol/m²·s)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                OutlinedTextField(
                    value = maxPPFD,
                    onValueChange = { maxPPFD = it },
                    label = { Text("最大PPFD (μmol/m²·s)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                OutlinedTextField(
                    value = hoursPerDay,
                    onValueChange = { hoursPerDay = it },
                    label = { Text("每日光照时长 (小时)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedPlant = plant.copy(
                    name = name,
                    nameEn = nameEn,
                    minLux = minLux.toIntOrNull() ?: plant.minLux,
                    maxLux = maxLux.toIntOrNull() ?: plant.maxLux,
                    minPPFD = minPPFD.toIntOrNull() ?: plant.minPPFD,
                    maxPPFD = maxPPFD.toIntOrNull() ?: plant.maxPPFD,
                    hoursPerDay = hoursPerDay
                )
                onSave(updatedPlant)
            }) {
                Text("保存")
            }
        },
        dismissButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = { resetToDefault() }) {
                    Text("重置")
                }
                TextButton(onClick = onDismiss) {
                    Text("取消")
                }
            }
        }
    )
}
