package Client;

import Core.Colours;
import Core.Patient;
import Core.Centre;
import Core.VaccineRegService;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.sql.Types.NULL;

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

            //doMenuLoop(socketWriter,socketReader);
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
            System.out.println("\t"+ Colours.GREEN+i+"."+LoginMenu.values()[i].toString()+Colours.RESET);
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

    private void doLoginMenuLoop(int user_id,int centre_id,PrintWriter socketWriter,Scanner socketReader)
    {
        printLoginMenu();
        MainMenu menuOption =getMainMenuOption();

        while(menuOption != MainMenu.LOGOUT)
        {
            switch (menuOption){
                case QUIT_APPLICATION:
                    break;
                case LOGOUT:
                    System.out.println(""+VaccineRegService.CLOSE);
                    System.exit(0);
                    break;
                case DISPLAY_VACCINE_CENTRES:
                    displayAllCentre(socketWriter,socketReader);
                    break;
                case BOOK_VACCINE:
                    bookVaccine(socketWriter,socketReader);
                    break;
                case DISPLAY_VACCINE_APPOINTMENT:
                    displayVaccineAppointment(user_id,socketWriter,socketReader);
                    break;
                case UPDATE_VACCINE_APPOINTMENT:
                    updateVaccineAppointment(user_id,centre_id,socketWriter,socketReader);
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
    private void bookVaccine(PrintWriter socketWriter,Scanner socketReader){
        int user_id=0;
        String email="";
        String password ="";

        boolean input=false;
        while(!input)
        {
            System.out.println("E");
        }
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
           // doLoginMenuLoop(user_id,socketWriter,socketReader);
        }
        else if(response.equals(VaccineRegService.LOGIN_UNSUCCESSFUL))
        {
            doMenuLoop(socketWriter,socketReader);
        }
    }


    private void register(PrintWriter socketWriter, Scanner socketReader)
    {
        int user_id=NULL;
        String email="";
        String password="";

        boolean input =false;
        while(!input)
        {
            System.out.println("Enter in your email");
            email=sc.next();
            if (RegexChecker.isValidEmail(email)){
                input = true;
            }
        }
        input = false;
        while(!input){
            System.out.println("Please enter your password");
            System.out.println("must be 8 characters long");
            System.out.println("at lease one uppercase and lowercase letter");
            System.out.println("and at least 1 number");

            password=sc.next();
            if(RegexChecker.isValidPassword(password)){
                input = true;
            }
        }
        String message ="REGISTER"+VaccineRegService.COMMAND_SEPARATOR+email+VaccineRegService.COMMAND_SEPARATOR+password;
        //String message ="INSERT INTO `user` (`user_id`, `email`, `password`) VALUES (NULL, 'user003@gmail.com', 'Password002@')";

        socketWriter.println(message);
        String response =socketReader.nextLine();
        System.out.println(response);

        if(response.equals(VaccineRegService.REGISTERED)){
            System.out.println("patient registered");
        }
        else if(response.equals(VaccineRegService.REG_FAILED)){
            System.out.println("Already registered ");
        }
    }

    private void displayAllCentre(PrintWriter socketWriter,Scanner socketReader){
        String message ="DISPLAY_ALL";

        socketWriter.println(message);
        String response = socketReader.nextLine();
        if(response == VaccineRegService.DISPLAY_FAILED){
            System.out.println("no centres found");
        }
        else
        {
            String[] components = response.split(VaccineRegService.COMMAND_SEPARATOR);
            for (String s:components){
                System.out.println(s);
            }
        }
    }
    private void displayVaccineAppointment(int user_id,PrintWriter socketWriter,Scanner socketReader)
    {
        String message = "DISPLAY CURRENT"+VaccineRegService.COMMAND_SEPARATOR+user_id;
        socketWriter.println(message);
        String response = socketReader.nextLine();
        int num =1;
        if(response == VaccineRegService.DISPLAY_FAILED){
            System.out.println("NO VACCINE APPOINTMENT FOUND");
        }
        else
        {
            String[]components = response.split(VaccineRegService.COMMAND_SEPARATOR);
            for(int i=0;i<components.length;i++){
                System.out.println(components[i]);
                num++;
            }
        }
    }

    private void updateVaccineAppointment(int user_id,int centre_id,PrintWriter socketWriter,Scanner socketReader)
    {
        System.out.println("Enter user_id");
        int id = sc.nextInt();

        String message = "UPDATE CURRENT"+VaccineRegService.COMMAND_SEPARATOR+user_id+VaccineRegService.COMMAND_SEPARATOR
                +centre_id;
    }


}
