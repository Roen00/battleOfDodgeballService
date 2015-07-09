package controllers

import forms.LoginForm._
import models.user.{UserData, UserInformationData}
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.DurationInt
import  scala.concurrent.ExecutionContext.Implicits.global
object Login extends Controller{


  def login = Action { implicit request =>
    request.session.get("email") match {
      case Some(email) => Ok(views.html.panel.mainPanel(UserInformationData(email)))
      case None => Ok(views.html.login.login_page(get))
    }
  }

  def authenticate = Action.async { implicit request =>
    get.bindFromRequest.fold(
      formWithErrors => {
        Logger.debug(s"authenticate [form errors]: ${formWithErrors.errors}")
        Future(BadRequest(views.html.login.login_page(formWithErrors)))
      },
      authenticationData => {
        UserData.find(authenticationData).map(
          _.map(_ =>
            Redirect(routes.MainPanel.index).withSession("email" -> authenticationData.email)
          ).getOrElse(Ok(views.html.login.login_page(get)))
        )
      }
    )
  }

  def AuthorizedAction[A](bp: BodyParser[A])(f: Request[A] => Result): Action[A] = {
    Action(bp) { implicit request =>
      request.session.get("email") match {
        case Some("true") => f(request)
        case _ => redirectToLoginPage
      }
    }
  }

  private def redirectToLoginPage: Result = {
    Redirect(routes.Login.login)
  }

  def signout = Action {
    redirectToLoginPage.withNewSession
  }
}

