package photosort;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoSort {

//    static File f;
//    static File[] fileArray1;
    
//    public PhotoSort(String s) {
//        
//        //Übergabe des Strings eines Ordners zum Auslesen der Dateien in ein File-Array
//        f = new File(s);
//        fileArray1 = f.listFiles();
//    }
    
    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args, String argsziel) throws IOException {
        
        //Übergabe des Strings eines Ordners zum Auslesen der Dateien in ein File-Array
        File f = new File(args);
        File[] fileArray1 = f.listFiles();
        
        
        
        runPhotoSort(fileArray1);
    }
    
    public static void runPhotoSort(File[] fa) throws IOException {
        
        //Aufruf des Konstruktors
        File[] fileArray = fa;
        
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
                if(fileArray1.isDirectory()) {
                    continue;
                    // Falls das File ein Unterordner ist, überspringen
                    
                } else {
                    try {
                        metadata = JpegMetadataReader.readMetadata(fileArray1);
                    } catch (JpegProcessingException ex) {
                        ex.printStackTrace();
                        System.out.println("Fehler bei Bild: " + fileArray1.getName());
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
                            //System.out.println("Date of picture: " + pictureDate + " "
                            //      + counter.id);

                            // Bild umbenennen durch 'move'
                            File newName = new File(fileArray1.getParentFile() + "/" + pictureDate + ".jpg");
                            File newNametwo = new File(fileArray1.getParentFile() + "/" + pictureDate);
                            // Für doppelte Bilder (ohne das ".jpg")
                            
                            // system.out: "C:/Users/eccomania/Desktop/Testbilder/BILDDATUM-COUNTER.ID.jpg"
                            
                            //System.out.println("Name vor Rename: " + fileArray1.getName());

                            //System.out.println("Gewünschter Name nach Rename: " + pictureDate + ".jpg");

                            if (fileArray1.renameTo((newName))) {
                                System.out.println("Successfully renamed");
                            } else {
                                System.out.println("Error");
                            }

                            // Jahreszahl des  Bildes entnehmen
                            String str = newName.getName();
                            String strtwo = newNametwo.getName();
                            // Für doppelte Bilder
                            
                            String jahreszahl = new String(str.substring(0, 4));

                            // Zähler, zum Überprüfen, ob alle Bilder umbenannt wurden
                            zaehler++;

                            // Files erstellen zum Verschieben des Bildes
                            File yeardir = new File(newName.getParentFile() + "/" + jahreszahl);
                            // system.out: "C:/Users/eccomania/Desktop/Testbilder/JAHRESZAHL"

                            File namefile = new File(str);
                            File namefiletwo = new File(strtwo);
                            // Für doppelte Bilder
                            
                            // system.out: Name des Bildes

                            File newfiledir = new File(yeardir + "/" + namefile);
                            File newfiledirtwo = new File(yeardir + "/" + namefiletwo);
                            // Für doppelte Bilder
                            
                            // system.out: "C:/Users/eccomania/Desktop/Testbilder/JAHRESZAHL/Name des Bildes"

                            // Bild mit neuem Datum in passenden Unterordner verschieben
                            if(yeardir.isDirectory()){
                                if (newName.renameTo(newfiledir)) {
                                    System.out.println("Successfully moved");
                                } else {
                                    System.out.println("Error while trying to move");
                                    
                                    // Für doppelte Bilder
                                    File filetwo = new File(newfiledirtwo + " (2).jpg");                                    
                                    if (newName.renameTo(filetwo)) {
                                        System.out.println("Successfully moved in second try");    
                                    } else {
                                        System.out.println("Error while trying to move in second try");
                                    }
                                    
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
                        } catch (Exception exp) {
                            exp.toString();
                            System.out.println("Fehler bei Bild: " + fileArray1.getName());
                            continue;
                        }
                    }
                }
            }
        }
                
        // Überprüfen, ob alle Bilder umbenannt wurden, anschließend Meldung ausgeben
        if(zaehler == anzahlbilder) {
            String meldung1 = "Es wurden alle " + zaehler + " Bilder umbenannt.";
            photosort_ui.PhotoSort_UI.Erfolgsmeldung(true, meldung1);
        } else {
            String meldung2 = "Es konnten allerdings nicht alle Bilder umbenannt werden.";
            photosort_ui.PhotoSort_UI.Erfolgsmeldung(false, meldung2);
        }
        
    }
    
    
}