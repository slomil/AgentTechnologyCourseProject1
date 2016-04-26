# AgentsCourseProject

## System Architecture

![servers_diagram](https://cloud.githubusercontent.com/assets/9393829/14836814/4f88643a-0c13-11e6-81f3-5ac383dee9a2.png)

## Configuration
Start JBoss with standalone-agents.xml
<br />
```
-Djboss.server.default.config=standalone-agents.xml
```

#### Required
For slave nodes add
```
-Dmaster=x.x.x.x:port (Master Node IP Address and Port)
```

#### Optional
Set Local IP Address and port 
```
-Dlocal=x.x.x.x:port
```
Set Host Name
```
-Dalias=alias_value
```

### Example
```bash
# Master Node
.\standalone.bat -D"jboss.server.default.config"=standalone-agents.xml -Dlocal="192.168.0.1:8080" -D"alias=MasterNode"

# Slave Node 1
.\standalone.bat -D"jboss.server.default.config"=standalone-agents.xml -D"jboss.socket.binding.port-offset"=100 -Dmaster="192.168.0.1:8080" -Dalias=SlaveNode1

# Slave Node 2
.\standalone.bat -D"jboss.server.default.config"=standalone-agents.xml -D"jboss.socket.binding.port-offset"=200 -Dmaster="192.168.0.1:8080"

```
