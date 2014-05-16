/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package photosort;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author eccomania
 */
public class OnePhotoSort {
    
    public static void main(String[] args) throws IOException {
        
        File jpegFile = new File("C:/Users/eccomania/Desktop/Testbilder/123.jpg");

        //Metadaten auslesen
        Metadata metadata = null;
        
        if (jpegFile != null) {
            try {
                metadata = JpegMetadataReader.readMetadata(jpegFile);
            }catch (JpegProcessingException ex) {
                ex.printStackTrace();
            }   // iterate through metadata directories
                if (metadata != null){
                    com.drew.metadata.exif.ExifSubIFDDirectory directory =
                            metadata.getDirectory
                                (com.drew.metadata.exif.ExifSubIFDDirectory.class);
                    try {
                        Date date = directory.getDate
                        (com.drew.metadata.exif.ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

                        // Bilder-Datum in Namen konvertieren
                        Date tempdate = date;

                        // Counter für laufende Nummer erstellen
                        Counter counter = new Counter();

                        // Format für Datum festlegen
                        DateFormat df = new SimpleDateFormat("yyyy_MM_dd, HH_mm_ss");

                        // Datum der Bilder holen und unter pictureDate speichern
                        String pictureDate = df.format(tempdate);

                        // Das Datum des Bildes im gewünschten Format ausgeben
                        // + laufende Nummer
                        System.out.println("Date of picture: " + pictureDate + " "
                                + counter.id);
                        
                        // Bild umbenennen
                        File newName = new File(jpegFile.getParentFile() + "/" + pictureDate + ".jpg");
                                                
                        System.out.println("Name vor Rename: " + jpegFile.getName());
                        
                        System.out.println("Gewünschter Name nach Rename: " + pictureDate + ".jpg");

                        if (jpegFile.renameTo((newName))) {
                            System.out.println("Renamed");
                        } else {
                            System.out.println("Error");
                        }
                        
                        System.out.println("Name nach Rename: " + newName.getName());
                        
                        
                    } catch (Exception exp) {
                        exp.toString();
                    }
                }
        }
    }
}