package by.lobanovs.weatherapp.repositories;

import by.lobanovs.weatherapp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    @Query("SELECT COUNT(DISTINCT m.createdAt) FROM Measurement m WHERE m.raining = true")
    Long countRainyDays();
}
