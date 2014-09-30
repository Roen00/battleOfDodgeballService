package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import models.user.UserData
import play.api.GlobalSettings
import play.api.mvc.Action
import play.api.mvc.Controller
import play.modules.reactivemongo.MongoController
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models.user.AuthenticationData
import models.user.UserInformationData
import play.api.mvc.BodyParser
import play.api.mvc.Request
import play.api.mvc.Result
object Login extends Controller with MongoController with json.Formats {

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
        case _ => Redirect(routes.Login.login)
      }
    }
  }

  def signout = Action {
    Redirect(routes.Login.login).withNewSession
  }

}

