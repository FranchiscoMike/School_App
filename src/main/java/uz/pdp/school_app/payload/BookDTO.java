package uz.pdp.school_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private String description;
    private List<String> authors;
    private Integer fileId;
    private Integer photoId;
    private Integer languageId;
    private Integer subjectId;
    private Integer classId;

}
