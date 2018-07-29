import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class View {
    public static Map<String, Integer> infusions;
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
    private AttributePanel attributePanel;
    private JPanel mainPanel;
    private JLabel hp;
    private JLabel stamina;
    private JLabel equipLoad;
    private JLabel slot;
    private JLabel castSpeed;
    private JLabel atkStr;
    private JLabel atkDex;
    private JLabel magBns;
    private JLabel fireBns;
    private JLabel lghBns;
    private JLabel darkBns;
    private JLabel poisonBns;
    private JLabel bleedBns;
    private JLabel physDef;
    private JLabel magDef;
    private JLabel fireDef;
    private JLabel lghDef;
    private JLabel darkDef;
    private JLabel poisonRes;
    private JLabel bleedRes;
    private JLabel petRes;
    private JLabel curseRes;
    private JLabel agl;
    private JLabel poise;
    DataModel dataModel;

    public View() {
        infusions = new HashMap<>();
        infusions.put("Uninfused", 0);
        infusions.put("Fire", 1);
        infusions.put("Lightning", 2);
        infusions.put("Dark", 3);
        infusions.put("Magic", 4);
        infusions.put("Enchanted", 5);
        infusions.put("Raw", 6);
        dataModel = new DataModel(attributePanel);
        dataModel.update("VGR");
        dataModel.update("END");
        dataModel.update("VIT");
        dataModel.update("ATN");
        dataModel.update("STR");
        dataModel.update("DEX");
        dataModel.update("ADP");
        dataModel.update("INT");
        dataModel.update("FTH");
        update();
        attributePanel.addActionListener(e -> {
            dataModel.update(e.getActionCommand());
            update();
        });
    }
    public void update(){
        hp.setText(""+dataModel.getStat("HP"));
        stamina.setText(""+dataModel.getStat("Stamina"));
        equipLoad.setText(""+dataModel.getStat("Equip Load")/10.0);
        slot.setText(""+dataModel.getStat("Slot"));
        castSpeed.setText(""+dataModel.getStat("Cast Speed"));
        atkStr.setText(""+dataModel.getStat("ATK Str"));
        atkDex.setText(""+dataModel.getStat("ATK Dex"));
        magBns.setText(""+dataModel.getStat("Magic BNS"));
        fireBns.setText(""+dataModel.getStat("Fire BNS"));
        lghBns.setText(""+dataModel.getStat("Lghtng BNS"));
        darkBns.setText(""+dataModel.getStat("Dark BNS"));
        poisonBns.setText(""+dataModel.getStat("Poison BNS"));
        bleedBns.setText(""+dataModel.getStat("Bleed BNS"));
        physDef.setText(""+dataModel.getStat("Phys DEF"));
        magDef.setText(""+dataModel.getStat("Magic DEF"));
        fireDef.setText(""+dataModel.getStat("Fire DEF"));
        lghDef.setText(""+dataModel.getStat("Lghtng DEF"));
        darkDef.setText(""+dataModel.getStat("Dark DEF"));
        poisonRes.setText(""+dataModel.getStat("Poison RES"));
        bleedRes.setText(""+dataModel.getStat("Bleed RES"));
        petRes.setText(""+dataModel.getStat("Petrify RES"));
        curseRes.setText(""+dataModel.getStat("Curse RES"));
        agl.setText(""+dataModel.getStat("AGL"));
        poise.setText(""+dataModel.getStat("Poise"));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DS2: SotFS Build Planner");
        frame.setContentPane(new View().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        attributePanel = new AttributePanel(this);
    }
}
