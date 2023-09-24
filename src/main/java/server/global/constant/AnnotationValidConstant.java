package server.global.constant;

public class AnnotationValidConstant {

    private AnnotationValidConstant() {
    }

    public static final String NICKNAME_VALID_MESSAGE = "영문, 숫자, 밑줄, 마침표만 허용됩니다";
    public static final String NICKNAME_REGEXP = "^[a-zA-Z0-9_.-]+$";
}
