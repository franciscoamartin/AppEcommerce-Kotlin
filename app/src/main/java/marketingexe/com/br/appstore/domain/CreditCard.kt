package marketingexe.com.br.appstore.domain

class CreditCard (
    val number: String,
    val enterprise: String,
    val ownerFullName: String,
    val ownerRegNumber: String = "",
    val expireMonth: Int = 0,
    val expireYear: Int = 0,
    val securityCode: String = ""

){
    fun getNumberAsHidden() = String.format("**** **** **** %s", number)

    fun getOwnerFullNameAsHidden(): String {
        val nameList = ownerFullName.split("")
        val firstName = nameList.first().substring(0,2)
        val lastName = nameList.last()

        return String.format(" %s... %s", firstName,lastName)
    }
}