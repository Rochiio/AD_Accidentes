@file:Suppress("UNCHECKED_CAST", "USELESS_CAST")
package models
import org.jetbrains.kotlinx.dataframe.annotations.*
import org.jetbrains.kotlinx.dataframe.ColumnsContainer
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.DataRow
import org.jetbrains.kotlinx.dataframe.columns.ColumnGroup

val ColumnsContainer<models.Accidente>.calle: DataColumn<String> @JvmName("Accidente_calle") get() = this["calle"] as DataColumn<String>
val DataRow<models.Accidente>.calle: String @JvmName("Accidente_calle") get() = this["calle"] as String
val ColumnsContainer<models.Accidente>.distrito: DataColumn<String> @JvmName("Accidente_distrito") get() = this["distrito"] as DataColumn<String>
val DataRow<models.Accidente>.distrito: String @JvmName("Accidente_distrito") get() = this["distrito"] as String
val ColumnsContainer<models.Accidente>.fecha: DataColumn<java.time.LocalDate> @JvmName("Accidente_fecha") get() = this["fecha"] as DataColumn<java.time.LocalDate>
val DataRow<models.Accidente>.fecha: java.time.LocalDate @JvmName("Accidente_fecha") get() = this["fecha"] as java.time.LocalDate
val ColumnsContainer<models.Accidente>.hora: DataColumn<java.time.LocalTime> @JvmName("Accidente_hora") get() = this["hora"] as DataColumn<java.time.LocalTime>
val DataRow<models.Accidente>.hora: java.time.LocalTime @JvmName("Accidente_hora") get() = this["hora"] as java.time.LocalTime
val ColumnsContainer<models.Accidente>.lesividad: DataColumn<models.tiposLesividad> @JvmName("Accidente_lesividad") get() = this["lesividad"] as DataColumn<models.tiposLesividad>
val DataRow<models.Accidente>.lesividad: models.tiposLesividad @JvmName("Accidente_lesividad") get() = this["lesividad"] as models.tiposLesividad
val ColumnsContainer<models.Accidente>.numExpediente: DataColumn<String> @JvmName("Accidente_numExpediente") get() = this["numExpediente"] as DataColumn<String>
val DataRow<models.Accidente>.numExpediente: String @JvmName("Accidente_numExpediente") get() = this["numExpediente"] as String
val ColumnsContainer<models.Accidente>.numero: DataColumn<Int> @JvmName("Accidente_numero") get() = this["numero"] as DataColumn<Int>
val DataRow<models.Accidente>.numero: Int @JvmName("Accidente_numero") get() = this["numero"] as Int
val ColumnsContainer<models.Accidente>.positivoAlcohol: DataColumn<Boolean> @JvmName("Accidente_positivoAlcohol") get() = this["positivoAlcohol"] as DataColumn<Boolean>
val DataRow<models.Accidente>.positivoAlcohol: Boolean @JvmName("Accidente_positivoAlcohol") get() = this["positivoAlcohol"] as Boolean
val ColumnsContainer<models.Accidente>.positivoDrogas: DataColumn<Boolean> @JvmName("Accidente_positivoDrogas") get() = this["positivoDrogas"] as DataColumn<Boolean>
val DataRow<models.Accidente>.positivoDrogas: Boolean @JvmName("Accidente_positivoDrogas") get() = this["positivoDrogas"] as Boolean
val ColumnsContainer<models.Accidente>.sexo: DataColumn<String> @JvmName("Accidente_sexo") get() = this["sexo"] as DataColumn<String>
val DataRow<models.Accidente>.sexo: String @JvmName("Accidente_sexo") get() = this["sexo"] as String
val ColumnsContainer<models.Accidente>.tipoAccidente: DataColumn<models.tiposAccidente> @JvmName("Accidente_tipoAccidente") get() = this["tipoAccidente"] as DataColumn<models.tiposAccidente>
val DataRow<models.Accidente>.tipoAccidente: models.tiposAccidente @JvmName("Accidente_tipoAccidente") get() = this["tipoAccidente"] as models.tiposAccidente
val ColumnsContainer<models.Accidente>.tipoPersona: DataColumn<models.tiposPersona> @JvmName("Accidente_tipoPersona") get() = this["tipoPersona"] as DataColumn<models.tiposPersona>
val DataRow<models.Accidente>.tipoPersona: models.tiposPersona @JvmName("Accidente_tipoPersona") get() = this["tipoPersona"] as models.tiposPersona
val ColumnsContainer<models.Accidente>.tipoVehiculo: DataColumn<models.tiposVehiculo> @JvmName("Accidente_tipoVehiculo") get() = this["tipoVehiculo"] as DataColumn<models.tiposVehiculo>
val DataRow<models.Accidente>.tipoVehiculo: models.tiposVehiculo @JvmName("Accidente_tipoVehiculo") get() = this["tipoVehiculo"] as models.tiposVehiculo
val ColumnsContainer<models.Accidente>.tramoEdad: DataColumn<String> @JvmName("Accidente_tramoEdad") get() = this["tramoEdad"] as DataColumn<String>
val DataRow<models.Accidente>.tramoEdad: String @JvmName("Accidente_tramoEdad") get() = this["tramoEdad"] as String
