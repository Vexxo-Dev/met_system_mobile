package com.met.mainsystem.ui.auth

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.met.mainsystem.R
import com.met.mainsystem.databinding.ActivitySignUpBinding
import com.met.mainsystem.ui.common.applyEdgeToEdge

class SignUpActivity : AppCompatActivity() {

    /** Account types offered on the sign-up screen. */
    enum class Role(@StringRes val idHint: Int) {
        STUDENT(R.string.hint_student_id),
        DOCTOR(R.string.hint_staff_id),
        ADMIN(R.string.hint_admin_id),
    }

    private lateinit var binding: ActivitySignUpBinding
    private var role = Role.STUDENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyEdgeToEdge(binding.root, binding.headerContent, lightStatusBar = true)

        savedInstanceState?.getString(KEY_ROLE)?.let { role = Role.valueOf(it) }
        roleViews().forEach { (r, view) -> view.setOnClickListener { selectRole(r) } }
        selectRole(role)

        binding.social.textSocialDivider.setText(R.string.or_sign_up_with)
        binding.buttonBack.setOnClickListener { finish() }
        binding.buttonGoLogin.setOnClickListener { finish() }
        binding.buttonSignUp.setOnClickListener { submit() }
        binding.social.buttonGoogle.setOnClickListener { showMessage(R.string.msg_coming_soon) }
        binding.social.buttonMicrosoft.setOnClickListener { showMessage(R.string.msg_coming_soon) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_ROLE, role.name)
    }

    private fun roleViews(): Map<Role, View> = mapOf(
        Role.STUDENT to binding.roleStudent,
        Role.DOCTOR to binding.roleDoctor,
        Role.ADMIN to binding.roleAdmin,
    )

    private fun selectRole(selected: Role) {
        role = selected
        roleViews().forEach { (r, view) -> view.isSelected = r == selected }
        binding.inputUniversityId.hint = getString(selected.idHint)
        binding.inputUniversityId.error = null
    }

    private fun submit() {
        val results = listOf(
            AuthValidation.requireNotEmpty(binding.inputFullName),
            AuthValidation.requireNotEmpty(binding.inputUniversityId),
            AuthValidation.requireEmail(binding.inputEmail),
            AuthValidation.requirePassword(binding.inputPassword),
        )
        if (results.all { it }) {
            // TODO: create the account for the selected role once the backend is ready.
            showMessage(R.string.msg_signup_ok)
        }
    }

    private fun showMessage(@StringRes resId: Int) {
        Snackbar.make(binding.root, resId, Snackbar.LENGTH_SHORT).show()
    }

    private companion object {
        const val KEY_ROLE = "role"
    }
}
