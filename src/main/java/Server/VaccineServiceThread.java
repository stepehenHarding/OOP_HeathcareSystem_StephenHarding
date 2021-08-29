package Server;

import Core.VaccineRegService;
import Client.Request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class VaccineServiceThread extends Thread {
    private Socket dataSocket;
    private Scanner input;
    private PrintWriter output;
    private int number;

    public VaccineServiceThread(ThreadGroup group, String name, Socket dataSocket, int number) {
        super(group, name);
        try {
            this.dataSocket = dataSocket;
            this.number = number;
            input = new Scanner(new InputStreamReader(this.dataSocket.getInputStream()));
            output = new PrintWriter(this.dataSocket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void run() {
        String incomingMessage = "";
        String response;

        try {
            while (true) {

                response = null;

                //get input from the client
                incomingMessage = input.nextLine();
                System.out.println("Received message " + incomingMessage);

                //process the input and generate response
                String[] components = incomingMessage.split(VaccineRegService.COMMAND_SEPARATOR);

                if (components[0].equals(new Request(VaccineRegService.REGISTER).toString())) {
                    String ValidEvent = input.nextLine();
                    System.out.println("Recieved Message: " + ValidEvent);


                }
            }
        }catch(NoSuchElementException nse)
        {
            System.out.println(nse.getMessage());
        }
        finally
        {
            try
            {
                System.out.println("\n Closing connection with client #" + number + "...");
                dataSocket.close();
            }
            catch(IOException ioe)
            {
                System.out.println("Unable to disconnect: " + ioe.getMessage());
                System.exit(1);
            }
        }
    }
}
