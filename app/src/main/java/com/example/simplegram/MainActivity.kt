package com.example.simplegram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.simplegram.fragments.ComposeFragment
import com.example.simplegram.fragments.HomeFragment
import com.example.simplegram.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {

        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        // get fragment manager
        val fragmentManager: FragmentManager = supportFragmentManager

        //
        findViewById<BottomNavigationView>( R.id.bottom_navigation ).setOnItemSelectedListener {
            item ->

            var currentFragment: Fragment? = null

            when ( item.itemId ) {
                R.id.action_home -> {
                    // home screen
                    currentFragment = HomeFragment()
                }

                R.id.action_compose -> {
                    // compose screen
                    currentFragment = ComposeFragment()
                }

                R.id.action_profile -> {
                    // profile screen
                    currentFragment = ProfileFragment()
                }
            }

            if ( currentFragment != null )
            {
                fragmentManager.beginTransaction().replace( R.id.flContainer, currentFragment ).commit()
            }

            // true = we handled this user interaction
            true
        }

        findViewById<BottomNavigationView>( R.id.bottom_navigation ).selectedItemId = R.id.action_home
    }

    override fun onCreateOptionsMenu( menu: Menu? ) : Boolean {
        menuInflater.inflate( R.menu.menu, menu )
        return true
    }

    override fun onOptionsItemSelected( item: MenuItem): Boolean {
        if ( item.itemId == R.id.logout ) {
            Toast.makeText( this, "Logging out", Toast.LENGTH_SHORT ).show()
            ParseUser.logOut()
            finish()
//            val intent = Intent( this, ComposeActivity::class.java )
//            startActivityForResult( intent, REQUEST_CODE )
        }
        return super.onOptionsItemSelected( item )
    }

    companion object {
        const val TAG = "MainActivity"
    }
}