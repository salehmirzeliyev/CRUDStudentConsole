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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Student {

    public static List<Student> allStudents = new ArrayList<>();
    public  static HashMap<Integer,Student> allStudentsHashMapID = new HashMap<>();
    public  static HashMap<String,Student> allStudentsHashMapName = new HashMap<>();
    public  static HashMap<String,Student> allStudentsHashMapFather = new HashMap<>();
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

    @Override
    public String toString() {
        return  "\nid: " + id +
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
        HashMap<Integer,Student> students = school.getStudents();
        for(Student student: students.values()){
            allStudentsHashMapID.put(student.getId(),student);
            allStudentsHashMapName.put((student.getName()+student.getId()),student);
            allStudentsHashMapFather.put((student.getFatherName()+student.getId()),student);
        }
    }

    public  void createStudent(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please, enter student name: ");
        String name = sc.nextLine();
        System.out.print("Please, enter student surname: ");
        String surname = sc.nextLine();
        System.out.print("Please, enter student father name: ");
        String fatherName = sc.nextLine();
        String email="";
        while (true){
            System.out.print("Please, enter student email: ");
            email =  sc.nextLine();
            if (checkEmail(email))
                break;
        }

        String phoneNumber="";
        while (true){
            System.out.print("Please, enter student phone number: ");
            phoneNumber =  sc.nextLine();
            if (checkPhoneNumber(phoneNumber))
                break;
        }
        Student newStudent = new Student(getRandomId(),name,surname,fatherName,email,phoneNumber);
        allStudentsHashMapName.put((newStudent.getName()+newStudent.getId()),newStudent);
        allStudentsHashMapID.put((newStudent.getId()),newStudent);
        allStudentsHashMapFather.put((newStudent.getFatherName()+newStudent.getId()),newStudent);
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
            System.out.println("Enter correct digit!!");
            updateStudent();
            return;
        }
        Student findStudent = allStudentsHashMapID.get(Integer.parseInt(id));
        if (findStudent!=null){
            showStudent(findStudent);
            System.out.println("Do you want to change name?\n1.Yes\n2.No");
            if (isChange()){
                System.out.println("Enter New Name:");
                String en = scanner.next();
                findStudent.setName(en);
            }
            System.out.println("Do you want to change surname?\n1.Yes\n2.No");
            if (isChange()){
                System.out.print("Enter New Surname: ");
                String en =  scanner.next();
                findStudent.setSurname(en);
            }
            System.out.println("Do you want to change father name?\n1.Yes\n2.No");
            if (isChange()){
                System.out.print("Enter New Father Name: ");
                String en =  scanner.next();
                findStudent.setFatherName(en);
            }
            System.out.println("Do you want to change email?\n1.Yes\n2.No");
            if (isChange()){
                String email =  "";
                while (true){
                    System.out.print("Enter New Email: ");
                    email =  scanner.next();
                    if (checkEmail(email))
                        break;
                }
                findStudent.setEmail(email);
            }
            System.out.println("Do you want to change phone number?\n1.Yes\n2.No");
            if (isChange()){
                String phoneNumber="";
                while (true){
                    System.out.print("Enter New phone: ");
                    phoneNumber =  scanner.next();
                    if (checkPhoneNumber(phoneNumber))
                        break;
                }
                findStudent.setPhoneNumber(phoneNumber);
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
            System.out.println("Enter correct digit!!");
            removeStudent();
        }
        Student findStudent = allStudentsHashMapID.get(Integer.parseInt(id));
        if (findStudent!=null){
            allStudentsHashMapID.remove(Integer.parseInt(id));
            addJson();
        }
        else {
            System.out.println("There is no any student with this id!!");
            removeStudent();
        }
        return;
    }

    public void searchStudent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n1. ID\n2. Name\n3. Father name\n4. Exit");
        String entered = scanner.nextLine();
        if (checkDigit(entered)){
            int enteredInt = Integer.parseInt(entered);
            switch (enteredInt){
                case 1:
                    System.out.println("Enter student's ID");
                    String id = scanner.nextLine();
                    if (checkDigit(id)){
                        Student find = allStudentsHashMapID.get(Integer.parseInt(id));
                        if (find!=null){
                            showStudent(find);
                        }
                        else {
                            System.out.println("Sorry, we cannot find student with this ID!!");
                            searchStudent();
                            return;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter student's name");
                    String name = scanner.nextLine();
                    Stream<Map.Entry<String, Student>> searchResult = allStudentsHashMapName.entrySet().stream().filter(w->w.getKey().toLowerCase().startsWith(name.toLowerCase()));
                    long numberOf = allStudentsHashMapName.keySet().stream().filter(w->w.toLowerCase().startsWith(name.toLowerCase())).count();
                    getStudentsForSearching(searchResult,numberOf);
                    break;
                case 3:
                    System.out.println("Enter student's father name");
                    String fatherName = scanner.nextLine();
                    Stream<Map.Entry<String, Student>> searchRes = allStudentsHashMapFather.entrySet().stream().filter(w->w.getKey().toLowerCase().startsWith(fatherName.toLowerCase()));
                    long numOf = allStudentsHashMapFather.keySet().stream().filter(w->w.toLowerCase().startsWith(fatherName.toLowerCase())).count();
                    getStudentsForSearching(searchRes,numOf);
                    break;
                case 4:
                    return;
                default:
                    searchStudent();
                    break;
            }
        }
    }

    public void getStudentsForSearching(Stream<Map.Entry<String, Student>> searchResult,long numberOf){
        if (numberOf!=0){
            for (Map.Entry<String, Student> ss: searchResult.collect(Collectors.toList())){
                System.out.println("-------------");
                System.out.println(ss.getValue().toString());
            }
        }
        else {
            System.out.println("Sorry, we cannot find student with this name!!");
        }
    }

    public void showStudents(){
        for(Student st: allStudentsHashMapID.values()){
            System.out.println("-----------------");
            System.out.println(st.toString());
        }
    }

    public void showStudent(Student st){
        System.out.println(st.toString());
    }

    public void addJson(){
        School school = new School();
        school.setStudents(allStudentsHashMapID);
        allStudentsHashMapName.clear();
        allStudentsHashMapFather.clear();
        for (Student st: allStudentsHashMapID.values()){
            allStudentsHashMapName.put((st.getName()+st.getId()),st);
            allStudentsHashMapFather.put((st.getFatherName()+st.getId()),st);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("studentJson.json")) {
            gson.toJson(school, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isChangeOrNot;

    boolean isChange(){
        Scanner scanner = new Scanner(System.in);
        String entered = scanner.nextLine();
        if (!checkDigit(entered)){
            System.out.println("Please, enter correct option!!");
            isChange();
            if (isChangeOrNot){
                return true;
            }
            else return false;
        }
        else {
            int enteredInt = Integer.parseInt(entered);
            if (enteredInt==1){
                isChangeOrNot = true;
                return true;
            }else if(enteredInt==2){
                isChangeOrNot = false;
                return false;
            }
            else {
                System.out.println("Please, enter correct option!!");
                isChange();
                if (isChangeOrNot){
                    return true;
                }
                else return false;
            }
        }
    }

    boolean checkDigit(String scan){
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(scan);
        if (scan.length()<9&&matcher.matches()==true){
            return true;
        }
        return false;
    }

    int getRandomId(){
        int rId = (int)(Math.random()*100000);
        boolean isExist = allStudentsHashMapID.containsKey(rId);
        if(isExist){
            getRandomId();
        }
        return rId;
    }

    boolean checkEmail(String email){
        Pattern pattern = Pattern.compile("[a-zA-z|\\\\.|\\d]+@[a-zA-Z|]+[.][a-zA-Z]+");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            System.out.println("Please, enter correct email!!!");
            return matcher.matches();
        }
        return matcher.matches();
    }

    boolean checkPhoneNumber(String phoneNumber){
        Pattern pattern = Pattern.compile("[+]{1}[9]{2}[4]{1}(([5]([0]|[1]|[5]))|([7]([0]|[7]))|([9]([9])))[1-9][0-9]{6}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()){
            System.out.println("Please, enter phone number (Ex: +994(50/51/55/70/77/99)6705569)!!!");
            return matcher.matches();
        }
        return matcher.matches();
    }
}
