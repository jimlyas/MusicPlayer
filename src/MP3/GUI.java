package MP3;
//Author J I M L Y

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class GUI extends javax.swing.JFrame {
    playlist pl = new playlist();
    ArrayList updatelist = new ArrayList();
    Player player;
    File diputar;
    FileInputStream fis; BufferedInputStream bis;
    long pauselocation; long songlength;
    
    public GUI() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/tombol/music.png")));
        judul.setHorizontalAlignment(SwingConstants.CENTER);
        judul.setVerticalAlignment(SwingConstants.CENTER);
        shortcut();
    }
    
   void updatelist(){
       updatelist = pl.getListSong();
       DefaultListModel model = new DefaultListModel();
       for(int i=0;i<updatelist.size();i++){
           model.add(i,((File)updatelist.get(i)).getName().replaceAll(".mp3",""));
       }
       list.setModel(model);
   }
   void add(){
       pl.add(this);
       updatelist();
   }
   
 static int lagidiputar =0;
 boolean pause = false;
 boolean loop = false;
 static int indexlagu;
 static boolean habis= false;
 void putar(){
        if(lagidiputar==0){
        try{
             indexlagu = list.getSelectedIndex();
             if(indexlagu==-1){
                 indexlagu=0;
             }
             list.setSelectedIndex(indexlagu);
             diputar = (File)this.updatelist.get(indexlagu);
             fis = new FileInputStream(diputar);
             bis = new BufferedInputStream(fis);
             judul.setText(diputar.getName().replaceAll(".mp3", ""));
             player = new Player(bis);
             songlength=fis.available();
             lagidiputar=1;
             if(pause==true){
                 fis.skip(songlength-pauselocation);
                 pause=false;
             }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
         new Thread(){
             public void run(){
              try {
                player.play();
                if(player.isComplete()){
                    next();
                }
                    } catch (JavaLayerException ex) {
                        System.out.println(ex.getMessage());
                            }
                }
             }.start();
        } else{
            player.close();
            lagidiputar=0; putar();
        }
 }
  void next(){
        try{
            player.close();
             ++indexlagu;
             if(indexlagu>list.getLastVisibleIndex()){
                 --indexlagu;
                 JOptionPane.showMessageDialog(rootPane,"Lagu sudah habis");
                 return;
             }else{
                 loop=true;
             list.setSelectedIndex(indexlagu);
             diputar = (File)this.updatelist.get(indexlagu);
             fis = new FileInputStream(diputar);
             judul.setText(diputar.getName().replaceAll(".mp3", ""));
             bis = new BufferedInputStream(fis);
             player = new Player(bis);
             songlength=fis.available();
             lagidiputar=1;
             }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
         new Thread(){
             public void run(){
              try {
                  player.play();
                    //Untuk melakukan LOOP
                    if(player.isComplete()){
                        next();
                    }  
                   } catch (Exception ex) {
                     System.out.println(ex.getMessage());
                    }
                 }
             }.start();
 }
  
    void prev(){
         try{
             player.close();
             --indexlagu;
             if(indexlagu==-1){
                 ++indexlagu;
                 JOptionPane.showMessageDialog(rootPane, "Lagu sudah habis");
             } else{
             list.setSelectedIndex(indexlagu);
             diputar = (File)this.updatelist.get(indexlagu);
             judul.setText(diputar.getName().replaceAll(".mp3", ""));
             fis = new FileInputStream(diputar);
             bis = new BufferedInputStream(fis);
             player = new Player(bis);
             songlength=fis.available();
             lagidiputar=1;
             }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
         new Thread(){
             public void run(){
                 try {
                     player.play();
                    //Untuk melakukan LOOP
                    if(player.isComplete()){
                        next();
                    } 

                 } catch (JavaLayerException ex) {
                     System.out.println(ex.getMessage());
                 }
                 }
             }.start();
 }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        judul = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        StopButton = new javax.swing.JLabel();
        PrevButton = new javax.swing.JLabel();
        PlayButton = new javax.swing.JLabel();
        NextButton = new javax.swing.JLabel();
        PauseButton = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        openfile = new javax.swing.JMenu();
        CreditMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MUSIC PLAYER");
        setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        setResizable(false);

        judul.setBackground(new java.awt.Color(255, 255, 255));
        judul.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        judul.setForeground(new java.awt.Color(0, 0, 0));
        judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judul.setText("Play your music here!");
        judul.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        judul.setOpaque(true);

        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(list);

        StopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_stop.png"))); // NOI18N
        StopButton.setToolTipText("Menghentikan lagu saat ini");
        StopButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StopButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                StopButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StopButtonMouseExited(evt);
            }
        });

        PrevButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_prev.png"))); // NOI18N
        PrevButton.setToolTipText("putar lagu sebelumnya");
        PrevButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PrevButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PrevButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PrevButtonMouseExited(evt);
            }
        });

        PlayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_play.png"))); // NOI18N
        PlayButton.setToolTipText("Putar lagu");
        PlayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PlayButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PlayButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PlayButtonMouseExited(evt);
            }
        });

        NextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_next.png"))); // NOI18N
        NextButton.setToolTipText("Putar lagu selanjutnya");
        NextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NextButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                NextButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                NextButtonMouseExited(evt);
            }
        });

        PauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_pause.png"))); // NOI18N
        PauseButton.setToolTipText("Jeda lagu saat ini");
        PauseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PauseButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PauseButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PauseButtonMouseExited(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(204, 255, 204));
        jMenuBar1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        openfile.setText("Open File");
        openfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openfileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                openfileMouseEntered(evt);
            }
        });
        jMenuBar1.add(openfile);

        CreditMenu.setText("Credit");
        CreditMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CreditMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(CreditMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(judul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(StopButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PrevButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PlayButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NextButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PauseButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(judul, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StopButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PrevButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PlayButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(NextButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PauseButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void StopButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StopButtonMouseEntered
        StopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_stop_hover.png")));
    }//GEN-LAST:event_StopButtonMouseEntered

    private void StopButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StopButtonMouseExited
         StopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_stop.png")));
    }//GEN-LAST:event_StopButtonMouseExited

    private void PrevButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrevButtonMouseEntered
        PrevButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_prev_hover.png")));
    }//GEN-LAST:event_PrevButtonMouseEntered

    private void PrevButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrevButtonMouseExited
        PrevButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_prev.png")));
    }//GEN-LAST:event_PrevButtonMouseExited

    private void PlayButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseEntered
        PlayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_play_hover.png")));
    }//GEN-LAST:event_PlayButtonMouseEntered

    private void PlayButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseExited
         PlayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_play.png")));
    }//GEN-LAST:event_PlayButtonMouseExited

    private void NextButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextButtonMouseEntered
         NextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_next_hover.png")));
    }//GEN-LAST:event_NextButtonMouseEntered

    private void NextButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextButtonMouseExited
         NextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_next.png")));
    }//GEN-LAST:event_NextButtonMouseExited

    private void PauseButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PauseButtonMouseEntered
        PauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_pause_hover.png")));
    }//GEN-LAST:event_PauseButtonMouseEntered

    private void PauseButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PauseButtonMouseExited
         PauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tombol/tombol_pause.png")));
    }//GEN-LAST:event_PauseButtonMouseExited

    private void openfileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openfileMouseEntered
        openfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_openfileMouseEntered

    private void PlayButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseClicked
        putar();
    }//GEN-LAST:event_PlayButtonMouseClicked

    private void NextButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextButtonMouseClicked
        next();
    }//GEN-LAST:event_NextButtonMouseClicked

    private void PrevButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrevButtonMouseClicked
        prev();
    }//GEN-LAST:event_PrevButtonMouseClicked

    private void PauseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PauseButtonMouseClicked
        try {
            pauselocation=fis.available();
            player.close();
            pause=true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_PauseButtonMouseClicked

    private void StopButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StopButtonMouseClicked
        player.close();
        pauselocation=0; songlength=0;
    }//GEN-LAST:event_StopButtonMouseClicked

    private void listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listMouseClicked
        if(evt.getClickCount()==2){
            putar();
        }
    }//GEN-LAST:event_listMouseClicked

    private void CreditMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CreditMenuMouseClicked
        new credit().setVisible(true);
    }//GEN-LAST:event_CreditMenuMouseClicked

    private void openfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openfileMouseClicked
        add();
    }//GEN-LAST:event_openfileMouseClicked

    void shortcut(){
       this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), "Stop");
       this.getRootPane().getActionMap().put("Stop", new AbstractAction(){
           public void actionPerformed(ActionEvent e) {
               player.close();
               pauselocation=0; songlength=0;
           }
       });
        
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), "Play");
        this.getRootPane().getActionMap().put("Play", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                putar();
            }
        });
        
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK), "Pause");
        this.getRootPane().getActionMap().put("Pause", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                 try {
                    pauselocation=fis.available();
                    player.close();
                    pause=true;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }   
            }
        });
        
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK), "Open");
        this.getRootPane().getActionMap().put("Open", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                 add();
            }
        });
        
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "Next");
        this.getRootPane().getActionMap().put("Next", new AbstractAction(){
           public void actionPerformed(ActionEvent e) {
               next();
           }
        });
        
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK), "Prev");
        this.getRootPane().getActionMap().put("Prev", new AbstractAction(){
           public void actionPerformed(ActionEvent e) {
               prev();
           }
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
try{
    Thread.sleep(3000);
    UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
}catch(Exception e){
    System.out.println(e.getMessage());
}
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu CreditMenu;
    private javax.swing.JLabel NextButton;
    private javax.swing.JLabel PauseButton;
    private javax.swing.JLabel PlayButton;
    private javax.swing.JLabel PrevButton;
    private javax.swing.JLabel StopButton;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel judul;
    private javax.swing.JList<String> list;
    private javax.swing.JMenu openfile;
    // End of variables declaration//GEN-END:variables
}
