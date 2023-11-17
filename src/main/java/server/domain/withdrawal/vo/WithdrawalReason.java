package server.domain.withdrawal.vo;

public enum WithdrawalReason {

    REASON_1("원하는 상품이나 정보가 없음"),
    REASON_2("자주 사용하지 않음"),
    REASON_3("추천 콘텐츠가 마음에 들지 않음"),
    REASON_4("잦은 오류와 장애가 발생함"),
    REASON_5("다른 계정으로 재가입하기 위함"),
    REASON_6("기타");

    public final String message;

    WithdrawalReason(final String message) {
        this.message = message;
    }
}
