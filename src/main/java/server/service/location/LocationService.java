package server.service.location;

import server.mapper.location.LocationResponse;

import java.util.List;

public interface LocationService {

    List<LocationResponse> findLocation(final String query, final String x, final String y);
}
