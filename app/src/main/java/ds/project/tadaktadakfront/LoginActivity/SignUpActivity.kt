package ds.project.tadaktadakfront.LoginActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ds.project.tadaktadakfront.R


class SignUpActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var buttonJoin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.editText_email)
        editTextPassword = findViewById(R.id.editText_passWord)
        buttonJoin = findViewById(R.id.btn_join)

        buttonJoin.setOnClickListener{
            var email = editTextEmail.text.toString()
            var password = editTextPassword.text.toString()
            firebaseAuth.createUserWithEmailAndPassword(email,password) // 회원 가입
                .addOnCompleteListener {
                        result ->
                    if(result.isSuccessful){
                        Toast.makeText(this,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                        if(firebaseAuth.currentUser!=null){
                            var intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else if(result.exception?.message.isNullOrEmpty()){
                        Toast.makeText(this,"오류가 발생했습니다.",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
