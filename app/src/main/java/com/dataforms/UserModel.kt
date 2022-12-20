package com.dataforms

data class UserModel(
    var name: String = "",
    val status: String = "",
    val image: String = "",
    var number: String = "",
    val uid: String = "",
    val online: String = "offline",
    val typing: String = "false"
) {

}

