
import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.io.File;
        import java.io.IOException;
        import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    String name, desc, id;
    double cost;
    static RandomAccessFile file;
    String path = "src/ProductList.txt";
    File usrFile = new File(path);
    int records = 0;
    JPanel mainPnl, middlePnl, ctrlPnl;

    JLabel nameLbl,descLbl, idLbl, costLbl;
    static JTextField nameTF, descTF, productTF, costTF;
    JTextField recordCntField;
    JLabel recordCntLbl;
    JButton quitBtn;
    JButton addBtn;
    JButton clearBtn;
    public RandProductMaker(){
        mainPnl = new JPanel();
        setTitle("File Streams");

        createForm();


        mainPnl.add(middlePnl);

        CreateCtrlPnl();

        mainPnl.add(ctrlPnl, BorderLayout.SOUTH);
        add(mainPnl);

        setSize(800, 400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void createForm(){
        middlePnl = new JPanel();
        middlePnl.setLayout(new GridLayout(10, 1));
        nameLbl = new JLabel("Product Name:");
        nameTF = new JTextField(40);
        descLbl = new JLabel("Product Description:");
        descTF = new JTextField(50);
        idLbl = new JLabel("Product ID:");
        productTF = new JTextField(6);
        costLbl = new JLabel("Product Cost:");
        costTF = new JTextField(20);
        recordCntLbl = new JLabel("Record Count");
        recordCntField = new JTextField(10);
        recordCntField.setEditable(false);
        middlePnl.add(nameLbl);
        middlePnl.add(nameTF);
        middlePnl.add(descLbl);
        middlePnl.add(descTF);
        middlePnl.add(idLbl);
        middlePnl.add(productTF);
        middlePnl.add(costLbl);
        middlePnl.add(costTF);
        middlePnl.add(recordCntLbl);
        middlePnl.add(recordCntField);
    }

    private void CreateCtrlPnl(){
        ctrlPnl = new JPanel();
        ctrlPnl.setLayout(new GridLayout(1, 2));
        addBtn = new JButton("Add Product");
        addBtn.addActionListener((ActionEvent ae) ->{
            if(validInp()){
                name = namePadding(nameTF.getText());
                desc = descPadding(descTF.getText());
                id = idPadding(productTF.getText());
                cost = Double.parseDouble(costTF.getText());
                try {
                    file = new RandomAccessFile(usrFile, "rw");
                    file.seek(file.length());
                    file.writeBytes(id + ", " + name + "," + desc + "," + String.format("%.2f", cost) + "\n");
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                records++;
                recordCntField.setText(String.valueOf(records));
                nameTF.setText(null);
                descTF.setText(null);
                productTF.setText(null);
                costTF.setText(null);
                JOptionPane.showMessageDialog(this, "Successfully Wrote a File");
            }
            else if (!validInp()){
                JOptionPane.showMessageDialog(this, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent ae) ->{
            nameTF.setText(null);
            descTF.setText(null);
            productTF.setText(null);
            costTF.setText(null);
            recordCntField.setText(null);
            JOptionPane.showMessageDialog(this, "Product Information Cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));
        ctrlPnl.add(addBtn);
        ctrlPnl.add(clearBtn);
        ctrlPnl.add(quitBtn);
    }
    public static String namePadding(String name){
        do{
            name += " ";
        }while(name.length() < 20);
        return name;
    }
    public static String descPadding(String desc){
        do{
            desc += " ";
        }while(desc.length() < 60);
        return desc;
    }
    public static String idPadding(String id){
        do{
            id += " ";
        }while(id.length() < 6);
        return id;
    }
    public static boolean validInp(){
        String name = nameTF.getText();
        String desc = descTF.getText();
        String id = productTF.getText();
        double cost = Double.parseDouble(costTF.getText());
        if(name.length() <= 20 && desc.length() <= 60 && id.length() <= 6 && costTF != null){
            return true;
        }
        else{
            return false;
        }
    }
}