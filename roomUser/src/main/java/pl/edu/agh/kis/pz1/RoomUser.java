package pl.edu.agh.kis.pz1;

import lombok.Data;
import java.security.SecureRandom;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa uzytkownika dziedziczaca po Thread. Wykonuje cała logike zwiazana z watkami.
 */
@Data
abstract public class RoomUser extends Thread {
    private static Logger logger = Logger.getLogger(RoomUser.class.getName());
    /**
     * Zmienna opisujaca role uzytkownika.
     */
    private boolean isReader;
    /**
     * Zmienna konczaca petle w funkcji run.
     */
    private boolean terminator = true;
    /**
     * Generator liczb pseudolosowych.
     */
    private SecureRandom randomWaitTime = new SecureRandom();
    /**
     * Minimalny czas pobytu czytelnika w czytelni.
     */
    private int minTime = 1000;
    /**
     * Maksymalny czas pobytu czytelnika w czytelni.
     */
    private int maxTime = 5000;
    /**
     * Semafora uzytkowników informujaca watki kiedy moga zaczac dzialanie.
     */
    Semaphore RoomUseremaphore = new Semaphore(0);
    /**
     * Wirtualna funkcja request wykorzystujaca klasy Reader i Writer.
     */
    abstract void request();
    /**
     * Wirtualna funkcja finish wykorzystujaca klasy Reader i Writer.
     */
    void finish() {}
    /**
     * Funkcja run nadpisujaca funkcje w Thread. Wykonuje cykl pracy uzytkownika.
     * Uzytkownik czeka az semafora wyniesie wartosc wieksza od 0 i bedzie mogla zaczac dzialac.
     * Po skonczonej wywolujemy funkcje finish.
     */
    @Override
    public void run() {
        try {
            while (terminator) {
                request();
                RoomUseremaphore.acquire();
                if (isReader) {
                    logger.log(Level.INFO, "Czytam");
                    Thread.sleep(randomWaitTime.nextInt(maxTime));
                } else {
                    logger.log(Level.INFO, "Pisze");
                    Thread.sleep(3000);
                }
                finish();
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "!!Wystapil blad podczas pobytu w czytelni!!");
            Thread.currentThread().interrupt();
        }
    }
    /**
     * Funkcja inkrementujaca wartosc semafory uzytkownikow.
     */
    public void enterReadingRoom() {
        RoomUseremaphore.release();
    }
    /**
     * Getter
     */
    public boolean getIsReader() {
        return isReader;
    }
    /**
     * Setter
     */
    public void setIsReader(boolean isReader) {
        this.isReader = isReader;
    }
}