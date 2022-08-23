# Melita-Message-Consumer Spring Boott Microservice (AMQP Message Consumer)

Create IntelliJ Configuration to run `MessageConsumer.java` using Environment > VM Options 
* `-Dspring.cloud.config.enabled=true`

The component in the following git repo must already be running since it provides the cloud configuration for this component:
* https://github.com/nzneil22/Melita-Config-Server


Components fom the following repositories must also be running for the full workflow:
* https://github.com/nzneil22/Melita-Rest-Server
* https://github.com/nzneil22/Melita-Amqp-Server
