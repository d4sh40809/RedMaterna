package com.Dasha.redmaterna

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("users")

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val uid = auth.currentUser?.uid ?: return
        database.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _userProfile.value = snapshot.getValue(UserProfile::class.java)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun saveUserProfile(profile: UserProfile, onComplete: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        database.child(uid).setValue(profile).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }
}
