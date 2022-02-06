package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA 스펙 상 기본 생성자가 필요하므로 만들어주되, protected로 닫아둔다.
    // JPA 구현 라이브러리가 리플렉션 같은 기술을 사용할 수 있도록 지원하는 것이다.
    protected Address() {
    }

    // 값 타입은 변경 불가능하도록 설계해야 하므로 객체 생성 시에만 값을 할당하고 변경 불가능하게 한다.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
