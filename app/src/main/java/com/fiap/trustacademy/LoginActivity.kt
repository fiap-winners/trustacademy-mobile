package fiap.com.steam

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //variaveis globais

    private var email: String? = null
    private var password: String? = null

    //elementos da interface UI

    private var tvForgotPassword: TextView? = null
    private var etEmail: TextView? = null
    private var etPassword: TextView? = null
    private var btnLogin: TextView? = null
    private var btnCreateAccount: TextView? = null
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColorTo(color: Int) {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

    private fun initialise(){
        tvForgotPassword = findViewById(R.id.tv_forgot_password) as TextView
        etEmail = findViewById(R.id.et_email) as EditText
        etPassword = findViewById(R.id.et_password) as EditText
        btnLogin = findViewById(R.id.btn_login) as Button
        btnCreateAccount = findViewById(R.id.btn_register_account) as Button
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!
            .setOnClickListener{ startActivity(Intent(this@LoginActivity,ForgotPasswordActivity::class.java))}

        btnCreateAccount!!.setOnClickListener { startActivity(Intent(this@LoginActivity,CreateAccountActivity::class.java))}

        btnLogin!!.setOnClickListener { loginUser()}

    }

    private fun loginUser(){
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgressBar!!.setMessage("Verificando Usuario")
            mProgressBar!!.show()

            Log.d(TAG, "Login do usuario")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this){
                task ->

                mProgressBar!!.hide()

                //Autenticando o usuario, atualizando Ui com as informações de login

                if (task.isSuccessful) {
                    Log.d(TAG, "Logado com sucesso")
                    updateUi()
                } else {
                    Log.e(TAG, "erro ao logar", task.exception)
                    Toast.makeText(this@LoginActivity, "Autenticação falhou.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
