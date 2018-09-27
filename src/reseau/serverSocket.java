package reseau;

import model.Addresse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class serverSocket {

    public static void main(String args[]) throws IOException {

        final int portNumber = 80;
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);

        while(true) {

            //-----------Variables-----------//
            Socket socket = serverSocket.accept();
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            String strOutput,strExit;
            BufferedReader br;
            System.out.println("The client is connected");
            //-------------------------------//

            //-----------First Question-----------//
            pw.println("What's you name ?");
            br = new BufferedReader(new InputStreamReader (socket.getInputStream()));
            strOutput = br.readLine();

            if ("exit".equalsIgnoreCase(strOutput)) {
                do{
                    System.out.println("The client wants exit");
                    pw.println("Do you want exit ?");
                    strExit = br.readLine();

                    if("yes".equalsIgnoreCase(strExit)) {
                        System.out.println("The client is disconnected");
                        socket.close();
                        pw.close();
                        break;
                    }
                    System.out.println("Finally,the client doesn't wants exit");
                    pw.println("What's you name ?");
                    br = new BufferedReader(new InputStreamReader (socket.getInputStream()));
                    strOutput = br.readLine();
                }while("exit".equalsIgnoreCase(strOutput));
            }
            System.out.println("The client name is "+strOutput);

            //---------------------------------------//

            //-----------Second Question-----------//
            pw.println("Hello, How are you ?");
            strOutput = br.readLine();

            if("exit".equalsIgnoreCase(strOutput)) {
                do {//
                    System.out.println("The client wants exit");
                    pw.println("Do you want exit ?");
                    strExit = br.readLine();

                    if("yes".equalsIgnoreCase(strExit)) {
                        System.out.println("The client is disconnected");
                        socket.close();
                        pw.close();
                        break;
                    }

                    System.out.println("Finally,the client doesn't exit");
                    pw.println("So, How are you ?");
                    strOutput = br.readLine();
                }while("exit".equalsIgnoreCase(strOutput));
            }
            System.out.println("The client is "+strOutput);

            //---------------------------------------//

            //-----------Third Question-----------//
            pw.println("Where do you from?");
            strOutput = br.readLine();

            if("exit".equalsIgnoreCase(strOutput)) {
                do { //
                    System.out.println("The client wants exit");
                    pw.println("Do you want exit ?");
                    strExit = br.readLine();

                    if("yes".equalsIgnoreCase(strExit)) {
                        System.out.println("The client is disconnected");
                        socket.close();
                        pw.close();
                        break;
                    }
                    System.out.println("Finally,the client doesn't exit");
                    pw.println("So, Where do you from ?");
                    strOutput = br.readLine();
                }while("exit".equalsIgnoreCase(strOutput));
            }


            //---------------------------------------//

            //-----------Close-----------//
            pw.close();
            socket.close();
            //Addresse ad = new Addresse();
        }
    }
}
