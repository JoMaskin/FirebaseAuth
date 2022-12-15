package com.ymaskin.firbaseauth.util

import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return emailPattern.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    val minLength = 4
    return this.isNotBlank() && this.length >= minLength
}