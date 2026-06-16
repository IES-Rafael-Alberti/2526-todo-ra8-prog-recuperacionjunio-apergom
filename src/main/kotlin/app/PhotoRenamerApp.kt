package org.iesra.app

import org.iesra.domain.port.InputFileReader
import org.iesra.domain.port.PhotoProcessor
import org.iesra.domain.port.PrintSummary
import org.iesra.domain.port.ScriptWriter
import java.nio.file.Path

class PhotoRenamerApp {

    fun run(inputPath: Path) {
        // Leer el fichero de entrada ya validado por ArgumentParser.
        // Validar el formato general del fichero y construir el objeto TripInput.
        val trip = InputFileReader().read(inputPath)

        // Procesar las fotos validas y generar el resultado con comandos y estadisticas.
        val processor = PhotoProcessor()

        val result = processor.process(trip)

        // Escribir el script <lugar>.sh con los comandos mv generados.
        val writer = ScriptWriter()
        writer.write(trip.place, result)

        val summary = PrintSummary()

        summary.print(result, trip)


    }


}

