fun main ()
{
    // var vs val
    // Use var when underlying variable needs to be changed in further execution once initialization is done
    // Use val when underlying variable need to be constant or unchangeable

    var userName = "Pratik"
    val userAge  = 30
    println("My Name is $userName, and my age is $userAge, After two years I will be ${userAge + 2}")

    userName = "Patil"
    println("My Name is $userName, and my age is $userAge, After two years I will be ${userAge + 2}")

    // --------------------------------------------------------------------------------------------------

    // Number Data Types
    val sampleByte :Byte = 32               // Range from -128 to 127
    val sampleShort : Short  = 32           // Range from -32768 to 32767
    val sampleInt : Int = 32                // Range from -2147483648 to 2147483647
    val sampleLong : Long  = 32             // Range from -9223372036854775808L to 9223372036854775807L
    val sampleFloat : Float = 32.32F        // Range from 1.4E-45F to 3.4028235E38F
    val sampleDouble : Double = 32.0        // Range from 4.9E-324 to 1.7976931348623157E308

    // Alpha Numeric Data types
    val sampleString : String = "Hi"
    val sampleChar : Char = 'H'

    // Boolean Data type
    val sampleBool : Boolean = true

    println("Assigned sample byte is $sampleByte")
    println("Assigned sample Short is $sampleShort")
    println("Assigned sample Int is $sampleInt")
    println("Assigned sample Long is $sampleLong")
    println("Assigned sample Float is $sampleFloat")
    println("Assigned sample Double is $sampleDouble")
    println("Assigned sample String is $sampleString")
    println("Assigned sample char is $sampleChar")
    println("Assigned sample Boolean is $sampleBool")
}