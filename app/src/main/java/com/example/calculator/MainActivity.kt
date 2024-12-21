package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    lateinit var inputTextView: TextView
    lateinit var resultTextView: TextView
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.textView1)
        resultTextView = findViewById(R.id.textView2)

        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val button00: Button = findViewById(R.id.button00)
        val buttonDot: Button = findViewById(R.id.buttondot)

        val buttonAdd: Button = findViewById(R.id.buttonadd)
        val buttonSubtract: Button = findViewById(R.id.buttonsubtract)
        val buttonMultiply: Button = findViewById(R.id.buttonx)
        val buttonDivide: Button = findViewById(R.id.divide)
        val buttonPercent: Button = findViewById(R.id.buttonpercent)
        val buttonEqual: Button = findViewById(R.id.buttonequal)
        val buttonAC: Button = findViewById(R.id.buttonAC)
        val buttonBack: ImageButton = findViewById(R.id.back)

        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, button00)
        for (button in numberButtons) {
            button.setOnClickListener { appendToExpression((button as Button).text.toString(), true) }
        }

        buttonDot.setOnClickListener { appendToExpression(".", true) }
        buttonAdd.setOnClickListener { appendToExpression("+", false) }
        buttonSubtract.setOnClickListener { appendToExpression("-", false) }
        buttonMultiply.setOnClickListener { appendToExpression("*", false) }
        buttonDivide.setOnClickListener { appendToExpression("/", false) }
        buttonPercent.setOnClickListener { appendToExpression("%", false) }

        buttonAC.setOnClickListener {
            expression = ""
            inputTextView.text = ""
            resultTextView.text = ""
        }
        buttonBack.setOnClickListener {
            if (expression.isNotEmpty()) {
                expression = expression.substring(0, expression.length - 1)
                inputTextView.text = expression
                try {
                    val result = calculateResult()
                    resultTextView.text = result
                } catch (e: Exception) {
                    resultTextView.text = "Error"
                }
            }
        }
        buttonEqual.setOnClickListener {
            try {
                val result = calculateResult()
                resultTextView.text = result
            } catch (e: Exception) {
                resultTextView.text = "Error"
            }
        }
    }
    private fun appendToExpression(string: String, canClear: Boolean) {
        if (canClear) {
            resultTextView.text = ""
            inputTextView.append(string)
            expression += string
        } else {
            if (expression.isNotEmpty() && isOperator(expression.last().toString())) {
                return
            } else {
                inputTextView.append(string)
                expression += string
            }
        }
        updateResult()
    }
    private fun isOperator(char: String): Boolean {
        return char == "+" || char == "-" || char == "*" || char == "/" || char == "%"
    }

    private fun updateResult() {
        try {
            val result = calculateResult()
            resultTextView.text = result
        } catch (_: Exception) {
        }
    }
    private fun calculateResult(): String {
        var exp = expression.replace("%", "/100")
        val expressionBuilder = ExpressionBuilder(exp).build()
        val result = expressionBuilder.evaluate()
        return result.toString()
    }
}