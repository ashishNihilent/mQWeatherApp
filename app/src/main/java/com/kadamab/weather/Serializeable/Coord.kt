import com.google.gson.annotations.SerializedName

data class Coord (

	@SerializedName("lon") val lon : Float,
	@SerializedName("lat") val lat : Float
)