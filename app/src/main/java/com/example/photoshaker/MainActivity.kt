package com.example.photoshaker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.photoshaker.databinding.ActivityMainBinding
import com.example.photoshaker.ui.FragmentPhoto
import com.example.photoshaker.ui.FragmentSavedPhotos

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FragmentPhoto())
                .commit()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_photo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itm_show -> {
                openFragment()
               // Toast.makeText(this, " I am here! ", Toast.LENGTH_SHORT).show()
            }
            R.id.itm_close -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun openFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, FragmentSavedPhotos())
            .addToBackStack(TAG)
            .commit()
    }
}
const val TAG = "SAVED_FOTOS"