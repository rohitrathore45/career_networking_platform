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

    public List<Person> getSecondDegreeConnectionsOfUser(Long userId) {
        log.info("Getting Second degree connections of user with id : {}", userId);
        return personRepository.getSecondDegreeConnections(userId);
    }

    public List<Person> getThirdDegreeConnectionsOfUser(Long userId) {
        log.info("Getting Third degree connections of user with id : {}", userId);
        return personRepository.getThirdDegreeConnections(userId);
    }
}
