package org.iesra.domain.model

class SmartphonePhotoFile(originalName : String ) : PhotoFile(originalName) {
    override val orderKey: String = originalName.split("_").first()

}