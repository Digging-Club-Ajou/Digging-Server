package server.service.album;

import org.springframework.web.multipart.MultipartFile;

public interface AlbumCreateService {

    void createAlbum(final long memberId, final String albumName, final MultipartFile albumImage);
}
