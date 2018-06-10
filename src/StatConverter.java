import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StatConverter implements Serializable{
    private Map<Integer, Integer> strength, dex, fire, lightning, magic, dark;

    public StatConverter(){
        strength = new HashMap<>();
        dex = new HashMap<>();
        fire = new HashMap<>();
        lightning = new HashMap<>();
        magic = new HashMap<>();
        dark = new HashMap<>();
    }
    public void initialise(Map<Integer, Integer> strength, Map<Integer, Integer> dex, Map<Integer, Integer> magic, Map<Integer, Integer> fire, Map<Integer, Integer> lightning, Map<Integer, Integer> dark){
        this.strength = strength;
        this.dex = dex;
        this.fire = fire;
        this.lightning = lightning;
        this.magic = magic;
        this.dark = dark;
    }

    public int getStrengthBonus(int strength){
        return this.strength.get(strength);
    }
    public int getDexBonus(int dex){
        return this.dex.get(dex);
    }
    public int getFireBonus(int intelligence, int faith){
        int val = (intelligence+faith)/2;
        return fire.get(val);
    }
    public int getLightningBonus(int faith){
        return lightning.get(faith);
    }
    public int getMagicBonus(int intelligence){
        return magic.get(intelligence);
    }
    public int getDarkBonus(int intelligence, int faith){
        if(intelligence<faith){
            return dark.get(intelligence);
        }else{
            return dark.get(faith);
        }
    }
}
