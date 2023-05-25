package com.example.mobile

import java.util.Vector

class Value() {
    var doubleValue = Double.MIN_VALUE
    var intValue = Int.MIN_VALUE
    var boolValue = false
    var WasThereChanges = false
    var array = Vector<Value>()
    var variableName = ""
    var type = 0; // 1 - Double, 2 - Int, 3 - Boolean, 4 - array
    var fatherName = ""
    var memberIndex = -1

    constructor(value: Double, name: String = "") : this() {
        this.doubleValue = value
        this.variableName = name
        this.type = 1
    }

    constructor(value: Int, name: String = "") : this() {
        this.intValue = value
        this.variableName = name
        this.type = 2
    }

    constructor(value: Boolean, name: String = "") : this() {
        this.boolValue = value
        this.WasThereChanges = true
        this.variableName = name
        this.type = 3
    }

    constructor(value: Vector<Value>, name: String = "") : this() {
        this.array = value
        this.variableName = name
        this.type = 4
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
    fun GetOutput(): String{
        when(type){
            1 -> return GetDouble().toString()
            2 -> return GetInteger().toString()
            3 -> return GetBool().toString()
            4 -> {
                var i = 0
                var sb = StringBuilder()
                while (i < array.size){
                    sb.append(array[i].GetOutput()).append(", ")
                    i++
                }
                return sb.toString()
            }
            else -> return "SOME MISTAKES"
        }
    }
    fun IsThereVariable(): Boolean {
        if (variableName != "")
            return true
        else
            return false
    }
}