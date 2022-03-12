package uz.pdp.school_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.school_app.entity.Class;

public interface ClassRepository extends JpaRepository<Class,Integer> {
    boolean existsByNameAndIdNot(String name, Integer id);

    boolean existsByName(String name);

}
