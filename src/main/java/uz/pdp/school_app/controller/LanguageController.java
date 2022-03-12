package uz.pdp.school_app.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.school_app.entity.Language;
import uz.pdp.school_app.repository.LanguageRepository;

@RequestMapping("/api/language")
@RequiredArgsConstructor
@RestController

public class LanguageController {
    final LanguageRepository languageRepository;

    // chiroyli Crud yozilsa 3 tasiga shuni ishlatamiz

    /**
     * show all languages
     *
     * @return
     */
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok("Hozirda bor bo'lgan tillar :" +
                "\n\n" + languageRepository.findAll());
    }

    /**
     * get one from db
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        if (languageRepository.existsById(id)) {
            Language byId = languageRepository.getById(id);
            if (byId.isActive()) {
                return ResponseEntity.ok().body("found :\n" + byId);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * edit data on db
     *
     * @param language
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody Language language, @PathVariable Integer id) {
        if (languageRepository.existsById(id)) {
            Language language1 = languageRepository.getById(id);
            if (languageRepository.existsByNameAndIdNot(language.getName(), id)) {

                language1.setActive(language.isActive());
                language1.setName(language.getName());

                Language save = languageRepository.save(language1);


                return ResponseEntity.ok().body("edited :\n" + save);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *  add to the db
     * @param language
     * @return
     */
    @PostMapping
//    @PreAuthorize(value = "hasAnyAuthority('ALL')")
    public HttpEntity<?> add(@RequestBody @NotNull Language language) {
        if (!languageRepository.existsByName(language.getName())) {
            Language language1 = new Language();
            language1.setName(language.getName());
            language1.setActive(true);
            Language save = languageRepository.save(language1);

            return ResponseEntity.ok().body("saved:\n" + save);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     *  do not delete from delete but also isActive =  false
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        if (languageRepository.existsById(id)) {
            Language language = languageRepository.getById(id);

            // teskarisini qiladi shunisi to'griroq bo'aldi
            language.setActive(!language.isActive());

            Language save = languageRepository.save(language);

            return ResponseEntity.ok().body("deleted!");
        }
        return ResponseEntity.notFound().build();
    }

}
