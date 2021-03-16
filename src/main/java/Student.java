import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Student {

    private Long studentId;
    private String studentName;
    private List<Subject> subjects;


}
