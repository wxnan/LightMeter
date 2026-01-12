package com.example.lightmeter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.BackHandler
import com.example.lightmeter.data.*
import com.example.lightmeter.ui.theme.*
import com.example.lightmeter.ui.icons.CustomIcons

@Composable
fun SceneScreen(
    currentLux: Float,
    selectedScene: SceneType,
    showSelector: Boolean,
    onToggleSelector: () -> Unit,
    onSelectScene: (SceneType) -> Unit,
    modifier: Modifier = Modifier
) {
    val status = getSceneLuxStatus(currentLux, selectedScene)
    
    if (showSelector) {
        SceneSelector(
            selectedScene = selectedScene,
            onSelectScene = onSelectScene,
            onBack = onToggleSelector,
            modifier = modifier
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RealtimeLuxDisplayScene(currentLux, status)
            
            SceneSelectorCard(
                selectedScene = selectedScene,
                onShowSelector = onToggleSelector
            )
            
            SceneDetailsCard(selectedScene)
        }
    }
}

@Composable
fun RealtimeLuxDisplayScene(
    lux: Float,
    status: SceneLuxStatus,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                text = String.format("%.0f", lux),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = status.iconColor
            )
            
            Text(
                text = "Lux",
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
fun SceneSelectorCard(
    selectedScene: SceneType,
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
                    text = "当前场景",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = selectedScene.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "${selectedScene.recommendedLux} lux",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Button(
                onClick = onShowSelector,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text("选择场景")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(CustomIcons.ChevronRight, contentDescription = null)
            }
        }
    }
}

@Composable
fun SceneDetailsCard(
    scene: SceneType,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = getSceneIcon(scene.id),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = scene.name,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = scene.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            Card(
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
                        text = "推荐照度",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${scene.recommendedLux} lux",
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
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
                    Text(
                        text = "色温建议",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = scene.colorTemp,
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = getColorTempDescription(scene.colorTemp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
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
                    Text(
                        text = "💡 照明建议",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    scene.tips.forEach { tip ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(
                                text = "✓ ",
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = tip,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            
            if (scene.specialRequirements != null) {
                Card(
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
                            text = "⚠️ 特殊要求",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        scene.specialRequirements.forEach { req ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    text = "• ",
                                    color = MaterialTheme.colorScheme.tertiary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = req,
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
}

@Composable
fun SceneSelector(
    selectedScene: SceneType,
    onSelectScene: (SceneType) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)
    
    Column(
        modifier = modifier
            .fillMaxSize()
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
                text = "选择场景",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        DataRepository.scenes.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { scene ->
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = { onSelectScene(scene) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = getSceneIcon(scene.id),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.tertiary,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = scene.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${scene.recommendedLux} lux",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

fun getSceneIcon(sceneId: String) = when (sceneId) {
    "reading" -> CustomIcons.Book
    "living" -> CustomIcons.Sofa
    "bedroom" -> CustomIcons.Bed
    "dining" -> CustomIcons.Coffee
    "kitchen" -> CustomIcons.Utensils
    "bathroom" -> CustomIcons.Toilet
    "study" -> CustomIcons.Laptop
    "corridor" -> CustomIcons.Footprints
    "children" -> CustomIcons.Baby
    "elderly" -> CustomIcons.HeartPulse
    else -> CustomIcons.Lightbulb
}

data class SceneLuxStatus(
    val text: String,
    val statusColor: androidx.compose.ui.graphics.Color,
    val bgColor: androidx.compose.ui.graphics.Color,
    val iconColor: androidx.compose.ui.graphics.Color
)

@Composable
fun getSceneLuxStatus(lux: Float, scene: SceneType): SceneLuxStatus {
    val range = scene.recommendedLux.split("-").map { it.toInt() }
    return when {
        lux < range[0] -> SceneLuxStatus(
            text = "照度偏低",
            statusColor = Red500,
            bgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            iconColor = MaterialTheme.colorScheme.tertiary
        )
        lux > range[1] -> SceneLuxStatus(
            text = "照度偏高",
            statusColor = Orange500,
            bgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            iconColor = MaterialTheme.colorScheme.tertiary
        )
        else -> SceneLuxStatus(
            text = "照度适宜",
            statusColor = Green500,
            bgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            iconColor = MaterialTheme.colorScheme.tertiary
        )
    }
}

fun getColorTempDescription(colorTemp: String): String {
    val temp = colorTemp.split("-")[0].toIntOrNull() ?: 4000
    return when {
        temp < 3300 -> "暖白光（温馨放松）"
        temp < 5300 -> "自然白光（舒适清晰）"
        else -> "冷白光（清爽明亮）"
    }
}
