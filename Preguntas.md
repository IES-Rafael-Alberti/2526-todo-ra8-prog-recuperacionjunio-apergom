# Preguntas de evaluación por RA

Estas preguntas sirven para que expliques y defiendas la solución que has desarrollado, relacionando el código con los resultados de aprendizaje.

**IMPORTANTE**: Utiliza enlaces permanentes al código fuente. También puedes copiarlo y pegarlo en tu respuesta entre

```Kotlin
aquí el código
```

para que se vea mejor. No copies y pegues capturas de pantalla.

## UD1, RA1: Estructura de un programa informático

1. Identifica los bloques principales de tu solución (`Main`, parser de argumentos, aplicación, lector, procesador, escritor y modelos). Explica qué responsabilidad tiene cada bloque y por qué no debe estar todo el código dentro de `main`.

Respuesta a pregunta 1:

2. Localiza en tu solución ejemplos de variables, constantes o literales, operadores y conversiones de tipos. Explica para qué se usan en el procesamiento de nombres, fechas, contadores y rutas de fichero.

Respuesta a pregunta 2:

## UD2, RA3: Estructuras de control, depuración y excepciones

3. Explica qué estructuras de selección y repetición aparecen en tu solución para validar argumentos, recorrer listas de fotos, detectar formatos incorrectos y generar los comandos `mv`.

Respuesta a pregunta 3:

4. Describe qué excepciones o errores controla tu programa cuando el fichero de entrada no existe, tiene menos de tres líneas o contiene nombres de foto con formato incorrecto. Indica en qué clase gestionas cada caso.

Respuesta a pregunta 4:

## UD3, RA6: Tipos avanzados de datos y colecciones

5. Explica qué colecciones utilizas para almacenar las fotos de smartphone, las fotos réflex y la lista final de comandos. Justifica por qué usas la que usas y no otra.

Respuesta a pregunta 5:

6. Tu solución puede usar expresiones regulares para validar nombres como `IMG_20210613_104512.jpg` o `P130621_083827.jpg`. Escribe qué comprueba cada patrón y explica cómo se relaciona con la creación de objetos `SmartphonePhotoFile` y `ReflexPhotoFile`.

Respuesta a pregunta 6:

## UD4, RA2: Fundamentos de programación orientada a objetos

7. Explica qué objetos instancias durante la ejecución del programa y por qué usas un objeto y no un tipo primitivo/básico. Por ejemplo: `TripInput`, `PhotoFile`, `RenameCommand`, etc.

Respuesta a pregunta 7:

8. Elige una clase de tu solución y explica sus propiedades, sus métodos, su constructor y un ejemplo de llamada a uno de sus métodos desde otra clase.

Respuesta a pregunta 8:

## UD5, RA4: Programas organizados en clases

9. Justifica por qué tu solución está dividida en varias clases en lugar de resolverse con funciones sueltas. Relaciona tu respuesta con cohesión, separación de responsabilidades y mantenimiento.

Respuesta a pregunta 9:

10. Explica qué modificadores de visibilidad usas en las clases de tu solución. Indica qué miembros deben ser públicos y cuáles deben ser privados, por ejemplo en `PhotoProcessor`, `InputFileReader` o `ScriptWriter`.

Respuesta a pregunta 10:

## UD6, RA7: Herencia, polimorfismo e interfaces

11. Explica la jerarquía formada por `PhotoFile`, `SmartphonePhotoFile` y `ReflexPhotoFile`. Indica cuál es la superclase, cuáles son las subclases y qué comportamiento sobrescribe o especializa cada una.

Respuesta a pregunta 11:

12. Justifica por qué el procesador trabaja con una lista de `PhotoFile` y no con dos listas separadas todo el tiempo. Relaciona tu respuesta con polimorfismo y con la ordenación por la clave `YYYYMMDDHHMMSS`.

Respuesta a pregunta 12:

## UD7, RA5: Entrada y salida de información

13. Describe el flujo completo de entrada y salida: cómo recibes la ruta por consola, cómo lees el fichero `.in`, qué comprobaciones haces, cómo interpretas sus tres líneas, qué mensajes de resumen o error muestras por consola, cómo generas el fichero `<lugar>.sh` y qué tipos de ficheros usas.

Respuesta a pregunta 13: La ruta es obtenida desde la consola ```bash ./gradlew run --args="examples/italia.in"``` y el ArgumentParser valida que el fichero a leer contenga las especificaciones básicas requeridas (que sea un fichero regular y que exista), las tres líneas del parseador son:

```kotlin

//Toma el argumento
        val inputPath = Path.of(args[0])

        //El fichero existe?
        require(Files.exists(inputPath)) {
            "El fichero de entrada no existe: $inputPath"
        }

        //Es un fichero regular?
        require(Files.isRegularFile(inputPath)) {
            "La ruta indicada no es un fichero: $inputPath"
        }

```

En la primera linea se le da el valor del fichero que se indica desde la consola mediante una lectura de argumentos de esta que se realiza dentro de la funcion "parse" justo aquí:

```kotlin
fun parse(args: Array<String>): Path 
```

Esta función retorna inputPath que es nuestro fichero a interpretar, de esta manera se produce la entrada del fichero que contiene el lugar del viajey las fotos separadas en smartphone y reflex. Con esto obtenido, en la clase InputFileReader tenemos el metodo "read", que como bien dice su nombre lee estos datos y crea unn objeto TripInput con esos datos.

```kotlin 
class InputFileReader {
    fun read(inputPath: Path): TripInput {
        val lineas = inputPath.readLines()

        val place = lineas.first()

        val smartphoneFiles = lineas[1].split(" ")

        val reflexFiles = lineas[2].split(" ")


        return TripInput(place, smartphoneFiles, reflexFiles)
    }
}
```

El procesado de los datos se producen dentro de PhotoProcessor que contiene tres metodos, dos de ellos similares que sirven para validar las imagenes (validacion completa no implementada) segun su clave de inicio que es dada gracias a las clases PhotoFile, SmartphonePhotoFile y ReflexPhotoFile. 

La otra funcion es la del procesado que genera un objeto de la clase ProcessingResult con: La lista de comandos que se han generado en base a las fotos válidas, las estadisticas de cada uno de los tipos de fotos y la lista de errores que tienen de formato las imagenes.

````kotlin
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

````

````kotlin

data class ProcessingResult (
    val commands: List<String>,
    val reflexStats: PhotoTypeStats,
    val smartphoneStats: PhotoTypeStats,
    val errors: List<String>
)

````

Por último la escritura de datos se realiza a partir de la clase ScriptWriter, que, utilizando StringBuilder() escribe los comandos necesarios para ejecutar en su ficher <lugar>.sh correspondiente.

````kotlin
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
````
En cuanto a este apartado, dentro del programa se pedía la existencia de una clase adicional llamada "RenameCommand" que era la que, en teoría llevarí registro de los comandos y se encargaría de escribirlos pero mi decisión de diseño fue implementar esto dentro de el procesado de fotos y utilizar los valores de la clase ProcessingResult como referencia para lo demás.

Otra decisión tomada ha sido que en vez de generar el archivo dentro de la misma carpeta, se genere fuera, es, principalmente, para llevar constancia de ambos archivos, tanto el otorgado por el porfesor como el generado por mí, ya que durante la depuración de la aplicación y el proceso de realización de la misma ha sido más fácil de esta manera. No es "correcto", pero ayuda a comparar el archivo base con el ya manipulado.

Lo último que comentar es el manejo de errores/excepciones que he realizado, es bastante básico y produce ciertos errores dentro del programa como la validacion de algunas imagenes que deberían ser contadas como errores. Ha sido tomada debido a la falta de tiempo por haber empezado el examen tarde y porque quería centrarme en la realización de los puntos claves del criterio.

14. Indica qué alternativas ofrece Kotlin para leer y escribir ficheros. Compara opciones como `File`, `Path`, `readLines`, `bufferedReader`, `writeText` y `bufferedWriter`, y justifica cuál usarías en esta solución.

Respuesta a pregunta 14: Existen numerosas alternativas dentro de kotlin para poder leer o manipular archivos, en mi caso he decidido usar los que he usado como pueden ser el `StringBuilder()`, `readLines`... porque son los que mas he utilizado para preparar el examen y son metodos que he aplicado durante las prácticas, no quiero decir que sean los métodos más óptimos para esto pero son los que a mí me han resultado más faciles de aplicar y entender para este ejercicio.

## UD9, RA9: Bases de datos relacionales

15. Explica qué objetos importantes de la librería JDBC usas en el `insert` de esta ampliación y para qué sirve cada uno, por ejemplo `DriverManager`, `Connection` y `PreparedStatement`. Indica también para qué serviría `ResultSet` en una consulta.

Respuesta a pregunta 15:

16. Propón cómo completarías el resto del CRUD sobre la tabla `resumen_procesamiento`, sin implementarlo: consulta de resúmenes, modificación de un registro incorrecto y borrado de un resumen almacenado.

Respuesta a pregunta 16:
