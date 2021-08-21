package Server;

import Core.VaccineRegServiceDetails;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class VaccineServer
{
    public static void main(String[] args)
    {
        ServerSocket listeningSocket = null;
        Socket dataSocket = null;

        try
        {
            listeningSocket = new ServerSocket(VaccineRegServiceDetails.LISTENING_PORT);
            ThreadGroup clientGroup = new ThreadGroup("Client threads");
            //Place more emphasis on accepting clients than processing them
            //by setting their priority to be one less than the main thread
            clientGroup.setMaxPriority(Thread.currentThread().getPriority()-1);

            boolean continueRunning=true;
            int threadCount=0;

            while(continueRunning)
            {
                dataSocket = listeningSocket.accept();

                threadCount++;
                System.out.println("The server has now accepted"+threadCount+"clients");

                VaccineServiceThread newClient = new VaccineServiceThread(clientGroup,dataSocket.getInetAddress()+"", dataSocket, threadCount);
                newClient.start();
            }
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        finally
        {
            try
            {
                if(dataSocket != null)
                {
                    dataSocket.close();
                }
                if(listeningSocket != null)
                {
                    listeningSocket.close();
                }
            }
            catch (IOException e)
            {
                System.out.println("Unable to disconnect " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
