package com.duoc.diegomorales.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.diegomorales.data.Manager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    private val _registerSuccess = MutableStateFlow<Boolean?>(null)
    val registerSuccess: StateFlow<Boolean?> = _registerSuccess

    private val _resetSuccess = MutableStateFlow<Boolean?>(null)
    val resetSuccess: StateFlow<Boolean?> = _resetSuccess

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _allUsers = MutableStateFlow<List<String>>(emptyList())
    val allUsers: StateFlow<List<String>> = _allUsers

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = Manager.loginUser(email, password)
            _loginSuccess.value = result
            _isLoading.value = false
        }
    }

    fun resetLoginState() {
        _loginSuccess.value = null
    }

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = Manager.registerUser(email, password, name)
            _registerSuccess.value = result
            _isLoading.value = false
        }
    }

    fun resetRegisterState() {
        _registerSuccess.value = null
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = Manager.resetPassword(email)
            _resetSuccess.value = result
            _isLoading.value = false
        }
    }

    fun loadAllUsers() {
        viewModelScope.launch {
            val users = Manager.getAllUsersEmails()
            _allUsers.value = users
        }
    }

    fun deleteUser(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            Manager.deleteUser(email)
            loadAllUsers()
            _isLoading.value = false
        }
    }

    fun logout() {
        Manager.logout()
    }
}
