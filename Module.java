public class Module{

    private int [] moduleMarks = new int[3];

    public void setModule1Mark(int mark){
        this.moduleMarks[0] = mark;
    }

    public void setModule2Mark(int mark){
        this.moduleMarks[1] = mark;
    }

    public void setModule3Mark(int mark){
        this.moduleMarks[2] = mark;
    }

    public int[] getModuleMark(){
        return moduleMarks;
    }

    public int getTotal(){
        return moduleMarks[0] + moduleMarks[1] + moduleMarks[2];
    }

    public int getAverage(){
        return getTotal()/3;
    }

    public String moduleGrade(){

        if(getAverage()>=80){
            return "Distinction";
        }else if(getAverage()>=70){
            return "Merit";
        }else if(getAverage()>=40){
            return "Pass";
        }else{
            return "Fail";
        }
    }
}
