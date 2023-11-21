package server.controller.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.mapper.location.LocationResponse;
import server.mapper.location.LocationResult;
import server.service.location.LocationCompositionService;
import server.service.location.LocationService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class LocationController {

    private final LocationCompositionService locationCompositionService;

    public LocationController(final LocationCompositionService locationCompositionService) {
        this.locationCompositionService = locationCompositionService;
    }

    @GetMapping("/location")
    public LocationResult findLocation(@RequestParam final String query,
                                       @RequestParam final String x,
                                       @RequestParam final String y,
                                       @RequestParam final int page) {
        List<LocationResponse> locationAfterResponses = locationCompositionService.findLocation(query, x, y, page);
        return new LocationResult(locationAfterResponses);
    }
}
