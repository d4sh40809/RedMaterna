package com.Dasha.redmaterna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.auth.auth



class ForumViewModel : ViewModel() {

    private val db = Firebase.database.reference

    // Fixed path instead of posts/{uid}
    private val postRef = db.child("forum_posts").child("test_post")

    private val _post = MutableLiveData<ForumPost?>(null)
    val post: LiveData<ForumPost?> = _post

    private val _isSaving = MutableLiveData(false)
    val isSaving: LiveData<Boolean> = _isSaving

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private var listener: ValueEventListener? = null

    fun startListening() {
        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _post.value = snapshot.getValue(ForumPost::class.java)
            }
            override fun onCancelled(error: DatabaseError) {
                _errorMessage.value = "Failed to load post: ${error.message}"
            }
        }
        postRef.addValueEventListener(listener!!)
    }

    fun savePost(name: String, content: String) {
        _isSaving.value = true

        postRef.setValue(ForumPost(name, content))
            .addOnSuccessListener {
                _isSaving.value = false
            }
            .addOnFailureListener { e ->
                _isSaving.value = false
                _errorMessage.value = "Failed to save: ${e.message}"
            }
    }

    override fun onCleared() {
        postRef.removeEventListener(listener!!)
        super.onCleared()
    }
}