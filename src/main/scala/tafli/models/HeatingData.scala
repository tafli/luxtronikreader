package tafli.models

case class HeatingData(circuitFlow: Double,
                       circuitReturnCurrent: Double,
                       circuitReturnTarget: Double,
                       tempAmbient: Double,
                       serviceWaterTarget: Double,
                       serviceWaterCurrent: Double,
                       operationTimePump: Int,
                       operationTimeHeating: Int,
                       operationTimeServiceWater: Int,
                       operationSince: Int,
                       operationStatus: Int)


object HeatingData {
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
  val OPERATION_TIME_HEATING_PUMP = 63
  val OPERATION_TIME_HEATING = 64
  val OPERATION_TIME_SERVICE_WATER = 65

  val OPERATING_SINCE = 67

  /*
  status values:
  0 = heating
  1 = service water
  2 = swimming pool / photovoltaik
  3 = EVU
  4 = defrost
  5 = standby
  6 = heating ext. energy source
  7 = cooling
   */
  val OPERATING_STATUS = 80
}

