package fiap.com.steam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class ForgotPasswordActivity : AppCompatActivity() {

    private val TAG = "ForgotPasswordActivity"

    //Elementos da Interface UI

    private var etEmail: EditText? = null
    private var btnSubmit: Button? = null

    //Referencia ao Firebase
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        inicialise()
    }

    private fun inicialise(){
        etEmail = findViewById(R.id.et_email) as EditText
        btnSubmit = findViewById(R.id.btn_submit) as Button

        mAuth = FirebaseAuth.getInstance()

        btnSubmit!!.setOnClickListener { sendPasswordEmail()}
    }

    private fun sendPasswordEmail(){
        val email = etEmail?.text.toString()

        if (!TextUtils.isEmpty(email)){
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val message = "Email Enviado."
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else{
                        Log.w(TAG, task.exception!!.message)
                        Toast.makeText(this, "Nenhum usuário encontrado, com esse e-mail", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Entre com um e-mail válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(){
        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
