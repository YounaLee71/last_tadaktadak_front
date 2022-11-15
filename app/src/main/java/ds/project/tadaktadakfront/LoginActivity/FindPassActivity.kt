package ds.project.tadaktadakfront.LoginActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ds.project.tadaktadakfront.R


class FindPassActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var editTextEmail: EditText
    lateinit var buttonfind: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pass)

        firebaseAuth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.editUserEmail)
        buttonfind = findViewById(R.id.buttonFind)

        buttonfind.setOnClickListener {

            var emailAddress = editTextEmail.text.toString()
            firebaseAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(this, "이메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this, "메일 보내기 실패!", Toast.LENGTH_LONG).show();
                    }
                }

        }
    }
}
