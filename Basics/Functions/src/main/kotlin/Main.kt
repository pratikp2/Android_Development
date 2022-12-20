fun main() {
    sampleFunction("Patrik")
    println(getMax(10, 20))
    println(getMin(10, 20))
}

// Simple Function
fun sampleFunction(name:String) {
    println(name)
}

// Prototype for Function Return
fun getMax(num1: Int, num2: Int): Int {
    return if (num1 < num2) num2 else num1
}

// Prototype for Single Expression Function
fun getMin (num1:Int, num2:Int) =  if (num1 > num2) num2 else num1

// Function Overloading in KotLin

