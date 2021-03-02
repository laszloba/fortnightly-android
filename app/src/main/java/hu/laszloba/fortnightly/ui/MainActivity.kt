package hu.laszloba.fortnightly.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.laszloba.fortnightly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
