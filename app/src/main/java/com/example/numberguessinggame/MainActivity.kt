@file:Suppress("NAME_SHADOWING")

package com.example.numberguessinggame

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private var state: Boolean = false
    private var secretNumber: Int = -1
    private var round: Int = 0
    private lateinit var message : TextView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
        val guessNumber: EditText = findViewById(R.id.edtguessNumber)
        guessNumber.isClickable=false
        guessNumber.isEnabled=false
        guessNumber.isVisible = false
        message = findViewById(R.id.txtmessage)
        button.setOnClickListener {
            if(state) {
                if(guessNumber.text.isNotEmpty() && guessNumber.text.all { char -> char.isDigit() }) {
                    val res:Int = checker(Integer.parseInt(guessNumber.text.toString()))
                    if(!this@MainActivity.state) {
                        message.text = String.format("Your guess number is correct!. You have try it %d", this@MainActivity.round)
                        button.text = "Play Again!"
                    }
                    else if(res == -1) {
                        message.text = "Hint: It's lower!"
                    }
                    else {
                        message.text = "Hint: It's higher!"
                    }
                }
                else {
                    message.text = "Your input must be integer."

                }
            }
            else {
                newGame()
            }
            guessNumber.setText("")
        }
    }
    private fun checker(guessNumber: Int): Int {
        if(guessNumber == this@MainActivity.secretNumber) {
            state = false
            val guessNumber: EditText = findViewById(R.id.edtguessNumber)
            guessNumber.isClickable=false
            guessNumber.isEnabled=false
            guessNumber.isVisible = false

        }

        this@MainActivity.round++
        if(guessNumber > this@MainActivity.secretNumber) {
            return -1
        }
        return 1
    }
    @SuppressLint("SetTextI18n")
    private fun newGame() {
        this@MainActivity.state = true
        this@MainActivity.round = 0
        this@MainActivity.secretNumber = (Math.random() * 1000).toInt() + 1
        val button: Button = findViewById(R.id.button)
        button.text = "Guess Number"
        val guessNumber: EditText = findViewById(R.id.edtguessNumber)
        guessNumber.isClickable=true
        guessNumber.isEnabled=true
        guessNumber.isVisible = true
        message.text = ""

    }
}