package photosort;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.lang.Object;
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
        
        // Auslesen von Bildern eines Ordners in eine Liste
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

//            System.out.println("Anzahl Unterordner: " + anzahlunterordner
//                    + ", Anzahl Bilder: " + anzahlbilder);
            }

        //Metadaten auslesen
        Metadata metadata = null;
        
        Integer zaehler = 0;

        if (fileArray != null) {
            for (File fileArray1 : fileArray) {
                try {
                    metadata = JpegMetadataReader.readMetadata(fileArray1);
                } catch (JpegProcessingException ex) {
                    ex.printStackTrace();
                }// catch (IOException e) {
//                    e.printStackTrace();
//                }
                
                // iterate through metadata directories
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
                        
                        // Bild umbenennen durch 'move'
                        File newName = new File(fileArray1.getParentFile() + "/" + pictureDate + ".jpg");
                                                
                        // System.out.println("Name vor Rename: " + fileArray1.getName());
                        
                        // System.out.println("Gewünschter Name nach Rename: " + pictureDate + ".jpg");

                        if (fileArray1.renameTo((newName))) {
                            System.out.println("Successfully renamed");
                        } else {
                            System.out.println("Error");
                        }
                        
                        // System.out.println("Name nach Rename: " + newName.getName());
                        
                        // Jahreszahl des  Bildes entnehmen
                        String str = newName.getName();
                        String jahreszahl = new String(str.substring(0, 4));
                        
                        // Zähler, zum Überprüfen, ob alle Bilder umbenannt wurden
                        zaehler++;
                        
                        // Neuen Unterordner anlegen mit Datum; EINMALIG
                        // Bild mit neuem Datum in passenden Unterordner verschieben
                        
                        File yeardir = new File(newName.getParentFile() + "/" + jahreszahl);
                        // system.out: "C:/Users/eccomania/Desktop/Testbilder/JAHRESZAHL"
                        
                        File namefile = new File(str);
                        // system.out: Name des Bildes
                        
                        File newfiledir = new File(yeardir + "/" + namefile);
                        // system.out: "C:/Users/eccomania/Desktop/Testbilder/JAHRESZAHL/Name des Bildes"
                        
                        if(yeardir.isDirectory()){
                            if (newName.renameTo(newfiledir)) {
                                System.out.println("Successfully moved");
                            } else {
                                System.out.println("Error while trying to move");
                            }
                        } else {
                            yeardir.mkdir();
                            // Ordner mit Jahreszahl erstellen, falls noch nicht vorhanden
                            
                            System.out.println("Ordner mit dem Namen '" + jahreszahl + "' wurde erstellt");
                            
                            if (newName.renameTo(newfiledir)) {
                                System.out.println("Successfully moved");
                            } else {
                                System.out.println("Error while trying to move");
                            }
                        }
                        
                        
                        // Sortieren der Bilder nach Name
                        
                        
                    } catch (Exception exp) {
                        exp.toString();
                    }
                }
            }
        }
        // Überprüfen, ob alle Bilder umbenannt wurden und Meldung
        if(zaehler == anzahlbilder) {
            System.out.println("Es wurden alle " + zaehler + " Bilder umbenannt!" );    
        } else {
            System.out.println("Achtung, es konnten nicht alle Bilder umbenannt werden!");
        }
    }
}
