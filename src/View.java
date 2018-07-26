import javax.swing.*;

public class View {
    private JPanel rightHand;
    private JPanel rings;
    private JPanel leftHand;
    private JPanel armor;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JComboBox comboBox9;
    private JComboBox comboBox10;
    private JComboBox comboBox11;
    private JComboBox comboBox12;
    private JComboBox comboBox13;
    private JComboBox comboBox14;
    private AttributePanel attributeSelector1;
    private JPanel mainPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("View");
        frame.setContentPane(new View().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
