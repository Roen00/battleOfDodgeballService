package models.user.async

import database.Mongo
import models.user.{AuthenticationData, UserData, UserInformationData}
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.core.commands.LastError

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object UserData extends json.Formats {

  def find(authenticationData: AuthenticationData): Future[Option[UserData]] = {
    findAbstract(Json.obj("authenticationData" -> Json.toJson(authenticationData)))
  }

  def find(userInformationData: UserInformationData): Future[Option[UserData]] = {
    findAbstract(Json.obj("userInformationData" -> Json.toJson(userInformationData)))
  }

  private def findAbstract(query: JsObject): Future[Option[UserData]] = {
    val collection = Mongo.db.collection[JSONCollection]("users")
    val userResult = collection.find(query).cursor[UserData].headOption
    userResult
  }

  def insert(userData: UserData): Future[LastError] = {
    val collection = Mongo.db.collection[JSONCollection]("users")
    val query = Json.toJson(userData)
    collection.insert(query)
  }
}
