package org.iesra.domain.model

class ReflexPhotoFile(originalName: String) : PhotoFile(originalName) {

    override val orderKey: String = originalName.split("").first()

}