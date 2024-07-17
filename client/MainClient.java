package client;

import java.io.*;
import java.net.*;

import client.gui.ViewEventGUI;
import shared.model.Event;

public class MainClient {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static PrintWriter writer;

    public static void main(String[] args) {
        Event event = new Event();
        ViewEventGUI viewEventGUI = new ViewEventGUI(event);
        viewEventGUI.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection();
            }
        });
        openConnection(viewEventGUI);
    }

    private static void openConnection(ViewEventGUI viewEventGUI) {
        try {
            clientSocket = new Socket("localhost", 1511);
            System.out.println("Connected to server...");
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            Event receivedEvent = (Event) in.readObject();
            viewEventGUI.setUI(receivedEvent);
            viewEventGUI.setClientSocket(reader, writer, in);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection() {
        try {
            System.out.println("Closing connection...");
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
            if (clientSocket != null)
                clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
