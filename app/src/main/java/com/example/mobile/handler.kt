package com.example.mobile
import RPN
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.UUID


fun MathExpression(string: String) {
    var expression = RPN(string)
    expression.math()
}

fun Output(block: ComposeBlock){
    if(blocksData.containsKey(block.id)) {
        if (table.containsKey(blocksData.getValue(block.id))){
            console.print("OUTPUT: "+blocksData.getValue(block.id) + "= " + table.getValue(blocksData.getValue(block.id)).getOutput())
        }
    }
}
fun BlockConcatenation(Blocks: SnapshotStateList<ComposeBlock>): String {
    var i = 0;
    val sb = StringBuilder()
    while (i < Blocks.size){
        val CurrentBlock = Blocks.get(i);
        if (CurrentBlock.blockType == "output")
            Output(CurrentBlock)
        else{
            if(blocksData.containsKey(CurrentBlock.id)){
                sb.append(blocksData.getValue(CurrentBlock.id))
            }
        }
        i++
    }
    return sb.toString()
}
fun setVariable(id: UUID, value: String): Unit {
    if (blocksData.contains(id)) {
        blocksData.replace(id, value)
    } else
        blocksData.put(id, value)
}

fun BuildProject(){
    val SavedBlocks = AllBlocks
    console.clear()
    statusField.newStatus("BUILDING")
    var i = 0
    while (i < AllBlocks.size){
        var CurrentBlock = AllBlocks.get(i)
        CurrentBlock.onUpdate
//        console.print(i.toString())
//        console.print(CurrentBlock.blockType)
//        console.print(CurrentBlock.id.toString())
        if (CurrentBlock.blockType == "output"){
            Output(CurrentBlock)
        }
        else {
            if(blocksData.containsKey(CurrentBlock.id)) {
                console.print("SENTENCE IS:" + blocksData.getValue(CurrentBlock.id))
                MathExpression(blocksData.getValue(CurrentBlock.id))
            }
        }
        i++
    }
    AllBlocks = SavedBlocks
    table.clear()
}
