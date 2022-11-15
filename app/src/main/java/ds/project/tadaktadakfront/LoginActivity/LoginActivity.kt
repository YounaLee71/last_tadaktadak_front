package ds.project.tadaktadakfront.LoginActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import ds.project.tadaktadakfront.MainActivity
import ds.project.tadaktadakfront.R


class LoginActivity : AppCompatActivity() {
    lateinit var emailEt: EditText
    lateinit var passwordEt: EditText
    lateinit var loginBtn: Button
    lateinit var signbtn: Button
    lateinit var googlebtn: Button
    lateinit var kakaobtn: Button
    lateinit var findpassbtn: Button
    private var auth: FirebaseAuth?=null
    private var googleSignInClient: GoogleSignInClient? = null
    private var GOOGLE_LOGIN_CODE = 9001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.email_et)
        passwordEt = findViewById(R.id.pwd_et)
        loginBtn = findViewById(R.id.btn_login)
        signbtn = findViewById(R.id.signBtn)
        googlebtn = findViewById(R.id.btn_google)
        kakaobtn = findViewById(R.id.btn_kakao)
        findpassbtn = findViewById(R.id.findpass)

        signbtn.setOnClickListener {//회원가입 버튼
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        findpassbtn.setOnClickListener {//비밀번호 재설정
            val intent = Intent(this, FindPassActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener { //로그인 버튼
            var email = emailEt.text.toString()
            var password = passwordEt.text.toString()
            signIn(email, password)
        }

        googlebtn.setOnClickListener { //구글 로그인 버튼
            googleLogin()
        }


        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    // 로그아웃하지 않을 시 자동 로그인 , 회원가입시 바로 로그인 됨
    public override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    // 로그인
    private fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(
                            baseContext, "로그인에 성공 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(auth?.currentUser)
                    } else {
                        Toast.makeText(
                            baseContext, "로그인에 실패 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }

    }

    // 구글 로그인 함수
    fun googleLogin() {
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 아이디, 비밀번호 맞을 때
                    moveMainPage(task.result?.user)
                } else {
                    // 틀렸을 때
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }


override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == GOOGLE_LOGIN_CODE) {
        var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)!!
        // 구글API가 넘겨주는 값 받아옴

        if (result.isSuccess) {
            var accout = result.signInAccount
            firebaseAuthWithGoogle(accout)
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
        }
    }

}




    // 로그인시 유저정보 넘겨주고 메인 액티비티 호출
    fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}


