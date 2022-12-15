package com.ymaskin.firbaseauth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ymaskin.firbaseauth.model.User

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val usersReference = Firebase.database.reference.child("users")
    private lateinit var listener: ValueEventListener

    private val users = arrayListOf<User>()

    fun getCurrentUser() = auth.currentUser

    fun createUserWithEmailAndPassword(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun signOutUser() {
        auth.signOut()
    }

    fun saveUserData(user: User) {
        user.id?.let { usersReference.child(it).setValue(user) }
    }

    fun getUser(username: String): User? {
        return users.find { it.name == username }
    }

    fun registerUsersListener() {
        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                users.clear()
                val tempList = arrayListOf<User>()
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    user?.let { userToAdd -> tempList.add(userToAdd) }
                }
                users.addAll(tempList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DataFragment", "Something went wrong", error.toException())
            }
        }
        usersReference.addValueEventListener(listener)
    }

    fun unregisterListener() {
        if (::listener.isInitialized) {
            usersReference.addValueEventListener(listener)
        }
    }
}