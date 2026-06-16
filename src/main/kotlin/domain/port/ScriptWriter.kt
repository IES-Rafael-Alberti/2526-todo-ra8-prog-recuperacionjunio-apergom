package org.iesra.domain.port

import org.iesra.domain.model.ProcessingResult
import java.nio.file.Files
import java.nio.file.Paths

class ScriptWriter {
    fun write(place: String, result: ProcessingResult) {

        val commands = result.commands

        val sb = StringBuilder()


        for (command in commands) {
            sb.append("$command\n")
        }

        val scriptPath = Paths.get("${place}.sh")
        Files.write(scriptPath, sb.toString().toByteArray())
    }
}