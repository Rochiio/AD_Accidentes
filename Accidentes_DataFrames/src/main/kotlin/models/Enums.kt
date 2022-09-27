package models

enum class tiposAccidente(value:String){
    COLISIONDOBLE("Colisión doble"),
    COLISIONMULTIPLE("Colisión múltiple"),
    ALCANCE("Alcance"),
    CHOQUECONTRAOBSTACULO("Choque contra obstáculo fijo"),
    ATROPELLOPERSONA("Atropello a persona"),
    VUELCO("Vuelco"),
    CAIDA("Caída"),
    OTRAS("Otras causas")
}

//TODO remirar documento de lesividades
enum class tiposLesividad(){
    LEVE,LEVEINGRESO,ASISTENCIA,GRAVE,FALLECIDO,DESCONOCIDO,SIN_ASISTENCIA
}

enum class tiposPersona(value: String){
    CONDUCTOR("Conductor"),
    PEATON("Peatón"),
    PASAJERO("Pasajero")
}

enum class tiposVehiculo(value: String){
    TURIMO("Turismo"),
    MOTO("Motocicleta hasta 125cc"),
    FURGO("Furgoneta"),
    MOTOMAYOR("Motocicleta > 125cc"),
    CAMION("Camión rígido"),
    TERRENO("Todo terreno"),
    BICIEPAC("Bicicleta EPAC (pedaleo asistido)"),
    VMU("VMU eléctrico"),
    OBRAS("Maquinaria de obras"),
    CICLOMOTOR("Ciclomotor"),
    AUTOBUSARTI("Autobús articulado"),
    VEHICULO("Vehículo articulado"),
    OTROS("Otros vehículos con motor"),
    CARAVANA("Autocaravana"),
    AUTOBUS("Autobús"),
    BICI("Bicicleta"),
    DESCONOCIDO("Desconocido")



}