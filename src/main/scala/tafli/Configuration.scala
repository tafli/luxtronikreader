package tafli

import java.util.concurrent.TimeUnit

import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration.{Duration, FiniteDuration}

object Configuration {
  val config: Config = ConfigFactory.load

  val ip = config.getString("luxtronik.ip")
  val port = config.getInt("luxtronik.port")

  val dbSchema = config.getString("luxtronik.db.schema")
  val dbTable = config.getString("luxtronik.db.table")

  val updateInterval: FiniteDuration =
    FiniteDuration(
      Duration(config.getString("luxtronik.updateInterval")).toMillis,
      TimeUnit.MILLISECONDS)

}
