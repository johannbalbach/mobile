package com.example.mobile
import RPN



fun MathExpression(string: String) {
    var expression = RPN(string)
    expression.Math()
}

fun BuildProject(){
    var values = blocksData.values
    var j = 0
    while(j < values.size)
    {
        println(values.elementAt(j))
        j++
    }
    println("BUILDING")
    var i = 0
    while (i < AllBlocks.size){
        var CurrentBlock = AllBlocks.get(i)
        println(CurrentBlock.blockType)
        println(CurrentBlock.id)
        if (CurrentBlock.blockType == "variable"){
            if(blocksData.containsKey(CurrentBlock.id)) {
                MathExpression(blocksData.getValue(CurrentBlock.id))
                println(blocksData.getValue(CurrentBlock.id))
                println("WORKING")
            }
        }
        else if (CurrentBlock.blockType == "output"){
            if(blocksData.containsKey(CurrentBlock.id)) {
                println("INFO IS" + blocksData.getValue(CurrentBlock.id))
                if (table.containsKey(blocksData.getValue(CurrentBlock.id))){
                    println("OUTPUT: "+blocksData.getValue(CurrentBlock.id) + "= " + table.getValue(blocksData.getValue(CurrentBlock.id)).GetDouble())
                }
                println("SMTH")
            }
        }
        else if (CurrentBlock.blockType == "for"){
        }
        i++
    }
}
