package com.pi.criptdex

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SingUpViewModel: ViewModel(){

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable : LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun onLoginChanged(userName: String, email: String, password: String) {
        _userName.value = userName
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidUserName(userName) && isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidUserName(userName: String): Boolean = userName.isNotEmpty()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

}