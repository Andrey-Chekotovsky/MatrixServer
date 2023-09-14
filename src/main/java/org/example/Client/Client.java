package org.example.Client;

import org.example.Models.Matrix;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static private int PORT = 4448;
    static private Socket serverSocket;
    static private ObjectOutputStream out;
    static private ObjectInputStream in;
    static private Scanner scanner = new Scanner(System.in);
    static private void initialise() throws IOException{
        serverSocket = new Socket("127.0.0.1", PORT);
        out = new ObjectOutputStream(serverSocket.getOutputStream());
        in = new ObjectInputStream(serverSocket.getInputStream());
    }
    static public void main(String... args){
        int input = 0;
        try{
            initialise();
            do{
                System.out.println("Send new matrix?(1 - yes, 0 - no)");
                input = scanner.nextInt();
                switch(input){
                    case 0 -> {
                        System.out.println("Goodbye");
                        sendMessage(String.valueOf(input));
                    }
                    case 1 -> {
                        sendMessage(String.valueOf(input));
                        Matrix matrix = initializeMatrix();
                        sendMatrix(matrix);
                        System.out.println(readMassage());
                    }
                }
            }while (input != 0);
        }
        catch (IOException excp){
            excp.printStackTrace();
        }

    }
    static private String readMassage() throws IOException {
        String str = "";
        try {
            str = in.readUTF();
        }
        catch (EOFException ignored) {}
        return str;
    }

    static private void sendMessage(String message) throws IOException{
        out.writeUTF(message);
        out.flush();
    }
    static private void sendMatrix(Matrix matrix) throws IOException{
        out.writeObject(matrix);
    }
    private static Matrix initializeMatrix() {
        System.out.println("Enter rows: ");
        int rows = scanner.nextInt();
        System.out.println("Enter columns: ");
        int columns = scanner.nextInt();
        Matrix matrix = new Matrix(rows, columns);
        matrix.generate();
        System.out.println("Generated matrix:\n" + matrix);
        return matrix;
    }
}
