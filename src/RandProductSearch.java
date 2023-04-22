import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.stream.Stream;

public class RandProductSearch extends JFrame {
    JPanel mainPnl, titlePnl, searchPnl, ctrlPnl;
    JTextField searchField;
    JTextArea resArea;
    JScrollPane pane;
    JLabel title;
    JButton quitBtn, clearBtn, searchBtn;
    String res;
    ArrayList<String> rafIn = new ArrayList<>();
    StringBuffer sb;
    Stream<String> stream;
    String path = "src/ProductList.txt";
    File usrFile = new File(path);
    public RandProductSearch() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        setTitle("Product Search");
        setSize(900, 600);
        createTitle();
        mainPnl.add(titlePnl, BorderLayout.NORTH);
        createSearch();
        mainPnl.add(searchPnl, BorderLayout.CENTER);
        createControl();
        mainPnl.add(ctrlPnl, BorderLayout.SOUTH);
        add(mainPnl);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void createTitle(){
        titlePnl = new JPanel();
        title = new JLabel("Search Product:");
        searchField = new JTextField("", 20);
        titlePnl.add(title);
        titlePnl.add(searchField);
    }
    private void createSearch(){
        searchPnl = new JPanel();
        resArea = new JTextArea(30, 60);
        resArea.setEditable(false);
        pane = new JScrollPane(resArea);
        searchPnl.add(pane);
    }
    private void createControl(){
        ctrlPnl = new JPanel();
        clearBtn = new JButton("Clear");
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) ->{
            System.exit(0);
        });
        searchBtn = new JButton("Search");
        searchBtn.addActionListener((ActionEvent ae ) ->{
            res = searchField.getText();
            if(res.isEmpty()){
                JOptionPane.showMessageDialog(this, "Cannot search for empty string!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                sb = new StringBuffer();
                try {
                    RandomAccessFile raf = new RandomAccessFile(usrFile, "rw");
                    while(raf.getFilePointer() < raf.length()){
                        rafIn.add(raf.readLine() + "\n");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            stream = rafIn.stream().filter(s -> s.contains(res));
            stream.forEach(s -> resArea.append(s));
        });
        ctrlPnl.add(searchBtn);
        ctrlPnl.add(clearBtn);

        ctrlPnl.add(quitBtn);
   }
}