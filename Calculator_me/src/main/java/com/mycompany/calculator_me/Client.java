/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.calculator_me;

/**
 *
 * @author burak
 */
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("İşlemi girin (örnek: 2 + 3): ");
                String operation = consoleInput.readLine();
                out.println(operation);

                String response = in.readLine();
                System.out.println("Sunucudan gelen cevap: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
