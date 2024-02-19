# Service Registration and Discovery
## Spring-cloud-netflix

1. 만드는 건 Spring Project 만들듯이 하고 dependency에 Eureka Server를 추가해주면 된다.
2. 유레카 서버 역할을 하기 위해서는 서버의 자격으로 등록을 해주어야 한다.

   그 작업을 해주는 것이 `@EnableEurekaServer` 라는 어노테이션이다. → 서비스 디스커버리로서 기동한다.

   이런 어노테이션 정보를 Spring Boot가 초기 기동되면서 다 긁어모아서 메모리 저장하고 있다가 필요한 작업대로 순차적으로 진행.

3. application.yml 파일에 설정을 바꾸어준다.

```yaml
server:
  port: 8761

spring:
  application:
    name: discoveryservice

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

- 각각의 마이크로 서비스에 고유한 아이디를 부여해야 되는데 그 아이디의 역할로써 어플리케이션 네임이라는 것을 등록
- eureka 설정 자체 ⇒ 중요!
- 유레카 클라이언트 설정하는데, 서버를 설정하는데 왜 클라이언트 설정이 필요한가?
    - 유레카 라이브러리가 포함된 채 스프링 부트가 기동이 되면 기본적으로 유레카 클라이언트 역할로서 어딘가에다 등록하는 작업을 시도하게 된다.
    - 그중에서 `register-with-eureka` 라는 설정과 `fetch-registry` 라는 두 가지 설정값은 설정하지 않으면 true로 설정이 됨.
    - 기본적으로 현재 작업하고 있는 것을 클라이언트 역할로 전화번호부에 등록하듯이 자신의 정보를 자신한테 등록하는 의미가 없는 작업이 된다. 
    - 그래서 false로 지정.
    - (즉, 유레카 서버 자체는 기동을 하되 자기 자신의 정보를 외부에 있는 다른 마이크로 서비스가 유레카 서버로부터 어떤 정보를 주고받는 그런 역할을 할 필요가 없기 때문에 자기 자신을 등록하지 않음.)
    - 서버로서 기동만 되어 있으면 된다.

### 참고
[Service Discovery 스프링 공식 문서](https://spring.io/guides/gs/service-registration-and-discovery)
[Spring-cloud-netflix](https://github.com/spring-cloud/spring-cloud-netflix)