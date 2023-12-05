package xyz.starchen.wincc.pojo

data class MemoryData(
    val id: Int?,
    val difference: Double,
    val qualified: Boolean,
    val number: Int,
    val round: Int,
    val checkTime: Int
) {
    override fun toString(): String {
        return "MemoryData(id=$id, difference=$difference, qualified=$qualified, number=$number, round=$round, checkTime=$checkTime)"
    }
}
