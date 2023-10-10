package server.global.constant;

public enum SpotifyConstant {

    SpotifyConstant() {
    };

    public static String spotifyAccessToken;
    public static final String SPOTIFY_TRACKS_URL = "https://api.spotify.com/v1/search?q=";
    public static final String BASIC_CONDITION = "&type=track&limit=30";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String TRACKS = "tracks";
    public static final String ITEMS = "items";
    public static final String NAME = "name";
    public static final String ARTISTS = "artists";
    public static final String ALBUM = "album";
    public static final String IMAGES = "images";
    public static final String URL = "url";
    public static final int ZERO = 0;

    public static void updateToken(final String updateToken) {
        spotifyAccessToken = updateToken;
    }
}
