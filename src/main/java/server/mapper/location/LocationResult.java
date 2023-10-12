package server.mapper.location;

import java.util.List;

public record LocationResult(
        List<LocationResponse> locationResponses
) {
}
