package org.example;

import static org.example.Menu.main_menu;

public class App
{
    public static void main( String[] args )
    {
        DataBase dataBase = new DataBase();
        main_menu(dataBase);
    }
}
