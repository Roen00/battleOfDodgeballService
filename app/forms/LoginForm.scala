package forms

import models.user.AuthenticationData
import play.api.data.Form
import play.api.data.Forms.email
import play.api.data.Forms.mapping
import play.api.data.Forms.text
import play.api.i18n.Messages

object LoginForm {
  val get = Form(
    mapping(
      "email" -> email.verifying(Messages("error.email.required"), { !_.isEmpty }),
      "password" -> text)(AuthenticationData.apply)(AuthenticationData.unapply))

}