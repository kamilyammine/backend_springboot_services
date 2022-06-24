package com.sirenanalytics.worldbank_feedback.repository;

import com.sirenanalytics.worldbank_feedback.model.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>
{
    /*--------------------------------------------------------------------------+
    |   Each project is a road with dozens (or even hundreds) of points.  We    |
    |   want the 10 closest projects, so we calculate the closest point in      |
    |   each road and take the top 10 using the haversine formula inside a      |
    |   correlated subquery.                                                    |
    |   12742 is the diameter of the earth in km                                |
    +--------------------------------------------------------------------------*/
    String correlatedHaversineFormulaQuery =
            "select id, kadaa_key \"kadaaKey\", name_en_us \"nameEnUs\", name_ar_lb \"nameArLb\", description_en_us \"descriptionEnUs\", description_ar_lb \"descriptionArLb\", " +
                    "(select asin(sqrt( " +
                    "sin(radians(c.latitude-:latitude)/2)^2 + " +
                    "sin(radians(c.longitude-:longitude)/2)^2 * " +
                    "cos(radians(:latitude)) * " +
                    "cos(radians(c.latitude)) " +
                    ")) * 12742 as distance_in_km from coordinate c where project_id = p.id order by distance_in_km asc limit 1 " +
                    ") as closest_point_distance_in_km " +
                    "from project p order by closest_point_distance_in_km asc limit 10;";

    Page<Project> findByKadaaKey(Pageable pageable, String kadaaKey);

    @Query(value = correlatedHaversineFormulaQuery, nativeQuery = true)
    List<Map<String, String>> findClosestProjectsToLatLong(@Param("latitude") double latitude, @Param("longitude") double longitude);

    List<Project> findByDescriptionEnUsLikeIgnoreCase(String partialWord);

    List<Project> findByDescriptionArLbLikeIgnoreCase(String partialWord);
}
