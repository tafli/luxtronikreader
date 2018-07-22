package tafli.models

import java.time.ZonedDateTime

import scalikejdbc._

case class HeatingData(createdAt: ZonedDateTime,
                       heatingPreLim: Double,
                       heatingPostLim: Double,
                       heatingOpHours: Double,
                       waterTempIs: Double,
                       waterTempMust: Double,
                       waterOpHours: Double,
                      ) {
//  def destroy()(implicit session: DBSession = HeatingData.autoSession): Int = HeatingData.destroy(this)(session)
}


object HeatingData extends SQLSyntaxSupport[HeatingData] {
  // in °C, value / 10
  val HEATING_CIRCUIT_FLOW = 10
  val HEATING_CIRCUIT_RETURN_CURRENT = 11
  val HEATING_CIRCUIT_RETURN_TARGET = 12

  // in °C, value / 10
  val TEMP_AMBIENT = 15

  // in °C, value / 10
  val SERVICE_WATER_CURRENT = 17
  val SERVICE_WATER_TARGET = 18

  // in seconds
  val OPERATION_HOURS_HEATING_PUMP = 63
  val OPERATION_HOURS_HEATING = 64
  val OPERATION_HOURS_SERVICE_WATER = 65

  /*
  status values:
  0 = heating
  1 = service water
  2 = swimming pool / Photovoltaik
  3 = EVU
  4 = defrost
  5 = standby
  6 = heating ext. energy source
  7 = cooling
   */
  val OPERATING_STATUS = 80
  val OPERATING_SINCE = 67

//  val d = HeatingData.syntax("d")
//
//  override val tableName = "data"
//
//  def apply(d: SyntaxProvider[HeatingData])(rs: WrappedResultSet): HeatingData = apply(d.resultName)(rs)
//
//  def apply(d: ResultName[HeatingData])(rs: WrappedResultSet): HeatingData = new HeatingData(
//    stationId = rs.get(d.stationId),
//    stationType = rs.get(d.stationType),
//    temperature = rs.get(d.temperature),
//    humidity = rs.get(d.humidity),
//    windSpeed = rs.get(d.windSpeed),
//    gustSpeed = rs.get(d.gustSpeed),
//    rain = rs.get(d.rain),
//    windDirection = rs.get(d.windDirection),
//    batteryLow = rs.get(d.batteryLow),
//    createdAt = rs.get(d.createdAt)
//  )
//
//  def create(
//              stationId: Int,
//              stationType: Int,
//              temperature: Double,
//              humidity: Int,
//              windSpeed: Option[Double] = None,
//              gustSpeed: Option[Double] = None,
//              rain: Option[Double] = None,
//              windDirection: Option[Int] = None,
//              batteryLow: Option[Boolean] = None)(implicit session: DBSession = autoSession): Unit = {
//    withSQL {
//      insert.into(HeatingData).namedValues(
//        column.stationId -> stationId,
//        column.stationType -> stationType,
//        column.temperature -> temperature,
//        column.humidity -> humidity,
//        column.windSpeed -> windSpeed,
//        column.gustSpeed -> gustSpeed,
//        column.rain -> rain,
//        column.windDirection -> windDirection,
//        column.batteryLow -> batteryLow
//      )
//    }.update.apply()
//  }
//
//  def destroy(entity: HeatingData)(implicit session: DBSession = autoSession): Int = {
//    withSQL {
//      delete.from(HeatingData).where.eq(column.id, entity.id)
//    }.update.apply()
//  }

}

