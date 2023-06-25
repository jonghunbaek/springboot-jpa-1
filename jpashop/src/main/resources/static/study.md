# JPA 활용1
### 1.JPA, DB 설정
+ Entity매니저를 통한 모든 데이터 변경은 트랜잭션 안에서 이뤄져야 한다.
+ 같은 영속성 컨텍스트에서 아이디가 같으면 같은 객체다.

### 2. 도메인 분석, 설계
+ 양방향 연관관계보단 단방향을 사용하는 것이 유리
+ 상속관계 매핑 3가지 방법(1. 싱글테이블 전략, 2, joined(정교), tableperclass(클래스별 테이블 생성)) - 부모클래스에 @Inheritance, @DiscriminatorColumn, @DiscriminatorValue
+ 양방향관계에선 연관관계 주인을 정해줘야한다.
+ enum 타입엔 @Enumerated 어노테이션을 붙여주고 EnumType.STRING으로 ORDINAL은 숫자로 매기기 때문에 중간에 값이 추가될 경우 문제가 발생

### Tip
+ 테케에 @Transactional이 있으면 테케 종료 후 데이터를 롤백한다.