package org.example.Server;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Models.Matrix;
import org.example.Models.Pair;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@NoArgsConstructor
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public Server(ServerSocket socket){
        this.serverSocket = socket;
    }
    void initialise() throws IOException{
        clientSocket = serverSocket.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }
    void endWorkWithClient() throws IOException{
        out.close();
        in.close();
    }
    public void startServer(){
        try {
            do{
                initialise();
                if(workWithClient() == 0){
                    endWorkWithClient();
                    break;
                }
            }
            while(true);
        }
        catch (IOException | ClassNotFoundException excp)
        {
            excp.printStackTrace();
        }
        finally {
            close();
        }
    }
    private Pair<Integer> calculate(Matrix matrix){
        int[][] numbers = matrix.getMatrix();
        Pair<Integer> intPair = new Pair<>(0, 0);
        int min = numbers[0][0];
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (numbers[i][j] < min){
                    intPair.setFirstElement(i);
                    intPair.setSecondElement(j);
                    min =  numbers[i][j];
                }
            }
        }
        return intPair;
    }
    private int workWithClient() throws IOException, ClassNotFoundException{
        int userInput = Integer.parseInt(readMassage());
        switch (userInput) {
            case 0 -> System.out.println("client exited");
            case 1 -> {
                Matrix matrix = (Matrix) in.readObject();
                System.out.println(matrix);
                Pair<Integer> integerPair = calculate(matrix);
                sendMessage("The smallest number has next coordinates: row " + integerPair.getFirstElement()
                        + " column " + integerPair.getSecondElement());
            }
        }
        return userInput;
    }
    private void close(){
        try {
            clientSocket.close();
            serverSocket.close();
        }
        catch (IOException excp)
        {
            excp.printStackTrace();
        }
    }
    private String readMassage() throws IOException{
        String str = "";
        try {
            str = in.readUTF();
        }
        catch (EOFException ignored) {}
        return str;
    }

    private void sendMessage(String message) throws IOException{
        out.writeUTF(message);
        out.flush();
    }

}
