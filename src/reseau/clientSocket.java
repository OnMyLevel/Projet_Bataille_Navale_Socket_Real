package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class clientSocket {

    public static void main(String args[]) throws IOException {

        final String host = "localhost";
        final int portNumber = 81;
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        while (true) {

            //-----------Variables-----------//
            Socket socket = new Socket(host, portNumber);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader InputBR;
            String StrInput,StrInputExit;
            Scanner sc;
            //-------------------------------//

            //-----------First Question-----------//
            System.out.println("server says : " + br.readLine());
            InputBR = new BufferedReader(new InputStreamReader(System.in));
            StrInput = InputBR.readLine();
            out.println(StrInput);

            if ("exit".equalsIgnoreCase(StrInput)) {
                do{//
                    System.out.println("server says : " + br.readLine());
                    StrInputExit = InputBR.readLine();
                    out.println(StrInputExit);

                    if("yes".equalsIgnoreCase(StrInputExit)) {
                        socket.close();
                        break;
                    }
                    System.out.println("server says : " + br.readLine());
                    InputBR = new BufferedReader(new InputStreamReader(System.in));
                    StrInput = InputBR.readLine();
                    out.println(StrInput);
                }while("exit".equalsIgnoreCase(StrInput));
            }
            //---------------------------------------//

            //-----------Second Question-----------//
            System.out.println("server says : " + br.readLine());
            InputBR = new BufferedReader(new InputStreamReader(System.in));
            StrInput = InputBR.readLine();
            out.println(StrInput);

            if ("exit".equalsIgnoreCase(StrInput)) {
                do{//

                    System.out.println("server says : " + br.readLine());
                    StrInputExit = InputBR.readLine();
                    out.println(StrInputExit);

                    if("yes".equalsIgnoreCase(StrInputExit)) {
                        socket.close();
                        break;
                    }
                    System.out.println("server says : " + br.readLine());
                    InputBR = new BufferedReader(new InputStreamReader(System.in));
                    StrInput = InputBR.readLine();
                    out.println(StrInput);
                }while("exit".equalsIgnoreCase(StrInput));
            }
            //---------------------------------------//

            //-----------Third Question-----------//
            System.out.println("server says : " + br.readLine());
            InputBR = new BufferedReader(new InputStreamReader(System.in));
            StrInput = InputBR.readLine();
            out.println(StrInput);

            if ("exit".equalsIgnoreCase(StrInput)) {
                do{//

                    System.out.println("server says : " + br.readLine());
                    StrInputExit = InputBR.readLine();
                    out.println(StrInputExit);

                    if("yes".equalsIgnoreCase(StrInputExit)) {
                        socket.close();
                        break;
                    }
                    System.out.println("server says : " + br.readLine());
                    InputBR = new BufferedReader(new InputStreamReader(System.in));
                    StrInput = InputBR.readLine();
                    out.println(StrInput);
                }while("exit".equalsIgnoreCase(StrInput));
            }
            //---------------------------------------//
        }
    }
}
