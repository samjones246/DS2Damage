import javax.management.Attribute;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AttributePanel extends JPanel {
    View parent;
    private int pointsAvailable = 0;
    AttributeSelector[] attributes;
    JLabel pointsLabel;
    AttributePanel(View parent){
        this.parent = parent;
        setLayout(new GridLayout(11,2));
        pointsLabel = new JLabel("0");
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        AttributeSelector level = new AttributeSelector(0, null);
        add(new JLabel("Level:"));
        add(level);
        attributes = new AttributeSelector[9];
        String[] stats = new String[]{"VGR","END","VIT","ATN","STR","DEX","ADP","INT","FTH"};
        for(int i=0;i<9;i++){
            attributes[i] = new AttributeSelector(1,stats[i]);
        }
        add(new JLabel("Points Available:"));
        add(pointsLabel);
        add(new JLabel("VGR:"));
        add(attributes[0]);
        add(new JLabel("END:"));
        add(attributes[1]);
        add(new JLabel("VIT:"));
        add(attributes[2]);
        add(new JLabel("ATN:"));
        add(attributes[3]);
        add(new JLabel("STR:"));
        add(attributes[4]);
        add(new JLabel("DEX:"));
        add(attributes[5]);
        add(new JLabel("ADP:"));
        add(attributes[6]);
        add(new JLabel("INT:"));
        add(attributes[7]);
        add(new JLabel("FTH:"));
        add(attributes[8]);
    }
    class AttributeSelector extends JPanel{
        private int currentValue;
        AttributeSelector(int type, String name){
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
                pointsLabel.setText(""+pointsAvailable);
                if(name!=null) {
                    fireActionEvent(name);
                }
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
                pointsLabel.setText(""+pointsAvailable);
                if(name!=null) {
                    fireActionEvent(name);
                }
            });
            value.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    try{
                        int newVal = Integer.parseInt(value.getText());
                        if(type == 0){
                            if(newVal<(currentValue-pointsAvailable)){
                                newVal = currentValue-pointsAvailable;
                            }else if(newVal>999) {
                                newVal = 999;
                            }
                            pointsAvailable+=newVal-currentValue;
                            currentValue = newVal;
                            value.setText(""+currentValue);
                            pointsLabel.setText(""+pointsAvailable);
                        }else{
                            if(newVal<base){
                                newVal = base;
                            }else if(newVal>currentValue+pointsAvailable){
                                newVal = currentValue+pointsAvailable;
                            }
                            pointsAvailable-=newVal-currentValue;
                            currentValue=newVal;
                            value.setText(""+currentValue);
                            pointsLabel.setText(""+pointsAvailable);
                        }
                    }catch (Exception ex){
                        value.setText(""+currentValue);
                    }
                    if(name!=null) {
                        fireActionEvent(name);
                    }
                }
            });
        }
        public int getCurrentValue(){
            return currentValue;
        }
    }
    public int get(String attribute){
        switch (attribute){
            case "VGR":
                return attributes[0].getCurrentValue();
            case "END":
                return attributes[1].getCurrentValue();
            case "VIT":
                return attributes[2].getCurrentValue();
            case "ATN":
                return attributes[3].getCurrentValue();
            case "STR":
                return attributes[4].getCurrentValue();
            case "DEX":
                return attributes[5].getCurrentValue();
            case "ADP":
                return attributes[6].getCurrentValue();
            case "INT":
                return attributes[7].getCurrentValue();
            case "FTH":
                return attributes[8].getCurrentValue();
            default:
                return 0;
        }
    }
    public synchronized void addActionListener(ActionListener listener){
        listenerList.add(ActionListener.class, listener);
    }
    private synchronized void fireActionEvent(String stat){
        Object[] listeners = listenerList.getListenerList();
        ActionEvent actionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, stat);
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                ((ActionListener)listeners[i+1]).actionPerformed(actionEvent);
            }
        }
    }
}
