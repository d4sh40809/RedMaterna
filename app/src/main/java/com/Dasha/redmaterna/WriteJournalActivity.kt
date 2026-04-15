package com.Dasha.redmaterna

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Dasha.redmaterna.ui.theme.RedMaternaTheme
import com.google.gson.Gson
import java.io.File

class WriteJournalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedMaternaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WriteJournalScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
@Preview
fun WriteJournalScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val gson = Gson()

    var selectedMood by remember { mutableStateOf("") }
    var inputContent by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("") }

    val emojis = listOf("😊", "😐", "😔", "😴", "🤮", "🤒", "🤯", "🥳")
    val tags = listOf("Anxiety", "Tiredness", "Happy", "Nausea", "Hopeful", "Stress", "Other", "Baby")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How are you feeling?",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )

        // Mood selection grid (2 rows, 4 columns)
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            for (row in 0..1) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0..3) {
                        val index = row * 4 + col
                        val emoji = emojis[index]
                        val isSelected = selectedMood == emoji
                        
                        Button(
                            onClick = { selectedMood = emoji },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color.LightGray else Color.White,
                                contentColor = Color.Black
                            ),
                            border = if (isSelected) null else BorderStroke(1.dp, Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = emoji, fontSize = 24.sp)
                        }
                    }
                }
            }
        }

        TextField(
            value = inputContent,
            onValueChange = { inputContent = it },
            label = { Text("Body") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(150.dp)
        )

        Text(
            text = "Add a tag",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )

        // Tags selection grid (2 rows, 4 columns)
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
            for (row in 0..1) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0..3) {
                        val index = row * 4 + col
                        val tag = tags[index]
                        val isSelected = selectedTag == tag
                        
                        Button(
                            onClick = { selectedTag = tag },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color.LightGray else Color.White,
                                contentColor = Color.Black
                            ),
                            border = if (isSelected) null else BorderStroke(1.dp, Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = tag, fontSize = 10.sp, maxLines = 1)
                        }
                    }
                }
            }
        }
        
        Button(
            onClick = {
                if (selectedMood.isNotEmpty() && inputContent.isNotEmpty() && selectedTag.isNotEmpty()) {
                    val entry = JournalEntry(selectedMood, inputContent, selectedTag)
                    val filename = "journal_${entry.timestamp}.json"
                    val file = File(context.filesDir, filename)
                    file.writeText(gson.toJson(entry))
                    
                    // Return to previous screen
                    (context as? Activity)?.finish()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(210, 150, 168),
                contentColor = Color.White
            )
        ) {
            Text("Save Entry")
        }
    }
}
