package pl.edu.agh.kis.pz1;

import lombok.Data;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa reprezentujaca czytelnie.
 */
@Data
public class ReadingRoom {
    private static Logger logger = Logger.getLogger(ReadingRoom.class.getName());
    /**
     * Maksymalna ilosc uzytkownikow w czytelni.
     */
    public static final int MAX_Reader = 5;
    /**
     * Kolejka osob do wejscia.
     */
    private LinkedBlockingQueue < RoomUser > RoomUserQueue = new LinkedBlockingQueue < > ();
    /**
     * Semafora zapewniająca poprawna liczbe czytelnikow i pisarzy w czytelni.
     */
    private Semaphore libSem = new Semaphore(MAX_Reader);
    /**
     * Ilosc czytelnikow.
     */
    public static int readerCount;
    /**
     * Ilosc pisarzy.
     */
    private static int writerCount;
    public static void main(String[] args) {
        try {
            readerCount = Integer.parseInt(args[0]);
            writerCount = Integer.parseInt(args[1]);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "!!Nieprawidlowy typ danych!!");
            System.exit(0);
        }
        logger.log(Level.INFO, "Czytelnia otwarta!");
        ReadingRoom readingRoom = new ReadingRoom();
        readingRoom.openReadingRoom();
    }
    /**
     * Funkcja dodaje podanych przez uzytkownika pisarzy i czytelników oraz przetwarza ich przez funkcje manageRoomUser.
     */
    public void openReadingRoom() {
        for (int i = 0; i < writerCount; i++) {
            Writer writer = new Writer(this, false);
            RoomUserQueue.add(writer);
            writer.start();
        }
        for (int i = 0; i < readerCount; i++) {
            Reader reader = new Reader(this, true);
            RoomUserQueue.add(reader);
            reader.start();
        }
        while (!RoomUserQueue.isEmpty()) {
            manageRoomUser();
        }
    }
    /**
     * Funkcja dzieli uzytkownikow na czytelnikow i pisarzy, nastepnie zajmuje semafore w pelni lub nie. 
     * Nastepnie wykonuje funkcje enterReadingRoom.
     */
    void manageRoomUser() {
        RoomUser roomUser = RoomUserQueue.poll();
        assert roomUser != null;
        if (roomUser.getIsReader()) {
            libSem.acquireUninterruptibly();
        } else {
            libSem.acquireUninterruptibly(MAX_Reader);
        }
        roomUser.enterReadingRoom();
    }
    /**
     * Po skonczonej pracy czytelnika zwalniamy semafore.
     */
    public void finishReading(Reader reader) {
        libSem.release();
        reader.setTerminator(false);
    }
    /**
     * Po skonczonej pracy pisarza zwalniamy semafore.
     */
    public void finishWriting(Writer writer) {
        libSem.release(MAX_Reader);
        writer.setTerminator(false);
    }
}