import javax.swing.*;
import java.awt.*;

public class AttributeSelector extends JPanel {
    private int currentValue;
    AttributeSelector(){
        JButton plus = new JButton("+"), minus = new JButton("-");
        JLabel value = new JLabel("0");
        setLayout(new GridLayout(1,3));
        add(plus);
        add(value);
        add(minus);
        currentValue = 0;
        plus.addActionListener(e -> {
            currentValue++;
            value.setText(""+currentValue);
        });
        minus.addActionListener(e -> {
            currentValue--;
            value.setText(""+currentValue);
        });
    }
    public int getValue(){
        return currentValue;
    }
}
