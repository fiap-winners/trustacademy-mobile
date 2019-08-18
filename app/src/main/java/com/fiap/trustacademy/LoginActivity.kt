package com.fiap.trustacademy

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //elementos da interface UI
    private var mProgressBar: ProgressDialog? = null

    //referencias ao banco de dados
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColorTo(R.color.colorPrimary)
        }

        initialise()
    }

//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColorTo(color: Int) {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = ContextCompat.getColor(baseContext, color)
}

    private fun initialise(){
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            loginUser()
        }

        tv_forgot_password.setOnClickListener {
            val intentLogin = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intentLogin)
        }

        btn_register_account.setOnClickListener {
            val intentLogin = Intent(this, CreateAccountActivity::class.java)
            startActivity(intentLogin)
        }

    }

    private fun loginUser(){
        val email: String? = et_email.text.toString()
        val password: String? = et_password.text.toString()

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()){
            mProgressBar!!.setMessage("Verificando Usuario")
            mProgressBar!!.show()

            Log.d(TAG, "Login do usuario")

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->

                mProgressBar!!.hide()

                //Autenticando o usuario, atualizando Ui com as informações de login

                if (task.isSuccessful) {
                    Log.d(TAG, "Logado com sucesso")
                    updateUi()
                } else {
                    Log.e(TAG, "erro ao logar", task.exception)
                    Toast.makeText(this, "Autenticação falhou.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi(){
        val intent = Intent(this, DocumentsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
