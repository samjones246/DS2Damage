import javax.management.Attribute;
import javax.swing.*;
import java.awt.*;
public class AttributePanel extends JPanel {
    private int pointsAvailable = 0;
    AttributePanel(){
        setLayout(new GridLayout(11,2));
        class AttributeSelector extends JPanel{
            private int currentValue;
            AttributeSelector(int type){
                int base;
                if(type == 0){
                    base = 1;
                }else{
                    base = 6;
                }
                JButton plus = new JButton("+"), minus = new JButton("-");
                JTextField value = new JTextField(""+base);
                value.setHorizontalAlignment(SwingConstants.CENTER);
                setLayout(new GridLayout(1,3));
                add(minus);
                add(value);
                add(plus);
                currentValue = base;
                plus.addActionListener(e -> {
                    if(type==0){
                        currentValue++;
                        pointsAvailable++;
                    }else if(pointsAvailable>0) {
                        currentValue++;
                        pointsAvailable--;
                    }
                    value.setText(""+currentValue);
                });
                minus.addActionListener(e -> {
                    if(currentValue>base){
                        if(type==0&&pointsAvailable>0){
                            currentValue--;
                            pointsAvailable--;
                        }else if(type==1){
                            currentValue--;
                            pointsAvailable++;
                        }
                    }
                    value.setText(""+currentValue);
                });
            }
        }
        AttributeSelector level = new AttributeSelector(0);
        add(new JLabel("Level:"));
        add(level);


    }
}
