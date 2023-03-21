## Index  
  - Transaction
      - Transaction 기초
      - Spring Transaction  
  - Async  
      - Async Non Bloacking - Sync Blocking
      - Spring Async  

# 1. Transaction
## Transaction 기초
### 설명
트랜잭션(transaction)이란 "쪼갤 수 없는 업무 처리의 최소 단위"를 말한다.

#### 격리 수준 (Isolation Level) :
Transaction Isolation level을 나타내는 정보 다른 Transaction의 작업과 격리되는 정도를 지정한다.

1) *READ_UNCOMMITED (LEVEL0)* :  
Transaction 처리중인 혹은 아직 커밋되지 않은 데이터를 다른 Transaction이 읽는 것을 허용  

- ※  DIRTY READ : *위와 같이 다른 트랜잭션이 처리하는 작업이 완료 되지 않았는데도 다른 Transaction에서 볼 수 있는 현상을 말한다. READ_UNCOMMITED 격리 수준에서만 일어나는 현상*


2) *REPEATABLE_READ (LEVEL 2)* :  
Transaction이 완료될 때까지 SELECT 문장이 사용하는 모든 데이터에 SHARED_LOCK이 걸리므로 다른 사용자는 그 영역에 해당하는 데이터에 대한 수정이 불가능하다.  

- ※ *Exclusive lock(write transaction) : 어떤 Transaction에서 데이터를 변경하고자 할때 해당 Transaction이 완료될때까지 해당 테이블 혹은 레코드를 다른 트랜잭션에서 읽거나 쓰지 못하게 한다.*

- ※ *Shared lock (Read Transaction) : 리소스를 다른 사용자가 동시에 읽을 수 있게 하되 변경은 불가능하게 하는 것, 												즉 어떤 트랜잭션에서 데이터를 읽고자 할 때 다른 shared lock은 허용이 되지만 exclusive lock은 불가하다.*  

3) *SERIALIZABLE (LEVEL 3)* :  
완벽한 읽기 일관성 유지  
Transaction이 완료될 때까지 SELECT 문장이 사용하는 모든 데이터에 shared lock이 걸리므로 다른 사용자는 그 영역에 해당되는 데이터에 대한 수정 및 입력이 불가능하다.  

3) *DEFAULT* :  
따로 설정 안해주면 DB에 정의되있는 ISO 레벨을 따른다.  
  
  
#### 전파 (Propagation) : 
Transaction 전파 정도를 나타내는 정보이다. 트랜잭션 경계의 시작 지점에서 트랜잭션 전파 속성을 참조해서 해당 범위의 트랜잭션을 어떤 식으로 진행시킬지 정할 수 있다.  
<img src="img/propagation_1.png" alt="propagation" border="0">

#### 타임아웃 (Timeout) :  
트랜잭션 실행 한도 시간으로, 이 시간이 지나면 ROLLBACK 된다.

#### 읽기전용 (readOnly) :  
읽기 전용 트랜잭션인지에 대해 나타낸다.  
- ※ *readOnly가 true이면 Spring은 해당 Transaction의 flushMode를 true로 설정하게 된다.  
flush가 일어나지 않으면, COST절감이 가능하고 생성/수정/삭제가 일어나지 않기 때문에 스냅샷을 만들 필요가 없어 성능 상의 이점이 있다.*  

  
#### 경겨설정 전략
일반적으로 Tx의 시작과 종료는 Service Layer 내부 메소드에 달려있다. Tx의 경계를 설정하는 방법으로는 
1. 코드를 통해 임의적으로 결정하는 방법 -> 프로그래밍적 트랜잭션 (transaction v1 코드 참조)
2. AOP를 사용하여 지정하는 방법 -> 선언적 트랜잭션 방식 (transaction v2, v3.. 코드 참조)  
이 두가지로 크게 나눌 수 있다.

## Spring @Transactional  



> reference :  
https://taetaetae.github.io/2016/10/08/20161008/  
https://jeong-pro.tistory.com/94  


# 2. Async






