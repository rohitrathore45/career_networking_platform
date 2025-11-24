package com.rohit.careerNetworkingPlatform.ConnectionsService.service;

import com.rohit.careerNetworkingPlatform.ConnectionsService.entity.Person;
import com.rohit.careerNetworkingPlatform.ConnectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId) {
        log.info("Getting first degree connections of user with id : {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }
}
