import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.serialization.json.Json

fun main() {

    val url = "https://raw.githubusercontent.com/a25anaisgm/WoW-APIRest/master/src/main/kotlin/wow_personajes.json"

    // 1️⃣ Descargar JSON
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    val personajes = Json.decodeFromString<List<Personaje>>(response.body())

    println("✓ Personajes cargados: ${personajes.size}")

    // 2️⃣ Menú
    println("\n¿Qué quieres buscar?")
    println("1) Por ESTADO (VIVO / FALLECIDO / DESAPARECIDO)")
    println("2) Por FACCIÓN (HORDA / ALIANZA / NEUTRAL)")
    println("3) Por RAZA (ej: HUMANO, ORCO, ELFO...)")
    println("4) Por ALINEACIÓN (HEROE / VILLANO / REDIMIDO)")
    print("Opción (1-4): ")

    val opcion = readLine()?.trim()

    val resultado = when (opcion) {

        "1" -> {
            print("Escribe el ESTADO: ")
            val estado = readLine()?.trim()?.uppercase()
            personajes.filter { it.estado == estado }
        }

        "2" -> {
            print("Escribe la FACCIÓN: ")
            val faccion = readLine()?.trim()?.uppercase()
            personajes.filter { it.faccion == faccion }
        }

        "3" -> {
            print("Escribe la RAZA: ")
            val raza = readLine()?.trim()?.uppercase()
            personajes.filter { it.raza.contains(raza ?: "") }
        }

        "4" -> {
            print("Escribe la ALINEACIÓN: ")
            val alineacion = readLine()?.trim()?.uppercase()
            personajes.filter { it.alineacion == alineacion }
        }

        else -> emptyList()
    }

    if (resultado.isEmpty()) {
        println("\n⚠ No se encontraron resultados.")
        return
    }

    // 3️⃣ Mostrar resultados bonitos
    resultado.forEach {

        val iconoEstado = when (it.estado) {
            "VIVO" -> "✓"
            "FALLECIDO" -> "✖"
            "DESAPARECIDO" -> "?"
            else -> "-"
        }

        println("========================================")
        println("☼ Nombre: ${it.nombre}")
        println("◈ Raza: ${it.raza}")
        println("⚔ Clase: ${it.clase}")
        println("⚑ Facción: ${it.faccion}")
        println("$iconoEstado Estado: ${it.estado}")
        println("★ Alineación: ${it.alineacion}")
    }

    println("========================================")
}