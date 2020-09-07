import com.example.tsaving.model.Account
import com.google.gson.annotations.SerializedName
import java.util.Date

class Customer (
    @SerializedName("cust_id")
    val customerId: Int,

    @SerializedName("account_num")
    val accountNum: String,

    @SerializedName("cust_name")
    val customerName : String,

    @SerializedName("cust_address")
    val customerAddress : String,

    @SerializedName("cust_phone")
    val customerPhone: String,
    @SerializedName("cust_email")
    val customerEmail :String,
    @SerializedName("cust_pict")
    val customerPicture :String,
    @SerializedName("is_verified")
    val isVerified : Boolean,
    @SerializedName("channel")
    val channel :String,
    @SerializedName("created_at")
    val createdAt : Date,
    @SerializedName("updated_at")
    val updatedAt : Date
)
