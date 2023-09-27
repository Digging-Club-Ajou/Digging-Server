package server.global.constant;

public enum TestConstant {

    TEST_USERNAME("테스트 이름"),
    TEST_NICKNAME("TestNickname"),
    TEST_LOGIN_ID("TestLoginId123@"),
    TEST_PASSWORD("TestPassword123@"),
    TEST_PHONE_NUMBER("010-1234-5678"),
    TEST_EMAIL("test@gmail.com"),
    TEST_ACCESS_TOKEN("Test AccessToken");

    public final String value;

    TestConstant(final String value) {
        this.value = value;
    }
}
