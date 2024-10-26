package by.lobanovs.weatherapp.repositories;


import by.lobanovs.weatherapp.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    boolean existsByName(String name);
}
