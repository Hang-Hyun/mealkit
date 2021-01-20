
밀키트 판매

본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.
이는 클라우드 네이티브 애플리케이션의 개발에 요구되는 체크포인트들을 통과하기 위한 예시 답안을 포함합니다.
- 체크포인트 : https://workflowy.com/s/assessment-check-po/T5YrzcMewfo4J6LW


# Table of contents

- [예제 - 밀키트판매](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현:](#구현-)
     1. SAGA
     2. CQRS
     3. Correlation
     4. Req/Res
  - [운영](#운영)
  
     5. Gateway
     6. Deploy
     7. CB
     8. HPA
     9. Readiness


# 서비스 시나리오


기능적 요구사항

1. 판매자가 상품을 등록한다.
2. 회원이 상품을 선택하여 주문한다.
3. 주문이 완료되면 고객에게 주문내역이 안내된다.
3. 고객이 결제하고, 결제완료되면 배송 요청된다.
4. 결제완료되면 재고수량 변경을 한다.
5. 회원이 배송완료처리하면 배송완료된다.
6. 배송상태가 변경될 때마다 고객에게 안내된다.
6. 고객이 주문 취소할 수 있다.
7. 주문취소되면 판매자에게 알림이 간다.
8. 판매자에게 알림이 가고 나면 결제가 취소된다.
9. 결제가 취소되면 배송이 취소 된다.
10. 배송이 취소되면 판매자에게 배송 취소 알림이 간다.
9. 판매자는 주문취소 요청내역을 조회가능하다.

비기능적 요구사항
1. 트랜잭션
    1. 결제가 되지 않은 주문건은 아예 거래가 성립되지 않아야 한다  Sync 호출
    1. 판매자에게 알림이 가지 않으면 주문취소가 되지 않는다. Sync 호출
    
1. 장애격리
    1. 상품관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
    1. 알림시스템이 과중되면 사용자를 잠시동안 받지 않고 주문 취소를 잠시후에 하도록 유도한다  Circuit breaker, fallback
1. 성능
    1. 판매자는 수시로 취소 요청내역을 마이페이지에서 확인할 수 있어야 한다  CQRS




## Event Storming 결과
* MSAEz 로 모델링한 이벤트스토밍 결과:  


### 완성된 1차 모형
![image](https://user-images.githubusercontent.com/75401920/105209557-50db5580-5b8d-11eb-9f05-3f46d6b2a068.png)


    - Marketing 서비스 및 셀러 View Model 추가

### 1차 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증

    - 고객이 상품을 선택하여 주문한다 
    - 주문하면 결제가 동시에 이뤄진다.
    - 주문이 완료되면 구매자에게 알림이 발송된다.
    - 결제가 이뤄지면 배송이 요청된다.
    - 결제가 이뤄지면 상품재고가 감소한다.
    - 배송상태가 변동되면 구매자에게 알림이 간다.
    - 배송이 요청되고 배송이 시작되면 주문상태가 배송시작으로 변경된다. 

![image](https://user-images.githubusercontent.com/75401920/105209753-88e29880-5b8d-11eb-8b57-30a1516dbaf8.png)

    - 고객이 주문을 취소할 수 있다 
    - 주문이 취소되면 판매자에게 알림이 간다. 
    - 알림이 발송되면 결제를 취소한다. 
    - 결제가 취소되면 배송을 취소한다.
    - 배송이 취소되면 주문상태가 배송취소로 변경된다. 




# 구현:

분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트로 구현하였다. 


1. 기능 테스트

주문생성

![image](https://user-images.githubusercontent.com/75401920/105197182-b0caff80-5b7f-11eb-9a07-f447b7fdc3f1.png)


주문 취소

![image](https://user-images.githubusercontent.com/75401920/105200054-c42b9a00-5b82-11eb-9bf0-3bbfdbc27c0c.png)


알림 확인(동기방식 및 Correaltion)

![image](https://user-images.githubusercontent.com/75401920/105200173-e4f3ef80-5b82-11eb-82be-8b91f905afa8.png)


결제 취소

![image](https://user-images.githubusercontent.com/75401920/105200332-14a2f780-5b83-11eb-8a2c-46a1699272d8.png)


배송 취소

![image](https://user-images.githubusercontent.com/75401920/105200462-33a18980-5b83-11eb-9912-d1d2c684b5d3.png)

재고 원복

![image](https://user-images.githubusercontent.com/75401920/105200683-74999e00-5b83-11eb-8cf1-dc29b2eb117b.png)



2. 셀러페이지 조회

![image](https://user-images.githubusercontent.com/75401920/105208363-f7bef200-5b8b-11eb-8a7d-086f95698335.png)



3. 알림 장애 시 주문취소 불가

![image](https://user-images.githubusercontent.com/75401920/105201230-fab5e480-5b83-11eb-9068-4801368a0967.png)



   

4. Gateway, Deploy

product 상품 등록 
 - LoadBalancer로 노출된 퍼블릭IP로 상품등록 API 호출

![image](https://user-images.githubusercontent.com/75401920/105191822-ddc8e380-5b7a-11eb-8211-3b3c5024fbfb.png)


애져에 배포되어 있는 상황 조회 kubectl get all

![image](https://user-images.githubusercontent.com/75401920/105196928-76f9f900-5b7f-11eb-859f-caf6ad584aaf.png)



5. Istio 적용 캡쳐

  - Istio테스트를 위해서 Marketing에 sleep 추가
  
![image](https://user-images.githubusercontent.com/75401920/105005616-e89b4f80-5a78-11eb-82cb-de53e5881e3f.png)

 - istio Virtual Service 생성

![image](https://user-images.githubusercontent.com/75401920/105209144-d90d2b00-5b8c-11eb-9d32-5a81e348c330.png)

![image](https://user-images.githubusercontent.com/75401920/105208750-656b1e00-5b8c-11eb-872e-dc9c51a327c7.png)

 - siege 테스트

![image](https://user-images.githubusercontent.com/75401920/105209299-06f26f80-5b8d-11eb-86bf-e4b0f27d32bc.png)


6. AutoScale Out


 - autoscale 적용

kubectl autoscale deployment order --cpu-percent=20 --min=1 --max=10

siege -c100 -t30S -v --content-type "application/json" 'http://10.0.22.136:8080/orders POST {"prodId": 1, "qty":5}'

 - AutoScale적용 후 seige를 통해서 부하 테스트 시  order pod 개수가 증가함

![image](https://user-images.githubusercontent.com/75401920/105206906-394e9d80-5b8a-11eb-90ea-463a33781b5b.png)


![image](https://user-images.githubusercontent.com/75401920/105207003-56836c00-5b8a-11eb-804a-72fea60f83b3.png)


7. Readiness Probe

 - readiness probe 적용전

siege -c1 -t2000S -v --content-type "application/json" 'http://10.0.127.214:8080/orders POST {"prodNm": "1001", "qty":5}'

kubectl set image deploy product=skcc06.azurecr.io/product:v2

![image](https://user-images.githubusercontent.com/75401920/105202941-da872500-5b85-11eb-84d1-04398aecf47d.png)

 - readiness probe 적용

![image](https://user-images.githubusercontent.com/75401920/105203175-2a65ec00-5b86-11eb-9253-ea05b1f80336.png)

 - 적용 후 테스트 

![image](https://user-images.githubusercontent.com/75401920/105203589-a3fdda00-5b86-11eb-8d81-e07e2dd53d64.png)

![image](https://user-images.githubusercontent.com/75401920/105203774-d8719600-5b86-11eb-8acf-d98d00424c2c.png)



9. configmap 

 - configmap 생성

![image](https://user-images.githubusercontent.com/75401920/105248814-6f0b7a80-5bba-11eb-835c-26bc4bfa3609.png)


 - configmap 적용

![image](https://user-images.githubusercontent.com/75401920/105248860-8480a480-5bba-11eb-9e09-bfc48fab4ec1.png)

 - configmap 적용된 모습

![image](https://user-images.githubusercontent.com/75401920/105249332-5059b380-5bbb-11eb-986d-e86f7143424c.png)


10. Liveness probe

 - Liveness probe 적용
   실패를 확인하기 위해서 포트를 8081로 임의 변경함

![image](https://user-images.githubusercontent.com/75401920/105214182-05c44100-5b93-11eb-9377-4cd46b7f4964.png)

 - 실패로 인해서 restart 수 증가하는 모습 확인 가능
 
![image](https://user-images.githubusercontent.com/75401920/105214090-e3322800-5b92-11eb-9230-3933cb039281.png)




---------------------------------------------------------------------------------------------------------------------------------


주문생성

![image](https://user-images.githubusercontent.com/75401920/105197182-b0caff80-5b7f-11eb-9a07-f447b7fdc3f1.png)

주문확인

![image](https://user-images.githubusercontent.com/75401920/105197288-ca6c4700-5b7f-11eb-9d21-28bdbd145010.png)


결제확인

![image](https://user-images.githubusercontent.com/75401920/105197401-ee2f8d00-5b7f-11eb-871d-9fabd7126788.png)

배송확인

![image](https://user-images.githubusercontent.com/75401920/105197568-14edc380-5b80-11eb-9b3d-d7e353322479.png)

상품재고 확인

![image](https://user-images.githubusercontent.com/75401920/105197656-3058ce80-5b80-11eb-922b-76de46f176e6.png)


알림 확인

![image](https://user-images.githubusercontent.com/75401920/105197980-8c235780-5b80-11eb-8af5-769200160960.png)





