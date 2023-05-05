package RPN

import java.util.*
import kotlin.math.pow

class RPN() {
    private val expression = ""
    var infixExpr: String = expression
    var postfixExpr: String = ""

    constructor(expression: String) : this() {
        this.infixExpr = expression
        this.postfixExpr = convertToPostfix(infixExpr + "\r")
    }

    private val operationPriority = mapOf('(' to 0, '+' to 1, '-' to 1,
        '*' to 2, '/' to 2, '^' to 3, '~' to 4)

    private fun getStringNumber(inputExpr: String, inputPos: Int): Pair<String, Int> {
        var number = ""
        var currentPos = inputPos
        while (currentPos < inputExpr.length) {
            val currentChar = inputExpr[currentPos]
            if (currentChar.isDigit()) {
                number += currentChar
            } else {
                currentPos--
                break
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
            if(currentChar.isDigit()) {
                postfixExpr += getStringNumber(infixExpr, i).first + " "
                i = getStringNumber(infixExpr, i).second
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
            else if(operationPriority.containsKey(currentChar)) {
                var operator = currentChar
                if(operator == '-' && (i == 0 || (i > 1 && operationPriority.containsKey( infixExpr[i-1] )))) {
                    operator = '~'
                }

                while ((stack.size > 0) && ((operationPriority[stack.peek()] ?: -1) >= (operationPriority[operator] ?: -1) )) {
                    postfixExpr += stack.pop()
                }
                stack.push(operator)
            }
            i++
        }

        stack.reverse()
        stack.forEach { op ->
            postfixExpr += op
        }

        return postfixExpr
    }

    private fun executeOperation(operator: Char, firstNumber: Double, secondNumber: Double): Double = when (operator) {
        '+' -> firstNumber + secondNumber
        '-' -> firstNumber - secondNumber
        '*' -> firstNumber * secondNumber
        '/' -> firstNumber / secondNumber
        '^' -> firstNumber.pow(secondNumber)
        else -> 0.0
    }

    fun getResult(): Double {
        val numberStack = Stack<Double>()
        var operationsCounter = 0

        var i = 0
        while (i < postfixExpr.length) {
            val currentChar = postfixExpr[i]

            if(currentChar.isDigit()) {
                val number = getStringNumber(postfixExpr, i).first + " "
                i = getStringNumber(postfixExpr, i).second
                numberStack.push(number.toDouble())
            }
            else if (operationPriority.containsKey(currentChar)) {
                operationsCounter += 1
                if (currentChar == '~') {
                    val lastStackElem = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0

                    numberStack.push(executeOperation('-', 0.0, lastStackElem))
                    println("${operationsCounter}) $currentChar$lastStackElem = ${numberStack.peek()}")
                    continue
                }

                val secondOperand = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0
                val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else 0.0

                numberStack.push(executeOperation(currentChar, firstOperand, secondOperand))
                println("${operationsCounter}) $firstOperand $currentChar $secondOperand = ${numberStack.peek()}")
            }
            i++
        }

        return numberStack.pop()
    }
}