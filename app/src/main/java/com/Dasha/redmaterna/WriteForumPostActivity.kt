package com.Dasha.redmaterna

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Dasha.redmaterna.ui.theme.RedMaternaTheme

class WriteForumPostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedMaternaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WriteForumPostScreen(
                        modifier = Modifier.padding(innerPadding),
                        onPostSuccess = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun WriteForumPostScreen(
    modifier: Modifier = Modifier,
    forumViewModel: ForumViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    onPostSuccess: () -> Unit
) {
    val userProfile by userViewModel.userProfile.collectAsState()
    var inputTitle by remember { mutableStateOf("") }
    var inputContent by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create a Forum Post",
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Post anonymously", modifier = Modifier.weight(1f))
            Switch(
                checked = isAnonymous,
                onCheckedChange = { isAnonymous = it }
            )
        }

        TextField(
            value = if (isAnonymous) "Anonymous" else userProfile?.username ?: "",
            onValueChange = { },
            label = { Text("Username") },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            value = inputTitle,
            onValueChange = { inputTitle = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            value = inputContent,
            onValueChange = { inputContent = it },
            label = { Text("What's on your mind?") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(200.dp)
        )

        Button(
            onClick = {
                val finalName = if (isAnonymous) "Anonymous" else userProfile?.username ?: "User"
                if (inputTitle.isNotEmpty() && inputContent.isNotEmpty()) {
                    forumViewModel.addPost(finalName, inputTitle, inputContent)
                    onPostSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(210, 150, 168),
                contentColor = Color.White
            )
        ) {
            Text("Post")
        }
    }
}
