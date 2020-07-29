import com.example.tsaving.model.Account
import java.util.Date

class Customer (id: Int, accNum: Account, custName: String, custAddress: String, custPhone: String, custEmail: String, custPassword: String, custPict: String, verified: Boolean, channel: String, created: Date, updated: Date) {
    val customerId = id
    val accountNum = accNum
    val customerName = custName
    val customerAddress = custAddress
    val customerPhone = custPhone
    val customerEmail = custEmail
    val customerPassword = custPassword
    val customerPicture = custPict
    val isVerified = verified
    val channel = channel
    val createdAt = created
    val updatedAt = updated
}
