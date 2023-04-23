package org.example;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student {
    private int index_number;
    private String
            name,
            surname;
    private final Scanner scan = new Scanner(System.in);
    private final String
            str_regex = "^[A-Za-zĆŁŻąęćłńóżź]*$",
            num_regex = "[1-9][0-9]{4}$";
    public final Pattern
            pattern_name = Pattern.compile(str_regex),
            pattern_num = Pattern.compile(num_regex);


    public Student(int index_number, String name, String surname) {
        this.index_number = index_number;
        this.name = name;
        this.surname = surname;
    }
    public Student(){}
    public void scan_data(){    //wczytanie studenta od użytkownika
        System.out.println("\t\tINSERT STUDENT DATA");
        try {
            System.out.println("Index number:");
            setIndex_number(Integer.parseInt(validation(pattern_num)));

            System.out.println("Name:");
            setName(StringUtils.capitalize(validation(pattern_name)));

            System.out.println("Surname:");
            setSurname(StringUtils.capitalize(validation(pattern_name)));

        } catch (Exception e) {
            System.out.println("student's data loading error");
        }
    }
    public String validation(Pattern pattern){  //weryfikajca wprowadzanych danych
        String temp;
        Matcher m;
        do{
            temp = scan.nextLine();
            m = pattern.matcher(temp);
            if(!m.matches()) System.out.println("Invalid value!");
        }while(!m.matches());

        return temp;
    }


    @Override
    public String toString() {

        return "Student: \t " +
                index_number + "\t" +
                name + '\t' +
                surname;

    }

    public int getIndex_number() {
        return index_number;
    }

    public void setIndex_number(int index_number) {
        this.index_number = index_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
