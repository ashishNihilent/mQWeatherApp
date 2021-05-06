import com.google.gson.annotations.SerializedName

data class Orders (

	@SerializedName("firstName") val firstName : String,
	@SerializedName("lastName") val lastName : String,
	@SerializedName("phoneNumber") val phoneNumber : Double,
	@SerializedName("customers") val customers : List<Customers>
)