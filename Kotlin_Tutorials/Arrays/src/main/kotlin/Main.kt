fun main()
{
    // Array Size will be fixed at time of initialization
    val names : Array<String> = arrayOf("Employee 1","Employee 2","Employee 3")
    names[2] = "Employee 3.14"
    println("First Element : ${names[0]}")
    println("Array Size : ${names.size}")

    // String Entry itself can be treated as array as it is char array
    println("3rd Letter of string index 2 is ${names[2][2]}")

    // Doing below step will cause exception to be thrown addition to initialized array is not allowed
    // names[3] = "Employee 4"

    // Multidimensional Arrays
    val numbers : Array <Array <Int>> = arrayOf(
        arrayOf(1,2,3),
        arrayOf(4,5,6),
        arrayOf(7,8,9)
    )
    println(numbers[1][1])
    println(numbers[1].size)

}