
package photosort_ui;


import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

//public class PhotoSort_UI{

public class PhotoSort_UI {
    
    public static JFileChooser fcstart;
    public static JFileChooser fcziel;
    public static JLabel label1;
    public static JTextField startpfad;
    public static JTextField zielpfad;
    public static String filename; 
    public static String filenameziel;
    public static JFrame frame;
    public static boolean shouldFill = true;
    public static boolean shouldWeightX = true;
    public static boolean unterordner = false;
    public static JRadioButton unterordnerButton;
    
    public static void main(String args[]){
        
        // Initialisierung des Hauptfensters Frame 'frame'
        frame = new JFrame("Photo-Programm vom Dave!");
        frame.setBounds(300,300,600,600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.lightGray);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
            // Erstellung eines JPanels 'p'
            JPanel p = new JPanel();

            // p bekommt GridBagLayout
            p.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            if (shouldFill) {
                //natural height, maximum width
                c.fill = GridBagConstraints.HORIZONTAL;
            }
            
/*----------------------------------------------------------------------------*/
            // Startordner-Button
            JButton openButtonStart = new JButton("Wählen Sie einen Startordner:");
            if (shouldWeightX) {
                   c.weightx = 0.5;
            }
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 0;

            // ActionListener für Startordner-Button
            openButtonStart.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ChooseStartFile();
                }
            });
            
            p.add(openButtonStart, c);
            
/*----------------------------------------------------------------------------*/            
            // Textfeld für Startpfad
            startpfad = new JTextField();
//            startpfad.setPreferredSize(new Dimension(50, 50));
            startpfad.setBackground(Color.white);
            startpfad.addFocusListener(new FocusAdapter() {

                @Override public void focusGained(FocusEvent evt){
                  startpfad.selectAll();
                }
            }); 
            
            JButton textfeldstart = new JButton();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 10;
            c.weightx = 0.5;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 1;
            textfeldstart.add(startpfad);
            p.add(textfeldstart, c);
            
            
/*----------------------------------------------------------------------------*/            
            // Zielordner-Button
            JButton openButtonZiel = new JButton("Wählen Sie einen Zielordner:");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 0;
            
            // ActionListener für Zielornder-Button   
            openButtonZiel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ChooseDestinationFile();
                }
            });
            
            p.add(openButtonZiel, c);
            
/*----------------------------------------------------------------------------*/            
            // Textfeld für Zielpfad
            zielpfad = new JTextField();
//            zielpfad.setPreferredSize(new Dimension(50, 50));
            zielpfad.setBackground(Color.white);
            zielpfad.addFocusListener(new FocusAdapter() {

                @Override public void focusGained(FocusEvent evt){
                  zielpfad.selectAll();
                }
            }); 
            
            JButton textfeldziel = new JButton();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 10;
            c.weightx = 0.5;
            c.gridwidth = 3;
            c.gridx = 1;
            c.gridy = 1;
            textfeldziel.add(zielpfad);
            p.add(textfeldziel, c);         
            
/*----------------------------------------------------------------------------*/            
            // 'Unterordner'-Button
            unterordnerButton = new JRadioButton("Unterordner einbeziehen?");
            
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.CENTER;
            c.ipady = 20;
            c.weightx = 0.0;
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = 2;
            
            p.add(unterordnerButton, c);
            
/*----------------------------------------------------------------------------*/            
            // 'Sortieren'-Button
            JButton sortieren = new JButton("Sortieren!");
                
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.CENTER;
            c.ipady = 40;      //make this component tall
            c.weightx = 0.0;
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = 3;
            
            // ActionListener für 'Sortieren'-Button
            sortieren.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    Sortieren();
                }
            });
            
            p.add(sortieren, c);

 /*----------------------------------------------------------------------------*/           
            // 'Beenden'-Button
            JButton beenden = new JButton("Beenden");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 0;       //reset to default
            c.weighty = 1.0;   //request any extra vertical space
            c.anchor = GridBagConstraints.PAGE_END; //bottom of space
            c.insets = new Insets(10,0,0,0);  //top padding
            c.gridx = 1;       //aligned with button 2
            c.gridwidth = 2;   //2 columns wide
            c.gridy = 4;       //forth row
            
            // ActionListener für 'Benden'-Button
            beenden.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    close();  
                }
                
                // Private subclass close() zum Beenden der Anwedung
                private void close() {
                    
                    JFrame frame2 = new JFrame();
                    int optionPane = JOptionPane.showConfirmDialog(frame2, "Wollen Sie das Programm wirklich schließen?");
                    
                    frame2.pack();
                    frame2.setVisible(true);
                    
                    if(optionPane == JOptionPane.YES_OPTION) {                    
                        frame.setVisible(false);
                        frame.dispose();
                        frame2.setVisible(false);
                        frame2.dispose();
                    } else {
                        frame2.setVisible(false);
                        frame2.dispose();
                    }
                }
            });
            
            p.add(beenden, c);
            
            // Panel p in Frame frame einfügen
            frame.add(p);

            // Frame einblenden
            frame.pack();
            frame.setVisible(true);
    }
    
    public static void ChooseStartFile() {
                
        fcstart = new JFileChooser((System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop"));
        fcstart.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fcstart.showDialog(null, "Diesen Ordner auswählen");
        File f = fcstart.getSelectedFile();
        filename = f.getAbsolutePath();
        startpfad.setText(filename);
        System.out.println("Choosen Directory = " + filename);
    }
    
    public static void ChooseDestinationFile() {
        
        fcziel = new JFileChooser((System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop"));
        fcziel.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fcziel.showDialog(null, "Diesen Zielordner auswählen");
        File f = fcziel.getSelectedFile();
        filenameziel = f.getAbsolutePath();
        zielpfad.setText(filenameziel);
    }
    
    public static void Sortieren() {
        
        // Boolean unterordner auf true setzen, wenn JRadioButton unterordnerButton aktiviert wurde
        if (unterordnerButton.isSelected() == true) {
             unterordner = true;
        }
        
        // Falls kein Zielordner gewählt wurde -> Fehlermeldung
        if (filename != null && filenameziel != null) {
            try{
                photosort.PhotoSort.main(filename, filenameziel, unterordner);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else if (filename != null && filenameziel == null){
            JOptionPane.showMessageDialog(frame,
            "Wählen Sie bitte einen Zielordner über die angegebene Schaltfläche.",
            "Zielordner fehlt",
            JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    public static void Erfolgsmeldung(Boolean b, String s) {
        
        if(b == true) {
            JOptionPane.showMessageDialog(frame, s, "Erfolgreich sortiert!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, s, "Erfolgreich sortiert!", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
}