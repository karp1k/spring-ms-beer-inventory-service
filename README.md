# spring-ms-beer-inventory service

check the project page! [*see*](https://github.com/users/karp1k/projects/1)

firstly, please download [beer-common](https://github.com/karp1k/spring-ms-beer-common) and do **clean-install** mvn goals

secondly, you will need to download the BOM project [ms-brewery-bom](https://github.com/karp1k/spring-ms-brewery-bom) 
and don't forget to **install** it to the local repository

Then, you will need to download [activemq-artemis](https://github.com/vromero/activemq-artemis-docker) broker to communicate services among themselves , after the MQ broker server up and running, set the correct credentials in the *application.properties*

tomcat port: 8082

related repo's: 

- [beer-common](https://github.com/karp1k/spring-ms-beer-common)

- [beer-order-service](https://github.com/karp1k/spring-ms-beer-order-service)

- [beer-service](https://github.com/karp1k/spring-ms-beer-service)

- [brewery-bom](https://github.com/karp1k/spring-ms-brewery-bom)

- [brewery-eureka](https://github.com/karp1k/spring-ms-brewery-eureka)

- [brewery-gateway](https://github.com/karp1k/spring-ms-brewery-gateway)
