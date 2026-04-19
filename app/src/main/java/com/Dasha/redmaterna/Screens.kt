package com.Dasha.redmaterna


import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Dasha.redmaterna.ui.theme.SelectionColor
import com.google.gson.Gson

@Composable
@Preview
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRecordClick: () -> Unit = {},
    onJournalClick: () -> Unit = {},
    onSymptomsClick: () -> Unit = {},
    viewModel: UserViewModel = viewModel()
) {
    val userProfile by viewModel.userProfile.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(.95f)) {

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .height(150.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "ad",
                        style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Text(text = "", modifier = Modifier.padding(10.dp))
                    Column {
                        Text(text = "You're doing amazing!", style= TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground))
                        Text(text = "Every day is a step forward in your beautiful journey. Take time to rest and care for yourself today.", style= TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                    }
                }
            }

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(Modifier.fillMaxWidth().padding(10.dp)) {
                    Column(modifier = Modifier.weight(0.6f)) {
                        Text(text = "How you're feeling", style= TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant))
                        Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                            Text(text = "😊", style = TextStyle(fontSize = 30.sp))
                            Text(text = "Peaceful", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface), modifier = Modifier.padding(6.dp))
                        }
                    }
                    Column(modifier = Modifier.weight(0.4f), horizontalAlignment = Alignment.End) {
                        Text(text = "Pregnancy week", style= TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant))
                        Text(text = "${userProfile?.weeks ?: "0"}", style= TextStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 30.sp), modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                    }
                }
            }

            Button(
                onClick = onRecordClick,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(text = "+ Record how i feel", style= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(text = "Quick access", style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    
                    Row(modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                    ){
                        Button(
                            onClick = onJournalClick,
                            modifier = Modifier.weight(1f).padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Journal",
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(text = "Journal", fontSize = 12.sp)
                            }
                        }

                        Button(
                            onClick = onSymptomsClick,
                            modifier = Modifier.weight(1f).padding(start = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Symptoms",
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(text = "Symptoms", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }


            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ForumScreen(modifier: Modifier = Modifier, viewModel: ForumViewModel = viewModel()) {
    val context = LocalContext.current
    val posts by viewModel.posts.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        if (posts.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No posts yet.", color = MaterialTheme.colorScheme.onBackground)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                posts.forEach { post ->
                    ForumPostItem(post)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        FloatingActionButton(
            onClick = {
                val intent = Intent(context, WriteForumPostActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create Post")
        }
    }
}

@Composable
fun ForumPostItem(post: ForumPost) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = post.name ?: "Anonymous",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                    val dateStr = try {
                        java.text.DateFormat.getDateTimeInstance().format(java.util.Date(post.timestamp))
                    } catch (e: Exception) {
                        "Recently"
                    }
                    Text(
                        text = dateStr,
                        style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            Text(
                text = post.title ?: "",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(text = post.content ?: "", style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
        }
    }
}

@Composable
@Preview
fun SupportScreen(
    modifier: Modifier = Modifier,
    onForumClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You're not alone",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Connect with specialists and our caring community",
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        SupportCard(
            title = "Chat with specialist",
            subtitle = "Get professional support anytime",
            iconPainter = rememberVectorPainter(Icons.Default.DateRange),
            onClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SupportCard(
            title = "Community forum",
            subtitle = "Share experiences with other moms",
            iconPainter = painterResource(R.drawable.forum_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
            onClick = onForumClick
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Crisis Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFDF2F2) // Light red background
            ),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color(0xFFF5E1E1))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "In crisis?",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B1A10)
                    )
                )
                Text(
                    text = "If you're experiencing a mental health emergency, please reach out immediately.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF8B1A10).copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B1A10),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(text = "Call Crisis Line: 988", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SupportCard(
    title: String,
    subtitle: String,
    iconPainter: Painter,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = subtitle,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(modifier: Modifier = Modifier, initialTabIndex: Int = 0) {
    val context = LocalContext.current
    var journalEntries by remember { mutableStateOf<List<JournalEntry>>(emptyList()) }
    var symptomEntries by remember { mutableStateOf<List<SymptomEntry>>(emptyList()) }
    val lifecycleOwner = LocalLifecycleOwner.current
    
    var selectedTabIndex by remember { mutableIntStateOf(initialTabIndex) }
    val options = listOf("Journal", "Symptoms")

    LaunchedEffect(initialTabIndex) {
        selectedTabIndex = initialTabIndex
    }

    fun loadData() {
        val files = context.filesDir.listFiles() ?: emptyArray()
        val gson = Gson()

        journalEntries = files.filter { it.name.startsWith("journal_") && it.name.endsWith(".json") }
            .mapNotNull { file ->
                try {
                    val text = file.readText()
                    if (text.isBlank()) return@mapNotNull null
                    gson.fromJson(text, JournalEntry::class.java)
                } catch (e: Exception) {
                    null
                }
            }.sortedByDescending { it.timestamp }

        symptomEntries = files.filter { it.name.startsWith("symptom_") && it.name.endsWith(".json") }
            .mapNotNull { file ->
                try {
                    val text = file.readText()
                    if (text.isBlank()) return@mapNotNull null
                    gson.fromJson(text, SymptomEntry::class.java)
                } catch (e: Exception) {
                    null
                }
            }.sortedByDescending { it.timestamp }
    }

    LaunchedEffect(Unit) {
        loadData()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                loadData()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { selectedTabIndex = index },
                    selected = index == selectedTabIndex,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = SelectionColor,
                        activeContentColor = MaterialTheme.colorScheme.primary,
                        inactiveContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text(label)
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            if (selectedTabIndex == 0) {
                JournalContent(entries = journalEntries, onAddClick = {
                    val intent = Intent(context, WriteJournalActivity::class.java)
                    context.startActivity(intent)
                })
            } else {
                SymptomsContent(entries = symptomEntries, onAddClick = {
                    val intent = Intent(context, WriteSymptomActivity::class.java)
                    context.startActivity(intent)
                })
            }
        }
    }
}

@Composable
fun JournalContent(entries: List<JournalEntry>, onAddClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (entries.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No journal entries yet.", color = MaterialTheme.colorScheme.onBackground)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                entries.forEach { entry ->
                    JournalEntryItem(entry)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Write Journal")
        }
    }
}

@Composable
fun SymptomsContent(entries: List<SymptomEntry>, onAddClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (entries.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No symptom entries yet.", color = MaterialTheme.colorScheme.onBackground)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                entries.forEach { entry ->
                    SymptomEntryItem(entry)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Write Symptom")
        }
    }
}

@Composable
fun SymptomEntryItem(entry: SymptomEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = entry.type ?: "Physical",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                    val dateStr = try {
                        java.text.DateFormat.getDateTimeInstance().format(java.util.Date(entry.timestamp))
                    } catch (e: Exception) {
                        "Unknown date"
                    }
                    Text(
                        text = dateStr,
                        style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            Text(text = entry.content ?: "", style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface))
        }
    }
}

@Composable
fun JournalEntryItem(entry: JournalEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = entry.mood ?: "", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = entry.tag ?: "No Tag",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                    val dateStr = try {
                        java.text.DateFormat.getDateTimeInstance().format(java.util.Date(entry.timestamp))
                    } catch (e: Exception) {
                        "Unknown date"
                    }
                    Text(
                        text = dateStr,
                        style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            Text(text = entry.content ?: "", style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface))
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, viewModel: UserViewModel = viewModel()) {
    val userProfile by viewModel.userProfile.collectAsState()
    val context = LocalContext.current

    if (userProfile == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        var username by remember { mutableStateOf(userProfile?.username ?: "") }
        var age by remember { mutableStateOf(userProfile?.age ?: "") }
        var weeks by remember { mutableStateOf(userProfile?.weeks ?: "") }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            TextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            TextField(
                value = weeks,
                onValueChange = { weeks = it },
                label = { Text("Pregnancy Weeks") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            TextField(
                value = userProfile?.email ?: "",
                onValueChange = { },
                label = { Text("Email") },
                enabled = false,
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    val updatedProfile = userProfile?.copy(username = username, age = age, weeks = weeks)
                    if (updatedProfile != null) {
                        viewModel.saveUserProfile(updatedProfile) { success ->
                            if (success) {
                                Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text("Save Changes")
            }
        }
    }
}
