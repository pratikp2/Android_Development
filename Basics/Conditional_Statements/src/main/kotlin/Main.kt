fun main()
{
    val flag : Boolean = true
    var statusCode : Int = 5

    // If statement
    if(flag) println("TRUE") else println("FALSE")                                // Basic Use
    if(statusCode in 4..7) println("IN 4 to 7") else {"Not in Range"}       //  Range base Use
    if(statusCode !in 4..7) println("Not in Range") else {"IN 4 to 7"}      // Range Base Use
    statusCode = if(flag) 9 else 10                                              // Use for Assignment

    /********************************************************************************************************/

    // When statement

    val time = 12
    val isSunday = false

    when(time)
    {
        1, 2, 3 -> println("Time For Nap")
        in 4..5 -> println("Why Up So early")
        in 6..11 -> println("Good Morning")
        12 -> println("Time for lunch")
        else -> println("Good Day ...!!")
    }

    var greeting : String = when(time)                                          // Use for Assignment
    {
        in 4..5 -> "Why Up So early"
        in 6..11 -> "Good Morning"
        12 -> "Time for lunch"
        else -> "Good Day ...!!"
    }
    println(greeting);

    greeting = when                                                             // Use Boolean Outcome
    {
        isSunday -> "Gonna stay in Bed All Day ...!!!!!!!!!"
        time == 1 || time == 2 || time == 3 -> "Time For Nap"
        time > 5 -> "Why Up So early"
        time in 6..11 -> "Good Morning"
        else -> "Good Day ...!!"
    }
    println(greeting);
}