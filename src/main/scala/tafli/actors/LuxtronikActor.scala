package tafli.actors

import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket

import akka.actor.{Actor, ActorLogging, Props}
import tafli.Configuration
import tafli.actors.LuxtronikActor.{ReadCalculation, ReadParameters, ReadVisibility}

import scala.collection.immutable

object LuxtronikActor {
  def props = Props(new LuxtronikActor)

  case class ReadParameters()

  case class ReadCalculation()

  case class ReadVisibility()

}

class LuxtronikActor extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("LuxtronikActor started")

  override def postStop(): Unit = log.info("LuxtronikActor stopped")

  log.info(s"Connection to [${Configuration.ip}:${Configuration.port}]...")
  val socket = new Socket(Configuration.ip, Configuration.port)
  val dataIn = new DataInputStream(socket.getInputStream)
  val dataOut = new DataOutputStream(socket.getOutputStream)
  log.info("Connected!")

  override def receive: Receive = {
    case ReadParameters => {
      val params = readData(3003)

      params.zipWithIndex.foreach { param =>
        log.debug(s"${param._2} => [${param._1}]")
      }
    }

    case ReadCalculation => {
      val data = readData(3004)

      data.zipWithIndex.foreach { param =>
        log.debug(s"${param._2} => [${param._1}]")
      }
    }

    case ReadVisibility => {
      val data = readData(3005)

      data.zipWithIndex.foreach { param =>
        log.debug(s"${param._2} => [${param._1}]")
      }
    }
  }

  def readData(id: Int) = {
    log.debug("Flushing input...")
    while (dataIn.available > 0) dataIn.readByte

    log.debug(s"Reading Data [$id]...")
    dataOut.writeInt(id)
    dataOut.writeInt(0)
    dataOut.flush()

    if (dataIn.readInt != id) log.debug("Didn't got correct reply...")

    if(id == 3004) dataIn.readInt

    val len = dataIn.readInt
    if (len <= 0) log.info("No data found!") else log.debug(s"Got data #[$len]")

    val data = (1 to len).map(index => dataIn.readInt)

    data
  }
}