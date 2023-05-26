package com.example.mobile

import java.util.Vector

class Value() {
    private var doubleValue = Double.MIN_VALUE
    private var intValue = Int.MIN_VALUE
    private var boolValue = false

    var array = Vector<Value>()
    var isChanged = false
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
        this.isChanged = true
        this.variableName = name
        this.type = 3
    }

    constructor(value: Vector<Value>, name: String = "") : this() {
        this.array = value
        this.variableName = name
        this.type = 4
    }

    fun getDouble(): Double {
        if (doubleValue != Double.MIN_VALUE)
            return doubleValue
        else if (intValue != Int.MIN_VALUE)
            return intValue.toDouble()
        else if (isChanged) {
            console.print("SOMEWHERE BOOL IS USED AS DOUBLE")
            return 0.0
        } else {
            console.print("NO CHANGES DOUBLE")
            return 0.0
        }
    }
    fun getInteger(): Int {
        if (intValue != Int.MIN_VALUE)
            return intValue
        else if (doubleValue != Double.MIN_VALUE)
            return doubleValue.toInt()
        else if (isChanged) {
            console.print("SOMEWHERE BOOL IS USED AS INTEGER")
            return 0
        } else {
            console.print("NO CHANGES")
            return 0
        }
    }
    fun getBool(): Boolean {
        if (isChanged)
            return boolValue
        else if (doubleValue != Double.MIN_VALUE) {
            console.print("SOMEWHERE DOUBLE IS USED AS BOOL")
            return false
        } else if (intValue != Int.MIN_VALUE) {
            console.print("SOMEWHERE INTEGER IS USED AS DOUBLE")
            return false
        }
        else{
            console.print("NO CHANGES")
            return false
        }
    }
    fun getOutput(): String{
        when(type){
            1 -> return getDouble().toString()
            2 -> return getInteger().toString()
            3 -> return getBool().toString()
            4 -> {
                var i = 0
                var sb = StringBuilder()
                while (i < array.size){
                    if (i == array.size-1)
                        sb.append(array[i].getOutput())
                    else
                        sb.append(array[i].getOutput()).append(", ")
                    i++
                }
                return sb.toString()
            }
            else -> return "SOME ERRORS IN THE RESULTS OUTPUT"
        }
    }
    fun isThereVariable(): Boolean {
        if (variableName != "")
            return true
        else
            return false
    }
    fun setValue(equal: Value){
        when(equal.type){
            1 -> {
                this.doubleValue = equal.getDouble()
                this.intValue = Int.MIN_VALUE
                this.boolValue = false
            }
            2 ->{
                this.doubleValue = Double.MIN_VALUE
                this.intValue = equal.getInteger()
                this.boolValue = false
            }
            3 ->{
                this.doubleValue = Double.MIN_VALUE
                this.intValue = Int.MIN_VALUE
                this.boolValue = equal.getBool()
            }
            4 -> this.array = equal.array
        }
    }
}
