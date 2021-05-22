
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Student student = new Student();
        student.JSToOb();
        menu();
    }

    public static void menu(){
        Student st = new Student();
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Create Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Update Student");
            System.out.println("4. Show all students");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Select option: ");
            String enterNum = scanner.nextLine();
            if (st.checkDigit(enterNum)){
                switch (Integer.parseInt(enterNum)){
                    case 1:
                        Student forCreate = new Student();
                        forCreate.createStudent();
                        break;
                    case 2:
                        Student forRem = new Student();
                        forRem.removeStudent();
                        break;
                    case 3:
                        Student scc = new Student();
                        scc.updateStudent();
                        break;
                    case 4:
                        Student forShow = new Student();
                        forShow.showStudents();
                        break;
                    case 5:
                        Student forSearch = new Student();
                        forSearch.searchStudent();
                        break;
                    case 6:
                        return;
                    default:
                }
            }
            else {
                menu();
            }
        }
    }
}
