import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class School {
    private int id;

    private HashMap<Integer,Student>  students;

}
