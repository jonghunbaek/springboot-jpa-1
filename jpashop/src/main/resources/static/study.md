# JPA 활용1
### 1.JPA, DB 설정
+ Entity매니저를 통한 모든 데이터 변경은 트랜잭션 안에서 이뤄져야 한다.
+ 같은 영속성 컨텍스트에서 아이디가 같으면 같은 객체다.

### 2. 도메인 분석, 설계
+ 양방향 연관관계보단 단방향을 사용하는 것이 유리
+ 상속관계 매핑 3가지 방법(1. 싱글테이블 전략, 2, joined(정교), tableperclass(클래스별 테이블 생성)) - 부모클래스에 @Inheritance, @DiscriminatorColumn, @DiscriminatorValue
+ 양방향관계에선 연관관계 주인을 정해줘야한다.
+ enum 타입엔 @Enumerated 어노테이션을 붙여주고 EnumType.STRING으로 ORDINAL은 숫자로 매기기 때문에 중간에 값이 추가될 경우 문제가 발생
+ 엔티티에는 setter사용을 지양하고 역할이 명확한 메서드를 생성해서 사용하자
+ 모든 연관관계는 지연로딩으로 설정(즉시로딩은 예측이 어렵고, 어떤 쿼리가 실행될지 트레이싱이 어렵다. 연관된 엔티티를 함께 조회해야하는 경우 fetch join 또는 엔티티 그래프 기능을 사용
+ xToOne관계는 기본이 즉시로딩이라 설정을 바꿔줘야 함
+ 컬렉션은 필드에서 바로 초기화하는 것이 안전, null에 안전, 하이버네이트가 컬렉션을 한번 감싸기 때문에 절대 컬렉션에 대한 수정은 금지
+ cascade = CascadeType.ALL - 영속화 전파
+ 연관관계 메서드를 사용하자 - 양방향 연관관계인 경우, 아래와 같은 식으로 하나의 메서드에서 값을 처리 
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
	
### 3. 애플리케이션 구현
+ jpql과 sql의 차이(jpql은 엔티티 객체 대상, sql은 테이블 대상으로)
+ jpa로직은 트랜잭션안에서 실행되어야 데이터 변경이 보장된다. - 지연로딩 가능
+ @Transactional(readOnly = true) - 읽기 전용은 성능을 높여줌
+ @Transactional은 기본적으로 롤백이 기본임(테케에서만)
+ 예외처리에 대한 테스트케이스 작성법 숙지
+ 생성 메서드

### Tip
+ 테케에 @Transactional이 있으면 테케 종료 후 데이터를 롤백한다.
+ 실무에선 다대다(@ManyToMany)절대로 쓰지 말것 - 운영이 어렵고 컬럼 추가가 안됨