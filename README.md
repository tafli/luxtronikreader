# Luxtronik Reader

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

A simple program that reads data from a luxtronik2 heat pump.

Following data is stored into a database:

- Heating circuit flow temperature
- Heating circuit return temperature current
- Heating circuit return temperature target
- Ambient temperature
- Service water target temperature
- Service water current temperature
- Pump operation time
- Heat pump operation time
- Service water operation time
- Operating time

## Configuration

A custom configuration file must be loaded to apply your configuration:

```bash
./bin/luxtronicreader -Dconfig.file=/path/to/your/application.conf
```

### Example

```ini
luxtronik.ip = "192.168.1.123"
luxtronik.port = 8888
luxtronik.updateInterval = "1 minute"
luxtronik.db.schema = "myschema"
luxtronik.db.table = "heating"

db.default.driver = "com.mysql.cj.jdbc.Driver"
db.default.url = "jdbc:mysql://mydatabase/luxtronik"
db.default.user = "luxtronik"
db.default.password = "Y0uD0ntR3m3mb3r"
```

## Database

The database table default name is **heating**.

```sql
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
```

## Luxtronik 2 protocol

The protocol of the Luxtronik 2 can be found here (german): https://www.loxwiki.eu/display/LOX/Luxtronik+2