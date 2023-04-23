package org.example;

import java.util.Scanner;
public class Menu {
    static int option;
    static String
            table="STUDENTS",
            column="INDEX_NUMBER";

    public static void main_menu(DataBase dataBase) {
        Scanner scan = new Scanner(System.in);

        System.out.println(
                """
                        \tSTUDENTS DATABASE MANAGEMENT
                         [1]Check connection\t[2]Show all records
                         [3]Show record\t\t\t[4]Add record
                         [5]Change record\t\t[6]Delete record
                         [7]Close Connection\t[8]Open connection\t[0]Exit""");
        do {
            System.out.println("OPTION: ");
            option = scan.nextInt();
            switch (option) {
                case 1 -> { if (dataBase.isConnected()) System.out.println("Database connected."); }
                case 2 -> dataBase.showAllRecords(table);
                case 3 -> dataBase.showRecords(table, column);
                case 4 -> dataBase.createRecord(table);
                case 5 -> dataBase.update_record(table);
                case 6 -> dataBase.delete_record(table);
                case 7 -> dataBase.closeConnection();
                case 8 -> dataBase.open_connection();
                case 0 -> { System.exit(0); dataBase.closeConnection();}
                default -> System.out.println("Invalid option!");
            }
        } while (option!=0);
    }
}
