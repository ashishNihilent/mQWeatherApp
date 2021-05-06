import com.google.gson.annotations.SerializedName
data class Customer (

	@SerializedName("firstName") val firstName : String,
	@SerializedName("lastName") val lastName : String,
	@SerializedName("address") val address : String,
	@SerializedName("city") val city : String,
	@SerializedName("state") val state : String,
	@SerializedName("zip") val zip : Int,
	@SerializedName("zipSuffix") val zipSuffix : Int,
	@SerializedName("phoneNumber") val phoneNumber : Double
)