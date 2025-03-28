import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Coursework{

    static Scanner input = new Scanner(System.in); //Allowing the system to get input.
    static Student [] studentsArray = new Student[100]; //Initializing array with dimension 100.
    static int registered = 0; //Initializing the students registered to 0.

    /**
     * Checks for available seats to register students.
     */
    public static void checkSeats(){
        int total = 0; //Initialized to get a count of available seats to register.
        registered = 0; //reset to avoid number of registered being doubled everytime selecting option 1.
        for(int check=0; check < studentsArray.length; ++check){
            if(studentsArray[check]==null){ //Checks whether there are spaces in the studentsArray.
                ++total; //If it is then increments by the variable total by 1.
            }else{
                ++registered; //else increments the students registered by 1.
            }
        }

        if(total==0){
            System.out.println("No seats available.");
        }else{
            System.out.println("Number of available seats: " + total);
        }
    }

    /**
     * Allows the user to register a student to the system and file.
     */
    public static void register(){
        String student_name, student_ID; //Initializing variables before while loops to store in the student object.

        if(registered<studentsArray.length){
            while(true){ //Runs until user inputs a string of alphabets only.
                System.out.print("Enter student name: ");
                student_name = input.next();

                if(student_name.matches("[a-zA-Z]+")){ //Checks whether the student name contains only alphabets.
                    break; //If is true then stops the endless loop.
                }
            }
            input.nextLine();
            while(true){ //Runs until user inputs as given in example.
                System.out.print("Enter student ID(eg:w1234567): ");
                student_ID = input.nextLine();

                if(student_ID.matches("[Ww]\\d{7}")){ //Checks if the student ID first character is w/W and has 7 digits.
                    break;
                }
            }

            Student student = new Student(student_ID, student_name) ;
            studentsArray[registered] = student;
            registered++; //increments the students registered by 1 everytime when user selects option 2 if and only if registered is less than 100.
            storeToFile(); //Saves the entered details to the file.

            System.out.println("Student registered successfully.");
        }else{
            System.out.println("No seats can be registered as the maximum registration is " + registered);
        }
    }

    /**
     * Deletes the student when entered ID matched with the existing ID.
     */
    public static void delete(){

        String stud_ID;
        while(true){ //Runs until user inputs as given in example.
            System.out.print("Enter student ID to be deleted (eg:w1234567): ");
            stud_ID = input.next();

            if(stud_ID.matches("[Ww]\\d{7}")){ //Checks if the student ID first character is w/W and has 7 digits.
                break;
            }
        }

        boolean found = false; //Initialized to find whether student is found or not.
        for(int x=0; x < registered; ++x){
            if(studentsArray[x]!=null){ //Checks if the studentsArray is not empty.
                if(studentsArray[x].getID().equals(stud_ID)){ //From an index it retrieves student id and checks if it is equal to the input value.
                    studentsArray[x] = null; //To delete it sets to null at the specified index.

                    for(int j=x; j<registered-1; j++){ //Loops to access values of the studentArray.
                        studentsArray[j] = studentsArray[j+1]; //Makes the elements next to the deleted element to fill up the free spaces.
                    }
                    --registered; //After deleting, the student is unregistered.
                    studentsArray[registered] =null; //Last element of the array is deleted.
                    found = true;
                    storeToFile();
                    break;
                }
            }
        }

        if(found){
            System.out.println("Student removed successfully.");
        }else{
            System.out.println("Student ID "+stud_ID+" not found.");
        }
    }

    /**
     * Helps to find a student with a valid student ID.
     */
    public static void find(){
        String search;
        while(true){ //Runs until user inputs as given in example.
            System.out.print("Enter student ID to find (eg:w1234567): ");
            search = input.next();

            if(search.matches("[Ww]\\d{7}")){ //Checks if the student ID first character is w/W and has 7 digits.
                break;
            }
        }

        boolean found = false;
        for(int j=0; j<registered; j++){ //Access every element with index of studentArray.
            if(studentsArray[j].getID().equals(search)){ //Checks if search is equal to student ID in the array.
                System.out.println("Student name of student_ID " + search + " is " + studentsArray[j].getName());
                found = true;
                break; //If found is true then breaks out of the loop to avoid accessing all the elements.
            }
        }

        if(found){
            System.out.println("Student found.");
        }else{
            System.out.println("Student not found with student_ID " + search);
        }
    }

    /**
     * Stores the students detail(name and ID) to the file named studentDetails.txt
     */
    public static void storeToFile(){
        try{
            FileWriter file = new FileWriter("studentDetails.txt"); //studentDetails file is created.
            for(int count=0; count<registered; count++){
                if(studentsArray[count]!=null){ //Determines the element of studentArray is not empty to store.
                    file.write(studentsArray[count].getID() + " " + studentsArray[count].getName() + " "); //Stores the ID and name of the student next to each other to the file.
                    int[] marks = studentsArray[count].getModule().getModuleMark(); //Sets the module marks to marks array.
                    file.write(marks[0] + " " + marks[1] + " " + marks[2] + "\n"); //Write the marks of indexes 0,1 and 2 to the file.
                }
            }
            file.close(); //File is closed after writing every element of the student array to the file.
        }catch(IOException e){ //Prints the message if the file has any issue.
            System.out.println("Error raised while storing the student details.");
        }
    }

    /**
     * Loads the file studentDetails to the system
     */
    public static void loadToSystem(){
        try {
            File file = new File("studentDetails.txt"); //Searches the file for the given file name.
            Scanner fileReader = new Scanner(file); //Scans the file.

            while (fileReader.hasNextLine()) { //Checks the file for data at each line.
                String line = fileReader.nextLine(); //Assigns each line to the variable line.
                String[] dataArray = line.split(" "); //Line is split by space and stored to array called dataArray.
                Student object= new Student(dataArray[0], dataArray[1]); //An object is created as a reference to Student class and value at index 0, 1 is stored to object.

                int module1Mark = Integer.parseInt(dataArray[2]); //After storing module marks which stores as string in the file is type-casted to integer.
                int module2Mark = Integer.parseInt(dataArray[3]);
                int module3Mark = Integer.parseInt(dataArray[4]);

                studentsArray[registered] = object; //Created object is now stored at an index of studentsArray.

                object.getModule().setModule1Mark(module1Mark); //set the module 1 marks to moduleMarks array at index 0 in the Module class.
                object.getModule().setModule2Mark(module2Mark); //set the module 2 marks to moduleMarks array at index 1 in the Module class.
                object.getModule().setModule3Mark(module3Mark); //set the module 3 marks to moduleMarks array at index 2 in the Module class.

                registered++; //Increments while the file has lines and makes a note to registered variable which is required when checkSeats() is called.
            }
            fileReader.close(); //Close the fileReader after loading.
            System.out.println("Student details loaded successfully.");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Used to view list of student name in ascending order with student ID.
     */
    public static void view(){

        System.out.println("Student Details");
        Student swap; //Initialized to have a reference of the element to be changed.

        for(int view=0; view<registered; view++){ //used to have a count.
            for(int sort=0; sort<registered; sort++){ //used to access the elements by index of the studentsArray
                if(studentsArray[sort]!=null && studentsArray[sort+1]!=null && studentsArray[sort].getName().compareTo(studentsArray[sort+1].getName())>0){
                    //check for index at a place and next to it not being empty and compares the names to sort from A to Z.
                    swap = studentsArray[sort];
                    studentsArray[sort] = studentsArray[sort+1];
                    studentsArray[sort+1] = swap;
                }
            }
        }

        for(int sortedData=0; sortedData<registered; sortedData++){ //used to print the sorted data in ascending order
            if(studentsArray[sortedData]!=null){
                System.out.println("Student "+ (sortedData+1));
                System.out.println("Student ID: "+studentsArray[sortedData].getID() + " , " + "Student name: "+ studentsArray[sortedData].getName());
                System.out.println();
            }
        }
    }

    /**
     * Updates the student name if there is a mistake.
     */
    public static void addStudent(){
        String id;
        int i;
        while(true){ //Runs until user inputs as given in example below.
            System.out.print("Enter student id(eg:w1234567): ");
            id = input.next();

            if(id.matches("[Ww]\\d{7}")){ //Checks if the student ID first character is w/W and has 7 digits.
                break;
            }
        }
        for(i=0; i<registered; i++){
            if(studentsArray[i]!=null && studentsArray[i].getID().equals(id)){ //checks the id is equal to the id in the array.
                System.out.print("Enter student name: ");
                String name = input.next();
                studentsArray[i].setName(name); //set the entered name to an index of the studentsArray.
                System.out.print("Student updated successfully.");
                System.out.println();
                storeToFile();
                break; //breaks only if studentsArray is not empty and input ID is equal to the ID existed in array.
            }
        }
        if(i==registered){
            System.out.println("No such student found with id: "+id);
        }
    }

    /**
     * Sets the marks for each module.
     */
    public static void moduleMarks(){

        System.out.print("Enter student ID(eg:w1234567): ");
        String id = input.next();
        boolean found = false;
        for(int i=0; i<registered; i++){
            if(studentsArray[i]!=null && studentsArray[i].getID().equals(id)){
                while(true) {
                    try {
                        found = true;
                        System.out.print("Enter marks for module 1 : ");
                        int module1 = input.nextInt();
                        if(module1<=0 || module1>=100){
                            System.out.println("Marks should be between 0 and 100");
                            continue;
                        }
                        studentsArray[i].getModule().setModule1Mark(module1);
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("Required type is integer.");
                        input.nextLine();
                    }
                }

                while(true){
                    try {
                        System.out.print("Enter marks for module 2 : ");
                        int module2 = input.nextInt();
                        if(module2<=0 || module2>=100){
                            System.out.println("Marks should be between 0 and 100");
                            continue;
                        }
                        studentsArray[i].getModule().setModule2Mark(module2);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Required type is integer.");
                        input.nextLine();
                    }
                }

                while(true) {
                    try {
                        System.out.print("Enter marks for module 3 : ");
                        int module3 = input.nextInt();
                        if(module3<=0 || module3>=100){
                            System.out.println("Marks should be between 0 and 100");
                            continue;
                        }
                        studentsArray[i].getModule().setModule3Mark(module3);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Required type is integer.");
                        input.nextLine();
                    }
                }
                System.out.println("Marks of the student " + studentsArray[i].getName() + " stored to the file successfully.");
                storeToFile();
                break;
            }
        }
        if(!found){
            System.out.println("Student not found with ID "+id);
        }
    }

    /**
     * Provides a summary of total number of registrations and students who scored more than 40 in module 1,2 and 3.
     */
    public static void generateSummary(){
        System.out.println("Total number of student registrations: " + registered);

        int studentCount = 0; //assigned to get a count of students scored > 40 in all 3 modules.
        int forModule1 = 0; //assigned to get a count of students scored > 40 in module 1.
        int forModule2 = 0; //assigned to get a count of students scored > 40 in module 2.
        int forModule3 = 0; //assigned to get a count of students scored > 40 in module 3.
        for (int i = 0; i < registered; i++) {
            int[] marks = studentsArray[i].getModule().getModuleMark(); //stores the marks to the marks array after retrieving from the moduleMarks array.

            if(marks[0]>40){
                ++forModule1;
            }
            if(marks[1]>40){
                ++forModule2;
            }
            if(marks[2]>40){
                ++forModule3;
            }
            if(marks[0]>40 && marks[1]>40 && marks[2]>40){
                ++studentCount;
            }
        }
        System.out.println("Total number of students who scored more than 40 in all 3 modules: "+studentCount);
        System.out.println("Total number of students who scored more than 40 in Module 1 : "+forModule1);
        System.out.println("Total number of students who scored more than 40 in Module 2 : "+forModule2);
        System.out.println("Total number of students who scored more than 40 in Module 3 : "+forModule3);
    }

    /**
     * Generates a report of each student with name, ID, module marks, total, average and module grade.
     */
    public static void generateReport(){
        Student swap;
        for(int i=0; i<registered-1; ++i){ //maintains a count.
            for(int j=0; j<registered-i-1; ++j){ //bubble sorting.
                if(studentsArray[j]!=null && studentsArray[j].getModule().getAverage() < studentsArray[j+1].getModule().getAverage()){
                    swap = studentsArray[j];
                    studentsArray[j] = studentsArray[j+1];
                    studentsArray[j+1] = swap;
                }
            }
        }

        for(int student=0; student<registered; student++){
            if(studentsArray[student]!=null){
                System.out.println();

                System.out.println("        Student "+ (student+1));
                System.out.println("Student_ID: "+studentsArray[student].getID());
                System.out.println("Student_name: " + studentsArray[student].getName());
                int [] marks = studentsArray[student].getModule().getModuleMark();
                System.out.println("Module_1_marks: "+marks[0]);
                System.out.println("Module_2_marks: "+marks[1]);
                System.out.println("Module_3_marks: "+marks[2]);
                System.out.println("Total: "+studentsArray[+student].getModule().getTotal());
                System.out.println("Average: "+studentsArray[student].getModule().getAverage());
                System.out.println("Module_Grade: "+studentsArray[student].getModule().moduleGrade());
            }
        }
    }


    public static void main(String[] args) { //automatically runs the program without calling the method.

        while(true){
                System.out.println();
                System.out.println("                ----------Student Management System----------");
                System.out.println();

                System.out.println("1.- Check available seats");
                System.out.println("2.- Register student(with ID)");
                System.out.println("3.- Delete student");
                System.out.println("4.- Find student(with ID)");
                System.out.println("5.- Store student details into a file and exit");
                System.out.println("6.- Load student details from the file to the system -- Make sure you load the file to the program only once.");
                System.out.println("7.- View the list of students based on their names");
                System.out.println("8.- Choose for more options");

                System.out.println();

                System.out.print("Enter an option: ");
            try{
                int option = input.nextInt();
                input.nextLine(); //consumes the next line.

                switch(option){
                    case 1:
                        checkSeats();
                        break;

                    case 2:
                        register();
                        break;

                    case 3:
                        delete();
                        break;

                    case 4:
                        find();
                        break;

                    case 5:
                        storeToFile();
                        System.out.println("Stored and Program exited...");
                        break;

                    case 6:
                        loadToSystem();
                        break;

                    case 7:
                        view();
                        break;

                    case 8:
                        moreOptions();
                        break;

                    default:
                        System.out.println("Invalid option. Try again!");
                }
                if(option==5){
                    break; //This helps the user to quit the program.
                }
            }catch(InputMismatchException e){
                System.out.println("Enter an integer value between 1 and 8");
                input.nextLine();
            }
        }
    }

    /**
     * Defined to go for more (sub) options as user selects option 8 in the main menu.
     */
    public static void moreOptions(){
        while(true){
            System.out.println();
            System.out.println("a. Add student name.");
            System.out.println("b. Module marks.");
            System.out.println("c. Generate a summary of the system.");
            System.out.println("d. Generate a report of student details.");
            System.out.println("z. Return to main menu.");

            System.out.println();

            System.out.print("Select an option: ");
            String sub_option = input.next().toLowerCase(); //converts the Uppercase alphabets to lowercase.

            switch(sub_option) {
                case "a":
                    addStudent();
                    break;

                case "b":
                    moduleMarks();
                    break;

                case "c":
                    generateSummary();
                    break;

                case "d":
                    generateReport();
                    break;

                case "z":
                    break;

                default:
                    System.out.println("Invalid option. Options are a,b,c,d and z");
            }
            if(sub_option.equals("z")){
                break; //when user press "z" it goes back to the main menu.
            }
        }
    }
}