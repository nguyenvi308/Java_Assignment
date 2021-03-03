import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private long id;
    private String pass;
    private  boolean isTeacher;
    private String name;
    private String address;
}
