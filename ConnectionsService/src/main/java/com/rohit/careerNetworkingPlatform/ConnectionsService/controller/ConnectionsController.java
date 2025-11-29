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
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId) {
        List<Person> personList = connectionsService.getFirstDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/{userId}/second-degree")
    public ResponseEntity<List<Person>> getSecondDegreeConnections(@PathVariable Long userId) {
        List<Person> personList = connectionsService.getSecondDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/{userId}/third-degree")
    public ResponseEntity<List<Person>> getThirdDegreeConnections(@PathVariable Long userId) {
        List<Person> personList = connectionsService.getThirdDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(personList);
    }

    @PostMapping("/sender/{userId}")
    public ResponseEntity<Void> sendConnectionRequest(@PathVariable Long userId) {
        connectionsService.sendConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Void> acceptConnectionRequest(@PathVariable Long userId) {
        connectionsService.acceptConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Void> rejectConnectionRequest(@PathVariable Long userId) {
        connectionsService.rejectConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }
}
