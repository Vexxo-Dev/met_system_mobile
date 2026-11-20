package com.met.mainsystem.ui.auth

import android.util.Patterns
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import com.met.mainsystem.R

/** Small helpers for validating auth forms and showing errors on [TextInputLayout]s. */
internal object AuthValidation {

    private const val MIN_PASSWORD_LENGTH = 6

    private fun TextInputLayout.value(): String = editText?.text?.toString()?.trim().orEmpty()

    fun requireNotEmpty(input: TextInputLayout): Boolean =
        check(input, input.value().isNotEmpty(), R.string.error_required)

    fun requireEmail(input: TextInputLayout): Boolean {
        val value = input.value()
        if (!check(input, value.isNotEmpty(), R.string.error_required)) return false
        return check(input, Patterns.EMAIL_ADDRESS.matcher(value).matches(), R.string.error_email)
    }

    fun requirePassword(input: TextInputLayout): Boolean {
        val value = input.editText?.text?.toString().orEmpty()
        if (!check(input, value.isNotEmpty(), R.string.error_required)) return false
        return check(input, value.length >= MIN_PASSWORD_LENGTH, R.string.error_password_short)
    }

    private fun check(input: TextInputLayout, valid: Boolean, @StringRes errorRes: Int): Boolean {
        input.error = if (valid) null else input.context.getString(errorRes)
        return valid
    }
}
