package server.global.constant;

public enum TextConstant {

    MEMBER_SESSION("MemberSession"),
    ACCESS_TOKEN("AccessToken"),
    REFRESH_TOKEN("RefreshToken"),
    DIGGING_CLUB("digging-club"),
    ALBUM_IMAGE("AlbumImage"),
    MELODY_CARD_IMAGE("MelodyCardImage");

    public final String value;

    TextConstant(final String value) {
        this.value = value;
    }
}
