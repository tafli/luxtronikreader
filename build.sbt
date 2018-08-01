lazy val akkaVersion = "2.5.13"
lazy val mysqlVersion = "8.0.11"
lazy val scalikeJdbcVersion = "3.3.0"
lazy val logbackVersion = "1.2.3"
lazy val scalaLoggingVersion = "3.9.0"
lazy val scalaTestVersion = "3.0.5"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, ScalikejdbcPlugin)
  .settings(
    name := "luxtronikreader",

    scalaVersion := "2.12.6",
    organization := "io.tafli",

    mainClass in(Compile, run) := Some("tafli.LuxtronikReader"),
    mainClass in(Compile, packageBin) := Some("tafli.LuxtronikReader"),

    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
      "org.scalikejdbc" %% "scalikejdbc" % scalikeJdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-config" % scalikeJdbcVersion,
      "mysql" % "mysql-connector-java" % mysqlVersion,
      "ch.qos.logback" % "logback-classic" % logbackVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
    )
  )
