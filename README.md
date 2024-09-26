# Melita-CRM Spring Boot Microservice (AMQP Message Consumer)

Run RabbitMQ on Docker using command:
* `docker run --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:3-management`

Create IntelliJ Configuration to run `MessageConsumer.java` using Environment > VM Options 
* `-Dspring.cloud.config.enabled=true`

The component in the following git repo,
* https://github.com/nzneil22/Melita-Config-Server

must already be running since it provides the cloud configuration for this component.

Components from the following repositories must also be running for the full workflow:
* https://github.com/nzneil22/Melita-Rest-Server
* https://github.com/nzneil22/Melita-Amqp-Server
