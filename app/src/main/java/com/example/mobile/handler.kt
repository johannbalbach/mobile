package com.example.mobile
import RPN



fun MathExpression(string: String) {
    var expression = RPN(string)
    expression.Math()
}

fun BuildProject(){
    println("BUILDING")
    var i = 0
    while (i < AllBlocks.size){
        var CurrentBlock = AllBlocks.get(i);
        println(CurrentBlock.blockType)
        println(CurrentBlock.id)
        if (CurrentBlock.blockType == "variable"){
            MathExpression(CurrentBlock.Data)
            println(CurrentBlock.Data)
            println("WORKING")
        }
        else if (CurrentBlock.blockType == "output"){
            println("INFO IS" + CurrentBlock.Data)
            if (table.containsKey(CurrentBlock.Data)){
                println("OUTPUT: "+CurrentBlock.Data + "= " + table.getValue(CurrentBlock.Data).GetDouble())
            }
            println("SMTH")
        }
        else if (CurrentBlock.blockType == "for"){
        }
        i++
    }
}
