package RPN

import java.util.*
import kotlin.math.pow

var table = Hashtable<String, Double>()

class RPN() {
    private class value() {
        var doubleValue = Double.MIN_VALUE
        var intValue = Int.MIN_VALUE
        var boolValue = false;
        var WasThereChanges = false;

        constructor(value: Double) : this() {
            this.doubleValue = value
        }

        constructor(value: Int) : this() {
            this.intValue = value
        }

        constructor(value: Boolean) : this() {
            this.boolValue = value
            this.WasThereChanges = true
        }

        fun GetDouble(): Double {
            if (doubleValue != Double.MIN_VALUE)
                return doubleValue
            else if (intValue != Int.MIN_VALUE)
                return intValue.toDouble()
            else if (WasThereChanges) {
               // println("THERE'RE SOME MISTAKES IN DOUBLE")
                return 0.0
            } else {
                //println("NO CHANGES")
                return 0.0
            }
        }

        fun GetInteger(): Int {
            if (intValue != Int.MIN_VALUE)
                return intValue
            else if (doubleValue != Double.MIN_VALUE)
                return doubleValue.toInt()
            else if (WasThereChanges) {
               // println("THERE'RE SOME MISTAKES IN INTEGER")
                return 0
            } else {
                //println("NO CHANGES")
                return 0
            }
        }

        fun GetBool(): Boolean {
            if (WasThereChanges)
                return boolValue
            else if (doubleValue != Double.MIN_VALUE) {
                //println("THERE'RE SOME MISTAKES IN BOOL WITH DOUBLE")
                return false
            } else if (intValue != Int.MIN_VALUE) {
                //println("THERE'RE SOME MISTAKES IN BOOL WITH INT")
                return false
            } else {
                //println("NO CHANGES")
                return false
            }
        }
    }

    private val expression = ""
    var infixExpr: String = expression
    var postfixExpr: String = ""

    constructor(expression: String) : this() {
        this.infixExpr = expression
        this.postfixExpr = convertToPostfix(infixExpr + "\r")
    }

    private val operationPriority = mapOf('?' to -2, ':' to -2, '{' to -1, '}' to -1, '(' to 0, '=' to 1, "&&" to 2,
        "||" to 2, '<' to 3, '>' to 3, ">=" to 3, "<=" to 3, "!=" to 3, "==" to 3, '!' to 4, '+' to 5, '-' to 5, '*' to 6,
        '/' to 6, '^' to 7, '~' to 8, '.' to 9
    )
    private val equalchecker = mapOf('<' to 0, '>' to 0, '!' to 0)

    private fun getFullString(inputExpr: String, inputPos: Int, isDigit: Boolean): Pair<String, Int> {
        var number = ""
        var currentPos = inputPos
        while (currentPos < inputExpr.length) {
            val currentChar = inputExpr[currentPos]
            if (isDigit) {
                if (currentChar.isDigit()) {
                    number += currentChar
                } else {
                    currentPos--
                    break
                }
            } else {
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
            if (i < infixExpr.length - 1) {
                twoChar.append(currentChar).append(infixExpr[i + 1])
            }
            if (currentChar.isDigit()) {
                postfixExpr += getFullString(infixExpr, i, true).first + " "
                i = getFullString(infixExpr, i, true).second
            } else if (currentChar == '(') {
                stack.push(currentChar)
            } else if (currentChar == ')') {
                while (stack.isNotEmpty() && stack.peek() != '(') {
                    postfixExpr += stack.pop()
                }
                if (stack.isNotEmpty())
                    stack.pop()
            } else if (operationPriority.containsKey(twoChar.toString())) {
                while ((stack.size > 0) && ((operationPriority[stack.peek()] ?: -1) >= (operationPriority[currentChar]
                        ?: -1)) && (stack.peek() != '(')
                ) {
                    postfixExpr += stack.pop()
                }
                stack.push(infixExpr[i + 1])
                stack.push(currentChar)
            } else if (operationPriority.containsKey(currentChar)) {
                var operator = currentChar
                if (operator == '-' && (i == 0 || (i > 1 && operationPriority.containsKey(infixExpr[i - 1])))) {
                    operator = '~'
                }
                if (operator == '=') {
                    if (!(equalchecker.containsKey(infixExpr[i - 1]))) {
                        while ((stack.size > 0) && ((operationPriority[stack.peek()]
                                ?: -1) >= (operationPriority[operator] ?: -1))
                        ) {
                            postfixExpr += stack.pop()
                        }
                        stack.push(operator)
                    }
                } else if (operator == '?' || operator == ':' || currentChar == '}' || currentChar == '{') {
                    postfixExpr += operator
                } else {
                    while ((stack.size > 0) && ((operationPriority[stack.peek()] ?: -1) >= (operationPriority[operator]
                            ?: -1))
                    ) {
                        postfixExpr += stack.pop()
                    }
                    stack.push(operator)
                }
            } else if (currentChar.isLetter()) {
                postfixExpr += getFullString(infixExpr, i, false).first + " "
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

    private fun executeConditions(operator: Char, firstNumber: Double, secondNumber: Double): Boolean =
        when (operator) {
            '>' -> firstNumber > secondNumber
            '<' -> firstNumber < secondNumber
            else -> false
        }

    private fun executeConditions(operator: String, firstNumber: Double, secondNumber: Double): Boolean =
        when (operator) {
            ">=" -> firstNumber >= secondNumber
            "<=" -> firstNumber <= secondNumber
            "!=" -> firstNumber != secondNumber
            "==" -> firstNumber == secondNumber
            else -> false
        }

    private fun executeConditions(operator: String, firstNumber: Boolean, secondNumber: Boolean): Boolean =
        when (operator) {
            "&&" -> firstNumber && secondNumber
            "||" -> firstNumber || secondNumber
            else -> false
        }

    private fun setVariable(name: String, value: Double) {
        if (name != null) {
            if (table.contains(name)) {
                table.replace(name, value)
            } else
                table.put(name, value)
        }
    }

    private fun printStack(stack: Stack<value>) {
        println("okee")
        var stackCopy = stack
        while (stackCopy.isNotEmpty())
            println(stackCopy.pop().doubleValue)
    }

    fun Math() {
        val numberStack = Stack<value>()
        var operationsCounter = 0
        var settingVariable = ""

        var i = 0
        while (i < postfixExpr.length) {
            var currentChar = postfixExpr[i]
            var twoChar = StringBuilder()
            if (i < postfixExpr.length - 1) {
                twoChar.append(currentChar).append(postfixExpr[i + 1])
            }
            if (currentChar.isDigit()) {
                val number = getFullString(postfixExpr, i, true).first
                i = getFullString(postfixExpr, i, true).second
                numberStack.push(value(number.toInt()))
            } else if (currentChar.isLetter()) {
                val word = getFullString(postfixExpr, i, false).first;
                val index = getFullString(postfixExpr, i, false).second;
                if (word == "true") {
                    numberStack.push(value(true))
                } else if (word == "false") {
                    numberStack.push(value(false))
                } else {
                    i = index
                    if (table.containsKey(word))
                        numberStack.push(value(table.getValue(word)))
                    else {
                        settingVariable = word
                    }
                }
            } else if (operationPriority.containsKey(twoChar.toString())) {
                val secondOperand = if (numberStack.isNotEmpty()) numberStack.pop() else value(0.0)
                val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else value(0.0)

                if (operationPriority.get(twoChar.toString()) == 2) {
                    numberStack.push(value(executeConditions(twoChar.toString(), firstOperand.GetBool(), secondOperand.GetBool())))
                } else
                    numberStack.push(value(executeConditions(twoChar.toString(), firstOperand.GetDouble(), secondOperand.GetDouble())))
            } else if (operationPriority.containsKey(currentChar)) {
                operationsCounter += 1
                if (currentChar == '~') {
                    val lastStackElem = if (numberStack.isNotEmpty()) numberStack.pop() else value(0.0)
                    numberStack.push(value(executeOperation('-', 0.0, lastStackElem.GetDouble())))
                } else if (currentChar == '=') {
                    if (!(equalchecker.containsKey(postfixExpr[i - 1]))) {
                        val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else value(0.0)
                        setVariable(settingVariable, firstOperand.GetDouble())
                        numberStack.push(value(table.getValue(settingVariable)))
                    }
                } else if (currentChar == '?' || currentChar == ':') {
                    if (numberStack.pop().GetBool()) {
                    } else {
                        while (currentChar != '}') {
                            currentChar = postfixExpr[i]
                            i++
                        }
                    }
                } else {
                    val secondOperand = if (numberStack.isNotEmpty()) numberStack.pop() else value(0.0)
                    val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else value(0.0)

                    if (currentChar == '>' || currentChar == '<')
                        numberStack.push(value(executeConditions(currentChar, firstOperand.GetDouble(), secondOperand.GetDouble())))
                    else
                        numberStack.push(value(executeOperation(currentChar, firstOperand.GetDouble(), secondOperand.GetDouble())))
                }
            }
            i++
        }
    }
}