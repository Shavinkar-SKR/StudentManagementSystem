public class Student{

    private String student_ID;
    private String student_name;
    private Module module;

    public Student(String student_ID, String student_name){
        this.student_ID = student_ID;
        this.student_name = student_name;
        this.module = new Module();
    }

    public String getName(){
        return student_name;
    }

    public void setName(String student_name){
        this.student_name = student_name;
    }

    public String getID(){
        return student_ID;
    }

    public void setID(String student_ID){
        this.student_ID = student_ID;
    }

    public Module getModule(){
        return module;
    }
}