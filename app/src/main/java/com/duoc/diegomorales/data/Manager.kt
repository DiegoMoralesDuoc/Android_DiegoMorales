package com.duoc.diegomorales.data

data class User(
    val email: String,
    val password: String
)
object Manager {
    private val users = mutableListOf<User>(
        User("diego@gmail.com","12345"),
        User("diegomorales@gmail.com","diegomorales"),
        User("diegomoralesalfaro@duocuc.cl","dma_password"),
        User("prueba@duocuc.cl","prueba"),
        User("profesor@duocuc.cl","profesor")
    )

    fun registerUser(email: String, password: String): Boolean {
        if (users.any { it.email == email}){
            return false
        }
        users.add(User(email, password ))
        return true
    }

    fun loginUser (email: String, password: String): Boolean {
        return users.any { it.email == email && it.password == password}
    }

    fun resetPassword(email: String, newPassword: String): Boolean{
        val user = users.find {it.email == email}
        return if (user != null) {
            users.remove(user)
            users.add(user.copy(password = newPassword))
            true
        } else {
            false
        }
    }

    fun getAllUsersEmails(): List<String> {
        return users.map { it.email }
    }
}