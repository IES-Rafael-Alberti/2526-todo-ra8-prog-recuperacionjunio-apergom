package org.iesra.domain.port

import org.iesra.domain.model.ProcessingResult
import org.iesra.domain.model.TripInput

class PrintSummary {

    fun print(result: ProcessingResult, trip: TripInput) {

        println("Procesadas la fotos de ${trip.place}.")
        if (result.errors.isEmpty()) {
            println("No se han detectado errores")
        } else {
            println("Errores detectados ${result.errors}")
        }
        println("Réflex: ")
        println("=========")
        println("Fotos leidas: ${result.reflexStats.read}")
        println("Correctas: ${result.reflexStats.correct}")
        println("Errores detectados: ${result.reflexStats.errors}")
        println()
        println("Smartphone: ")
        println("================")
        println("Fotos leidas: ${result.smartphoneStats.read}")
        println("Correctas: ${result.smartphoneStats.correct}")
        println("Errores detectados: ${result.smartphoneStats.errors}")
        println()
        println("Generado el script ${trip.place}.sh con ${result.commands.size} comandos mv.")
    }
}