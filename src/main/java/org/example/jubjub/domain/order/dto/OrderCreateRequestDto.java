package org.example.jubjub.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {
    private Long storeId;               // 주문할 매장 번호
    private Boolean isEcoContainer;     // 에코 용기 지참 여부
    private Integer expectedTotalAmount;// 고객이 화면에서 본 총 결제 예상 금액 (서버 검증용)
}