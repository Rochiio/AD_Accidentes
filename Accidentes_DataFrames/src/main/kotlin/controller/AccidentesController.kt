package controller

import jetbrains.datalore.base.values.Color
import models.*
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.export.ggsave
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.intern.Plot
import org.jetbrains.letsPlot.label.labs
import org.jetbrains.letsPlot.letsPlot
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AccidentesController {
    private var path: String = "${System.getProperty("user.dir")}${File.separator}data${File.separator}2022_Accidentalidad.csv"
    private lateinit var listaAccidentes: List<Accidente>
    private var dataFrame: DataFrame<Accidente>

    init {
        parseCsv()
        dataFrame = listaAccidentes.toDataFrame()
        dataFrame.cast<Accidente>()
        numAccidentesConIngreso()
        fallecidosPorBarrios()
        fallecidosPorSexo()

        mesMasIngresos()
        mesMasFallecidos()
        mediaIngresosPorMes()

        fallecidoAlcoholDrogasAmbos()
        barrioMasFallecidos()
        barrioMenosFallecidos()
        numIngresosTipoColision()
        numeroIngresosAtropello()
        numeroFallecidosAtropello()
        agruparAtropellosBarriosMayorMenor()
    }


    private fun agruparAtropellosBarriosMayorMenor() {
        println("Agrupación atropellos por barrio de mayor a menor")
        dataFrame.filter {  it.tipoAccidente==tiposAccidente.ATROPELLOPERSONA }.groupBy ("distrito").aggregate {
            count() into "total"
        }.sortByDesc("total").print()
    }

    private fun numeroFallecidosAtropello() {
        val num = dataFrame.filter { it.lesividad==tiposLesividad.FALLECIDO
                && it.tipoAccidente==tiposAccidente.ATROPELLOPERSONA }.count()
        println("Número de fallecidos por atropello: $num")
    }

    private fun numeroIngresosAtropello() {
        val num = dataFrame.filter { (it.lesividad==tiposLesividad.LEVEINGRESO || it.lesividad==tiposLesividad.GRAVE)
                && it.tipoAccidente==tiposAccidente.ATROPELLOPERSONA }.count()
        println("Número de ingresos por atropello: $num")
    }

    private fun numIngresosTipoColision() {
        val num = dataFrame.filter { (it.lesividad==tiposLesividad.LEVEINGRESO || it.lesividad==tiposLesividad.GRAVE)
                && (it.tipoAccidente==tiposAccidente.COLISIONMULTIPLE || it.tipoAccidente==tiposAccidente.COLISIONDOBLE)}.count()
        println("Número de ingresos de algún tipo de colisión: $num")
    }

    private fun barrioMenosFallecidos() {
        val barrio = dataFrame.filter { it.lesividad==tiposLesividad.FALLECIDO }.groupBy("distrito").aggregate {
            count() into "total"
        }.sortBy("total")

        println("Barrio con menos fallecidos ${barrio.first()}")
    }


    //TODO mirar por que de otra manera me sale que son VILLAVERDE -> 3
    private fun barrioMasFallecidos() {
       val barrio = dataFrame.filter { it.lesividad==tiposLesividad.FALLECIDO }.groupBy("distrito").aggregate {
            count() into "total"
        }.sortByDesc("total")

        println("Barrio con más fallecidos ${barrio.first()}")
    }

    private fun fallecidoAlcoholDrogasAmbos() {
        println("Fallecido que haya dado positivo en alcohol, drogas o ambos")

        dataFrame.filter { it.lesividad==tiposLesividad.FALLECIDO && (it.positivoAlcohol&&it.positivoDrogas)
                || (it.positivoAlcohol || it.positivoDrogas) }
            .groupBy("positivoAlcohol", "positivoDrogas").aggregate {
            count() into "total"
        }.print()
    }

    private fun mesMasIngresos() {
       var agrupadosMeses = dataFrame.convert { fecha }.with { it.month }
           .filter { it.lesividad==tiposLesividad.LEVE || it.lesividad==tiposLesividad.LEVEINGRESO }
           .groupBy("fecha").aggregate {
               count() into "total"
           }

        println("Mes con más ingresos: ${agrupadosMeses.max()["fecha"]}")
    }


    private fun mesMasFallecidos() {
        var agrupadosMeses = dataFrame.convert { fecha }.with { it.month }
            .filter { it.lesividad==tiposLesividad.FALLECIDO }
            .groupBy("fecha").aggregate {
                count() into "total"
            }
        println("Mes con más fallecidos: ${agrupadosMeses.max()["fecha"]}")
    }

    private fun mediaIngresosPorMes() {
        println("Media de ingresos por mes")
        dataFrame.convert { fecha }.with { it.month }
            .filter { it.lesividad==tiposLesividad.LEVEINGRESO|| it.lesividad==tiposLesividad.GRAVE }
            .groupBy("fecha").aggregate {
                mean() into "media"
            }.print()
    }


    private fun fallecidosPorSexo() {
        println("Fallecidos por sexo")
        dataFrame.filter { it.lesividad==tiposLesividad.FALLECIDO }.groupBy("sexo").aggregate {
            count() into "Total"
        }.print()
    }

    private fun fallecidosPorBarrios() {
        println("Fallecidos por barrios")
        dataFrame.filter { it.lesividad == tiposLesividad.FALLECIDO }.groupBy("distrito").aggregate {
            count() into "Total"
        }.print()
    }

    private fun numAccidentesConIngreso() {
        val num = dataFrame.filter { it.lesividad==tiposLesividad.LEVEINGRESO|| it.lesividad==tiposLesividad.GRAVE }.count()
        println("Nº de Accidentes que han tenido al menos un ingreso: $num")
    }


    /**
     * Saber si el barrio buscado existe
     */
    public fun isExist(barrio: String):Boolean{
        val lista = dataFrame.distinct("distrito").distrito.values()
        for (i in lista){
            if (barrio==i)
                return true
        }
        return false
    }




    /**
     * BARRIO ELEGIDO
     */


    /**
     *
     */
    public fun cuestionesBarrio(barrio: String){
        println("---------- Distrito: $barrio ----------")
        accidentesBarrioMes(barrio)
        problemasAlcoholDrogasAmbos(barrio)
        fallecidos(barrio)
        graficaAccidentesPorMeses(barrio)
        graficaTipoAccidentes()
        graficaTipoLesividad()
    }

    private fun graficaAccidentesPorMeses(barrio: String) {
        val agrupado = dataFrame.convert { fecha }.with { it.month }
            .groupBy("fecha").aggregate {
                count() into "total"
            }

        val fig:Plot = letsPlot(data=agrupado.toMap()) +geomBar(
            stat=Stat.identity,
            alpha=0.8,
            fill = Color.BLUE
        ) {
            x ="fecha"
            y ="total"
        } + labs(
            x ="Meses",
            y = "Total",
            title ="Total Accidentes por Mes"
        )

        ggsave(fig,"03-AccidentesPorMeses.png")
    }

    private fun accidentesBarrioMes(barrio: String) {
        dataFrame.convert { fecha }.with { it.month }
            .filter { it.distrito==barrio }
            .groupBy("fecha").aggregate {
                count() into "total"
            }.print()
    }

    private fun graficaTipoLesividad() {
        val agrupados = dataFrame.groupBy("lesividad").aggregate {
            count() into "total"
        }

        val fig:Plot = letsPlot(data=agrupados.toMap()) + geomBar(
            stat = Stat.identity,
            alpha = 0.8,
            fill = Color.LIGHT_MAGENTA
        ) {
            x = "lesividad"
            y = "total"
        } + labs(
            x = "tipo",
            y = "total",
            title = "Total tipos de lesividad"
        )

        ggsave(fig,"02-tipoLesividad.png")
    }


    private fun graficaTipoAccidentes() {
        val agrupados = dataFrame.groupBy("tipoAccidente").aggregate {
            count() into "total"
        }

        var fig: Plot = letsPlot(data = agrupados.toMap()) + geomBar(
            stat = Stat.identity,
            alpha = 0.8,
            fill = Color.CYAN
        ) {
            x = "tipoAccidente"
            y = "total"
        } + labs(
            x = "Tipos",
            y = "Total ",
            title = "Total de los tipos de accidentes",
        )
        ggsave(fig, "01-tipoAccidentes.png")
    }



    private fun fallecidos(barrio: String) {
        val num = dataFrame.filter {it.distrito==barrio && it.lesividad==tiposLesividad.FALLECIDO}.count()
        println("Número de fallecidos en $barrio: $num")
    }



    private fun problemasAlcoholDrogasAmbos(barrio: String) {
        println("Problemas con alcohol, drogas y ambos en $barrio")

        dataFrame.filter { it.distrito==barrio && ((it.positivoAlcohol && it.positivoDrogas) ||
                (it.positivoAlcohol || it.positivoDrogas)) }.groupBy("positivoAlcohol","positivoDrogas").aggregate {
                    count() into "total"
        }.print()

    }









    /**
     * Lo que tiene que ver con el mapeo del CSV
     */

    private fun parseCsv() {
        listaAccidentes = Files.lines(Path.of(path)).skip(1).map { line: String ->
            this.mapAccidente(line) }.toList()
    }

    private fun mapAccidente(line: String):Accidente{
        val campos = line.split(";")
        val expediente:String = campos[0]
        val fecha: LocalDate = parseFecha(campos[1])
        val hora: LocalTime = parseHora(campos[2])
        val calle: String = campos[3]
        val numero:Int = (campos[4].toIntOrNull())?: 0
        val distrito: String = campos[6]
        val tipoAccidente: tiposAccidente = parseTipoAccidente(campos[7])
        val tipoVehiculo: tiposVehiculo = parseTipoVehiculo(campos[9])
        val tipoPersona: tiposPersona = parseTipoPersona(campos[10])
        val tramoEdad: String = campos[11]
        val sexo: String = campos[12]
        val lesividad: tiposLesividad = parseLesividad(campos[13])
        val positivoAlcohol: Boolean = parseAlcohol(campos[17])
        val positivoDrogas: Boolean = parseDrogas(campos[18])

        return Accidente(expediente,fecha,hora,calle,numero,distrito, tipoAccidente,
            tipoVehiculo, tipoPersona, tramoEdad, sexo, lesividad, positivoAlcohol, positivoDrogas)
    }

    private fun parseDrogas(line: String): Boolean {
        return line=="1"
    }

    private fun parseAlcohol(line: String): Boolean {
        return line=="S"
    }

    private fun parseLesividad(line: String): tiposLesividad {
        var tipo: tiposLesividad = tiposLesividad.DESCONOCIDO
       when(line){
           "1" -> tipo = tiposLesividad.LEVE
           "2" -> tipo = tiposLesividad.LEVEINGRESO
           "3" -> tipo = tiposLesividad.GRAVE
           "4" -> tipo = tiposLesividad.FALLECIDO
           "5","6","7" -> tipo = tiposLesividad.ASISTENCIA
           "14","NULL" -> tipo = tiposLesividad.SIN_ASISTENCIA
           "77" -> tipo = tiposLesividad.DESCONOCIDO
       }
        return tipo
    }

    private fun parseTipoPersona(line: String): tiposPersona {
        var tipo: tiposPersona = tiposPersona.CONDUCTOR
        when(line){
            "Peatón" -> tipo = tiposPersona.PEATON
            "Conductor" -> tipo = tiposPersona.CONDUCTOR
            "Pasajero" -> tipo = tiposPersona.PASAJERO
        }
        return tipo
    }

    private fun parseTipoVehiculo(line: String): tiposVehiculo {
        var tipo: tiposVehiculo = tiposVehiculo.VEHICULO

        when (line) {
            "Turismo" -> tipo = tiposVehiculo.TURIMO
            "Motocicleta hasta 125cc" -> tipo = tiposVehiculo.MOTO
            "Furgoneta" -> tipo = tiposVehiculo.FURGO
            "Motocicleta > 125cc" -> tipo = tiposVehiculo.MOTOMAYOR
            "Camión rígido" -> tipo = tiposVehiculo.CAMION
            "Todo terreno" -> tipo = tiposVehiculo.TERRENO
            "Bicicleta EPAC (pedaleo asistido)" -> tipo = tiposVehiculo.BICIEPAC
            "VMU eléctrico" -> tipo = tiposVehiculo.VMU
            "Maquinaria de obras" -> tipo = tiposVehiculo.OBRAS
            "Ciclomotor" -> tipo = tiposVehiculo.CICLOMOTOR
            "Autobús articulado" -> tipo = tiposVehiculo.AUTOBUSARTI
            "Vehículo articulado" -> tipo = tiposVehiculo.VEHICULO
            "Otros vehículos con motor" -> tipo = tiposVehiculo.OTROS
            "Autocaravana" -> tipo = tiposVehiculo.CARAVANA
            "Autobús" -> tipo = tiposVehiculo.AUTOBUS
            "Bicicleta" -> tipo = tiposVehiculo.BICI
            "NULL" -> tipo = tiposVehiculo.DESCONOCIDO

        }
        return tipo
    }

    private fun parseTipoAccidente(line: String): tiposAccidente {
        var tipo: tiposAccidente = tiposAccidente.COLISIONMULTIPLE

        when (line) {
            "Alcance" -> tipo = tiposAccidente.ALCANCE
            "Colisión doble" -> tipo = tiposAccidente.COLISIONDOBLE
            "Colisión múltiple" -> tipo = tiposAccidente.COLISIONMULTIPLE
            "Choque contra obstáculo fijo" -> tipo = tiposAccidente.CHOQUECONTRAOBSTACULO
            "Atropello a persona" -> tipo = tiposAccidente.ATROPELLOPERSONA
            "Vuelco" -> tipo = tiposAccidente.VUELCO
            "Caída" -> tipo = tiposAccidente.CAIDA
            "Otras causas" -> tipo = tiposAccidente.OTRAS
        }
        return tipo
    }


    private fun parseHora(line: String): LocalTime {
        val hora = if (line.length <= 7) "0$line" else line
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return LocalTime.parse(hora,formatter)
    }


    private fun parseFecha(fecha: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.parse(fecha,formatter)
    }
}