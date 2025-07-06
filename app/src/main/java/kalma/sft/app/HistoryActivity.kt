package kalma.sft.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kalma.sft.app.ui.theme.KAVLOTheme

data class ExerciseSession(
    val id: Int,
    val type: String,
    val duration: String,
    val date: String,
    val caloriesBurned: Int,
    val heartRateAvg: Int,
    val oxygenAvg: Int,
    val icon: ImageVector,
    val intensity: String
)

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KAVLOTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HistoryScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    val skyBlue = Color(0xFF87CEEB)
    val lightSkyBlue = Color(0xFFE0F6FF)
    val deepSkyBlue = Color(0xFF4682B4)

    val exerciseSessions = remember {
        listOf(
            ExerciseSession(
                1, "Lari Pagi", "45 menit", "Hari ini, 06:30",
                320, 145, 96, Icons.Default.DirectionsRun, "Sedang"
            ),
            ExerciseSession(
                2, "Yoga", "30 menit", "Kemarin, 18:00",
                120, 85, 98, Icons.Default.SelfImprovement, "Ringan"
            ),
            ExerciseSession(
                3, "Bersepeda", "60 menit", "2 hari lalu, 16:00",
                450, 135, 97, Icons.Default.DirectionsBike, "Tinggi"
            ),
            ExerciseSession(
                4, "Push-up", "20 menit", "3 hari lalu, 07:00",
                180, 120, 95, Icons.Default.FitnessCenter, "Sedang"
            ),
            ExerciseSession(
                5, "Berenang", "40 menit", "4 hari lalu, 17:30",
                380, 140, 96, Icons.Default.Pool, "Tinggi"
            ),
            ExerciseSession(
                6, "Jalan Santai", "25 menit", "5 hari lalu, 19:00",
                100, 95, 99, Icons.Default.DirectionsWalk, "Ringan"
            )
        )
    }

    var selectedFilter by remember { mutableStateOf("Semua") }
    val filters = listOf("Semua", "Hari ini", "Minggu ini", "Bulan ini")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(lightSkyBlue, Color.White)
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    "Riwayat Olahraga",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = skyBlue
            )
        )

        // Statistics Summary
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    "Statistik Minggu Ini",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = deepSkyBlue
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatisticItem(
                        value = "6",
                        label = "Sesi",
                        color = Color(0xFF4CAF50)
                    )
                    StatisticItem(
                        value = "280",
                        label = "Menit",
                        color = Color(0xFF2196F3)
                    )
                    StatisticItem(
                        value = "1,550",
                        label = "Kalori",
                        color = Color(0xFFFF9800)
                    )
                }
            }
        }

        // Filter Buttons
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    selected = selectedFilter == filter,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = skyBlue,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = deepSkyBlue
                    )
                )
            }
        }

        // Exercise History List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(exerciseSessions) { session ->
                ExerciseSessionCard(session = session)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StatisticItem(
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ExerciseSessionCard(session: ExerciseSession) {
    val intensityColor = when (session.intensity) {
        "Ringan" -> Color(0xFF4CAF50)
        "Sedang" -> Color(0xFFFF9800)
        "Tinggi" -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        session.icon,
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        session.type,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1565C0)
                    )
                    Text(
                        session.date,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Intensity Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(intensityColor.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        session.intensity,
                        fontSize = 10.sp,
                        color = intensityColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Duration
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Schedule,
                    contentDescription = null,
                    tint = Color(0xFF757575),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    session.duration,
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard(
                    icon = Icons.Default.LocalFireDepartment,
                    value = "${session.caloriesBurned}",
                    label = "kcal",
                    color = Color(0xFFFF5722)
                )

                StatCard(
                    icon = Icons.Default.Favorite,
                    value = "${session.heartRateAvg}",
                    label = "bpm",
                    color = Color(0xFFE91E63)
                )

                StatCard(
                    icon = Icons.Default.Air,
                    value = "${session.oxygenAvg}%",
                    label = "O2",
                    color = Color(0xFF2196F3)
                )
            }
        }
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .width(90.dp)
            .height(70.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.05f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                label,
                fontSize = 10.sp,
                color = color.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryPreview() {
    KAVLOTheme {
        HistoryScreen()
    }
}