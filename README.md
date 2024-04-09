# spring-microservice

## 전체 아키텍처 구성

![image](https://github.com/Jake-huen/siren-order-microservice/assets/57055730/f2e84fa1-a03a-4ad4-b184-af294a68c4b4)


## 배포 계획

![image](https://github.com/Jake-huen/spring-microservice/assets/57055730/43ee4535-a942-47af-903a-c7539e856d68)


## MSA 기술 스택

### 마이크로 서비스 개발
- Spring Boot
- Spring Data JPA
- Spring Security, JWT

### Message Bus, 데이터 동기화
- Kafka - 데이터베이스 동기화
- RabbitMQ - Spring Config 설정 Message Bus

### Container
- Docker
- Kubernetes

### API Gateway
- Spring Cloud Gateway

### Database
- H2 Database
- MySQL(MariaDB)

### Service Mesh
- 모니터링

- 구성
	- Spring Cloud Eureka

- 분산 추적
  	- Zipkin
	- CircuitBreaker
	- Resilence4J

- Configuration
	- Spring Cloud Config
		- Git

- 서비스간 통신
	- FeignClient

### 모니터링
- Grafana
- Prometheus
