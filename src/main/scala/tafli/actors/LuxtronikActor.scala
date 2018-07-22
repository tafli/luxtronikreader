package tafli.actors

import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket

import akka.actor.{Actor, ActorLogging, Props}
import tafli.Configuration
import tafli.actors.LuxtronikActor.{ReadCalculation, ReadParameters, ReadVisibility}
import tafli.models.HeatingData._

object LuxtronikActor {
  def props = Props(new LuxtronikActor)

  case class ReadParameters()

  case class ReadCalculation()

  case class ReadVisibility()

}

class LuxtronikActor extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("LuxtronikActor started")

  override def postStop(): Unit = log.info("LuxtronikActor stopped")

  var parameters: Map[Int, Int] = Map()
  var calculations: Map[Int, Int] = Map()
  var visibilities: Map[Int, Int] = Map()

  log.info(s"Connecting to [${Configuration.ip}:${Configuration.port}]...")
  val socket = new Socket(Configuration.ip, Configuration.port)
  val dataIn = new DataInputStream(socket.getInputStream)
  val dataOut = new DataOutputStream(socket.getOutputStream)

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  RootActor.system.scheduler.schedule(0 seconds, Configuration.updateInterval, self, ReadCalculation)

  override def receive: Receive = {
    case ReadParameters => {
      parameters = readData(3003)
    }

    case ReadCalculation => {
      calculations = readData(3004)

      if(log.isDebugEnabled) {
        calculations.toSeq.sortBy(_._1).foreach(p =>
          log.debug(s"${p._1} -> ${p._2}")
        )
      }

      log.info(s"Vorlauftemp. Heizkreis: [${calculations(HEATING_CIRCUIT_FLOW) / 10.0}]°C")
      log.info(s"Rücklauftemp. Heizkreis Ist: [${calculations(HEATING_CIRCUIT_RETURN_CURRENT) / 10.0}]°C")
      log.info(s"Rücklauftemp. Heizkreis Soll: [${calculations(HEATING_CIRCUIT_RETURN_TARGET) / 10.0}]°C")
      log.info(s"Warmwasser ist: [${calculations(SERVICE_WATER_CURRENT) / 10.0}]°C")
      log.info(s"Warmwasser soll: [${calculations(SERVICE_WATER_TARGET) / 10.0}]°C")
      log.info(s"Aussentemperatur: [${calculations(TEMP_AMBIENT) / 10.0}]°C")
      log.info(s"Betriebszustand: [${calculations(OPERATING_STATUS)}]")
      log.info(s"Betriebsstunden Wärmepumpe: [${calculations(OPERATION_HOURS_HEATING_PUMP) / 3600.0}]")
    }

    case ReadVisibility => {
      visibilities = readData(3005)
    }
  }

  private def readData(id: Int): Map[Int, Int] = {
    log.debug("Flushing input...")
    while (dataIn.available > 0) dataIn.readByte

    log.debug(s"Reading Data [$id]...")
    dataOut.writeInt(id)
    dataOut.writeInt(0)
    dataOut.flush()

    if (dataIn.readInt != id) {
      log.debug("Didn't got correct reply...")
    } else if (id == 3004) {
      // Reading status sent first when reading calculations
      dataIn.readInt
    }

    val len = dataIn.readInt
    if (len <= 0) {
      log.info("No data found!")
    } else {
      log.debug(s"Got data #[$len]")
    }

    val data = (0 until len).map(_ -> dataIn.readInt)

    data.toMap
  }
}