package server.mapper.genre.dto;


public record GenreRequest(
        Long id,
        boolean kPop,
        boolean jPop,
        boolean rock,
        boolean pop

){

}
