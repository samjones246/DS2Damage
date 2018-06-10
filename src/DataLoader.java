import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
    private List<WeaponCategory> categories;
    private StatConverter statConverter;

    public DataLoader(){
        categories = new ArrayList<>();
        statConverter = new StatConverter();
    }

    public void load() throws IOException{
        File weaponData = new File("data/weapons.csv");
        File statData = new File("data/stats.csv");
        BufferedReader reader = new BufferedReader(new FileReader(weaponData));
        String line = null;
        while((line=reader.readLine())!=null){
            String[] data = line.split(",");
            String name = data[0];
            WeaponCategory weaponCategory = null;
            for(WeaponCategory category : categories){
                if (category.getName().equals(data[1])){
                    weaponCategory = category;
                }
            }
            if(weaponCategory==null){
                weaponCategory = new WeaponCategory(data[1]);
                categories.add(weaponCategory);
            }
            int[] weaponStats = new int[5];
            double[] weaponScalings = new double[6];
            for(int i = 0;i<5;i++){
                weaponStats[i]=Integer.parseInt(data[i+2]);
            }
            for(int i=0;i<6;i++){
                weaponScalings[i]=Double.parseDouble(data[i+7]);
            }
            Weapon weapon = new Weapon(name, weaponStats, weaponScalings);
            weaponCategory.addMember(weapon);
        }
        reader.close();

        reader = new BufferedReader(new FileReader(statData));
        line = null;
        Map<Integer, Integer> strength, dex, fire, lightning, magic, dark;
        strength = new HashMap<>();
        dex = new HashMap<>();
        fire = new HashMap<>();
        lightning = new HashMap<>();
        magic = new HashMap<>();
        dark = new HashMap<>();
        while((line=reader.readLine())!=null){
            String[] data = line.split(",");
            dex.put(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            strength.put(Integer.parseInt(data[0]), Integer.parseInt(data[2]));
            fire.put(Integer.parseInt(data[0]), Integer.parseInt(data[3]));
            dark.put(Integer.parseInt(data[0]), Integer.parseInt(data[4]));
            magic.put(Integer.parseInt(data[0]), Integer.parseInt(data[5]));
            lightning.put(Integer.parseInt(data[0]), Integer.parseInt(data[6]));
        }
        reader.close();
        statConverter.initialise(strength, dex, magic, fire, lightning, dark);
    }

    public List<WeaponCategory> getCategories() {
        return categories;
    }

    public StatConverter getStatConverter() {
        return statConverter;
    }

    /*
    private void update() throws IOException {
        categories = new ArrayList<>();
        statConverter = new StatConverter();
        URL url = new URL("http://darksouls2.wikidot.com/weapons");
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        boolean inTable = false;
        WeaponCategory weaponCategory;
        while ((line = reader.readLine()) != null) {
            if (line.contains("toc26")) {
                inTable = true;
            }else if(inTable){
                if(line.contains("<h3")){
                    String categoryName = line.split("<span>")[1];
                    categoryName = categoryName.split("</span>")[0];
                    if(categoryName.contains("Ammo")){
                        categoryName = categoryName.split(" ")[0];
                    }
                    weaponCategory = new WeaponCategory(categoryName);
                }else if(line.contains("</td>")){
                    break;
                }else{
                    String weaponPage = line.split("=\"")[1];
                    weaponPage = weaponPage.split("\">")[0];
                    URL weaponURL = new URL(url.toString()+weaponPage);
                }
            }
        }
    }
    */
}
