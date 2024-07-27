package com.example.statussaver

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.statussaver.Fragment.InstagramFragment
import com.example.statussaver.Fragment.StatusFragment
import com.example.statussaver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mBiding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.statusBarColor = Color.parseColor("#f09819")
        this.window.navigationBarColor = Color.parseColor("#f09819")
        mBiding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBiding.root)
        loadFragment(StatusFragment())
        mBiding.bottomnavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.whatsapp -> {
                    loadFragment(StatusFragment())
                    true
                }

                R.id.instagram -> {
                    loadFragment(InstagramFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }


    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager?.findFragmentById(R.id.container)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }
}