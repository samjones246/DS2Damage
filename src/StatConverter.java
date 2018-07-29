import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StatConverter implements Serializable{
    private Map<Integer, Integer> strength, dex, fire, lightning, magic, dark, psnbld, physDef, magDef, fireDef, lghDef, darkDef;

    public StatConverter(){
        strength = new HashMap<>();
        dex = new HashMap<>();
        fire = new HashMap<>();
        lightning = new HashMap<>();
        magic = new HashMap<>();
        dark = new HashMap<>();
        psnbld = new HashMap<>();
    }
    public void initialise(Map<Integer, Integer> strength, Map<Integer, Integer> dex, Map<Integer, Integer> magic, Map<Integer, Integer> fire, Map<Integer, Integer> lightning, Map<Integer, Integer> dark, Map<Integer, Integer> psnbld, Map<Integer, Integer> physDef, Map<Integer, Integer> magDef, Map<Integer, Integer> fireDef, Map<Integer, Integer> lghDef, Map<Integer, Integer> darkDef){
        this.strength = strength;
        this.dex = dex;
        this.fire = fire;
        this.lightning = lightning;
        this.magic = magic;
        this.dark = dark;
        this.psnbld = psnbld;
        this.physDef = physDef;
        this.magDef = magDef;
        this.fireDef = fireDef;
        this.lghDef = lghDef;
        this.darkDef = darkDef;
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
    public int getPoisonBonus(int dex, int adp){
        return psnbld.get((dex*3+adp));
    }
    public int getBleedBonus(int dex, int fth){
        return psnbld.get((dex*3+fth));
    }
    public int getPhysDef(int vit, int end, int str, int dex){
        int val = vit + end + str + dex;
        return physDef.get(val);
    }
    public int getMagicDef(int INT){
        return magDef.get(INT);
    }
    public int getFireDef(int INT, int fth){
        return fireDef.get(INT+fth);
    }
    public int getLightningDef(int fth){
        return lghDef.get(fth);
    }
    public int getDarkDef(int INT, int fth){
        if(INT<fth){
            return darkDef.get(INT);
        }else return darkDef.get(fth);
    }
}
