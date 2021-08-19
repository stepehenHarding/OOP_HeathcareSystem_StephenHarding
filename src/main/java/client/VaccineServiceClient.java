package client;

import core.VaccineRegServiceDetails;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VaccineServiceClient
{
    public static void main(String[] args)
    {
        Socket dataSocket = null;
        try
        {
            dataSocket = new Socket("localhost", VaccineRegServiceDetails.LISTENING_PORT);
            OutputStream out =dataSocket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(out));
            InputStream in= dataSocket.getInputStream();
            Scanner input = new Scanner(new InputStreamReader(in));

            Scanner Keyboard = new Scanner (System.in);

            String message = "";
            while (!message.equals(VaccineRegServiceDetails.CLOSE))
            {
                displayMenu();
                int choice = getNumber(Keyboard);
                String response = "";

                if(choice >0&& choice <3)
                {
                    switch(choice)
                    {
                        case 1:

                    }
                }
                else
                {
                    System.out.println("please enter a valid number");
                }
            }
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
//gets valid number from user
    private static int getNumber(Scanner keyboard)
    {
        boolean numberEntered = false;
        int number =0;

        while(!numberEntered)
        {
            try
            {
                number = keyboard.nextInt();
                numberEntered = true;
            }
            catch(InputMismatchException e)
            {
                System.out.println("please enter a valid number");
                keyboard.nextLine();
            }
        }
        keyboard.nextLine();
        return number;
    }

    private static void displayMenu()
    {
        System.out.println();
    }
}
