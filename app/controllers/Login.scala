package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import models.user.UserData
import play.api.GlobalSettings
import play.api.mvc.Action
import play.api.mvc.Controller
import play.modules.reactivemongo.MongoController
import forms.LoginForm

object Login extends Controller with MongoController with json.Formats {

  def login = Action {
    Ok(views.html.login_page(LoginForm.get))
  }

  def authenticate = Action { implicit request =>
    LoginForm.get.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.login_page(formWithErrors))
      },
      authenticationData => {
        UserData.find(authenticationData) match {
          case Some(result) => {
            Redirect(routes.MainPanel.index).withSession("email" -> authenticationData.email)
          }
          case None => Ok(views.html.login_page(LoginForm.get))
        }
      })
  }

}

