package photosort;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoSort {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //File jpegFile = new File("C:/Users/eccomania/Desktop/Testbilder/4.jpg");

        // Auslesen eines Ordners in eine Liste
        File f = new File("C:/Users/eccomania/Desktop/Testbilder");
        File[] fileArray = f.listFiles();

        //Ausgabe aller Dateien im Verzeichnis inkl. Art der Datei (deaktiviert)
        // + counter für Gesamtanzahl
        Integer anzahlbilder = 0;
        Integer anzahlunterordner = 0;

        if (fileArray != null) {
            for (File fileArray1 : fileArray) {
//                System.out.print(fileArray1.getAbsolutePath());
                if (fileArray1.isDirectory()) {
//                    System.out.print(" (Ordner)\n");
                    anzahlunterordner++;
                } else {
//                    System.out.print(" (Datei)\n");
                    anzahlbilder++;
                }
            }

            System.out.println("Anzahl Unterordner: " + anzahlunterordner
                    + ", Anzahl Bilder: " + anzahlbilder);
        }
        //Metadaten auslesen
        Metadata metadata = null;

        if (fileArray != null) {
            for (File fileArray1 : fileArray) {
                try {
                    metadata = JpegMetadataReader.readMetadata(fileArray1);
                } catch (JpegProcessingException ex) {
                    ex.printStackTrace();
                }   // iterate through metadata directories
                if (metadata != null) {
                    com.drew.metadata.exif.ExifSubIFDDirectory directory
                            = metadata.getDirectory(com.drew.metadata.exif.ExifSubIFDDirectory.class);
                    try {
                        Date date = directory.getDate(com.drew.metadata.exif.ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

                        // Bilder-Datum in Namen konvertieren
                        Date tempdate = date;

                        // Counter für laufende Nummer erstellen
                        Counter counter = new Counter();

                        // Format für Datum festlegen
                        DateFormat df = new SimpleDateFormat("yyyy_MM_dd, HH_mm_ss");

                        // Datum der Bilder holen und unter pictureDate speichern
                        String pictureDate = df.format(tempdate);

                        // Das Datum des Bilds im gewünschten Format ausgeben
                        // + laufende Nummer
                        System.out.println("Date of picture: " + pictureDate + " "
                                + counter.id);

//                        // FileArray mit Namen der Bilder umbenennen
//                        File tempname = new File(pictureDate);
//                        System.out.println(tempname);
//                        
//                        fileArray1.renameTo(new File(pictureDate));
                        // Bilder im Quellordner umbenennen
                        // Versuch mit STREAMS
                        FileWriter fw = null;
                        try {
                            fw = new FileWriter(fileArray1.getName() + ".jpg");
                            fw.write(pictureDate);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fw != null) {
                                try {
                                    fw.close();
                                } catch (IOException e) {
                                }
                            }
                        }

                    } catch (Exception exp) {
                        exp.toString();
                    }
                }
            }
        }

//        // Hinweis, wenn nicht alle Bilder ausgelesen werden konnten
//        if (anzahlbilder != counter.id) {
//            System.out.println("Es konnten nicht alle Bilder ausgelesen werden.");
//        }
    }
}
