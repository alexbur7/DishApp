package ru.alexbur.dishapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexbur.dishapp.R
import ru.alexbur.dishapp.databinding.ActivityMainBinding
import ru.alexbur.dishapp.presentation.dishes.DishesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState != null) return
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, DishesFragment.newInstance())
            .commit()
    }
}