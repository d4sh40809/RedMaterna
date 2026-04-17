package com.Dasha.redmaterna

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Dasha.redmaterna.ui.theme.RedMaternaTheme
import com.google.gson.Gson
import java.io.File

class WriteSymptomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedMaternaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WriteSymptomScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
@Preview
fun WriteSymptomScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val gson = Gson()

    var inputContent by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Physical") }
    val types = listOf("Physical", "Emotional")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What are you feeling?",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        TextField(
            value = inputContent,
            onValueChange = { inputContent = it },
            label = { Text("Describe your symptom") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(200.dp)
        )

        Text(
            text = "Type",
            modifier = Modifier.padding(bottom = 8.dp).align(Alignment.Start),
            fontWeight = FontWeight.Bold
        )

        // Radio buttons for type (Physical vs Emotional)
        types.forEach { type ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (type == selectedType),
                        onClick = { selectedType = type },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (type == selectedType),
                    onClick = null // null because the Row handles the click
                )
                Text(
                    text = type,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        
        Button(
            onClick = {
                if (inputContent.isNotEmpty()) {
                    val entry = SymptomEntry(inputContent, selectedType)
                    val filename = "symptom_${entry.timestamp}.json"
                    val file = File(context.filesDir, filename)
                    file.writeText(gson.toJson(entry))
                    
                    // Return to previous screen
                    (context as? Activity)?.finish()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
        ) {
            Text("Save Symptom")
        }
    }
}
