package com.duoc.diegomorales.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

data class User(
    val email: String = "",
    val name: String = ""
)

object Manager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val currentUser get() = auth.currentUser

    suspend fun registerUser(email: String, password: String, name: String = "Anónimo"): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId: String = result.user?.uid ?: return false

            val user = User(email = email, name = name)
            database.getReference("users").child(userId).setValue(user).await()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun resetPassword(email: String): Boolean {
        return try {
            auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getAllUsersEmails(): List<String> {
        return try {
            val snapshot = database.getReference("users").get().await()
            snapshot.children.mapNotNull { it.child("email").getValue(String::class.java) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun deleteUser(email: String) {
        try {
            val snapshot = database.getReference("users").get().await()
            val userId = snapshot.children.firstOrNull { it.child("email").getValue(String::class.java) == email }?.key
            userId?.let {
                database.getReference("users").child(it).removeValue().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun logout() {
        auth.signOut()
    }
}
