package com.sirenanalytics.worldbank_feedback.repository;

import com.sirenanalytics.worldbank_feedback.model.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long>
{
    String haversineClosestPointInProjectQuery =
            "select id, project_id, asin(sqrt( " +
                    "sin(radians(c.latitude-:latitude)/2)^2 + " +
                    "sin(radians(c.longitude-:longitude)/2)^2 * " +
                    "cos(radians(:latitude)) * " +
                    "cos(radians(c.latitude)) " +
                    ")) * 12742 as distance_in_km " +
                    "from coordinate c where project_id = :projectId " +
                    "order by distance_in_km asc limit 1";


    String haversineClosestPointAnyProjectQuery =
            "select id, project_id, asin(sqrt( " +
                    "sin(radians(c.latitude-:latitude)/2)^2 + " +
                    "sin(radians(c.longitude-:longitude)/2)^2 * " +
                    "cos(radians(:latitude)) * " +
                    "cos(radians(c.latitude)) " +
                    ")) * 12742 as distance_in_km " +
                    "from coordinate c" +
                    "order by distance_in_km asc limit 1";

    @Query(value = haversineClosestPointInProjectQuery, nativeQuery = true)
    List<Map<String, Object>> findClosestCoordinateFromLatLongAndProject(
            @Param("latitude") double latitude, @Param("longitude") double longitude, @Param("projectId") Long projectId);

    @Query(value = haversineClosestPointAnyProjectQuery, nativeQuery = true)
    List<Map<String, Object>> findClosestCoordinateFromLatLong(
            @Param("latitude") double latitude, @Param("longitude") double longitude);
}
