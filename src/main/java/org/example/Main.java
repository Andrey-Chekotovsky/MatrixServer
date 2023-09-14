package org.example;

import org.example.Server.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    static int PORT = 4448;
    public static void main(String[] args) {
        try {
            new Server(new ServerSocket(PORT)).startServer();
        }
        catch (IOException excp){
            excp.printStackTrace();
        }
    }
}