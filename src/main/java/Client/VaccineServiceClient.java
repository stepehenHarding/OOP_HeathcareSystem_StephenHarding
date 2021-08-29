package Client;

import Core.Colours;
import Core.VaccineRegService;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VaccineServiceClient
{
    private static Scanner sc= new Scanner(System.in);

    public static void main(String[] args){
        System.out.println("Welcome to the Vaccine Service");
        VaccineServiceClient client = new VaccineServiceClient();
        client.start();

    }
    public  void start()
    {
        Scanner in = new Scanner(System.in);
        try {

            Socket dataSocket = new Socket("localhost", 8080);
            System.out.println("connection successful");
            OutputStream os = dataSocket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os,true);

            Scanner socketReader = new Scanner(dataSocket.getInputStream());

            doMenuLoop(socketWriter,socketReader);

        }
        catch (IOException e)
        {
            System.out.println("IOException"+e);
        }
    }

    private void doMenuLoop(PrintWriter socketWriter, Scanner socketReader) {

        printMainMenu();
        LoginMenu menuOption =getNumber();

        while (menuOption != LoginMenu.QUIT_APPLICATION){
            switch (menuOption){
                case REGISTER:
                    System.out.println("Register");
                    register(socketWriter,socketReader);
                    break;

                case LOGIN:
                    System.out.println("LOGIN");
                    login(socketWriter,socketReader);
                    break;
            }
            printMainMenu();
            menuOption =getNumber();
        }
        System.out.println("Quitting Application");
    }

    private void printMainMenu()
    {
        for(int i=0;i<LoginMenu.values().length;i++)
        {
            System.out.println("\t"+ Colours.GREEN+i+"."+MainMenu.values()[i].toString()+Colours.RESET);
        }
        System.out.println("please enter an option (0 to exit)");
    }

    //gets valid main menu number from user
    private LoginMenu getNumber()
    {
        int option= sc.nextInt();
        LoginMenu menuOption = null;
        if(option <0|| option >2)
        {
            printMainMenu();
        }
        else
        {
            menuOption =LoginMenu.values()[option];
        }
        return menuOption;
    }

    private void doLoginMenuLoop()
    {

    }

    private void printLoginMenu()
    {

    }

    private void login(PrintWriter socketWriter, Scanner socketReader)
    {

    }

    private void register(PrintWriter socketWriter, Scanner socketReader)
    {

    }


}
