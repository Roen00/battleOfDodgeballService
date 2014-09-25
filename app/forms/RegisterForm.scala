package forms

import models.user.AuthenticationData
import models.user.UserData
import models.user.UserInformationData
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms.text

object RegisterForm {
  val get = Form(
    mapping(
      "userInformationData" -> mapping(
        "nickName" -> text)(UserInformationData.apply)(UserInformationData.unapply),
      "authenticationData" -> mapping(
        "email" -> text,
        "password" -> text)(AuthenticationData.apply)(AuthenticationData.unapply))(UserData.apply)(UserData.unapply))

}