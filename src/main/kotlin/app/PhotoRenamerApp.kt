package org.iesra.app

import org.iesra.domain.port.InputFileReader
import org.iesra.domain.port.PhotoProcessor
import org.iesra.domain.port.ScriptWriter
import java.nio.file.Path

class PhotoRenamerApp {

    fun run(inputPath: Path) {
        // Leer el fichero de entrada ya validado por ArgumentParser.

        val lectorArchivos = InputFileReader()

        // Validar el formato general del fichero y construir el objeto TripInput.

        val trip = InputFileReader().read(inputPath)

        // Procesar las fotos validas y generar el resultado con comandos y estadisticas.
        val processor = PhotoProcessor()

        val result = processor.process(trip)

        // Escribir el script <lugar>.sh con los comandos mv generados.
        val writer = ScriptWriter()
        writer.write(trip.place, result)


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

