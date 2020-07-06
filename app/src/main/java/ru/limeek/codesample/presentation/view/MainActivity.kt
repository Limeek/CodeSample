package ru.limeek.codesample.presentation.view

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ru.limeek.codesample.R

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}