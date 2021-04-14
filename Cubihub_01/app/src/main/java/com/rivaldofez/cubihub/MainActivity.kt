package com.rivaldofez.cubihub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.fl_fragment, UsersFragment())
        fragment.commit()

        bnav_main.setOnNavigationItemSelectedListener (onBottomNavListener)
    }

    private val onBottomNavListener = BottomNavigationView.OnNavigationItemSelectedListener { i ->
        var selectedFragment: Fragment = UsersFragment()

        when(i.itemId){
            R.id.item_users -> {
                selectedFragment = UsersFragment()
                bnav_main.getOrCreateBadge(R.id.item_users).apply {
                    isVisible = false
                    number = 0
                }
            }

            R.id.item_about -> {
                selectedFragment = AboutFragment()
            }
        }

        var fr = supportFragmentManager.beginTransaction()
        fr.replace(R.id.fl_fragment, selectedFragment)
        fr.commit()
        true
    }
}