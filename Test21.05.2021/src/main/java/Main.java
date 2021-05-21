
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Student student = new Student();
        student.JSToOb();
        menu();
    }

    public static void menu(){
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Create Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Update Student");
            System.out.println("4. Show all students");
            System.out.println("5. Search Student");
            System.out.print("Select option: ");
            int enterNum = scanner.nextInt();
            switch (enterNum){
                case 1:
                    Student sc = new Student();
                    sc.createStudent();
                    break;
                case 2:
                    Student s = new Student();
                    s.removeStudent();
                    break;
                case 3:
                    Student scc = new Student();
                    scc.updateStudent();
                    break;
                case 4:
                    Student cs = new Student();
                    cs.showStudents();
                    break;
                case 5:
                    break;
            }
        }
    }
}
