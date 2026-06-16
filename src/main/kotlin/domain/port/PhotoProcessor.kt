package org.iesra.domain.port

import org.iesra.domain.model.PhotoFile
import org.iesra.domain.model.ProcessingResult
import org.iesra.domain.model.ReflexPhotoFile
import org.iesra.domain.model.SmartphonePhotoFile
import org.iesra.domain.model.TripInput
import java.io.File

class PhotoProcessor {
  fun process(input: TripInput): ProcessingResult {}


    private fun buildValidReflexPhotos(names: List<String>): List<PhotoFile>{
        if (names.isEmpty()) return emptyList()
        val validPhotos = mutableListOf<PhotoFile>()

        for (name in names) {
            if (name.startsWith("P")) { // TODO: Completar validacion //
                validPhotos.add(ReflexPhotoFile(name))
            }
        }
        return validPhotos
    }

    private fun buildValidSmartphonePhotos(names:List<String>): List<PhotoFile> {
        if (names.isEmpty()) { return emptyList() }
        val validPhotos = mutableListOf<SmartphonePhotoFile>()

        for (name in names) {
            if (name.startsWith("IMG")) { // TODO: Completar validacion //
                validPhotos.add(SmartphonePhotoFile(name))
            }
        }
        return validPhotos
    }
}