package tafli

import com.typesafe.scalalogging.StrictLogging
import scalikejdbc.config.DBs
import tafli.actors.{LuxtronikActor, RootActor}


object LuxtronikReader extends App with StrictLogging {
  logger.info("Initialize DB")
  DBs.setupAll

  logger.info("Initialize Luxtronik Reader")
  val luxtronikReader = RootActor.system.actorOf(LuxtronikActor.props)
}