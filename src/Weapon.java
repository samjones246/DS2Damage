import java.io.Serializable;

public class Weapon implements Serializable{
    private String name;
    private int[] stats; // {Physical, Magic, Fire, Lightning, Dark}
    private float[] scalings; // {Strength, Dex, Magic, Fire, Lightning, Dark}

    public Weapon(String name, int[] stats, float[] scalings) {
        this.name = name;
        this.stats = stats;
        this.scalings = scalings;
    }

    public int getTotalRating(int strengthBonus, int dexBonus, int magicBonus, int fireBonus, int lightningBonus, int darkBonus){
        int total = 0;
        for(int i=0;i<stats.length;i++){
            total+=stats[i];
        }
        total+=Math.round(strengthBonus*scalings[0]);
        total+=Math.round(dexBonus*scalings[1]);
        total+=Math.round(magicBonus*scalings[2]);
        total+=Math.round(fireBonus*scalings[3]);
        total+=Math.round(lightningBonus*scalings[4]);
        total+=Math.round(darkBonus*scalings[5]);
        return total;
    }

    public String getName() {
        return name;
    }

    public int getPhysical() {
        return stats[0];
    }

    public int getFire() {
        return stats[2];
    }

    public int getLightning() {
        return stats[3];
    }

    public int getMagic() {
        return stats[1];
    }

    public int getDark() {
        return stats[4];
    }

    public double getStrScale() {
        return scalings[0];
    }

    public double getDexScale() {
        return scalings[1];
    }
    public double getMagicScale() {
        return scalings[2];
    }
    public double getFireScale() {
        return scalings[3];
    }

    public double getLightningScale() {
        return scalings[4];
    }

    public double getDarkScale() {
        return scalings[5];
    }

    @Override
    public String toString() {
        return name;
    }
}
