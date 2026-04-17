package com.Dasha.redmaterna

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForumViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().getReference("posts")

    private val _posts = MutableStateFlow<List<ForumPost>>(emptyList())
    val posts: StateFlow<List<ForumPost>> = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postList = mutableListOf<ForumPost>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(ForumPost::class.java)
                    post?.let { postList.add(it) }
                }
                // Reverse to show newest posts first
                _posts.value = postList.reversed()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    fun addPost(name: String, title: String, content: String) {
        if (name.isBlank() || title.isBlank() || content.isBlank()) return

        val newPostRef = database.push()
        val post = ForumPost(name = name, title = title, content = content)
        newPostRef.setValue(post)
    }
}
