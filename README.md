Microservice Showcase
=====================

Showcase code from tech talk "Key problems of Microservice Architecture"

Usage
---------------------

Prerequisites:

* Java 8 or higher
* Groovy 2.3 or higher
* Apache Zookeeper 3.4.6 or higher

Configuration -- update content of settings.properties with local IPs.


Services can be started by launching Groovy scripts from command line:

    groovy me/sokolenko/microservice/nav/NavigationService.groovy
    groovy me/sokolenko/microservice/domain/ChallengeService.groovy
    groovy me/sokolenko/microservice/usr/UserService.groovy

