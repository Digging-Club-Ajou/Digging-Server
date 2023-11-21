package server.service.location;

import org.springframework.stereotype.Service;
import server.mapper.location.LocationResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationCompositionService {

    private final LocationService locationService;

    public LocationCompositionService(final LocationService locationService) {
        this.locationService = locationService;
    }

    public List<LocationResponse> findLocation(final String query, final String x, final String y, final int page){
        List<LocationResponse> locationBeforeResponses;
        List<LocationResponse> locationAfterResponses = locationService.findLocation(query, x, y, page);
        String afterPlaceName = locationAfterResponses.get(0).placeName();

        if (page > 1) {
            locationBeforeResponses = locationService.findLocation(query, x, y, page - 1);
            for (LocationResponse locationBeforeResponse : locationBeforeResponses) {
                String beforePlaceName = locationBeforeResponse.placeName();
                if (beforePlaceName.equals(afterPlaceName)) {
                    return new ArrayList<>();
                }
            }
        }

        return locationAfterResponses;
    }
}
