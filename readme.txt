a. W bibliotece może się znajdować maksymalnie 5 czytelników lub 1 pisarz. Aby to osiągnąć, zaimplementowałem semafor, który informuje poszczególne wątki o liczbie ludzi w bibliotece.

b. mvn clean package
   Następnie z poziomu common\target: java -jar .\common-1.0-jar-with-dependencies.jar <ilość czytelników> <ilość pisarzy>

c. Serwer zmniejsza wartość semafora biblioteki w zależności od typu użytkownika, a następnie powiadamia pierwszego klienta w kolejce poprzez zwiększenie wartości semafora klienta, co pozwala mu wykonać swój cykl pracy. Po ukończeniu cyklu pracy, wywoływana jest funkcja finishReading/finishWriting, zwiększająca wartość semafora biblioteki i informująca użytkownika o jego pobycie w bibliotece.