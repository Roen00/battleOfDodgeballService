package forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models.user.AuthenticationData

object LoginForm {
  val get = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(3,16))(AuthenticationData.apply)(AuthenticationData.unapply))

}