package uz.pdp.school_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.school_app.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    boolean existsByNameAndIdNot(String name, Integer id);

    boolean existsByName(String name);

}
