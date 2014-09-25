package database

import scala.concurrent.ExecutionContext.Implicits.global

import reactivemongo.api.MongoDriver

object Mongo {
  val driver = new MongoDriver
  val connection = driver.connection(List("localhost:27017"))
  val db = connection("data")
}