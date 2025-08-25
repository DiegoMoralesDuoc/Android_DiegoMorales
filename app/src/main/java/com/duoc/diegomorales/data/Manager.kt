package com.duoc.diegomorales.data

data class User(
    val email: String,
    val password: String
)
object Manager {
    private val users = mutableListOf<User>(
        User("diego@duoc.cl","12345"),
        User("diegomorales@duoc.cl","diegomorales"),
        User("diegomoralesalfaro@duoc.cl","dma_password"),
        User("prueba@duoc.cl","prueba"),
        User("profesor@duoc.cl","profesor")
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
}