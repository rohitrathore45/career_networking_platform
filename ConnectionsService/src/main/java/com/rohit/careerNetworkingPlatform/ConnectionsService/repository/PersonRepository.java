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

    @Query("MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person) " +
            "WHERE personA.userId = $userId " +
            "RETURN DISTINCT personB")
    List<Person> getFirstDegreeConnections(Long userId);

    @Query("""
        MATCH (user:Person {userId: $userId})-[:CONNECTED_TO]-(f1:Person)-[:CONNECTED_TO]-(f2:Person)
        WHERE f2.userId <> $userId
          AND NOT (user)-[:CONNECTED_TO]-(f2)
        RETURN DISTINCT f2
    """)
    List<Person> getSecondDegreeConnections(Long userId);

    @Query("""
        MATCH (user:Person {userId: $userId})
            -[:CONNECTED_TO]-(f1:Person)
            -[:CONNECTED_TO]-(f2:Person)
            -[:CONNECTED_TO]-(f3:Person)
        WHERE f3.userId <> $userId
        AND NOT (user)-[:CONNECTED_TO]-(f3)
        AND NOT (user)-[:CONNECTED_TO*2]-(f3)
        RETURN DISTINCT f3;
    """)
    List<Person> getThirdDegreeConnections(Long userId);

}
