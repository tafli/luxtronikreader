package tafli.actors

import java.time.ZonedDateTime

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import tafli.actors.DbActor.SaveCalculatedData
import tafli.database.HeatingDAO
import tafli.models.HeatingData

object DbActor {
  def props(): Props = Props(new DbActor)

  val actor: ActorRef = RootActor.system.actorOf(props())

  case class SaveCalculatedData(data: HeatingData)

}

class DbActor extends Actor with ActorLogging {

  override def receive: Receive = {
    case SaveCalculatedData(data) =>
      log.debug("Saving heating data ...")

      HeatingDAO.create(
        data.circuitFlow,
        data.circuitReturnCurrent,
        data.circuitReturnTarget,
        data.tempAmbient,
        data.serviceWaterTarget,
        data.serviceWaterCurrent,
        data.operationTimePump,
        data.operationTimeHeating,
        data.operationTimeServiceWater,
        data.operationSince,
        data.operationStatus,
        ZonedDateTime.now
      )

    case _ => log.warning("Received unknown message ...")
  }
}