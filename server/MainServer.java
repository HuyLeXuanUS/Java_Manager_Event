package server;

import java.io.*;
import java.net.*;

import javax.swing.*;

import server.controller.ServerController;
import server.gui.ServerGUI;

import server.model.FileManager;
import server.model.TicketManager;
import shared.model.Event;

public class MainServer {
    public static void main(String[] args) {
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                launchServer();
            }
        });
        serverThread.start();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                launchGUI();
            }
        });
    }

    private static void launchServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1511);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    System.out.println("Server stopped");
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void launchGUI() {
        ServerGUI serverGUI = new ServerGUI();
        serverGUI.setVisible(true);
        new ServerController(serverGUI);
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                Event event = FileManager.loadEvent();

                out.writeObject(event);
                out.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                String request;
                while ((request = reader.readLine()) != null) {
                    System.out.println("Received from client: " + request);

                    String[] tokens = request.split("\\+");
                    if (tokens[0].equals("Booking")) {
                        Event loadEvent = FileManager.loadEvent();
                        boolean isBooked = loadEvent.bookTicket(Integer.parseInt(tokens[3]),
                                Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                        FileManager.saveEvent(loadEvent);
                        if (isBooked) {
                            TicketManager.addTicket(tokens[1], tokens[2], Integer.parseInt(tokens[3]),
                                    Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]),
                                    Integer.parseInt(tokens[6]));
                            writer.println("Booking success");
                        } else {
                            writer.println("Booking failed");
                        }
                    } else if (tokens[0].equals("Reload")) {
                        Event loadEvent = FileManager.loadEvent();
                        out.writeObject(loadEvent);
                    } else {
                        writer.println("Invalid request");
                    }
                }

                reader.close();
                writer.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
