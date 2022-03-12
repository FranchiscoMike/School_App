package uz.pdp.school_app.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.school_app.entity.Class;
import uz.pdp.school_app.entity.Class;
import uz.pdp.school_app.repository.ClassRepository;
import uz.pdp.school_app.repository.ClassRepository;

@RequestMapping("/api/class")
@RequiredArgsConstructor
@RestController
@Where(clause = "active = true")
public class ClassController {
    final ClassRepository classRepository;

    // chiroyli Crud yozilsa 3 tasiga shuni ishlatamiz

    /**
     * show all languages
     *
     * @return
     */
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok("Hozirda bor bo'lgan tillar :" +
                "\n" + classRepository.findAll());
    }

    /**
     * get one from db
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        if (classRepository.existsById(id)) {
            Class byId = classRepository.getById(id);
            if (byId.isActive()) {
                return ResponseEntity.ok().body("found :\n" + byId);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * edit data on db
     *
     * @param classi
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody Class classi, @PathVariable Integer id) {
        if (classRepository.existsById(id)) {
            Class classi1 = classRepository.getById(id);
            if (classRepository.existsByNameAndIdNot(classi.getName(), id)) {

                classi1.setActive(classi.isActive());
                classi1.setName(classi.getName());

                Class save = classRepository.save(classi1);

                return ResponseEntity.ok().body("edited :\n" + save);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *  add to the db
     * @param classi
     * @return
     */
    @PostMapping
    public HttpEntity<?> add(@RequestBody @NotNull Class classi) {
        if (!classRepository.existsByName(classi.getName())) {
            Class classi1 = new Class();
            classi1.setName(classi.getName());
            Class save = classRepository.save(classi1);

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
        if (classRepository.existsById(id)) {
            Class classi = classRepository.getById(id);

            // teskarisini qiladi shunisi to'griroq bo'aldi
            classi.setActive(!classi.isActive());

            Class save = classRepository.save(classi);

            return ResponseEntity.ok().body("deleted!  ko'rinishi :\n"+ save);
        }
        return ResponseEntity.notFound().build();
    }

}
