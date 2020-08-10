import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int salary;
    private Date fromDate;
    private String title;
}
