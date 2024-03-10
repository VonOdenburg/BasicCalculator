package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    val displayEditText : EditText by lazy { findViewById(R.id.displayEditText) }

    val clearButton : Button by lazy { findViewById(R.id.clearButton) }
    val dotButton : Button by lazy { findViewById(R.id.dotButton) }
    val addButton : Button by lazy { findViewById(R.id.addButton) }
    val subtractButton : Button by lazy { findViewById(R.id.subtractButton) }
    val multiplyButton : Button by lazy { findViewById(R.id.multiplyButton) }
    val divideButton : Button by lazy { findViewById(R.id.divideButton) }
    val equalButton : Button by lazy { findViewById(R.id.equalButton) }

    val button0 : Button by lazy { findViewById(R.id.button0) }
    val button1 : Button by lazy { findViewById(R.id.button1) }
    val button2 : Button by lazy { findViewById(R.id.button2) }
    val button3 : Button by lazy { findViewById(R.id.button3) }
    val button4 : Button by lazy { findViewById(R.id.button4) }
    val button5 : Button by lazy { findViewById(R.id.button5) }
    val button6 : Button by lazy { findViewById(R.id.button6) }
    val button7 : Button by lazy { findViewById(R.id.button7) }
    val button8 : Button by lazy { findViewById(R.id.button8) }
    val button9 : Button by lazy { findViewById(R.id.button9) }

    var text = ""

    lateinit var first : BigDecimal
    lateinit var second : BigDecimal
    lateinit var operator : String
    var afterEqual = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayEditText.inputType = InputType.TYPE_NULL

        clearButton.setOnClickListener {
            text = ""
            displayEditText.hint = "0"
            afterEqual = false
            showText()
        }

        dotButton.setOnClickListener {
            addToText(".")
            showText()
        }

        initNumButtons()

        addButton.setOnClickListener {
            changeFirst()
            operator = "+"
            afterEqual = false
            showText()
        }

        multiplyButton.setOnClickListener {
            changeFirst()
            operator = "*"
            afterEqual = false
            showText()
        }

        divideButton.setOnClickListener {
            changeFirst()
            operator = "/"
            afterEqual = false
            showText()
        }

        subtractButton.setOnClickListener {
            if (afterEqual) {
                changeFirst()
                afterEqual = false
                operator = "-"
            } else if(text.isEmpty()) {
                addToText("-")
            } else if (text != "-") {
                changeFirst()
                afterEqual = false
                operator = "-"
            }
            showText()
        }

        equalButton.setOnClickListener {
            if (afterEqual) {
                changeFirst()
            } else if (text.isEmpty()) {
                second = first
            } else {
                changeSecond()
            }
            lateinit var result : BigDecimal
            try {
                 result = when (operator) {
                    "+" -> first.add(second)
                    "-" -> first.subtract(second)
                    "*" -> first.multiply(second).setScale(14, RoundingMode.HALF_UP)
                    "/" -> first.divide(second, 14, RoundingMode.HALF_UP)
                    else -> BigDecimal.ZERO
                }
            } catch (e: Exception) {
                e.message?.let { it1 -> Log.e("TEST", it1) }
                result = BigDecimal.ZERO
            }
            displayEditText.hint = result.stripTrailingZeros().toString()
            afterEqual = true
            showText()
        }

        /*
            Tests for android can not guarantee the correctness of solutions that make use of
            mutation on "static" variables to keep state. You should avoid using those.
            Consider "static" as being anything on kotlin that is transpiled to java
            into a static variable. That includes global variables and variables inside
            singletons declared with keyword object, including companion object.
            This limitation is related to the use of JUnit on tests. JUnit re-instantiate all
            instance variable for each test method, but it does not re-instantiate static variables.
            The use of static variable to hold state can lead to state from one test to spill over
            to another test and cause unexpected results.
            Using mutation on static variables to keep state
            is considered a bad practice anyway and no measure
            attempting to give support to that pattern will be made.
        */
    }

    private fun changeFirst() {
        first = if(text.isEmpty()) displayEditText.hint.toString().toBigDecimal() else text.toBigDecimal()
        displayEditText.hint = first.stripTrailingZeros().toString()
        text = ""
    }

    private fun changeSecond() {
        second = if(text.isEmpty()) displayEditText.hint.toString().toBigDecimal() else text.toBigDecimal()
        text = ""
    }

    fun addToText(s: String) {
        when {
            s == "0" && text == "0" -> {
                return
            }
            s != "0" && s != "." && text == "0" -> {
                text = ""
            }
            s != "0" && s != "." && text == "-0" -> {
                text = "-"
            }
            s == "." && text == "-" -> {
                text = "-0"
            }
            s == "." && text.contains('.') -> {
                return
            }
            s == "." && text.isEmpty() -> {
                text = "0"
            }
        }
        text += s
    }

    fun showText() {
        displayEditText.setText(text)
    }

    private fun initNumButtons() {
        button0.setOnClickListener {
            addToText("0")
            showText()
        }

        button1.setOnClickListener {
            addToText("1")
            showText()
        }

        button2.setOnClickListener {
            addToText("2")
            showText()
        }

        button3.setOnClickListener {
            addToText("3")
            showText()
        }

        button4.setOnClickListener {
            addToText("4")
            showText()
        }

        button5.setOnClickListener {
            addToText("5")
            showText()
        }

        button6.setOnClickListener {
            addToText("6")
            showText()
        }

        button7.setOnClickListener {
            addToText("7")
            showText()
        }

        button8.setOnClickListener {
            addToText("8")
            showText()
        }

        button9.setOnClickListener {
            addToText("9")
            showText()
        }
    }
}