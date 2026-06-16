package org.iesra.domain.port

import org.iesra.domain.model.PhotoFile
import org.iesra.domain.model.PhotoTypeStats
import org.iesra.domain.model.ProcessingResult
import org.iesra.domain.model.ReflexPhotoFile
import org.iesra.domain.model.SmartphonePhotoFile
import org.iesra.domain.model.TripInput

class PhotoProcessor {
    private val errors: MutableList<String> = mutableListOf()
  fun process(input: TripInput): ProcessingResult {

      val reflexPhotos = buildValidReflexPhotos(input.reflexFiles)
      val smartphonePhotos = buildValidSmartphonePhotos(input.smartphoneFiles)

      val reflexStats = PhotoTypeStats(
          read = input.reflexFiles.size,
          correct = reflexPhotos.size,
          errors = input.reflexFiles.size - reflexPhotos.size
      )

      val smartphoneStats = PhotoTypeStats(
          read = input.smartphoneFiles.size,
          correct = smartphonePhotos.size,
          errors = input.smartphoneFiles.size - smartphonePhotos.size
      )

      val allValidPhotos = reflexPhotos + smartphonePhotos

      val orderedPhotos = allValidPhotos.sortedBy { it.orderKey }

      val commands = orderedPhotos.mapIndexed { index, photo ->
          val destFileName = "${input.place}_${String.format("%03d", index)}.jpg"
          "mv ${photo.originalName} $destFileName"
      }

        println(commands.joinToString("\n"))
      return ProcessingResult(
          commands = commands,
          reflexStats = reflexStats,
          smartphoneStats = smartphoneStats,
          errors = errors.toList()
      )
  }}


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
