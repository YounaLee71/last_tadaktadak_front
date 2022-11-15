package ds.project.tadaktadakfront

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewContractActivity:AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contract)

        nameEditText = findViewById(R.id.add_edittext_name)
        numberEditText = findViewById(R.id.add_edittext_number)
        addressEditText = findViewById(R.id.add_edittext_address)

        saveButton = findViewById(R.id.btn_save)

        saveButton.setOnClickListener {
            if (!TextUtils.isEmpty(numberEditText.text)) {
                val name = nameEditText.text.toString()
                val number = numberEditText.text.toString()
                val address = addressEditText.text.toString()

                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("number", number)
                    putExtra("address", address)
                })
            }
        }
    }
}
