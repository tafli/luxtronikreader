package tafli.database

import java.time.ZonedDateTime

import scalikejdbc._
import tafli.Configuration

case class HeatingDAO(
  id: Int,
  circuitFlow: Double,
  circuitReturnCurrent: Double,
  circuitReturnTarget: Double,
  tempAmbient: Double,
  serviceWaterCurrent: Double,
  serviceWaterTarget: Double,
  operationTimePump: Int,
  operationTimeHeating: Int,
  operationTimeServiceWater: Int,
  operationSince: Int,
  operationStatus: Int,
  createdAt: ZonedDateTime) {

  def save()(implicit session: DBSession = HeatingDAO.autoSession): HeatingDAO = HeatingDAO.save(this)(session)

  def destroy()(implicit session: DBSession = HeatingDAO.autoSession): Int = HeatingDAO.destroy(this)(session)

}


object HeatingDAO extends SQLSyntaxSupport[HeatingDAO] {

  override val schemaName = Some(Configuration.dbSchema)

  override val tableName = Configuration.dbTable

  override val columns = Seq("id", "circuit_flow", "circuit_return_current", "circuit_return_target", "temp_ambient", "service_water_current", "service_water_target", "operation_time_pump", "operation_time_heating", "operation_time_service_water", "operation_since", "operation_status", "created_at")

  def apply(h: SyntaxProvider[HeatingDAO])(rs: WrappedResultSet): HeatingDAO = apply(h.resultName)(rs)
  def apply(h: ResultName[HeatingDAO])(rs: WrappedResultSet): HeatingDAO = new HeatingDAO(
    id = rs.get(h.id),
    circuitFlow = rs.get(h.circuitFlow),
    circuitReturnCurrent = rs.get(h.circuitReturnCurrent),
    circuitReturnTarget = rs.get(h.circuitReturnTarget),
    tempAmbient = rs.get(h.tempAmbient),
    serviceWaterCurrent = rs.get(h.serviceWaterCurrent),
    serviceWaterTarget = rs.get(h.serviceWaterTarget),
    operationTimePump = rs.get(h.operationTimePump),
    operationTimeHeating = rs.get(h.operationTimeHeating),
    operationTimeServiceWater = rs.get(h.operationTimeServiceWater),
    operationSince = rs.get(h.operationSince),
    operationStatus = rs.get(h.operationStatus),
    createdAt = rs.get(h.createdAt)
  )

  val h = HeatingDAO.syntax("h")

  override val autoSession = AutoSession

  def find(createdAt: ZonedDateTime, id: Int)(implicit session: DBSession = autoSession): Option[HeatingDAO] = {
    withSQL {
      select.from(HeatingDAO as h).where.eq(h.createdAt, createdAt).and.eq(h.id, id)
    }.map(HeatingDAO(h.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[HeatingDAO] = {
    withSQL(select.from(HeatingDAO as h)).map(HeatingDAO(h.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(HeatingDAO as h)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[HeatingDAO] = {
    withSQL {
      select.from(HeatingDAO as h).where.append(where)
    }.map(HeatingDAO(h.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[HeatingDAO] = {
    withSQL {
      select.from(HeatingDAO as h).where.append(where)
    }.map(HeatingDAO(h.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(HeatingDAO as h).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    circuitFlow: Double,
    circuitReturnCurrent: Double,
    circuitReturnTarget: Double,
    tempAmbient: Double,
    serviceWaterCurrent: Double,
    serviceWaterTarget: Double,
    operationTimePump: Int,
    operationTimeHeating: Int,
    operationTimeServiceWater: Int,
    operationSince: Int,
    operationStatus: Int,
    createdAt: ZonedDateTime)(implicit session: DBSession = autoSession): HeatingDAO = {
    val generatedKey = withSQL {
      insert.into(HeatingDAO).namedValues(
        column.circuitFlow -> circuitFlow,
        column.circuitReturnCurrent -> circuitReturnCurrent,
        column.circuitReturnTarget -> circuitReturnTarget,
        column.tempAmbient -> tempAmbient,
        column.serviceWaterCurrent -> serviceWaterCurrent,
        column.serviceWaterTarget -> serviceWaterTarget,
        column.operationTimePump -> operationTimePump,
        column.operationTimeHeating -> operationTimeHeating,
        column.operationTimeServiceWater -> operationTimeServiceWater,
        column.operationSince -> operationSince,
        column.operationStatus -> operationStatus,
        column.createdAt -> createdAt
      )
    }.updateAndReturnGeneratedKey.apply()

    HeatingDAO(
      id = generatedKey.toInt,
      circuitFlow = circuitFlow,
      circuitReturnCurrent = circuitReturnCurrent,
      circuitReturnTarget = circuitReturnTarget,
      tempAmbient = tempAmbient,
      serviceWaterCurrent = serviceWaterCurrent,
      serviceWaterTarget = serviceWaterTarget,
      operationTimePump = operationTimePump,
      operationTimeHeating = operationTimeHeating,
      operationTimeServiceWater = operationTimeServiceWater,
      operationSince = operationSince,
      operationStatus = operationStatus,
      createdAt = createdAt)
  }

  def batchInsert(entities: collection.Seq[HeatingDAO])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'circuitFlow -> entity.circuitFlow,
        'circuitReturnCurrent -> entity.circuitReturnCurrent,
        'circuitReturnTarget -> entity.circuitReturnTarget,
        'tempAmbient -> entity.tempAmbient,
        'serviceWaterCurrent -> entity.serviceWaterCurrent,
        'serviceWaterTarget -> entity.serviceWaterTarget,
        'operationTimePump -> entity.operationTimePump,
        'operationTimeHeating -> entity.operationTimeHeating,
        'operationTimeServiceWater -> entity.operationTimeServiceWater,
        'operationSince -> entity.operationSince,
        'operationStatus -> entity.operationStatus,
        'createdAt -> entity.createdAt))
    SQL("""insert into heating(
      circuit_flow,
      circuit_return_current,
      circuit_return_target,
      temp_ambient,
      service_water_current,
      service_water_target,
      operation_time_pump,
      operation_time_heating,
      operation_time_service_water,
      operation_since,
      operation_status,
      created_at
    ) values (
      {circuitFlow},
      {circuitReturnCurrent},
      {circuitReturnTarget},
      {tempAmbient},
      {serviceWaterCurrent},
      {serviceWaterTarget},
      {operationTimePump},
      {operationTimeHeating},
      {operationTimeServiceWater},
      {operationSince},
      {operationStatus},
      {createdAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: HeatingDAO)(implicit session: DBSession = autoSession): HeatingDAO = {
    withSQL {
      update(HeatingDAO).set(
        column.id -> entity.id,
        column.circuitFlow -> entity.circuitFlow,
        column.circuitReturnCurrent -> entity.circuitReturnCurrent,
        column.circuitReturnTarget -> entity.circuitReturnTarget,
        column.tempAmbient -> entity.tempAmbient,
        column.serviceWaterCurrent -> entity.serviceWaterCurrent,
        column.serviceWaterTarget -> entity.serviceWaterTarget,
        column.operationTimePump -> entity.operationTimePump,
        column.operationTimeHeating -> entity.operationTimeHeating,
        column.operationTimeServiceWater -> entity.operationTimeServiceWater,
        column.operationSince -> entity.operationSince,
        column.operationStatus -> entity.operationStatus,
        column.createdAt -> entity.createdAt
      ).where.eq(column.createdAt, entity.createdAt).and.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: HeatingDAO)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(HeatingDAO).where.eq(column.createdAt, entity.createdAt).and.eq(column.id, entity.id) }.update.apply()
  }

}
