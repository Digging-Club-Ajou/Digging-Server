package server.global.constant;

public class AnnotationValidConstant {

    private AnnotationValidConstant() {
    }

    public static final String NICKNAME_VALID_MESSAGE = "영문, 숫자, 밑줄(_), 마침표(.)를 포함하여 2-20자를 지원합니다";
    public static final String NICKNAME_REGEXP = "^[a-zA-Z0-9_.]{2,20}$";
    public static final String ALBUM_NAME_REGEXP = "^[a-zA-Z0-9가-힣\\s]{1,15}$";
}
