package pl.edu.agh.kis.pz1;

import lombok.Data;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa pisarza dziedziczaca po uzytkowniku.
 */
@Data
public class Writer extends RoomUser {
    private static Logger logger = Logger.getLogger(Writer.class.getName());
    /**
     * Czytelnia jest dzielona miedzy uzytkownikami.
     */
    ReadingRoom readingRoom;
    Writer(ReadingRoom readingRoom, boolean isReader) {
        this.readingRoom = readingRoom;
        this.setIsReader(isReader);
    }
    /**
     * Funkcja informujaca o checi wejscia do czytelni.
     */
    @Override
    public void request() {
        logger.log(Level.INFO, "Chce pisac");
    }
    /**
     * Funckja informujaca o opuszczeniu czytelni.
     */
    @Override
    public void finish() {
        logger.log(Level.INFO, "Koncze wlasnie pisac");
        readingRoom.finishWriting(this);
    }
}