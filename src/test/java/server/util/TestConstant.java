package server.util;

public enum TestConstant {

    TEST_USERNAME("테스트 이름"),
    TEST_NICKNAME("test_nickname123"),
    TEST_LOGIN_ID("TestLoginId123@"),
    TEST_PASSWORD("TestPassword123@"),
    TEST_PHONE_NUMBER("010-1234-5678"),
    TEST_EMAIL("test@gmail.com"),
    TEST_AUTH_CODE("Test Auth Code");

    public final String value;

    TestConstant(final String value) {
        this.value = value;
    }
}
