package com.rohit.careerNetworkingPlatform.ConnectionsService.controller;

import com.rohit.careerNetworkingPlatform.ConnectionsService.entity.Person;
import com.rohit.careerNetworkingPlatform.ConnectionsService.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId,
                                                                  @RequestHeader("X-User-Id") Long userIdFromHeader) {
        log.info("User id is : {}", userIdFromHeader);
        List<Person> personList = connectionsService.getFirstDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(personList);
    }
}
