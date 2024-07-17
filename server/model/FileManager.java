package server.model;

import shared.model.Event;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {
    private static final String FILE_PATH = "server/data/events.dat";

    public static void saveEvent(Event event) {
        FileOutputStream fileOut;
        ObjectOutputStream objectOut;

        try {
            fileOut = new FileOutputStream(FILE_PATH);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(event);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Error saving event: " + e.getMessage());
        }
    }

    public static Event loadEvent() {
        FileInputStream fileIn;
        ObjectInputStream objectIn;
        Event event = null;

        try {
            fileIn = new FileInputStream(FILE_PATH);
            objectIn = new ObjectInputStream(fileIn);
            event = (Event) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading event: " + e.getMessage());
        }
        return event;
    }
}
