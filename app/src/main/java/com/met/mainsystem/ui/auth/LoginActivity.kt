package com.met.mainsystem.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.met.mainsystem.R
import com.met.mainsystem.databinding.ActivityLoginBinding
import com.met.mainsystem.ui.common.applyEdgeToEdge

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyEdgeToEdge(binding.root, binding.headerContent, lightStatusBar = false)

        binding.buttonLogin.setOnClickListener { submit() }
        binding.editPassword.setOnEditorActionListener { _, actionId, _ ->
            val done = actionId == EditorInfo.IME_ACTION_DONE
            if (done) submit()
            done
        }
        binding.buttonGoSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.buttonForgot.setOnClickListener { showMessage(R.string.msg_coming_soon) }
        binding.social.buttonGoogle.setOnClickListener { showMessage(R.string.msg_coming_soon) }
        binding.social.buttonMicrosoft.setOnClickListener { showMessage(R.string.msg_coming_soon) }
    }

    private fun submit() {
        val idOk = AuthValidation.requireNotEmpty(binding.inputIdOrEmail)
        val passwordOk = AuthValidation.requirePassword(binding.inputPassword)
        if (idOk && passwordOk) {
            // TODO: hook up authentication once the backend is ready.
            showMessage(R.string.msg_login_ok)
        }
    }

    private fun showMessage(@StringRes resId: Int) {
        Snackbar.make(binding.root, resId, Snackbar.LENGTH_SHORT).show()
    }
}
