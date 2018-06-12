import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.*;

public class MainWindow {
    public static Map<String, Integer> infusions;
    private JPanel mainPanel;
    private JSpinner strStat;
    private JSpinner dexStat;
    private JSpinner intStat;
    private JSpinner fthStat;
    private JLabel totalAR;
    private JButton calcButton;
    private JComboBox category;
    private JComboBox weapon;
    private JLabel physBase;
    private JLabel fireBase;
    private JLabel lgnBase;
    private JLabel magBase;
    private JLabel darkBase;
    private JLabel strBonus;
    private JLabel dexBonus;
    private JLabel magBonus;
    private JLabel fireBonus;
    private JLabel lgnBonus;
    private JLabel darkBonus;
    private JComboBox infusion;
    private JCheckBox robActive;
    private JCheckBox flynnActive;
    private JCheckBox sorcActive;
    private JCheckBox fireActive;
    private JCheckBox lghActive;
    private JCheckBox darkActive;
    private WeaponCategory selectedCategory;
    private int selectedInfusion;
    private Weapon selectedWeapon, actualSelected;
    private Weapon[] selectedType;
    private StatConverter statConverter;
    private int[] stats;
    private boolean[] activeRings;


    public MainWindow() {
        activeRings = new boolean[]{false,false,false,false,false,false};
        infusions = new HashMap<>();
        infusions.put("Uninfused", 0);
        infusions.put("Fire", 1);
        infusions.put("Lightning", 2);
        infusions.put("Dark", 3);
        infusions.put("Magic", 4);
        infusions.put("Enchanted", 5);
        infusions.put("Raw", 6);
        stats = new int[6];
        DataLoader dataLoader = new DataLoader();
        try {
            dataLoader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Could not load data files. Program will now exit.\n"+e.getMessage());
            System.exit(0);
        }
        statConverter = dataLoader.getStatConverter();
        category.setModel(new DefaultComboBoxModel(dataLoader.getCategories().toArray()));
        category.setSelectedIndex(0);
        selectedCategory = (WeaponCategory) category.getSelectedItem();
        updateWeaponList();
        strStat.setModel(new SpinnerNumberModel(40, 1, 99, 1));
        dexStat.setModel(new SpinnerNumberModel(40, 1, 99, 1));
        intStat.setModel(new SpinnerNumberModel(40, 1, 99, 1));
        fthStat.setModel(new SpinnerNumberModel(40, 1, 99, 1));
        updatePlayerStats();
        calcButton.addActionListener(e -> {
            updateTotal();
        });
        category.addItemListener(e -> {
            selectedCategory = (WeaponCategory) e.getItem();
            updateWeaponList();
        });
        weapon.addItemListener(e -> {
            selectedWeapon = (Weapon) e.getItem();
            updateInfusions();
        });
        strStat.addChangeListener(e -> updatePlayerStats());
        dexStat.addChangeListener(e -> updatePlayerStats());
        intStat.addChangeListener(e -> updatePlayerStats());
        fthStat.addChangeListener(e -> updatePlayerStats());
        infusion.addItemListener(e -> {
            selectedInfusion= infusions.get(infusion.getSelectedItem());
            updateWeaponStats();
        });
        robActive.addActionListener(e -> activeRings[0] = robActive.isSelected());
        flynnActive.addActionListener(e -> activeRings[1] = flynnActive.isSelected());
        sorcActive.addActionListener(e -> activeRings[2] = sorcActive.isSelected());
        fireActive.addActionListener(e -> activeRings[3] = fireActive.isSelected());
        lghActive.addActionListener(e -> activeRings[4] = lghActive.isSelected());
        darkActive.addActionListener(e -> activeRings[5] = darkActive.isSelected());
    }

    private void updateInfusions() {
        for(Weapon[] type : selectedCategory.getMembers()){
            if(type[selectedInfusion]!=null&&type[selectedInfusion].getName().equals(selectedWeapon.getName())){
                selectedType = type;
            }
        }
        List<String> availableInfusions = new ArrayList<>();
        for(Weapon possible : selectedType){
            if(possible!=null){
                availableInfusions.add(possible.getInfusion());
            }
        }
        infusion.setModel(new DefaultComboBoxModel(availableInfusions.toArray()));
        infusion.setSelectedIndex(0);
        selectedInfusion = 0;
        updateWeaponStats();
    }
    public void updateWeaponList(){
        List<Weapon[]> _available = selectedCategory.getMembers();
        List<Weapon> available = new ArrayList<>();
        for(Weapon[] type : _available){
            available.add(type[0]);
        }
        weapon.setModel(new DefaultComboBoxModel(available.toArray()));
        weapon.setSelectedIndex(0);
        selectedWeapon = (Weapon) weapon.getSelectedItem();
        updateInfusions();
    }
    public void updateWeaponStats(){
        actualSelected = null;
        actualSelected = selectedType[selectedInfusion];
        if(actualSelected==null){
            JOptionPane.showMessageDialog(null, "Invalid Infusion");
            infusion.setSelectedIndex(0);
            return;
        }
        physBase.setText("Physical: "+actualSelected.getPhysical());
        magBase.setText("Magic: "+actualSelected.getMagic());
        fireBase.setText("Fire: "+actualSelected.getFire());
        lgnBase.setText("Lightning: "+actualSelected.getLightning());
        darkBase.setText("Dark: "+actualSelected.getDark());
    }


    public void updateTotal(){
        totalAR.setText("Total Attack Rating: "+actualSelected.getTotalRating(stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], activeRings));
    }
    public void updatePlayerStats(){
        stats[0] = statConverter.getStrengthBonus((Integer) strStat.getValue());
        stats[1] = statConverter.getDexBonus((Integer) dexStat.getValue());
        stats[2] = statConverter.getMagicBonus((Integer) intStat.getValue());
        stats[3] = statConverter.getFireBonus((Integer) intStat.getValue(), (Integer) fthStat.getValue());
        stats[4] = statConverter.getLightningBonus((Integer) fthStat.getValue());
        stats[5] = statConverter.getDarkBonus((Integer) intStat.getValue(), (Integer) fthStat.getValue());
        strBonus.setText("Strength Bonus: "+stats[0]);
        dexBonus.setText("Dexterity Bonus: "+stats[1]);
        magBonus.setText("Magic Bonus: "+stats[2]);
        fireBonus.setText("Fire Bonus: "+stats[3]);
        lgnBonus.setText("Lightning Bonus: "+stats[4]);
        darkBonus.setText("Dark Bonus: "+stats[5]);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DS2: SOTFS Attack Rating Calculator");
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
