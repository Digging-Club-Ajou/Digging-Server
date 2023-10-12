package server.controller.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.mapper.location.LocationResponse;
import server.service.location.LocationService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public List<LocationResponse> findLocation(@RequestParam final String query,
                                               @RequestParam final String x,
                                               @RequestParam final String y) {
        return locationService.findLocation(query, x, y);
    }
}
