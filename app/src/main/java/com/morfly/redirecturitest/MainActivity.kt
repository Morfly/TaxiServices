package com.morfly.redirecturitest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.morfly.redirecturitest.controllers.LyftController
import com.morfly.redirecturitest.controllers.UberController


class MainActivity : AppCompatActivity() {

    private lateinit var loginUberButton: Button
    private lateinit var loginLyftButton: Button
    private lateinit var taxiService: TaxiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginUberButton = findViewById<Button>(R.id.loginUberButton)
        loginLyftButton = findViewById<Button>(R.id.loginLyftButton)
        loginUberButton.setOnClickListener {
            taxiService = UberController(this@MainActivity)
            taxiService.authorize()
        }
        loginLyftButton.setOnClickListener {
            taxiService = LyftController(this@MainActivity)
            taxiService.authorize()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        taxiService.onActivityResult(requestCode, resultCode, data)
    }
}
