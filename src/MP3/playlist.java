//Author J I M L Y
package MP3;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class playlist {
    JFileChooser fc = new JFileChooser("D:\\");
    ArrayList ls = new ArrayList();
    FileFilter filter = new FileNameExtensionFilter("MP3 File", "MP3");
    
    public void add(JFrame frame){
        fc.setMultiSelectionEnabled(true);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(filter);
        int valid = fc.showOpenDialog(frame);
        if(valid==javax.swing.JFileChooser.CANCEL_OPTION){
            return;
        }else if(valid==javax.swing.JFileChooser.APPROVE_OPTION){
            File[] file = fc.getSelectedFiles();
            ls.addAll(Arrays.asList(file));
        }
    }
    ArrayList getListSong(){
        return ls;
    }
}
