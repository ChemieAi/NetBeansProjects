/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.muzayedesistemi;

/**
 *
 * @author burak
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
 private ServerSocket serverSocket;
    private List<AuctionSession> sessions;
    private List<ClientHandler> clients;
    private AdminInterface adminInterface;
    
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            sessions = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Server started...");
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // Handle client in a separate thread
                ClientHandler clientHandler = new ClientHandler(clientSocket, sessions, clients, adminInterface);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int portNumber = 9999;
        List<AuctionSession> sessions = new ArrayList<>();
        List<ClientHandler> clients = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is running on port " + portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // Yönetici arayüzünü oluştur ve sunucuya bağlan
                AdminInterface adminInterface = new AdminInterface(clientSocket);

                // Yeni bir ClientHandler örneği oluştur
                ClientHandler clientHandler = new ClientHandler(clientSocket, sessions, clients, adminInterface);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

                // Yönetici arayüzünü işlemek için ayrı bir thread başlat
                Thread adminThread = new Thread(new AdminHandler(clientSocket, sessions, adminInterface));
                adminThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}