package models.user

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

import database.Mongo
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.modules.reactivemongo.json.collection.JSONCollection
case class UserData(userInformationData: UserInformationData, authenticationData: AuthenticationData)

object UserData extends json.Formats {

  def findAsync(authenticationData: AuthenticationData) = {
    val collection = Mongo.db.collection[JSONCollection]("users")
    val query = Json.obj("authenticationData" -> Json.toJson(authenticationData))
    val userResult = collection.find(query).cursor[UserData].headOption
    userResult
  }

  def find(authenticationData: AuthenticationData) = Await.result(findAsync(authenticationData), 10000.millisecond)

  def insert(userData: UserData) = {
    val collection = Mongo.db.collection[JSONCollection]("users")
    val query = Json.toJson(userData)
    collection.insert(query)
  }
}