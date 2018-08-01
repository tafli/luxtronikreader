CREATE TABLE `heating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `circuit_flow` double NOT NULL,
  `circuit_return_current` double NOT NULL,
  `circuit_return_target` double NOT NULL,
  `temp_ambient` double NOT NULL,
  `service_water_current` double NOT NULL,
  `service_water_target` double NOT NULL,
  `operation_time_pump` int(11) NOT NULL,
  `operation_time_heating` int(11) NOT NULL,
  `operation_time_service_water` int(11) NOT NULL,
  `operation_since` int(11) NOT NULL,
  `operation_status` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`created_at`)
)