package org.example;
import java.util.LinkedList;
import java.util.List;
public class App 
{
    public static void main( String[] args )
    {
        //DataBase dataBase = new DataBase();
        Student student = new Student();
        student.scan_data();

        System.out.println(student.getIndex_number());
        //dataBase.createRecord("STUDENTS",97765,"Pawel","Winski");
        //dataBase.showRecords("STUDENTS");

        //dataBase.closeConnection();
    }
}
