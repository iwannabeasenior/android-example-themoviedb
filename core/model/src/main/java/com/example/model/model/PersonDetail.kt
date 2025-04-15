package com.example.model.model

data class PersonDetail(
    val gender: Int,
    val id: Int,
    val name: String?,
    val birthday: String?,
    val placeOfBirth: String?,
    val biography: String?,
    val knownFor: String?,
    val popularity: Double?,
    val alsoKnownAs: List<String>?,
    val profilePath: String?,
    val imdbId: String?
)  {
    fun getGenderName(): String = Gender.fromIntGender(gender).name
//    fun getBirthday(): String {
//        // 1992-04-10
//        val parts = birthday?.split("-")
//        if (parts!= null && parts.size == 3) {
//            return "${parts[1]} ${parts[2]}, ${parts[0]}"
//        }
//        return placeOfBirth?: ""
//    }
}
enum class Gender(val gender: Int) {
    Male(1),
    Female(2),
    Other(0);
    companion object {
        fun fromIntGender(gender: Int): Gender {
            return when (gender) {
                Male.gender -> return Male
                Female.gender -> return Female
                else -> return Other
            }
        }
    }
}