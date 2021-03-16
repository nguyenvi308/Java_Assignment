import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Subject {

    private String subjectName;
    private Double score;
    private int numberOfQuestion;
    private int numberOfCorrectQuestions;

}
