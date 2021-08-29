package Client;

import Core.Colours;
import Core.Patient;
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

            Socket dataSocket = new Socket("localhost", 3306);
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

    private MainMenu getMainMenuOption()
    {
        int option = sc.nextInt();
        MainMenu menuOption =null;
        if(option <0||option >5)
        {
            printLoginMenu();
        }
        else
        {
            menuOption = MainMenu.values()[option];
        }
        return menuOption;
    }

    private void doLoginMenuLoop()
    {
        printLoginMenu();
        MainMenu menuOption =getMainMenuOption();

        while(menuOption != MainMenu.LOGOUT)
        {
            switch (menuOption){
                case QUIT_APPLICATION:
                    break;
                case LOGOUT:
                    break;
                case DISPLAY_VACCINE_CENTRES:
                    break;
                case BOOK_VACCINE:
                    break;
                case DISPLAY_VACCINE_APPOINTMENT:
                    break;
                case UPDATE_VACCINE_APPOINTMENT:
                    break;
            }
            printMainMenu();
            menuOption = getMainMenuOption();
        }
        System.out.println("QUIT APPLICATION");
    }

    private void printLoginMenu()
    {
        for (int i=0;i<MainMenu.values().length;i++)
        {
            System.out.println("\t"+Colours.GREEN + i+ "."+MainMenu.values()[i].toString()+Colours.RESET);
        }
        System.out.println("Enter in option(0 to cancel)>");
    }

    private void login(PrintWriter socketWriter, Scanner socketReader)
    {
        int user_id=0;
        String email="";
        String password ="";

        boolean input = false;
        while(!input)
        {
            System.out.println("Please enter your email ");
            email =sc.next();
            if(RegexChecker.isValidEmail(email)){
                input = true;
            }
        }
        input = false;
        while(!input){
            System.out.println("Please enter your password");
            System.out.println("must be 8 characters long");
            System.out.println("at lease one uppercase and lowercase letter");
            System.out.println("and at least 1 number");
            password =sc.next();
            if (RegexChecker.isValidPassword(password))
            {
                input = true;
            }
        }

        Patient p = new Patient(user_id,email,password);
        String message ="LOGIN"+VaccineRegService.COMMAND_SEPARATOR+p.getEmail()+VaccineRegService.COMMAND_SEPARATOR
                +p.getPassword();

        socketWriter.println(message);
        String response = socketReader.nextLine();
        System.out.println(response);

        if(response.equals(VaccineRegService.LOGIN_SUCCESSFUL)){
            System.out.println("LOGGED IN AS"+email);
            doMenuLoop(socketWriter,socketReader);
        }
        else if(response.equals(VaccineRegService.LOGIN_UNSUCCESSFUL))
        {
            doMenuLoop(socketWriter,socketReader);
        }
    }


    private void register(PrintWriter socketWriter, Scanner socketReader)
    {

    }


}
