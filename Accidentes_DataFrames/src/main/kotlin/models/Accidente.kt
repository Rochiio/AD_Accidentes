package models

import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import java.time.LocalDate
import java.time.LocalTime

@DataSchema
data class Accidente(
    val numExpediente: String,
    val fecha: LocalDate,
    val hora : LocalTime,
    val calle: String,
    val numero: Int,
    val distrito: String,
    val tipoAccidente: tiposAccidente,
    val tipoVehiculo: tiposVehiculo,
    val tipoPersona: tiposPersona,
    val tramoEdad: String,
    val sexo: String,
    val lesividad: tiposLesividad,
    val positivoAlcohol: Boolean,
    val positivoDrogas: Boolean
    ) {

    override fun toString(): String {
        return "Accidente(numExpediente='$numExpediente', fecha=$fecha, hora=$hora, calle='$calle', numero=$numero, distrito='$distrito', tipoAccidente=$tipoAccidente, tipoVehiculo=$tipoVehiculo, tipoPersona=$tipoPersona, tramoEdad='$tramoEdad', sexo='$sexo', lesividad=$lesividad, positivoAlcohol=$positivoAlcohol, positivoDrogas=$positivoDrogas)"
    }
}