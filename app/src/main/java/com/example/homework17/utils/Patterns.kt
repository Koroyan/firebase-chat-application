package com.example.homework17.utils

import java.util.regex.Pattern

object Patterns {
    private val validEmailAddres =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    fun validMail(mail: String): Boolean = validEmailAddres.matcher(mail).find()
}