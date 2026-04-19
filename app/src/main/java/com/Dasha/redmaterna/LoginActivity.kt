package com.Dasha.redmaterna

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.Dasha.redmaterna.ui.theme.RedMaternaTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG = "LoginDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        enableEdgeToEdge()
        setContent {
            RedMaternaTheme {
                var showProfileRequest by remember { mutableStateOf(false) }
                var isLoading by remember { mutableStateOf(true) }
                val context = LocalContext.current

                // Check existing session
                LaunchedEffect(Unit) {
                    val user = auth.currentUser
                    if (user != null) {
                        Log.d(TAG, "Session found for UID: ${user.uid}")
                        checkIfProfileExists(user.uid) { exists ->
                            if (exists) {
                                Log.d(TAG, "Profile exists, starting MainActivity")
                                startMainActivity()
                            } else {
                                Log.d(TAG, "Profile missing, showing ProfileRequest")
                                showProfileRequest = true
                                isLoading = false
                            }
                        }
                    } else {
                        Log.d(TAG, "No session found")
                        isLoading = false
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                        if (isLoading) {
                            CircularProgressIndicator()
                        } else if (showProfileRequest) {
                            ProfileRequestScreen(onProfileSubmitted = { age, weeks ->
                                isLoading = true
                                saveInitialProfile(age, weeks) { success ->
                                    if (success) {
                                        startMainActivity()
                                    } else {
                                        isLoading = false
                                        Toast.makeText(context, "Failed to save profile", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        } else {
                            LoginScreen(
                                onLoginSuccess = {
                                    isLoading = true
                                    val uid = auth.currentUser?.uid ?: ""
                                    checkAndHandleLogin(uid) { needsProfile ->
                                        if (needsProfile) {
                                            showProfileRequest = true
                                            isLoading = false
                                        } else {
                                            startMainActivity()
                                        }
                                    }
                                },
                                onLoginFailure = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun checkIfProfileExists(uid: String, callback: (Boolean) -> Unit) {
        FirebaseDatabase.getInstance().getReference("users").child(uid)
            .get().addOnSuccessListener { snapshot ->
                callback(snapshot.exists())
            }.addOnFailureListener { e ->
                Log.e(TAG, "Database check failed: ${e.message}")
                callback(false) 
            }
    }

    private fun checkAndHandleLogin(uid: String, callback: (Boolean) -> Unit) {
        checkIfProfileExists(uid) { exists ->
            callback(!exists)
        }
    }

    private fun saveInitialProfile(age: String, weeks: String, onComplete: (Boolean) -> Unit) {
        val user = auth.currentUser ?: return
        val profile = UserProfile(
            username = user.displayName ?: "User",
            age = age,
            weeks = weeks,
            email = user.email ?: "",
            uid = user.uid
        )
        FirebaseDatabase.getInstance().getReference("users").child(user.uid)
            .setValue(profile).addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

@Composable
@Preview
fun LoginScreen(onLoginSuccess: () -> Unit, onLoginFailure: (String) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val credentialManager = CredentialManager.create(context)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            if (BuildConfig.WEB_CLIENT_ID.isEmpty() || BuildConfig.WEB_CLIENT_ID == "YOUR_WEB_CLIENT_ID_HERE") {
                onLoginFailure("Web Client ID is not configured")
                return@Button
            }

            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            coroutineScope.launch {
                try {
                    val result = credentialManager.getCredential(context, request)
                    handleSignIn(result, auth, onLoginSuccess, onLoginFailure)
                } catch (e: NoCredentialException) {
                    onLoginFailure("No Google accounts found or SHA-1 mismatch.")
                } catch (e: GetCredentialException) {
                    onLoginFailure("Sign in failed: ${e.message}")
                }
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD296A8),
                contentColor = Color.White
            )) {
            Text("Sign in with Google")
        }
    }
}

private fun handleSignIn(
    result: GetCredentialResponse, 
    auth: FirebaseAuth, 
    onLoginSuccess: () -> Unit,
    onLoginFailure: (String) -> Unit
) {
    try {
        // Use createFrom to extract the Google ID token from the custom credential data
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
        
        val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
        auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onLoginSuccess()
            } else {
                onLoginFailure(task.exception?.message ?: "Firebase Auth Failed")
            }
        }
    } catch (e: Exception) {
        Log.e("LoginDebug", "Unexpected credential format: ${e.message}")
        onLoginFailure("Unexpected credential type: ${e.message}")
    }
}

@Composable
fun ProfileRequestScreen(onProfileSubmitted: (String, String) -> Unit) {
    var age by remember { mutableStateOf("") }
    var weeks by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        Text("Please complete your profile", modifier = Modifier.padding(bottom = 16.dp))
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = weeks,
            onValueChange = { weeks = it },
            label = { Text("Pregnancy Weeks") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        Button(onClick = { if (age.isNotEmpty() && weeks.isNotEmpty()) onProfileSubmitted(age, weeks) }) {
            Text("Complete Profile")
        }
    }
}
