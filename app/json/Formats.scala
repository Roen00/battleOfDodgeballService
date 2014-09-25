package json

import play.api.libs.json.Json
import models.user.AuthenticationData
import models.user.UserData
import models.user.UserInformationData

trait Formats {

  implicit val authenticationFormats = Json.format[AuthenticationData]
  implicit val userInformationFormats = Json.format[UserInformationData]
  implicit val userFormats = Json.format[UserData]

}