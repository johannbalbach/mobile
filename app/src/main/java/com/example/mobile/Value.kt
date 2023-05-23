package com.example.mobile

class Value {
    var doubleValue = Double.MIN_VALUE
    var intValue = Int.MIN_VALUE
    var boolValue = false
    var WasThereChanges = false
    var variableName = ""
    var type = 0; // 1 - Double, 2 - Int, 3 - Boolean

    constructor(){}

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
    fun IsThereVariable(): Boolean {
        if (variableName != "")
            return true
        else
            return false
    }
}