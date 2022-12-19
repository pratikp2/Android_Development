fun main()
{
    // Array Size will be fixed at time of initialization
    val names : Array<String> = arrayOf("Employee 1","Employee 2","Employee 3")

    // Doing below step will cause exception to be thrown addition to initialized array is not allowed
    // names[3] = "Employee 4"
    names[2] = "Employee 3.14"

    println("First Element : ${names[0]}")
    println("Array Size : ${names.size}")
}