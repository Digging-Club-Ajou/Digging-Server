package server.global.constant;

public enum SpotifyConstant {

    SpotifyConstant() {
    };

    public static String spotifyAccessToken;

    public static void updateToken(final String updateToken) {
        spotifyAccessToken = updateToken;
    }
}
