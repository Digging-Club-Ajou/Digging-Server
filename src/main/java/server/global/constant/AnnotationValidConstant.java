package server.global.constant;

public class AnnotationValidConstant {

    private AnnotationValidConstant() {
    }

    public static final String NICKNAME_VALID_MESSAGE = "닉네임은 영문/숫자/밑줄/마침표만 허용됩니다";
    public static final String ALBUM_NAME_VALID_MESSAGE = "앨범 이름은 한글/영문/숫자를 포함한 15자를 지원합니다";
    public static final String NICKNAME_REGEXP = "^[a-zA-Z0-9_.-]+$";
    public static final String ALBUM_NAME_REGEXP = "^[a-zA-Z0-9가-힣\\s]{1,15}$";
}
