import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MoBaSUI extends JFrame{

private JPanel panel1 = new JPanel();
private JPanel panel2 = new JPanel();
private JPanel panel3 = new JPanel();
    private JPanel panel4=new JPanel();
    private JPanel panel5=new JPanel();
    private JPanel panel6=new JPanel();
    public static int whichPanel;
    private javax.swing.JButton jButton3  = new javax.swing.JButton();
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
     private static javax.swing.JTextField jTextField3;
    private static javax.swing.JTextField jTextField4;
    private static javax.swing.JTextField jTextField5;
    private static javax.swing.JTextField jTextField6;
    private JLabel jLabel10;
    private JLabel jLabel20;
    private JPanel panel7=new JPanel();;
    private JLabel jLabel5;
    
    private JLabel jLabel6;
   

public MoBaSUI(){
    setDefaultCloseOperation(EXIT_ON_CLOSE);
//     initPanel();
    initMenu();
   
//    panel1.setBackground(Color.BLUE);
//    panel2.setBackground(Color.RED);
//    setLayout(new BorderLayout());
}

    private static class RunMoBaS implements ActionListener {

        public RunMoBaS() {
            
            
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            JFrame frame = new JFrame();
            
//             JDialog d = new JDialog(frame,"MoBaS", true);
//            JLabel label = new JLabel("Please wait...");
//            d.setLocationRelativeTo(null);
//            d.setTitle("MoBaS is running");
//            d.add(label);
//            d.pack();
//            d.setLocationRelativeTo(frame);
//            d.setVisible(true);
            MoBaS mobas;
            try {
                mobas = new MoBaS(jTextField3.getText(),jTextField4.getText(),jTextField5.getText(),jTextField6.getText(),whichPanel);
            } catch (IOException ex) {
                Logger.getLogger(MoBaSUI.class.getName()).log(Level.SEVERE, null, ex);
            }
//            d.setVisible(true);
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

private class MenuAction implements ActionListener {

    private JPanel panel;
    private MenuAction(JPanel pnl) {
        this.panel = pnl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        changePanel(panel);

    }

}
private class AnalysisMenuAction implements ActionListener {

    private JPanel panel;
    private AnalysisMenuAction(JPanel pnl) {
        this.panel = pnl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        analysisPanel(panel);

    }

}

private void initMenu() {
    JMenuBar menubar = new JMenuBar();
    
    //File Format Menu Item
    JMenu menu = new JMenu("File Format");
    JMenuItem menuItem1 = new JMenuItem("SNP Association");
    JMenuItem menuItem2 = new JMenuItem("Mapping");
    JMenuItem menuItem3 = new JMenuItem("PPI");
    menubar.add(menu);
    menu.add(menuItem1);
    menu.add(menuItem2);
    menu.add(menuItem3);
    menuItem1.addActionListener(new MenuAction(panel1));
    menuItem2.addActionListener(new MenuAction(panel2));
    menuItem3.addActionListener(new MenuAction(panel3));
    
    //Analysis Menu Item
    JMenu menu2 = new JMenu("Analysis");
    JMenuItem menuItem4 = new JMenuItem("Original");
    JMenuItem menuItem5 = new JMenuItem("Permuted Genotype");
    JMenuItem menuItem6 = new JMenuItem("Permuted PPI");
    menubar.add(menu2);
    menu2.add(menuItem4);
    menu2.add(menuItem5);
    menu2.add(menuItem6);
    
    menuItem4.addActionListener(new AnalysisMenuAction(panel4));
    menuItem5.addActionListener(new AnalysisMenuAction(panel5));
    menuItem6.addActionListener(new AnalysisMenuAction(panel6));  
    
     JMenu menu3 = new JMenu("Help");
     JMenuItem menuItem7 = new JMenuItem("Contact us");
   
    menu3.add(menuItem7);
     menubar.add(menu3);
     setJMenuBar(menubar);
     menuItem7.addActionListener(new MenuAction(panel7));
     
    jButton3.addActionListener(new RunMoBaS());
    

}

private void changePanel(JPanel panel) {
    getContentPane().removeAll();      
    getContentPane().add(panel, BorderLayout.CENTER);
    FileFormatPanel(panel);
//    getContentPane().doLayout();
    update(getGraphics());
}

private void changePanelAnalysis(JPanel panel) {
    getContentPane().removeAll();      
    getContentPane().add(panel, BorderLayout.CENTER);  
    analysisPanel(panel);
//    getContentPane().doLayout();
    update(getGraphics());
     
}

private void analysisPanel(JPanel panel){
    getContentPane().removeAll();  
     jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
         jTextField6 = new javax.swing.JTextField();
       jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); 
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setText("Find Subnetworks");
      
         jLabel6.setText("BackGround Association");
        jTextField6.setText("Folder which contains association of genes in permuted genotype/phenotype");
       

        
    if(panel == panel4){
        whichPanel = 4;
        jLabel1.setText("Original Data");
        jLabel2.setText("SNP Association");
        jLabel3.setText("PPI");
        jLabel4.setText("Mapping");
       
        jTextField3.setText(".assoc File......");
        jTextField4.setText("File for mapping SNP to gene....");
        jTextField5.setText("PPI File.....");
    }
     if(panel == panel5){
         whichPanel = 5;
        jLabel1.setText("Permuted Genotype");
        jLabel2.setText("SNP Associations Folder");
        jLabel3.setText("PPI");
        jLabel4.setText("Mapping");
        jTextField3.setText("Folder containing .assoc files for permuted genotype......");
        jTextField4.setText("File for mapping SNP to gene....");
        jTextField5.setText("PPI File.....");
    }
    if(panel == panel6){
        whichPanel = 6;
        jLabel1.setText("Permuted PPI");
        jLabel2.setText("SNP Associations");
        jLabel3.setText("Permuted PPI Folder");
        jLabel4.setText("Mapping");
        jTextField3.setText(".assoc files ......");
        jTextField4.setText("File for mapping SNP to gene....");
        jTextField5.setText("Folder containing permuted PPI files.....");
    }
      
    
    
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                        .addComponent(jTextField3)
                        .addComponent(jTextField5))
                        .addComponent(jTextField6))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
}

private void FileFormatPanel(JPanel panel){
    getContentPane().removeAll();
    jLabel10 = new javax.swing.JLabel();
    jLabel20 = new javax.swing.JLabel();
    jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    if(panel == panel1){     
        jLabel10.setText("SNP Association File ");
        jLabel20.setText("<html><br>SNP Association File is a file represents pvalue of each SNP with the disease computed by PLINK.(.assoc)<br><br>"
           + "Each line contains identifier for SNP and its p-value separated by a tab. <br><br>"
          
                + "rs12456 0.005 <br>"
                + "rs32421 0.6<br>"
                + "...</html>"
           );
    }
     if(panel == panel2){     
        jLabel10.setText("Mapping File ");
        jLabel20.setText("<html><br>Mapping File provides  mapping file to gene symbol.<br><br>"
           + "Each line contains identifier for SNP and its mapping gene separated by a tab.<br><br>"
                + "rs188805031  TTLL10<br>"
                + "rs182959636  CCNL2<br>"
                + "...</html>"
           );
    }
    
      if(panel == panel3){     
        jLabel10.setText("PPI File ");
        jLabel20.setText("<html>PPI File represents an interaction between proteins with confidence of existence of interaction as its weight.<br><br>" 
           + "Each line is an edge and its weight separated by a tab. <br>" 
           + "If no confidence is available, all weights count as constant to 1.<br><br>"
                + "A1BG	CRISP3	0.56623<br>" +
                  "AMBP	A2M	0.6412597<br>"
                + "...</html>");
   
    }
    
       if(panel == panel7){     
        jLabel10.setText("MoBaS :  ");
        jLabel20.setText("<html><br>MoBaS is a software tool for finding the disease associated subnetworks in protein-protein interaction (PPI) networks. A disease associated subnetwork is a subset of gene products that are heavily connected to each other in the protein-protein interaction network and are significantly associated with the disease when considered together.<br><br>"
                + "MoBaS uses a scoring method that is parameter-free and aims to score subnetworks by assessing the <span style=\"background-color:#FFFF00;\">disease association</span> of pairwise interactions and incorporating the <span style=\"background-color:#FFFF00;\"> statistical significance</span> of network connectivity and disease association." +
" <br><br> If you have any question, please contact marzieh@case.edu</html>");
   
    }
    
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
}

}