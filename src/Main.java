/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.swing.WindowConstants;

/**
 *
 * @author Marzieh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         MoBaSUI frame = new MoBaSUI();
    frame.setTitle("MoBaS");
    frame.setBounds(200, 200, 300, 200);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
}
