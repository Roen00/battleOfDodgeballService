package models.user.sync

import models.user.{AuthenticationData, UserData, UserInformationData}
import reactivemongo.core.commands.LastError

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}


object UserData extends json.Formats {

  def find(authenticationData: AuthenticationData): Option[UserData] =
    findAbstract(models.user.async.UserData.find(authenticationData))

  def find(userInformationData: UserInformationData): Option[UserData] =
    findAbstract(models.user.async.UserData.find(userInformationData))

  private def findAbstract(f: Future[Option[UserData]]): Option[UserData] =
    Await.result(f, 10000.millisecond)

  def insert(userData: UserData): LastError =
    Await.result(models.user.async.UserData.insert(userData), 10000.millisecond)

}
