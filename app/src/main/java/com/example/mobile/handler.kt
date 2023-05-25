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
        console.print(values.elementAt(j))
        j++
    }
    console.print("BUILDING")
    var i = 0
    while (i < AllBlocks.size){
        var CurrentBlock = AllBlocks.get(i)
        console.print(CurrentBlock.blockType)
        console.print(CurrentBlock.id.toString())
        if (CurrentBlock.blockType == "variable"){
            if(blocksData.containsKey(CurrentBlock.id)) {
                MathExpression(blocksData.getValue(CurrentBlock.id))
                console.print(blocksData.getValue(CurrentBlock.id))
                console.print("WORKING")
            }
        }
        else if (CurrentBlock.blockType == "output"){
            if(blocksData.containsKey(CurrentBlock.id)) {
                console.print("INFO IS" + blocksData.getValue(CurrentBlock.id))
                if (table.containsKey(blocksData.getValue(CurrentBlock.id))){
                    console.print("OUTPUT: "+blocksData.getValue(CurrentBlock.id) + "= " + table.getValue(blocksData.getValue(CurrentBlock.id)).GetDouble())
                }
                console.print("SMTH")
            }
        }
        else if (CurrentBlock.blockType == "for"){
        }
        i++
    }
}
