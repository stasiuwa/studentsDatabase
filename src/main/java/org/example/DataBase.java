package org.example;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DataBase {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:studentsDataBase";

    private Connection connection;
    private Statement statement;
    private final Scanner scan = new Scanner(System.in);

    public DataBase() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("DataBase driver not found ! ");
            e.printStackTrace();
        }
        open_connection();
    }
    public void open_connection(){
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("DataBase connection failed !");
            e.printStackTrace();
        }
        System.out.println("Database connected.");
    }
    public boolean isConnected(){
        try {
            if (!connection.isClosed()){
                connection.isValid(3);
                return true;
            } else {
                System.out.println("Database disconnected.");
            }

        } catch (SQLException e) {
            System.err.println("Connection error.");
        }
        return false;
    }
    public void createRecord(String table){
        if(!isConnected()) return;
        Student student = new Student();
        student.scan_data();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + table + " VALUES(?,?,?)");
            preparedStatement.setInt(1,student.getIndex_number());
            preparedStatement.setString(2,student.getName());
            preparedStatement.setString(3,student.getSurname());

            preparedStatement.execute();

            System.out.println("Record added.");
        } catch (SQLException e) {
            System.err.println("Inserting data:" +
                    " " + student.getIndex_number() +
                    " " + student.getName() +
                    " " + student.getName() +
                    " - failed.");

            if (e.getErrorCode() == 19) System.err.println("  -  Index number already exists in DataBase");
        }
    }

    public void showAllRecords(String table){
        if(!isConnected()) return;
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
        if ( !list.isEmpty() ) {
            for (Student s : list){ System.out.println(s.toString()); }
        } else System.out.println("No records found.");

    }
    public void showRecords(String table, String column){
        if(!isConnected()) return;
        List<Student> list = new LinkedList<>();

        System.out.println(column + ": ");
        String searched_val = scan.nextLine();
        searched_val = StringUtils.capitalize(searched_val.toLowerCase());
        if ( !Pattern.matches("[0-9]+",searched_val) ) searched_val = "'" + searched_val + "'";

        try {
            int index;
            String name, surname;

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + " LIKE " + searched_val);
            while(resultSet.next()){
                index = resultSet.getInt("INDEX_NUMBER");
                name = resultSet.getString("STUDENT_NAME");
                surname = resultSet.getString("STUDENT_SURNAME");
                list.add(new Student(index,name,surname));
            }
        } catch (SQLException e) {
            System.err.println("Loading data from DataBase failed");
            e.printStackTrace(); }

        if ( !list.isEmpty() ) {
            for (Student s : list){ System.out.println(s.toString()); }
        } else System.out.println("No records found.");
    }
    public void update_record(String table){
        if(!isConnected()) return;
        Student s = new Student();
        System.out.print("Change data for ");
        s.scan_data();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table + " WHERE INDEX_NUMBER = " + s.getIndex_number());
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No record found for index number: " + s.getIndex_number());
                return;
            }
        } catch (SQLException e) { e.printStackTrace();}
        String
                name = "'" + s.getName() + "'",
                surname = "'" + s.getSurname() + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + table + " SET STUDENT_NAME = " + name + ", STUDENT_SURNAME = " + surname + " WHERE INDEX_NUMBER = " + s.getIndex_number()
            );
            preparedStatement.execute();
            System.out.println("Record index number: " + s.getIndex_number() + " updated.");

        } catch (SQLException e) {
            System.err.println("Updating data for index number: " + s.getIndex_number() + "failed.");
            e.printStackTrace();
        }

    }
    public void delete_record(String table){
        if(!isConnected()) return;
        System.out.println("Delete record for index number: ");
        String index_num = scan.nextLine();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + table + " WHERE INDEX_NUMBER=" + Integer.parseInt(index_num));
            preparedStatement.execute();
            System.out.println("Record index number: " + index_num + "deleted.");

        }catch (SQLException e){
            System.out.println("No record found for index number: " + index_num);
            e.printStackTrace();
        }
    }
    public void closeConnection(){
        try{
            if (!connection.isClosed()) connection.close();
            System.out.println("Connection to DataBase closed.");

        } catch (SQLException e) {
            System.err.println("DataBase closing connection failed !");
        }
    }
}
