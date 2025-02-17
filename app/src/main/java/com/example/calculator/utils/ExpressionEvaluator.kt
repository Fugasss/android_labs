package com.example.calculator.utils

import java.util.Stack

fun evaluateExpression(expression: String): Int {
    val tokens = expression.replace(" ", "").toCharArray()
    val values = Stack<Int>()
    val operators = Stack<Char>()
    val allowedOperators = listOf('+', '-', '*', '/')

    var i = 0
    while (i < tokens.size) {
        when {
            tokens[i] == '-' && (i == 0 || tokens[i - 1] == '(' || tokens[i - 1] in allowedOperators) -> {
                var num = 0
                i++
                while (i < tokens.size && tokens[i].isDigit()) {
                    num = num * 10 + (tokens[i] - '0')
                    i++
                }
                values.push(-num)
                i--
            }
            tokens[i].isDigit() -> {
                var num = 0
                while (i < tokens.size && tokens[i].isDigit()) {
                    num = num * 10 + (tokens[i] - '0')
                    i++
                }
                values.push(num)
                i--
            }
            tokens[i] == '(' -> operators.push(tokens[i])
            tokens[i] == ')' -> {
                while (operators.isNotEmpty() && operators.peek() != '(') {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()))
                }
                operators.pop()
            }
            tokens[i] in allowedOperators -> {
                while (operators.isNotEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()))
                }
                operators.push(tokens[i])
            }
        }
        i++
    }

    while (operators.isNotEmpty()) {
        values.push(applyOperation(operators.pop(), values.pop(), values.pop()))
    }

    return values.pop()
}

private fun hasPrecedence(op1: Char, op2: Char): Boolean {
    if (op2 == '(' || op2 == ')') return false
    if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false
    return true
}

private fun applyOperation(op: Char, b: Int, a: Int): Int {
    return when (op) {
        '+' -> a + b
        '-' -> a - b
        '*' -> a * b
        '/' -> if (b != 0) a / b else throw ArithmeticException("Division by zero")
        else -> 0
    }
}