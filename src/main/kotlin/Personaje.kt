import kotlinx.serialization.Serializable

@Serializable
data class Personaje(
    val id: Int,
    val nombre: String,
    val raza: String,
    val clase: String,
    val faccion: String,
    val titulo: String,
    val estado: String,
    val alineacion: String
)