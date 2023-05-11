package RPN

import java.util.*
import kotlin.math.pow



class RPN() {
    private val expression = ""
    var infixExpr: String = expression
    var postfixExpr: String = ""
    var table = Hashtable<String, Double>()

    constructor(expression: String) : this() {
        this.infixExpr = expression
        this.postfixExpr = convertToPostfix(infixExpr + "\r")
    }

    private val operationPriority = mapOf('=' to 0, '(' to 1, '<' to 2, '>' to 2,
        ">=" to 2, "<=" to 2, "!=" to 2, "==" to 2, '!' to 3, "&&" to 4, "||" to 4,
        '+' to 5, '-' to 5, '*' to 6, '/' to 6, '^' to 7, '~' to 8, '.' to 9)


    private fun Boolean.toDouble() = if (this) 1.0 else 0.0
    private fun Double.toBoolean() = if (this == 1.0) true else false

    private fun setVariable(name: String, value: Double){
        if (name != null)
        {
            if (table.contains(name))
                table.replace(name, value)
            else
                table.put(name, value)
        }
    }
    private fun getFullString(inputExpr: String, inputPos: Int, isDigit: Boolean): Pair<String, Int> {
        var number = ""
        var currentPos = inputPos
        while (currentPos < inputExpr.length) {
            val currentChar = inputExpr[currentPos]
            if (isDigit)
            {
                if (currentChar.isDigit()) {
                    number += currentChar
                } else {
                    currentPos--
                    break
                }
            }
            else
            {
                if (currentChar.isLetter()) {
                    number += currentChar
                } else {
                    currentPos--
                    break
                }
            }
            currentPos++
        }
        return Pair(number, currentPos)
    }

    private fun convertToPostfix(infixExpr: String): String {
        var postfixExpr = ""
        val stack = Stack<Char>()
        var i = 0
        while (i < infixExpr.length) {
            val currentChar = infixExpr[i]
            var twoChar = StringBuilder()
            if (i < infixExpr.length - 1)
            {
                twoChar.append(currentChar).append(infixExpr[i+1])
            }
            if(currentChar.isDigit()) {
                postfixExpr += getFullString(infixExpr, i, true).first + " "
                i = getFullString(infixExpr, i, true).second
            }
            else if(currentChar == '(') {
                stack.push(currentChar)
            }
            else if(currentChar == ')') {
                while (stack.isNotEmpty() && stack.peek() != '(') {
                    postfixExpr += stack.pop()
                }
                stack.pop()
            }
            else if(operationPriority.containsKey(twoChar.toString()))
            {
                while ((stack.size > 0) && ((operationPriority[stack.peek()] ?: -1) >= (operationPriority[currentChar] ?: -1) )) {
                    postfixExpr += stack.pop()
                }
                stack.push(infixExpr[i+1])
                stack.push(currentChar)
            }
            else if(operationPriority.containsKey(currentChar)) {
                var operator = currentChar
                if(operator == '-' && (i == 0 || (i > 1 && operationPriority.containsKey( infixExpr[i-1] )))) {
                    operator = '~'
                }
                if (operator == '=') {
                    while ((stack.size > 0) && ((operationPriority[stack.peek()] ?: -1) >= (operationPriority[operator] ?: -1) )) {
                        postfixExpr += stack.pop()
                    }
                    stack.push(operator)
                }
                else {
                    while ((stack.size > 0) && ((operationPriority[stack.peek()] ?: -1) >= (operationPriority[operator] ?: -1) )) {
                        postfixExpr += stack.pop()
                    }
                    stack.push(operator)
                }
            }
            else if (currentChar.isLetter())
            {
                postfixExpr += getFullString(infixExpr, i,false).first + " "
                i = getFullString(infixExpr, i, false).second
            }
            i++
        }

        stack.reverse()
        stack.forEach { op ->
            postfixExpr += op
        }

        return postfixExpr
    }
    private fun concatenation(firstNumber: Double, secondNumber: Double): Double {
        val a = firstNumber.toInt().toString()
        val b = secondNumber.toInt().toString()
        val dot = '.'
        val sb = StringBuilder()
        sb.append(a).append(dot).append(b)
        return sb.toString().toDouble()
    }
    private fun executeOperation(operator: Char, firstNumber: Double, secondNumber: Double): Double = when (operator) {
        '+' -> firstNumber + secondNumber
        '-' -> firstNumber - secondNumber
        '*' -> firstNumber * secondNumber
        '/' -> firstNumber / secondNumber
        '^' -> firstNumber.pow(secondNumber)
        '.' -> concatenation(firstNumber, secondNumber)
        else -> 0.0
    }
    private fun executeConditions(operator: Char, firstNumber: Double, secondNumber: Double ): Boolean = when (operator) {
        '>' -> firstNumber > secondNumber
        '<'-> firstNumber < secondNumber
        else -> false
    }
    private fun executeConditions(operator: String, firstNumber: Double, secondNumber: Double ): Boolean = when (operator) {
        ">=" -> firstNumber >= secondNumber
        "<=" -> firstNumber <= secondNumber
        "!=" -> firstNumber != secondNumber
        "==" -> firstNumber == secondNumber
        "&&" -> firstNumber.toBoolean() && secondNumber.toBoolean()
        "||" -> firstNumber.toBoolean() || secondNumber.toBoolean()
        else -> false
    }

    fun getResult(): Double {
        val numberStack = Stack<Double>()
        var operationsCounter = 0
        var settingVariable = ""

        var i = 0
        while (i < postfixExpr.length) {
            val currentChar = postfixExpr[i]
            var twoChar = StringBuilder()
            if (i < postfixExpr.length - 1)
            {
                twoChar.append(currentChar).append(postfixExpr[i+1])
            }
            if(currentChar.isDigit()) {
                val number = getFullString(postfixExpr, i, true).first + " "
                i = getFullString(postfixExpr, i, true).second
                numberStack.push(number.toDouble())
            }
            else if(currentChar.isLetter()) {
                val variable = getFullString(postfixExpr, i, false).first
                i = getFullString(postfixExpr, i, false).second
                if (table.containsKey(variable))
                    numberStack.push(table.getValue(variable))
                else
                {
                    settingVariable = variable
                }
            }
            else if(operationPriority.containsKey(twoChar.toString()))
            {
                val secondOperand = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0
                val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0
                numberStack.push(executeConditions(twoChar.toString(), firstOperand, secondOperand).toDouble())
            }
            else if (operationPriority.containsKey(currentChar)) {
                operationsCounter += 1
                if (currentChar == '~') {
                    val lastStackElem = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0

                    numberStack.push(executeOperation('-', 0.0, lastStackElem))
                    continue
                }
                if (currentChar == '='){
                    val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0
                    setVariable(settingVariable, firstOperand)
                    numberStack.push(table.getValue(settingVariable))
                }
                else
                {
                    val secondOperand = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0
                    val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0

                    if (currentChar == '>' || currentChar == '<')
                        numberStack.push(executeConditions(currentChar, firstOperand, secondOperand).toDouble())
                    else
                        numberStack.push(executeOperation(currentChar, firstOperand, secondOperand))
                }
            }
            i++
        }


        return numberStack.pop()
    }
}

fun main(args: Array<String>) {
    val expression = "";
    val postfixExpr = RPN(expression);


    println("Ввод: " + postfixExpr.infixExpr)
    println("Постфиксная форма: " + postfixExpr.postfixExpr)
    println("Итого: " + postfixExpr.getResult())
}