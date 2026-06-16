package org.iesra.domain.port

import org.iesra.domain.model.TripInput
import java.nio.file.Path
import kotlin.io.path.readLines

class InputFileReader {
    fun read(inputPath: Path): TripInput {
        val lineas = inputPath.readLines()

        val place = lineas.first()

        val smartphoneFiles = lineas[1].split(" ")

        val reflexFiles = lineas[2].split(" ")


        return TripInput(place, smartphoneFiles, reflexFiles)
    }
}

