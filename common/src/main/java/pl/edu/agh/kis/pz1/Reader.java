package pl.edu.agh.kis.pz1;

import lombok.Data;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa czytelnika dziedziczaca po uzytkowniku.
 */
@Data
public class Reader extends RoomUser {
    private static Logger logger = Logger.getLogger(Reader.class.getName());
    /**
     * Czytelnia jest dzielonym zasobem miedzy innymi uzytkownikami.
     */
    ReadingRoom readingRoom;
    Reader(ReadingRoom readingRoom, boolean isReader) {
        this.readingRoom = readingRoom;
        this.setIsReader(isReader);
    }
    /**
     * Funkcja informujaca o checi wejscia do czytelni.
     */
    @Override
    void request() {
        logger.log(Level.INFO, "Chce czytac");
    }
    /**
     * Funckja informujaca o opuszczeniu czytelni.
     */
    @Override
    public void finish() {
        logger.log(Level.INFO, "Koncze wlasnie czytac");
        readingRoom.finishReading(this);
    }
}