package controllers

import forms.LoginForm._
import models.user.{AuthenticationData, UserData, UserInformationData}
import play.api.Logger
import play.api.data.Form
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Login extends Controller {


  def login = Action { implicit request =>
    request.session.get("email") match {
      case Some(email) => Ok(views.html.panel.mainPanel(UserInformationData(email)))
      case None => Ok(views.html.login.login_page(get))
    }
  }

  def authenticate = Action.async { request =>
    val hasErrors: (Form[AuthenticationData]) => Future[Result] = formWithErrors => {
      Logger.debug(s"authenticate [form errors]: ${formWithErrors.errors}")
      Future(BadRequest(views.html.login.login_page(formWithErrors)))
    }

    val login: (Option[UserData]) => Result = _.map(userData =>
      Redirect(routes.MainPanel.index).withSession("email" -> userData.authenticationData.email)
    ).getOrElse(Ok(views.html.login.login_page(get)))

    val formWithoutErrors: (AuthenticationData) => Future[Result] = authenticationData => {
      UserData.find(authenticationData).map(login)
    }

    get.bindFromRequest()(request).fold(hasErrors, formWithoutErrors)
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

