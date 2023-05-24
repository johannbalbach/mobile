import com.example.mobile.Value
import com.example.mobile.table
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

    private val operationPriority = mapOf('@' to -3, '?' to -2, ':' to -2, '{' to -1, '}' to -1, '(' to 0, '=' to 1, "&&" to 2,
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
                } else if (operator == '?' || operator == ':' || operator == '}' || operator == '{' || operator == '@') {
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

    private fun setVariable(name: String, value: Value) {
        if (name != null) {
            var type = value.type
            var copy = Value();
            when(type){
                1 -> copy = Value(value.GetDouble(), name)
                2 -> copy = Value(value.GetInteger(), name)
                3 -> copy = Value(value.GetBool(), name)
            }
            if (table.contains(name)) {
                table.replace(name, copy)
            } else
                table.put(name, copy)
        }
    }

    fun Math() {
        val numberStack = Stack<Value>()
        val conditionStack = Stack<Boolean>()
        var operationsCounter = 0

        var i = 0
        var flag = -1
        while (i < postfixExpr.length) {
            var currentChar = postfixExpr[i]
            var twoChar = StringBuilder()
            if (i < postfixExpr.length - 1) {
                twoChar.append(currentChar).append(postfixExpr[i + 1])
            }
            if (currentChar.isDigit()) {
                val number = getFullString(postfixExpr, i, true).first
                i = getFullString(postfixExpr, i, true).second
                numberStack.push(Value(number.toInt()))
            }
            else if (currentChar.isLetter()) {
                val word = getFullString(postfixExpr, i, false).first;
                val index = getFullString(postfixExpr, i, false).second;
                i = index
                if (word == "true") {
                    numberStack.push(Value(true))
                } else if (word == "false") {
                    numberStack.push(Value(false))
                } else {
                    if (table.containsKey(word)){
                        numberStack.push(table.getValue(word))
                    }
                    else{
                        setVariable(word, Value(0.0))
                        numberStack.push(table.getValue(word))
                    }
                }
            }
            else if (operationPriority.containsKey(twoChar.toString())) {
                val secondOperand = if (numberStack.isNotEmpty()) numberStack.pop() else Value(0.0)
                val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else Value(0.0)

                if (operationPriority.get(twoChar.toString()) == 2) {
                    numberStack.push(Value(executeConditions(twoChar.toString(), firstOperand.GetBool(), secondOperand.GetBool())))
                } else
                    numberStack.push(Value(executeConditions(twoChar.toString(), firstOperand.GetDouble(), secondOperand.GetDouble())))
            }
            else if (operationPriority.containsKey(currentChar)) {
                operationsCounter += 1
                if (currentChar == '~') {
                    val lastStackElem = if (numberStack.isNotEmpty()) numberStack.pop() else Value(0.0)
                    numberStack.push(Value(executeOperation('-', 0.0, lastStackElem.GetDouble())))
                } else if (currentChar == '=') {
                    if (!(equalchecker.containsKey(postfixExpr[i - 1]))) {
                        val secondOperand = if (numberStack.isNotEmpty()) numberStack.pop() else Value(0.0)
                        val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else Value(0.0)

                        if (firstOperand.IsThereVariable()) {
                            setVariable(firstOperand.variableName, secondOperand)
                        }
                    }
                } else if (currentChar == '?') {
                    if (numberStack.pop().GetBool()) {
                        conditionStack.push(true)
                    } else {
                        conditionStack.push(false)
                        while (postfixExpr[i] != '}') {
                            i++
                        }
                        currentChar = postfixExpr[i]
                    }
                } else if (currentChar == ':') {
                    if (!conditionStack.pop()){
                    }
                    else{
                        while (postfixExpr[i] != '}') {
                            i++
                        }
                        currentChar = postfixExpr[i]
                    }
                }
                else if (currentChar == '{' || currentChar == '}'){
                    if (currentChar == '}' && flag != -1) {
                        i = flag
                    }
                }
                else if (currentChar == '@'){
                    flag = i;
                }
                else {
                    val secondOperand = if (numberStack.isNotEmpty()) numberStack.pop() else Value(0.0)
                    val firstOperand = if (numberStack.isNotEmpty()) numberStack.pop() else Value(0.0)

                    if (currentChar == '>' || currentChar == '<')
                        numberStack.push(Value(executeConditions(currentChar, firstOperand.GetDouble(), secondOperand.GetDouble())))
                    else
                        numberStack.push(Value(executeOperation(currentChar, firstOperand.GetDouble(), secondOperand.GetDouble())))
                }
            }
            i++
        }
    }
}