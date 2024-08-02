# spring-microservice

Spring Cloud와 쿠버네티스를 이용한 '커피 사이렌 오더' 마이크로서비스 프로젝트

https://www.notion.so/MSA-0872b4efb7204ceb9b5dc3193b4c5618

## 전체 아키텍처 구성

![image](https://github.com/Jake-huen/siren-order-microservice/assets/57055730/f2e84fa1-a03a-4ad4-b184-af294a68c4b4)


## 배포 계획

![image](https://github.com/Jake-huen/siren-order-microservice/assets/57055730/0c869979-8372-4597-9d96-0e38b959f14c)



## MSA 기술 스택

### API Gateway
- Spring Cloud Gateway

### Service Discovery
- Spring Cloud Netflix Eureka

### 마이크로 서비스 개발
- Spring MVC, JPA : User-service, Counter-service, Store-service
- Spring Security, JWT : Config-service

### Message Bus, 데이터 동기화
- Kafka, Zookeeper : 서비스 간 메시지 produce, consume
- Kafka UI : Kafka 동작 시각화
- RabbitMQ : Spring Config 설정 Message Bus

### Container
- Docker : RabbitMQ, Prometheus, Grafana
- Docker-Compose : Kafka, Zookeeper, Kafka-UI
- Kubernetes
  - Deployment : apigateway-service, config-service, user-service, counter-service, store-service, frontend, mysql, zipkin
  - Service
  	- ClusterIP : user-service, counter-service, store-service
  	- NodePort : apigateway-service, config-service, discovery-service, frontend, mysql-svc, zipkin
  - Persistent Volume : MySQL 

### API Gateway
- Spring Cloud Gateway

### Database
- H2 Database : User-service, Store-service
- MySQL + PV, PVC : Counter-service

### MSA 구성
- 구성
	- Spring Cloud Eureka

- 분산 추적
  	- Zipkin
	- CircuitBreaker
	- Resilence4J

- Configuration
	- Spring Cloud Config
		- Private Git

- 서비스간 통신
	- FeignClient

### 모니터링
- Grafana
- Prometheus
