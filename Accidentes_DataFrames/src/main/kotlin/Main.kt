import controller.AccidentesController

fun main(args: Array<String>) {
    var inicio:Long =System.currentTimeMillis()


    val controller = AccidentesController()

    if (controller.isExist("CARABANCHEL")) {
        controller.cuestionesBarrio("CARABANCHEL")
    }else{
        println("El barrio ${args[0]} no existe")
    }

    var final:Long = System.currentTimeMillis()

    println("Ha tardado en obtener y procesar todo : ${(final-inicio)/1000} segundos")
}