/*package reseau;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class Clientmulti {

    public static void main(String args[]) throws Exception
    {
        final String host = "127.0.0.1";
        final int portnumber = 5000;
        System.out.println("Creating socket to '" + host + "' on port " + portnumber);

        while (true){

            //-----------Variables-----------//
            Socket sck =  new Socket(host,portnumber);
            BufferedReader br = new BufferedReader(new InputStreamReader(sck.getInputStream()));
            PrintWriter out = new PrintWriter(sck.getOutputStream());
            BufferedReader inputBR;
            String StrInput, StrInputExit;
            //-------------------------------//

            //-----------First Question-----------//
            System.out.println("Server : " + br.readLine());
            inputBR = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Client : ");
            StrInput = inputBR.readLine();
            out.print(StrInput);

            if ("exit".equalsIgnoreCase(StrInput)){
                System.out.println("Connection ended by Client");
                break;
            }

            //StrInput = inputBR.readLine();
            //System.out.print("Server : "+StrInput+"\n");

        }

        //sck.close();
        //br.close();
        //out.close();
        //inputBR.close();
    }
}
*/