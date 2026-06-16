package org.iesra.domain.model

data class ProcessingResult (
    val commands: List<String>,
    val reflexStats: PhotoTypeStats,
    val smartphoneStats: PhotoTypeStats,
    val errors: List<String>
)
