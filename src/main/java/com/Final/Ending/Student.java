package com.Final.Ending;


public class Student {
   private String name;
   private String clas;




    public Student(){

    }


    public Student( String name, String clas){
        this.name = name;
        this.clas = clas;
    }
    public Student( String name){
        this.name = name;
        this.clas = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }
}
