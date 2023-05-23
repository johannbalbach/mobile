package com.example.mobile
import RPN

fun Concatenation(s1: String, s2: String): String {
    val open_bracket = '('
    val close_bracket = ')'
    val sb = StringBuilder()
    sb.append(open_bracket).append(s1).append(s2).append(close_bracket)
    return sb.toString()
}

//fun MathExpression(string: String) {
//    var expression = RPN(string)
//    expression.Math()
//}
//
//fun Build(){
//    var i = 0
//    while (i < AllBlocks.size){
//        var CurrentBlock = AllBlocks.get(i);
//        if (CurrentBlock.blockType == "variable"){
//            val expression = Concatenation()
//            MathExpression(expression)
//        }
//        else if (CurrentBlock.blockType == "output"){
//
//        }
//        else if (CurrentBlock.blockType == "for"){
//
//        }
//    }
//}
