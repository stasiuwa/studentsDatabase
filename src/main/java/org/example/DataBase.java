package org.example;

import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:studentsDataBase";

    private Connection connection;
    private Statement statement;

    public DataBase() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("DataBase driver not found ! ");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("DataBase connection failed !");
            e.printStackTrace();
        }
        System.out.println("Connection do DataBase succeed.");
    }
    public boolean createRecord(String table, int index, String name, String surname){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + table + " VALUES(?,?,?)");
            preparedStatement.setInt(1,index);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,surname);

            preparedStatement.execute();

            System.out.println("Record added.");
        } catch (SQLException e) {
            System.err.println("Inserting data:" + index + " " + name + " " + surname + " - failed.");
            if (e.getErrorCode() == 19) System.err.println("    Index number already exists in DataBase");
            //e.printStackTrace();
        }
        return true;
    }

    public void showRecords(String table){
        List<Student> list = new LinkedList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);
            int index;
            String name, surname;
            while(resultSet.next()){
                index = resultSet.getInt("INDEX_NUMBER");
                name = resultSet.getString("STUDENT_NAME");
                surname = resultSet.getString("STUDENT_SURNAME");
                list.add(new Student(index,name,surname));
            }
        } catch (SQLException e) {
            System.err.println("Loading data from DataBase failed");
            e.printStackTrace();
        }
        for (Student s : list){
            System.out.println(s.getIndex_number() + " " + s.getName() + " " + s.getSurname());
        }
    }
    public void showRecord(String table, String search){

    }
    public void closeConnection(){
        try{
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("DataBase closing connection failed !");
        }
        System.out.println("Connection do DataBase closed.");
    }
}
