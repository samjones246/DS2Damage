import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow {
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
    private Map<String, Double> strScalings;
    private Map<String, Double> dexScalings;
    private WeaponCategory selectedCategory;
    private Weapon selectedWeapon;
    private StatConverter statConverter;
    private int[] stats;


    public MainWindow() {
        stats = new int[6];
        DataLoader dataLoader = new DataLoader();
        try {
            dataLoader.load();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not load data files. Program will now exit.");
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
            updateWeaponStats();
        });
        strStat.addChangeListener(e -> updatePlayerStats());
        dexStat.addChangeListener(e -> updatePlayerStats());
        intStat.addChangeListener(e -> updatePlayerStats());
        fthStat.addChangeListener(e -> updatePlayerStats());
    }

    public void updateTotal(){
        totalAR.setText("Total Attack Rating: "+selectedWeapon.getTotalRating(stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]));
    }

    public void updateWeaponList(){
        List<Weapon> available = selectedCategory.getMembers();
        weapon.setModel(new DefaultComboBoxModel(available.toArray()));
        weapon.setSelectedIndex(0);
        selectedWeapon = (Weapon) weapon.getSelectedItem();
        updateWeaponStats();
    }
    public void updateWeaponStats(){
        physBase.setText("Physical: "+selectedWeapon.getPhysical());
        magBase.setText("Magic: "+selectedWeapon.getMagic());
        fireBase.setText("Fire: "+selectedWeapon.getFire());
        lgnBase.setText("Lightning: "+selectedWeapon.getLightning());
        darkBase.setText("Dark: "+selectedWeapon.getDark());
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
