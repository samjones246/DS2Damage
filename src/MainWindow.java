import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainWindow {
    private JComboBox lightningScale;
    private JPanel mainPanel;
    private JSpinner physBase;
    private JSpinner fireBase;
    private JSpinner lightningBase;
    private JSpinner magicBase;
    private JSpinner darkBase;
    private JSpinner strStat;
    private JSpinner dexStat;
    private JSpinner intStat;
    private JSpinner darkStat;
    private JSpinner magicStat;
    private JSpinner lightningStat;
    private JSpinner fireStat;
    private JSpinner fthStat;
    private JComboBox strScale;
    private JComboBox dexScale;
    private JComboBox intScale;
    private JComboBox fthScale;
    private JComboBox fireScale;
    private JComboBox magicScale;
    private JComboBox darkScale;
    private JLabel totalAR;
    private JButton calcButton;
    private JComboBox category;
    private JComboBox weapon;
    private Map<String, Double> strScalings;
    private Map<String, Double> dexScalings;


    public MainWindow() {
        strScalings = new HashMap<>();
        strScalings.put("-", 0.0);
        strScalings.put("S", 1.0);
        strScalings.put("A", 0.8);
        strScalings.put("B", 0.55);
        strScalings.put("C", 0.35);
        strScalings.put("D", 0.20);
        strScalings.put("E", 0.01);

        dexScalings=new HashMap<>();
        dexScalings.put("-", 0.0);
        dexScalings.put("S", 0.6);
        dexScalings.put("A", 0.45);
        dexScalings.put("B", 0.35);
        dexScalings.put("C", 0.25);
        dexScalings.put("D", 0.15);
        dexScalings.put("E", 0.01);
        calcButton.addActionListener(e -> {
            updateTotal();
        });
    }

    public void updateTotal(){
        int total = (int)physBase.getValue()+(int)fireBase.getValue()+(int)magicBase.getValue()+(int)lightningBase.getValue()+(int)darkBase.getValue();
        total+=Math.round(strScalings.get(strScale.getSelectedItem())*(int)strStat.getValue());
        total+=Math.round(dexScalings.get(dexScale.getSelectedItem())*(int)dexStat.getValue());
        total+=Math.round(strScalings.get(intScale.getSelectedItem())*(int)intStat.getValue());
        total+=Math.round(strScalings.get(fthScale.getSelectedItem())*(int)fthStat.getValue());
        total+=Math.round(strScalings.get(fireScale.getSelectedItem())*(int)fireStat.getValue());
        total+=Math.round(strScalings.get(lightningScale.getSelectedItem())*(int)lightningStat.getValue());
        total+=Math.round(strScalings.get(magicScale.getSelectedItem())*(int)magicStat.getValue());
        total+=Math.round(strScalings.get(darkScale.getSelectedItem())*(int)darkStat.getValue());
        totalAR.setText("Total Attack Rating: "+total);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DSII AR Calculator");
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
