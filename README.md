# Smart Choice

## Project status

https://sonarcloud.io/organizations/nguyenmanhdang90/projects

#### smartchoice-fapi: 

[![build fapi](https://github.com/nguyenmanhdang90/nab-smartchoice/actions/workflows/build.yml/badge.svg)](https://github.com/nguyenmanhdang90/nab-smartchoice/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=nguyenmanhdang90_nab-smartchoice&metric=alert_status)](https://sonarcloud.io/dashboard?id=nguyenmanhdang90_nab-smartchoice)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=nguyenmanhdang90_nab-smartchoice&metric=coverage)](https://sonarcloud.io/dashboard?id=nguyenmanhdang90_nab-smartchoice)

#### smartchoice-log:

[![build logs](https://github.com/nguyenmanhdang90/nab-smartchoice/actions/workflows/build%20logs.yml/badge.svg)](https://github.com/nguyenmanhdang90/nab-smartchoice/actions/workflows/build%20logs.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=smartchoice-log&metric=alert_status)](https://sonarcloud.io/dashboard?id=smartchoice-log)

#### smartchoice-crawler: 

[![build crawler](https://github.com/nguyenmanhdang90/nab-smartchoice/actions/workflows/build%20crawler.yml/badge.svg)](https://github.com/nguyenmanhdang90/nab-smartchoice/actions/workflows/build%20crawler.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=smartchoice-crawler-2&metric=alert_status)](https://sonarcloud.io/dashboard?id=smartchoice-crawler-2)

*Only smartchoice-fapi is well-coverd by unit test, because it has the most function and business feature. Also by the scope of this test, I decided not take too much time try to cover all line of code for the others project. But the business code is well test by unit test*

*There are several issue with security hotspot scanned by sonarqube, I have aknowleadge them all and decided to by pass them since not have enought time and resource to handle them all, Ex: store sonar login token in source code,...*

---
## Development stack:

 1. [IntelliJ community](https://www.jetbrains.com/idea/)
 1. Java 8
 1. Docker
 1. Spring framework
 1. MySql
 1. maven
 1. liquibase

## CI tool

 1. Github actions
 1. Sonarcloud

---

## infrastructure

<img alt="infrastructure.svg" src="./documents/infrastructure.svg">

## smartchoice-fapi: 
 - front-end API.
 - Have two modules which are api and db.
 - api contain REST API, also services and security.
 - db contain entities and repository.

## smartchocie-crawler
 - Simple spring application in charge of make request to third party.
 - communication by REST API
 - For further expand, this project can modify into clonable by the scale of the whole system

## smartchoice-log
 - have a service and a entities to store user action in to db
 - communication by REST API

=> All of these services is separated and run independence by docker, communication with each other by REST API

---
## Database structure

<img alt="schema.svg" src="./documents/schema.svg">

## supplier
 - Store list of already config supplier, ex: lazada, shopee

## product

 - Store list of already config product with product code map to third party, ex: apple iphone 1 with product code P1

## supplier_product

 - Store an many to many relation ship between supplier and product, also act as a cache layer for smartchoice-fapi with expiry time.
 - Data get from third party

---

## Work flow of get price from third party

<img alt="workflow.svg" src="./documents/workflow.svg">

---
## Technical Practice being apply

 - **lombok** to get risk of boiler plate code
 - **Async** call to logs to reduce the impact of loging system with business system
 - **liquibase** to manage database
 - CI with **github actions**
 - Code scan with **sonarqube**
 - **Docker**
 - smartchoice-db also act as a **cache layer**to prevent sent too many request to third party
 - **Microservice** and **modularization** are applied

## How to start development

 - make sure you are using intellJ or other IDE can acept `.editorconfig` file and have lombok configured
 - java 8 is installed
 - Docker and docker compose are installed

Start to develop

 1. bring up mysql: run `/smartchoice-fapi/scripts/start.sh`
 1. modify the resource of `smartchoice-fapi/src/main/resources/application.yml` to `jdbc:mysql://<MYSQLIP>:3306/` (usually localhost)
 1. modify the resource `smartchoice-log/src/main/resources/application.yml` to `jdbc:mysql://<MYSQLIP>:3306/` (usually localhost)
 1. Start smartchoice-crawler
 1. Start smartchoice-fapi
 1. Start smartchoice-log
 1. Default username/password for authentication is inside `postman` script

 
## How to run
 1. Make sure **docker** and **docker-compose** is installed
 1. download package from https://github.com/nguyenmanhdang90/nab-smartchoice/releases
 1. run `preload.sh`
 1. run `start_mysql.sh`
 1. copy ip address of mysql and put it in `docker-compose-logs.yml` and `docker-conpose-fapi.yml`(2 places, see pictures below)
    <img alt="mysql_ip.svg" src="./documents/mysql_ip.svg">
 1. run `start_crawler.sh`
 1. copy ip address of crawler and put it in `docker-conpose-fapi.yml`(see pictures below)
    <img alt="crawlerip.svg" src="./documents/crawlerip.svg">
 1. run `start_log.sh`
 1. copy ip address of logs and put it in `docker-conpose-fapi.yml`(see pictures below)
    <img alt="logip.svg" src="./documents/logip.svg">
 1. run `start_fapi.sh` and wait up to 1-2 minutes (try `docker logs smartchoice-fapi` to know when its ready)
 1. load test api from `postman` folder to postman
 1. connect to db with details: `jdbc:mysql://localhost:3306/root?allowpublickeyretrieval=true&usessl=false` (username: root; password: Root@2021) to see more details
