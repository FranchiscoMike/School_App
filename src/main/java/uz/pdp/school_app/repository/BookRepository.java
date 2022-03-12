package uz.pdp.school_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.school_app.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByClassi_Id(Integer id);

    List<Book> findByClassi_IdAndLanguage_Id(Integer classId,Integer languageId);

    List<Book> findAllByLanguage_Id(Integer id);
    List<Book> findAllByClassi_Id(Integer id);

}
