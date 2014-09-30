package controllers

import forms.LoginForm
import models.user.UserData
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.Controller
import play.modules.reactivemongo.MongoController
import database.Mongo
import play.api.GlobalSettings
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.ExecutionContext.Implicits.global
import models.user.AuthenticationData
import models.user.UserInformationData
object MainPanel extends Controller{
	def index = Login.AuthorizedAction(parse.anyContent) { implicit request =>
	  Ok(views.html.panel.mainPanel(UserInformationData(request.session.get("email").get)))
	}
}