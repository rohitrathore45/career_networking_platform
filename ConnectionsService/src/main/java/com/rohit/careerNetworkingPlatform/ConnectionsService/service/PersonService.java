package com.rohit.careerNetworkingPlatform.ConnectionsService.service;

import com.rohit.careerNetworkingPlatform.ConnectionsService.entity.Person;
import com.rohit.careerNetworkingPlatform.ConnectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public void createPerson(Long userId, String name) {
        Person person = Person.builder()
                .name(name)
                .userId(userId)
                .build();

        personRepository.save(person);
    }
}
