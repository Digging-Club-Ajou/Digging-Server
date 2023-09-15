package server.util;

public enum TestConstant {

    TEST_USERNAME("테스트 이름"),
    TEST_LOGIN_ID("TestLoginId123@"),
    TEST_PASSWORD("TestPassword123@"),
    TEST_PHONE_NUMBER("010-1234-5678"),
    TEST_EMAIL("test@gmail.com");

    public final String value;

    TestConstant(final String value) {
        this.value = value;
    }
}
