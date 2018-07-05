package tafli

import com.typesafe.scalalogging.StrictLogging
import tafli.actors.{LuxtronikActor, RootActor}


object LuxtronikReader extends App with StrictLogging {
  logger.info("Initialize Luxtronik Reader")
  val luxtronikReader = RootActor.system.actorOf(LuxtronikActor.props)

  luxtronikReader ! LuxtronikActor.ReadCalculation
}