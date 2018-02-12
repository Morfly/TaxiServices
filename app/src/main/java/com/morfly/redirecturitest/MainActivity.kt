package com.morfly.redirecturitest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.morfly.redirecturitest.controllers.UberController


class MainActivity : AppCompatActivity() {

    private lateinit var loginUberButton: Button
    private lateinit var taxiService: TaxiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taxiService = UberController(this@MainActivity)
        loginUberButton = findViewById<Button>(R.id.loginUberButton)
        loginUberButton.setOnClickListener { taxiService.authorize() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        taxiService.onActivityResult(requestCode, resultCode, data)
    }
}
