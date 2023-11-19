package server.mapper.melody_card.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MelodyCardResponse {

    private long melodyCardId;
    private long albumId;
    private long memberId;
    private String nickname;
    private String artistName;
    private String songTitle;
    private String previewUrl;
    private String imageUrl;
    private String albumCoverImageUrl;
    private String address;
    private String cardDescription;
    private String color;
    private Boolean isImageUrl;
    private Boolean isLike;

    protected MelodyCardResponse() {
    }

    @Builder
    private MelodyCardResponse(final long melodyCardId, final long albumId, final long memberId,
                              final String nickname, final String artistName, final String songTitle,
                              final String previewUrl, final String imageUrl, final String albumCoverImageUrl,
                              final String address, final String cardDescription, final String color,
                               final Boolean isImageUrl, final Boolean isLike) {
        this.melodyCardId = melodyCardId;
        this.albumId = albumId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.artistName = artistName;
        this.songTitle = songTitle;
        this.previewUrl = previewUrl;
        this.imageUrl = imageUrl;
        this.albumCoverImageUrl = albumCoverImageUrl;
        this.address = address;
        this.cardDescription = cardDescription;
        this.color = color;
        this.isImageUrl = isImageUrl;
        this.isLike = isLike;
    }

    public void updateUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateLikeInfo(final Boolean isLike) {
        this.isLike = isLike;
    }
}