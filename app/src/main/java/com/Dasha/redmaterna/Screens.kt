package com.Dasha.redmaterna

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.gson.Gson
import java.io.File

@Composable
@Preview
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRecordClick: () -> Unit = {},
    onJournalClick: () -> Unit = {},
    onSymptomsClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(.95f)) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color(252, 247, 248), shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth(1f)
                    .border(width = 2.dp, color = Color(224, 208, 211), shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.TopCenter

            ) {
                Row {
                    Text(text = "test2", modifier = Modifier.padding(10.dp))
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "You're doing amazing!", style= TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                        Text(text = "Every day is a step forward in your beautiful journey. Take time to rest and care for yourself today.", style= TextStyle(color = Color(117, 102, 105)), modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                    }

                }
                
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth(1f)
                    .border(width = 2.dp, color = Color(224, 208, 211), shape = RoundedCornerShape(20.dp)),
            ) {
                Row(Modifier.fillMaxWidth(0.95f)) {
                    Column(modifier = Modifier.padding(10.dp).fillMaxWidth(0.6f)) {
                        Text(text = "How you're feeling", style= TextStyle(color = Color(117, 102, 105)))
                        Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                            Text(text = "😊", style = TextStyle(fontSize = 30.sp))
                            Text(text = "Peaceful", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), modifier = Modifier.padding(6.dp))
                        }
                    }
                    Column(modifier = Modifier.padding(10.dp).fillMaxWidth(1f), horizontalAlignment = Alignment.End) {
                        Text(text = "Pregnancy week", style= TextStyle(color = Color(117, 102, 105)))
                        Text(text = "24", style= TextStyle(color = Color(210, 150, 168), fontWeight = FontWeight.Bold, fontSize = 30.sp), modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                    }
                }

            }

            Button(
                onClick = onRecordClick,
                modifier = Modifier.padding(16.dp).fillMaxWidth(1f),
                colors = ButtonColors(containerColor = Color(210, 150, 168), contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.White )
            ) {
                Text(text = "+ Record how i feel", style= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(1f)
            ) {
                Text(text = "Quick access", style = TextStyle(color = Color(117, 102, 105)))
                
                Row(modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(1f)

                ){
                    Button(
                        onClick = onJournalClick,
                        modifier = Modifier.padding(top = 10.dp).fillMaxWidth(0.4f),
                        colors = ButtonColors(containerColor = Color(210, 150, 168), contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.White )
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Home",
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.CenterHorizontally)

                            )
                            Text(text = "Journal")
                        }

                    }

                    Spacer(modifier = Modifier.weight(0.1f))

                    Button(
                        onClick = onSymptomsClick,
                        modifier = Modifier.padding(top = 10.dp).fillMaxWidth(0.6f),
                        colors = ButtonColors(containerColor = Color(210, 150, 168), contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.White )
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Home",
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.CenterHorizontally)

                            )
                            Text(text = "Symptoms")
                        }
                    }
                }
            }

        }

    }
}

@Composable
@Preview
fun ForumScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val file = File(context.filesDir, "forum_data.json")
    val gson = Gson()

    var inputName by remember { mutableStateOf("") }
    var inputContent by remember { mutableStateOf("") }
    
    var savedPost by remember { 
        mutableStateOf(
            if (file.exists()) {
                try { gson.fromJson(file.readText(), ForumPost::class.java) ?: ForumPost("", "") } 
                catch (e: Exception) { ForumPost("", "") }
            } else ForumPost("", "")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = inputName,
            onValueChange = { inputName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = inputContent,
            onValueChange = { inputContent = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                val post = ForumPost(inputName, inputContent)
                file.writeText(gson.toJson(post))
                savedPost = post
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            Text("Save Data")
        }

        Text(text = "Saved Name: ${savedPost.name}", fontWeight = FontWeight.Bold)
        Text(text = "Saved Content: ${savedPost.content}")
    }
}

@Composable
@Preview
fun SupportScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Support Page Content", modifier = Modifier.padding(16.dp))
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

    // Update selected tab if initialTabIndex changes via navigation
    LaunchedEffect(initialTabIndex) {
        selectedTabIndex = initialTabIndex
    }

    // Helper function to load both types of entries
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

    // Initial load
    LaunchedEffect(Unit) {
        loadData()
    }

    // Refresh when returning to the screen (On Resume)
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
        // Toggle Switch at the top
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { selectedTabIndex = index },
                    selected = index == selectedTabIndex
                ) {
                    Text(label)
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            if (selectedTabIndex == 0) {
                // Journal Tab
                JournalContent(entries = journalEntries, onAddClick = {
                    val intent = Intent(context, WriteJournalActivity::class.java)
                    context.startActivity(intent)
                })
            } else {
                // Symptoms Tab
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
                Text("No journal entries yet.")
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
            containerColor = Color(210, 150, 168),
            contentColor = Color.White
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
                Text("No symptom entries yet.")
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
            containerColor = Color(210, 150, 168),
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Write Symptom")
        }
    }
}

@Composable
fun SymptomEntryItem(entry: SymptomEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(224, 208, 211))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder, 
                    contentDescription = null,
                    tint = Color(210, 150, 168),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = entry.type ?: "Physical",
                        style = TextStyle(
                            color = Color(210, 150, 168),
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
                        style = TextStyle(color = Color.Gray, fontSize = 10.sp)
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
            Text(text = entry.content ?: "", style = TextStyle(fontSize = 16.sp))
        }
    }
}

@Composable
fun JournalEntryItem(entry: JournalEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(224, 208, 211))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = entry.mood ?: "", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = entry.tag ?: "No Tag",
                        style = TextStyle(
                            color = Color(210, 150, 168),
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
                        style = TextStyle(color = Color.Gray, fontSize = 10.sp)
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
            Text(text = entry.content ?: "", style = TextStyle(fontSize = 16.sp))
        }
    }
}

@Composable
@Preview
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Profile Page Content", modifier = Modifier.padding(16.dp))
    }
}
