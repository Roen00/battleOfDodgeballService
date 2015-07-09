package controllers

import forms.RegisterForm
import models.user.AuthenticationData
import models.user.UserData
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.mvc.Controller
import play.modules.reactivemongo.MongoController
import scala.concurrent.ExecutionContext.Implicits.global
object Registration extends Controller with MongoController with json.Formats {

  def register = Action {
    Ok(views.html.register.register_page())
  }

  def registerRequest = Action.async { implicit request =>
    val userData = RegisterForm.get.bindFromRequest.get
    UserData.find(userData.authenticationData).map {
      case Some(result) => {
        Ok("Konto jest już zarejestrowane")
      }
      case None => {
        UserData.insert(userData)
        Redirect(routes.MainPanel.index).withSession("email" -> userData.authenticationData.email)
      }
    }
  }
}