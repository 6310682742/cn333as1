package com.example.numberguessinggame

import android.app.GameState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GameScreen()
                }
            }
        }
    }
}
@Composable
fun GameScreen() {
    var gameState by remember {
        mutableStateOf(0)
    }
    var myValue by remember {
        mutableStateOf(generateMyValue())
    }
    var counter by remember {
        mutableStateOf(0)
    }
    var guessValue by remember {
        mutableStateOf("")
    }
    var hint by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(5.dp)) {
            Text(text = "Number Guessing Game", fontSize = 24.sp,color = Color.White)
        }
        Row(Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Try to guess the number I'm thinking of from 1-1000!", fontSize = 20.sp, textAlign = TextAlign.Center, )
        }
        Row(Modifier.fillMaxWidth().height(200.dp).padding(5.dp),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            if(gameState == 1)
                TextField(
                    value = guessValue,
                    label = { Text(text = "Your Guess") },
                    onValueChange = {
                    newValue->guessValue=newValue
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
        }
        Row(Modifier.fillMaxWidth().height(150.dp),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(text = hint, color = Color.Gray)
        }
        Row(Modifier.fillMaxSize().padding(5.dp),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top) {
            Button(onClick = {
                if(gameState == 0 || gameState == 2) {
                    myValue = generateMyValue()
                    gameState = 1
                    counter=0
                    hint = ""
                }
                else {
                    val res = checkGuess(myValue, guessValue.toInt())
                    gameState = checkGameState(res)
                    hint = checkHint(res, counter, myValue)
                    counter++;
                    guessValue=""
                }
            }) {
                Text(text = buttonDisplay(gameState))
            }
        }

    }
}
private fun checkGameState(res:Int): Int {
    if(res == 0) {
        return 2
    }
    else {
        return 1
    }
}
private fun checkHint(res:Int, counter:Int, myValue: Int):String {
    if(res == 0) {
        return String.format("Yes, %d is correct! You have try %d times", myValue, counter)
    }
    else if(res < 0) {
        return "Hint: It's lower!"
    }
    else {
        return "Hint: It's higher"
    }
}
private fun checkGuess(myValue:Int, guessValue:Int): Int {

    return myValue - guessValue
}
private fun generateMyValue(): Int {
    return (1..1000).random()
}
private fun textToInt(text:String): Int {
    return text.toInt()
}
private fun buttonDisplay(gameState:Int): String {
    if(gameState == 0) {
        return "Start Game!"
    }
    else if(gameState == 1) {
        return "Guess!"
    }
    else {
        return "Play Again!"
    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NumberGuessingGameTheme {
        Greeting("Android")
    }
}