package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.utils.evaluateExpression

enum class Operation{
    MUL,
    DIV,
    PLUS,
    MINUS,
    CLEAR,
    RESULT,
    BRACKET_LEFT,
    BRACKET_RIGHT,
    ;

    override fun toString(): String {
        return when(name){
            "MUL" -> "*"
            "DIV" -> "/"
            "PLUS" -> "+"
            "MINUS" -> "-"
            "RESULT" -> "="
            "BRACKET_LEFT" -> "("
            "BRACKET_RIGHT" -> ")"
            else -> ""
        }
    }
}

class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textView = findViewById(R.id.calculation_text)

        findViewById<Button>(R.id.button0).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button1).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button2).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button3).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button4).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button5).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button6).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button7).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button8).setOnClickListener{onNumberClick(it)}
        findViewById<Button>(R.id.button9).setOnClickListener{onNumberClick(it)}

        findViewById<Button>(R.id.button_plus).setOnClickListener{onOperationClick(it, Operation.PLUS)}
        findViewById<Button>(R.id.button_mul).setOnClickListener{onOperationClick(it, Operation.MUL)}
        findViewById<Button>(R.id.button_minus).setOnClickListener{onOperationClick(it,Operation.MINUS)}
        findViewById<Button>(R.id.button_div).setOnClickListener{onOperationClick(it,Operation.DIV)}
        findViewById<Button>(R.id.button_par_left).setOnClickListener{onOperationClick(it,Operation.BRACKET_LEFT)}
        findViewById<Button>(R.id.button_par_right).setOnClickListener{onOperationClick(it,Operation.BRACKET_RIGHT)}
        findViewById<Button>(R.id.button_clear).setOnClickListener{onOperationClick(it,Operation.CLEAR)}
        findViewById<Button>(R.id.button_result).setOnClickListener{onOperationClick(it, Operation.RESULT)}

        Log.d(TAG, "onCreate");
    }

    private lateinit var textView: TextView

    private var expression: String = ""

    private val ERROR: String = "ERROR"

    private fun onNumberClick(view: View) {
        if (view !is Button) return

        if(expression==ERROR)
            expression =""

        expression += view.text
        textView.text = expression
    }

    private fun onOperationClick(view: View, operation: Operation) {
        if (view !is Button) return

        if(expression==ERROR)
            expression =""

        when(operation){
            Operation.CLEAR -> {
                expression = ""
                textView.text = ""
            }
            Operation.RESULT -> {
                val result = calculateResult()
                textView.text = result
                expression = result
            }
            else -> {
                expression += operation.toString()
                textView.text = expression
            }
        }
    }

    private fun calculateResult(): String{
        try {
            val result = evaluateExpression(expression)
            return result.toString()
        }catch (e: Exception){
            Log.e("ERROR", "calculateResult: $e")
            return ERROR
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

}
