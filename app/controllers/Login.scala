package controllers

import models.user.{AuthenticationData, UserData, UserInformationData}
import play.api.data.Forms._
import play.api.data._
import play.api.mvc.{Action, BodyParser, Controller, Request, Result}

object Login extends Controller {

  implicit val get = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(3, 16))(AuthenticationData.apply)(AuthenticationData.unapply))

  def login = Action { implicit request =>
    request.session.get("email") match {
      case Some(email) => {
        Ok(views.html.panel.mainPanel(UserInformationData(email)))
      }
      case None => Ok(views.html.login.login_page(get))
    }
  }

  def authenticate = Action { implicit request =>

    get.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors.errors)
        BadRequest(views.html.login.login_page(formWithErrors))
      },
      authenticationData => {
        UserData.find(authenticationData) match {
          case Some(result) => {
            Redirect(routes.MainPanel.index).withSession("email" -> authenticationData.email)
          }
          case None => Ok(views.html.login.login_page(get))
        }
      })
  }

  def AuthorizedAction[A](bp: BodyParser[A])(f: Request[A] => Result): Action[A] = {
    Action(bp) { implicit request =>
      request.session.get("email") match {
        case Some("true") => f(request)
        case _ => redirectToLoginPage
      }
    }
  }

  def signout = Action {
    redirectToLoginPage.withNewSession
  }

  private def redirectToLoginPage: Result = {
    Redirect(routes.Login.login)
  }
}

