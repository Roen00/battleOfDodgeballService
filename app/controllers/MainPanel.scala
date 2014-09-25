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
object MainPanel extends Controller{
	def index = Action { implicit request =>
	  Ok(request.session.get("email").getOrElse("no session"))
	}
}