package com.rohit.careerNetworkingPlatform.ConnectionsService.repository;

import com.rohit.careerNetworkingPlatform.ConnectionsService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByUserId(Long userId);

    @Query("""
        MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person)
        WHERE personA.userId = $userId
        RETURN DISTINCT personB
    """)
    List<Person> getFirstDegreeConnections(Long userId);
}
