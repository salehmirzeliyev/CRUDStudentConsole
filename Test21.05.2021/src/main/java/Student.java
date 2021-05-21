import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import javax.swing.text.html.Option;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Data
public class Student {

    public static List<Student> allStudents = new ArrayList<>();
    private int id;
    private String name;
    private String surname;
    private String fatherName;
    private String email;
    private String phoneNumber;

    public Student() {
    }

    public Student(int id,String name, String surname, String fatherName, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static List<Student> getAllStudents() {
        return allStudents;
    }

    public static void setAllStudents(List<Student> allStudents) {
        Student.allStudents = allStudents;
    }

    @Override
    public String toString() {
        return  "id: " + id +
                "\nname: " + name  +
                "\nsurname: " + surname +
                "\nfatherName: " + fatherName +
                "\nemail:" + email+
                "\nphoneNumber: " + phoneNumber;
    }

    public void JSToOb() throws IOException {

        Gson gson = new Gson();
        Type classroomType = new TypeToken<School>(){}.getType();
        School school = gson.fromJson(new FileReader("studentJson.json"), classroomType);
        allStudents = school.getStudents();
    }

    public  void createStudent(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please, enter student name: ");
        String name = sc.nextLine();
        System.out.print("Please, enter student surname: ");
        String surname = sc.nextLine();
        System.out.print("Please, enter student father name: ");
        String fatherName = sc.nextLine();
        System.out.print("Please, enter student email: ");
        String email =  sc.nextLine();
        System.out.print("Please, enter student phone number: ");
        String phoneNumber = sc.nextLine();
        Student newStudent = new Student((int)(Math.random()*6500),name,surname,fatherName,email,phoneNumber);
        allStudents.add(newStudent);
        addJson();
        System.out.print("Student: information");
        showStudent(newStudent);
        return;
    }

    public void updateStudent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id");
        String id = scanner.next();
        if(!checkDigit(id)){
            System.out.println("Enter only digit!!");
            updateStudent();
        }
        Optional<Student> findStudent = allStudents.stream().filter(w->w.id==Integer.parseInt(id)).findFirst();
        if (!findStudent.isEmpty()){
            showStudent(findStudent.get());
            System.out.println("Do you want to change name?\n1.Yes\n2.No");
            if (isChange()){
                System.out.println("Enter New Name:");
                String en = scanner.next();
                findStudent.get().setName(en);
            }
            System.out.println("Do you want to change surname?\n1.Yes\n2.No");
            if (isChange()){
                System.out.print("Enter New Surname: ");
                String en =  scanner.next();
                findStudent.get().setSurname(en);
            }
            System.out.println("Do you want to change father name?\n1.Yes\n2.No");
            if (isChange()){
                System.out.print("Enter New Father Name: ");
                String en =  scanner.next();
                findStudent.get().setFatherName(en);
            }
            System.out.println("Do you want to change email?\n1.Yes\n2.No");
            if (isChange()){
                System.out.print("Enter New Email: ");
                String en =  scanner.next();
                findStudent.get().setEmail(en);
            }
            System.out.println("Do you want to change phone number?\n1.Yes\n2.No");
            if (isChange()){
                System.out.print("Enter New phone: ");
                String en =  scanner.next();
                findStudent.get().setPhoneNumber(en);
            }
            addJson();
            return;
        }
        else {
            System.out.println("There is no any student with this ID");
            updateStudent();
        }

    }

    public void removeStudent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id");
        String id = scanner.next();
        if(!checkDigit(id)){
            System.out.println("Enter only digit!!");
            removeStudent();
        }
        Optional<Student> findStudent = allStudents.stream().filter(w->w.id==Integer.parseInt(id)).findFirst();
        if (!findStudent.isEmpty()){
            allStudents.remove(findStudent.get());
            addJson();
        }
        else {
            System.out.println("There is no any student with this id!!");
            removeStudent();
        }
        return;
    }

    public void showStudents(){
        allStudents.forEach(w-> {
            System.out.println("------------------");
            System.out.println(w.toString());
            System.out.println("------------------");
        });
    }

    public void showStudent(Student st){
        System.out.println(st.toString());
    }

    public void addJson(){
        School school = new School();
        school.setStudents(allStudents);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("studentJson.json")) {
            gson.toJson(school, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isChange(){
        Scanner scanner = new Scanner(System.in);
        String entered = scanner.nextLine();
        if (!checkDigit(entered)){
            System.out.println("Please, enter correct option!!");
            isChange();
        }
        else {
            int enteredInt = Integer.parseInt(entered);
            if (enteredInt==1){
               return true;
            }else if(enteredInt==2){
                return false;
            }
            else {
                System.out.println("Please, enter correct option!!");
                isChange();

            }
        }
        return false;
    }

    boolean checkDigit(String scan){
        Pattern pattern = Pattern.compile("[1-9]+");
        Matcher matcher = pattern.matcher(scan);
        return matcher.matches();
    }
}
