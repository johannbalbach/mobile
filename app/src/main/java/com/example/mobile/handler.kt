package com.example.mobile
import RPN



fun MathExpression(string: String) {
    var expression = RPN(string)
    expression.Math()
}

fun Build(){
    var i = 0
    while (i < AllBlocks.size){
        var CurrentBlock = AllBlocks.get(i);
        if (CurrentBlock.blockType == "variable"){
            MathExpression(CurrentBlock.Data)
        }
        else if (CurrentBlock.blockType == "output"){
            if (table.containsKey(CurrentBlock.Data)){
                println("OUTPUT: "+CurrentBlock.Data + "= " + table.getValue(CurrentBlock.Data).GetDouble())
            }
        }
        else if (CurrentBlock.blockType == "for"){
        }
    }
}
