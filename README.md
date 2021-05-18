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
 - Have two module which is api and db.
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

## Start development process:


## How to run
