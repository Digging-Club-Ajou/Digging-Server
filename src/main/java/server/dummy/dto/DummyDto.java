package server.dummy.dto;

import java.util.List;

public record DummyDto(
        List<RequestDto> requestDtos
) {
}
