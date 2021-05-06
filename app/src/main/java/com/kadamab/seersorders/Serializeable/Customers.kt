import com.google.gson.annotations.SerializedName

data class Customers (

	@SerializedName("orderRef") val orderRef : String,
	@SerializedName("status") val status : String,
	@SerializedName("scheduleDate") val scheduleDate : String,
	@SerializedName("scheduleStartTime") val scheduleStartTime : String,
	@SerializedName("scheduleEndTime") val scheduleEndTime : String,
	@SerializedName("customer") val customer : Customer,
	@SerializedName("serviceRequested") val serviceRequested : String,
	@SerializedName("serviceSpecialInstructions") val serviceSpecialInstructions : String,
	@SerializedName("jobIndicator") val jobIndicator : String,
	@SerializedName("imageUrl") val imageUrl : String
)