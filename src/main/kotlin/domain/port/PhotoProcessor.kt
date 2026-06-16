package org.iesra.domain.port

import org.iesra.domain.model.ReflexPhotoFile
import org.iesra.domain.model.SmartphonePhotoFile

class PhotoProcessor {
    private fun buildValidSmartPhotos(names: List<String>, photoFile: ReflexPhotoFile): List<SmartphonePhotoFile> {
        if (names.isEmpty()) return emptyList()

        val validSmartphone = ArrayList<SmartphonePhotoFile>()
        return validSmartphone
    }
    private fun buildValidReflexPhotos(names: List<String>, photoFile: ReflexPhotoFile): List<ReflexPhotoFile> {
        if (names.isEmpty()) return emptyList()


        val validReflex = ArrayList<ReflexPhotoFile>()
        return validReflex
    }
}