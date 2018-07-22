package tafli.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import tafli.models.HeatingData

object DbActor {
  def props(): Props = Props(new DbActor)

  val actor: ActorRef = RootActor.system.actorOf(props())

  case class SaveCalculatedData(data: HeatingData)


}

class DbActor extends Actor with ActorLogging {

  override def receive: Receive = {
//    case SaveStationData(id, data) =>
//      HeatingData.create(id,
//        stationType = 0,
//        temperature = data.temperature / 10.0,
//        humidity = data.humidity,
//        windSpeed = Option(data.windSpeed / 10.0),
//        gustSpeed = Option(data.gustSpeed / 10.0),
//        rain = Option(data.rain / 10.0),
//        windDirection = Option(data.windDirection),
//        batteryLow = Option(data.batteryLow))
    case _ =>
  }
}