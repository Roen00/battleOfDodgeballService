package forms

import models.user.AuthenticationData
import play.api.data.Forms._
import play.api.data._

object LoginForm {
  val get = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(3, 16))(AuthenticationData.apply)(AuthenticationData.unapply))
}