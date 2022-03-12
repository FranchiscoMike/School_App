package uz.pdp.school_app.controller;
/**
 * written by Mr.Mike
 */

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.school_app.entity.Book;
import uz.pdp.school_app.entity.Class;
import uz.pdp.school_app.entity.Language;
import uz.pdp.school_app.entity.Subject;
import uz.pdp.school_app.payload.BookDTO;
import uz.pdp.school_app.repository.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor

public class BookController {
    final BookRepository bookRepository;
    final AttachmentRepository attachmentRepository;
    final ClassRepository classRepository;
    final SubjectRepository subjectRepository;
    final LanguageRepository languageRepository;

    /**
     * show all books
     *
     * @return
     */
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    /**
     * get one  from db
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        if (bookRepository.existsById(id)) {
            return ResponseEntity.ok(bookRepository.getById(id));
        }
        return ResponseEntity.notFound().build();
    }


    /**
     *  do not delete make active = false
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        if (bookRepository.existsById(id)) {
            Book book = bookRepository.getById(id);

            book.setActive(false);
            bookRepository.save(book);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *  hamma narsa tekshirilgan  bormi yo'qmi deya
     * @param dto
     * @return
     */
    @PostMapping
    public HttpEntity<?> add(@RequestBody @NotNull BookDTO dto){
        if (classRepository.existsById(dto.getClassId())) {
            if (subjectRepository.existsById(dto.getSubjectId())) {
                if (languageRepository.existsById(dto.getLanguageId())) {
                    Class aClass = classRepository.getById(dto.getClassId());
                    Subject subject = subjectRepository.getById(dto.getSubjectId());
                    Language language = languageRepository.getById(dto.getLanguageId());

                    Book book = new Book();

                    // making new book process
                    return getHttpEntity(dto, aClass, subject, language, book);
                }
            }
        }
        return ResponseEntity.noContent().build();
    }

    @NotNull
    private HttpEntity<?> getHttpEntity(@RequestBody @NotNull BookDTO dto, Class aClass, Subject subject, Language language, @NotNull Book book) {
        book.setAuthors(dto.getAuthors());
        book.setClassi(aClass);
        book.setDescription(dto.getDescription());
        book.setFile(attachmentRepository.getById(dto.getFileId()));
        book.setPhoto(attachmentRepository.getById(dto.getPhotoId()));
        book.setLanguage(language);
        book.setSubject(subject);

        bookRepository.save(book);

        return ResponseEntity.ok("saved successfully");
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody BookDTO dto){
        if (bookRepository.existsById(id)) {
            if (classRepository.existsById(dto.getClassId())) {
                if (subjectRepository.existsById(dto.getSubjectId())) {
                    if (languageRepository.existsById(dto.getLanguageId())) {
                        Class aClass = classRepository.getById(dto.getClassId());
                        Subject subject = subjectRepository.getById(dto.getSubjectId());
                        Language language = languageRepository.getById(dto.getLanguageId());

                        Book book = bookRepository.getById(id);
                        // making new book process
                        return getHttpEntity(dto, aClass, subject, language, book);
                    }
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * using class id get all books
     * @param id
     * @return
     */
    @GetMapping("/class/{id}")
    public HttpEntity<?> getBooksUsingClassId(@PathVariable Integer id){
        List<Book> books = bookRepository.findByClassi_Id(id);

        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public HttpEntity<?> getUsingLanguageAndClass(@RequestParam(name = "language_id",defaultValue = "0")Integer language_id,
                                                  @RequestParam(name = "class_id",defaultValue = "0") Integer class_id){
        List<Book> books = new ArrayList<Book>();
        if (language_id==0 && class_id ==0){
             books = bookRepository.findAll();
        } else if (class_id==0){
             books = bookRepository.findAllByLanguage_Id(language_id);
        } else if (language_id==0){
             books = bookRepository.findAllByClassi_Id(class_id);
        }else {
             books = bookRepository.findByClassi_IdAndLanguage_Id(class_id, language_id);
        }

        return ResponseEntity.ok().body(books);


    }


}
