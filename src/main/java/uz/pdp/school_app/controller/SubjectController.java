package uz.pdp.school_app.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.school_app.entity.Language;
import uz.pdp.school_app.entity.Subject;
import uz.pdp.school_app.repository.LanguageRepository;
import uz.pdp.school_app.repository.SubjectRepository;

@RequestMapping("/api/subject")
@RequiredArgsConstructor
@RestController
@Where(clause = "active = true")
public class SubjectController {
    final SubjectRepository subjectRepository;

    // chiroyli Crud yozilsa 3 tasiga shuni ishlatamiz

    /**
     * show all languages
     * @return
     */
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok("Hozirda bor bo'lgan tillar :" +
                "\n" + subjectRepository.findAll());
    }

    /**
     * get one from db
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        if (subjectRepository.existsById(id)) {
            Subject byId = subjectRepository.getById(id);
            if (byId.isActive()) {
                return ResponseEntity.ok().body("found :\n" + byId);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * edit data on db
     *
     * @param subject
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody Subject subject, @PathVariable Integer id) {
        if (subjectRepository.existsById(id)) {
            Subject subject1 = subjectRepository.getById(id);
            if (subjectRepository.existsByNameAndIdNot(subject.getName(), id)) {

                subject1.setActive(subject.isActive());
                subject1.setName(subject.getName());

                Subject save = subjectRepository.save(subject1);

                return ResponseEntity.ok().body("edited :\n" + save);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *  add to the db
     * @param subject
     * @return
     */
    @PostMapping
    public HttpEntity<?> add(@RequestBody @NotNull Subject subject) {
        if (!subjectRepository.existsByName(subject.getName())) {

            Subject subject1 = new Subject();
            subject1.setName(subject.getName());
            Subject save = subjectRepository.save(subject1);

            return ResponseEntity.ok().body("saved:\n" + save);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        if (subjectRepository.existsById(id)) {
            Subject subject = subjectRepository.getById(id);

            // teskarisini qiladi shunisi to'griroq bo'aldi
           subject.setActive(!subject.isActive());

            Subject save = subjectRepository.save(subject);

            return ResponseEntity.ok().body("deleted!");
        }
        return ResponseEntity.notFound().build();
    }

}
