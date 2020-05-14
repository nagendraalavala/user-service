package com.perficient.service;

import com.perficient.service.health.HealthCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@CrossOrigin
@ApiIgnore
public class HealthCheckController
{
    private final String platform;
    private final String region;
    private final String zone;
    private RestTemplate restTemplate;

    
    public HealthCheckController(@Value("${platform}")String platform,
                                 @Value("${region}") String region,
                                 @Value("${zone}") String zone,RestTemplate restTemplate)
    {
        super();
        this.platform = platform;
        this.region = region;
        this.zone = zone;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String status()
    {
        return "Welcome to User-Service";
    }

    @GetMapping(value = "/health")
    public ResponseEntity<HealthCheck> health(HttpServletRequest request)
    {
        HealthCheck healthCheck = restTemplate.getForObject(URI.create(request.getRequestURI().toString().replace("/health","/actuator/health")),HealthCheck.class);
        if (healthCheck == null)
        {
            healthCheck = new HealthCheck();
            healthCheck.setStatus("User-Service Not Available");
        }
        healthCheck.setStatus(healthCheck.getStatus().toString());
        healthCheck.setPlatform(platform);
        healthCheck.setRegion(region);
        healthCheck.setZone(zone);
        return Boolean.valueOf(healthCheck.getStatus().toString().equalsIgnoreCase("User-Service Available"))
        ?ResponseEntity.ok(healthCheck):ResponseEntity.badRequest().body(healthCheck);
    }
}
