fun main()
{
    // Array Size will be fixed at time of initialization
    val names : Array<String> = arrayOf("Employee 1","Employee 2","Employee 3")

    // For Loops

    // Simple for Loop
    for (name:String in names) println(name)

    // Access index in For loops
    for (index in names.indices) println(index)

    // Range based For Loop
    for(i in 1..10) println(i)              // Includes last Bound
    for(i in 1 until 10) println(i)         // Excludes Last Bound
    for(i in 10 downTo 1) println(i)        // Count Downwards in enumeration
    for(i in 1..10 step 2) println(i)       // Print number using step size
    for(i in 'a'..'z') println(i)           // Print Character in alphabetical order

    /*****************************************************************************************************/



}