package server.global.constant;

public enum SpotifyConstant {

    SpotifyConstant() {
    };

    public static String spotifyAccessToken;
    public static final String SPOTIFY_TRACKS_URL = "https://api.spotify.com/v1/search?q=";
    public static final String SPOTIFY_ARTIST_URL = "https://api.spotify.com/v1/artists/";
    public static final String KAKAO_LOCATION_KEYWORD_URL = "https://dapi.kakao.com/v2/local/search/keyword";

    public static final String BASIC_CONDITION = "&type=track&limit=30";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String KAKAO_AK = "KakaoAK ";
    public static final String TRACKS = "tracks";
    public static final String ITEMS = "items";
    public static final String NAME = "name";
    public static final String ARTISTS = "artists";
    public static final String ALBUM = "album";
    public static final String PREVIEW_URL = "preview_url";
    public static final String IMAGES = "images";
    public static final String GENRES = "genres";
    public static final String URL = "url";
    public static final String QUERY = "query";
    public static final String BRACE_QUERY = "{query}";
    public static final String SORT = "sort";
    public static final String DISTANCE = "distance";
    public static final String DOCUMENTS = "documents";
    public static final String PLACE_NAME = "place_name";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String M = "m";
    public static final int ZERO = 0;
    public static final String ID = "id";

    public static final String countryCode = "KR";

    public static void updateToken(final String updateToken) {
        spotifyAccessToken = updateToken;
    }

    public static String getSpotifyToken() {
        return spotifyAccessToken;
    }
}
