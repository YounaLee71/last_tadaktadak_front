package ds.project.tadaktadakfront

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val fl: FrameLayout by lazy {
        findViewById(R.id.fl_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bnv_main = findViewById<BottomNavigationView>(R.id.bnv_main)
        bnv_main.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.first -> {
                        bnv_main.itemIconTintList =
                            ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        bnv_main.itemTextColor =
                            ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        NaviHome()
                        // Respond to navigation item 1 click
                    }
                    R.id.second -> {
                        bnv_main.itemIconTintList =
                            ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        bnv_main.itemTextColor =
                            ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        NaviContractCollection()


                    }
                    R.id.third -> {
                        bnv_main.itemIconTintList =
                            ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        bnv_main.itemTextColor =
                            ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        NaviChatbot()
                        // Respond to navigation item 3 click
                    }
                    else -> {
                        bnv_main.itemIconTintList =
                            ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        bnv_main.itemTextColor =
                            ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        NaviSetting()
                    }
                } as Fragment
            )
            true
        }
        bnv_main.selectedItemId = R.id.first
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }


}