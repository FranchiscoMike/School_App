package uz.pdp.school_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.school_app.entity.Attachment;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {

}
