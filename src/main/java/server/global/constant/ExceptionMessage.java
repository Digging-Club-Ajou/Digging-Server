package server.global.constant;

public enum ExceptionMessage {

    MEMBER_NOT_FOUND_EXCEPTION("회원을 찾을 수 없습니다"),
    CARD_FAVORITE_NOT_FOUND_EXCEPTION("좋아요 정보를 찾을 수 없습니다"),
    FOLLOWING_INFO_NOT_FOUND_EXCEPTION("팔로잉 정보를 찾을 수 없습니다"),
    NOTIFICATION_INFO_NOT_FOUND_EXCEPTION("알림 정보를 찾을 수 없습니다"),
    GENRE_NOT_FOUND_EXCEPTION("장르를 찾을 수 없습니다"),
    NICKNAME_DUPLICATE_EXCEPTION("다른 유저와 중복되는 닉네임입니다"),
    NICKNAME_NOT_FOUND_EXCEPTION("닉네임이 존재하지 않습니다"),
    NICKNAME_CAN_USE("사용 가능한 닉네임입니다"),
    NICKNAME_REGEX_EXCEPTION("영문, 숫자, 밑줄(_), 마침표(.)중 최소 2가지 이상의 조합으로 2-20자를 지원합니다"),
    REFRESH_TOKEN_NOT_FOUND_EXCEPTION("RefreshToken을 찾을 수 없습니다"),
    ALBUM_IMAGE_NOT_FOUND_EXCEPTION("앨범 이미지를 찾을 수 없습니다"),
    ALBUM_NOT_FOUND_EXCEPTION("앨범을 찾을 수 없습니다"),
    ALBUM_ALREADY_EXIST_EXCEPTION("앨범이 이미 존재합니다"),
    ALBUM_EMOTICONS_REGEX_EXCEPTION("특수문자 및 이모티콘은 사용할 수 없습니다"),
    ALBUM_NAME_REGEX("한글, 영문, 숫자를 포함한 15자를 지원합니다"),
    ALBUM_NAME_ALREADY_EXIST_EXCEPTION("이미 존재하는 앨범 이름입니다"),
    PASSWORD_NOT_MATCH_EXCEPTION("비밀번호가 일치하지 않습니다"),
    ACCESS_TOKEN_JSON_PARSING("AccessToken으로부터 정보를 가져올 수 없습니다"),
    CLAIMS_UNAUTHORIZED("토큰으로부터 사용자 정보를 가져올 수 없습니다"),
    COOKIE_NOT_EXIST("쿠키가 존재하지 않습니다"),
    MEMBER_SESSION_JSON_PARSING("세션 정보를 Json 형태로 파싱할 수 없습니다"),
    MUSIC_JSON_PARSING("음악 정보를 Json 형태로 파싱할 수 없습니다"),
    KAKAO_JSON_PARSING("카카오 유저 정보를 Json 형태로 파싱할 수 없습니다."),
    LOCATION_JSON_PARSING("위치 정보를 Json 형태로 파싱할 수 없습니다"),
    REFRESH_TOKEN_NOT_MATCH("Refresh Token이 일치하지 않습니다"),
    REFRESH_TOKEN_NOT_EXIST("Refresh Token을 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_VALID("Refresh Token이 유효하지 않습니다"),
    PROFILES_SAVE_EXCEPTION("프로필 사진을 저장할 수 없습니다"),
    NOTIFICATION_BAD_REQUEST("알림을 보낼 수 없습니다"),
    NOTIFICATION_MESSAGE_BAD_REQUEST("알림 메세지를 생성할 수 없습니다"),

    MELODY_CARD_LIMIT("멜로디 카드의 최대 갯수 만큼 저장되어 있습니다"),
    MELODY_CARD_NOT_FOUND("멜로디 카드가 존재하지 않습니다"),
    MELODY_CARD_EXCEPTION("멜로디 카드를 저장할 수 없습니다"),
    FOLLOWING_NOT_FOUND("팔로잉 정보를 찾을 수 없습니다."),
    FOLLOWING_ALREADY_EXISTS("팔로잉 정보가 이미 존재합니다"),
    ALBUM_NOT_EXIST("앨범이 존재하지 않습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH("서버에 전달한 파라미터와 타입이 맞지 않습니다"),
    SERVLET_REQUEST_BINDING("파라미터나 HTTP 바디 데이터를 바인딩 할 수 없습니다"),
    METHOD_NOT_ALLOWED("HTTP 메소드가 올바르지 않습니다"),
    NOT_ENOUGH_PLAY_RECORD("활동이 충분하지 않아 활동 내역을 추적할 수 없습니다"),
    CARD_FAVORITE_SELF_EXCEPTION("자기 자신의 멜로디 카드는 좋아요 누를 수가 없습니다"),
    FOLLOWING_SELF_EXCEPTION("자기 자신의 팔로잉 할 수가 없습니다"),
    ARTIST_INFO_NOT_FOUND("아티스트 정보를 찾을 수 없습니다"),
    NOTIFICATION_NOT_FOUND_EXCEPTION("알림이 없습니다");

    public final String message;

    ExceptionMessage(final String message) {
        this.message = message;
    }
}
