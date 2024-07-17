package server.model;

import shared.model.Ticket;

import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class TicketManager {
    private static final String FILE_PATH = "server/data/tickets.dat";

    public static void saveTicket(List<Ticket> tickets) {
        FileOutputStream fileOut;
        ObjectOutputStream objectOut;

        try {
            fileOut = new FileOutputStream(FILE_PATH);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(tickets);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Error saving tickets: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Ticket> loadTicket() {
        List<Ticket> tickets = null;

        try {
            FileInputStream fileIn = new FileInputStream(FILE_PATH);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            tickets = (List<Ticket>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading tickets: " + e.getMessage());
        }
        return tickets;
    }

    public static void addTicket(String name, String phone, int showtime, int section, int row, int seat) {
        List<Ticket> tickets = loadTicket();
        if (tickets == null) {
            tickets = new java.util.ArrayList<Ticket>();
        }
        tickets.add(new Ticket(name, phone, showtime, section, row, seat));
        saveTicket(tickets);
    }
}
