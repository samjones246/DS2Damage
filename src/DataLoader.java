import java.io.*;
import java.util.*;

public class DataLoader {
    private List<WeaponCategory> categories;
    private StatConverter statConverter;

    public DataLoader() {
        categories = new ArrayList<>();
        statConverter = new StatConverter();
    }

    public void load() throws IOException {
        InputStream weaponData = getClass().getResourceAsStream("data/weapons.csv");
        InputStream statData = getClass().getResourceAsStream("data/stats.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(weaponData));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String name = data[0];
            WeaponCategory weaponCategory = null;
            for (WeaponCategory category : categories) {
                if (category.getName().equals(data[1])) {
                    weaponCategory = category;
                }
            }
            if (weaponCategory == null) {
                weaponCategory = new WeaponCategory(data[1]);
                categories.add(weaponCategory);
            }
            int[] weaponStats = new int[5];
            int[] rings = new int[6];
            float[] weaponScalings = new float[6];
            for (int i = 0; i < 5; i++) {
                weaponStats[i] = Integer.parseInt(data[i + 2]);
            }
            for (int i = 0; i < 6; i++) {
                weaponScalings[i] = Float.parseFloat(data[i + 7]) / 100;
            }
            for (int i = 0; i < 6; i++) {
                rings[i] = Integer.parseInt(data[i + 13]);
            }
            Weapon weapon = new Weapon(name, weaponStats, weaponScalings, rings, data[19]);
            weaponCategory.addMember(weapon);
        }
        reader.close();

        reader = new BufferedReader(new InputStreamReader(statData));
        line = null;
        Map<Integer, Integer> strength, dex, fire, lightning, magic, dark, psnbld, physDef, magDef, fireDef, lghDef, darkDef;
        strength = new HashMap<>();
        dex = new HashMap<>();
        fire = new HashMap<>();
        lightning = new HashMap<>();
        magic = new HashMap<>();
        dark = new HashMap<>();
        psnbld = new HashMap<>();
        physDef = new HashMap<>();
        magDef = new HashMap<>();
        fireDef = new HashMap<>();
        lghDef = new HashMap<>();
        darkDef = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            dex.put(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            strength.put(Integer.parseInt(data[0]), Integer.parseInt(data[2]));
            fire.put(Integer.parseInt(data[0]), Integer.parseInt(data[3]));
            dark.put(Integer.parseInt(data[0]), Integer.parseInt(data[4]));
            magic.put(Integer.parseInt(data[0]), Integer.parseInt(data[5]));
            lightning.put(Integer.parseInt(data[0]), Integer.parseInt(data[6]));
            try{
                psnbld.put(Integer.parseInt(data[7]), Integer.parseInt(data[8]));
            }catch (Exception ignored){ }
            try {
                physDef.put(Integer.parseInt(data[9]), Integer.parseInt(data[10]));
            }catch (Exception ignored){ }
            try {
                magDef.put(Integer.parseInt(data[11]), Integer.parseInt(data[12]));
                fireDef.put(Integer.parseInt(data[13]), Integer.parseInt(data[14]));
                lghDef.put(Integer.parseInt(data[15]), Integer.parseInt(data[16]));
                darkDef.put(Integer.parseInt(data[17]), Integer.parseInt(data[18]));
            }catch (Exception ignored){ }
        }
        reader.close();
        Map<Integer, Integer>[] fillerup = new Map[]{psnbld, physDef, magDef, fireDef, lghDef, darkDef};
        for(Map<Integer, Integer> map : fillerup){
            Map<Integer, Integer> changes = new HashMap<>();
            Set<Integer> keyset = map.keySet();
            int max = 0;
            for(Integer val : keyset){
                if(val>max){
                    max = val;
                }
            }
            Iterator<Integer> itr = keyset.iterator();
            while(itr.hasNext()){
                int base = itr.next();
                int val = base+1;
                while((base!=max)&&(!map.containsKey(val))){
                    changes.put(val+1, map.get(base));
                    val++;
                }
            }
            for(Integer val : changes.keySet()){
                map.put(val, changes.get(val));
            }
        }
        statConverter.initialise(strength, dex, magic, fire, lightning, dark, psnbld, physDef, magDef, fireDef, lghDef, darkDef);
    }

    public List<WeaponCategory> getCategories() {
        return categories;
    }

    public StatConverter getStatConverter() {
        return statConverter;
    }
}
