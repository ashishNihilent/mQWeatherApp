import com.google.gson.annotations.SerializedName

data class Sys (

	@SerializedName("sunrise") val sunrise : Float,
	@SerializedName("sunset") val sunset : Float
)