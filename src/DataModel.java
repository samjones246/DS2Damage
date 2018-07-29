import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataModel {
    private Map<String, Integer> stats;
    private Map<String, String[]> affects;
    private String[] names = new String[]{"HP","Stamina","Equip Load","Slot","Cast Speed","ATK Str","ATK Dex","Magic BNS","Fire BNS","Lghtng BNS","Dark BNS","Poison BNS","Bleed BNS","Phys DEF","Magic DEF","Fire DEF","Lghtng DEF","Dark DEF","Poison RES","Bleed RES","Petrify RES","Curse RES","AGL","Poise"};
    private AttributePanel attributePanel;
    DataModel(AttributePanel attributePanel){
        this.attributePanel = attributePanel;
        stats = new HashMap<>();
        for(String name : names){
            stats.put(name,0);
        }
        affects = new HashMap<>();
        affects.put("VGR", new String[]{"HP", "Petrify RES"});
        affects.put("END", new String[]{"HP", "Stamina", "Phys DEF", "Poise"});
        affects.put("VIT", new String[]{"HP", "Equip Load", "Phys Def", "Poison RES"});
        affects.put("ATN", new String[]{"HP", "Slot", "Cast Speed", "Curse RES", "AGL"});
        affects.put("STR", new String[]{"HP", "ATK Str", "Phys DEF"});
        affects.put("DEX", new String[]{"HP", "ATK Dex", "Poison BNS", "Bleed BNS", "Phys DEF"});
        affects.put("ADP", new String[]{"HP", "Poison BNS", "Poison RES", "Bleed RES", "Petrify RES", "Curse RES", "AGL", "Poise"});
        affects.put("INT", new String[]{"HP", "Cast Speed", "Magic BNS", "Fire BNS", "Dark BNS", "Magic DEF", "Fire DEF", "Dark DEF"});
        affects.put("FTH", new String[]{"HP", "Cast Speed", "Fire BNS", "Lghtng BNS", "Dark BNS", "Bleed BNS", "Fire DEF", "Lghtng DEF", "Dark DEF", "Bleed RES"});

    }
    public int getStat(String stat){
        return stats.get(stat);
    }
    public void update(String attribute){
        DataLoader loader = new DataLoader();
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StatConverter statConverter = loader.getStatConverter();
        for(String stat : affects.get(attribute)){
             switch(stat){
                 case "HP": {
                    int val = 500;
                    int vgr = attributePanel.get("VGR");
                    if(vgr<=20){
                        val+=30*vgr;
                    }else if(vgr<=50){
                        val+=30*20;
                        val+=20*(vgr-20);
                    }else{
                        val+=30*20;
                        val+=20*30;
                        val+=5*(vgr-50);
                    }
                    int[] otherStats = new int[]{attributePanel.get("END"),attributePanel.get("VIT"),attributePanel.get("ATN"),attributePanel.get("STR"),attributePanel.get("DEX"),attributePanel.get("ADP"),attributePanel.get("INT"),attributePanel.get("FTH")};
                    for(int other : otherStats){
                        if(other<=20){
                            val+=2*other;
                        }else if(other<=50){
                            val+=2*20;
                            val+=other-20;
                        }else{
                            val+=2*20;
                            val+=30;
                        }
                    }
                    stats.put("HP", val);
                 }
                 case "Stamina":{
                    int val = 80;
                    int end = attributePanel.get("END");
                    if(end<=20){
                        val+=2*end;
                    }else if(end<=98){
                        val+=20*2;
                        val+=end-20;
                    }else{
                        val=200;
                    }
                    stats.put("Stamina", val);
                 }
                 case "Equip Load":{
                     double val = 38.5;
                     int vit = attributePanel.get("VIT");
                     if(vit<=29){
                         val+=1.5*vit;
                     }else if(vit<=49){
                         val+=1.5*29;
                         val+=vit-29;
                     }else if(vit<=70){
                         val+=1.5*29;
                         val+=20;
                         val+=0.5*(vit-49);
                     }else if(vit<=98){
                         val+=1.5*29;
                         val+=20;
                         val+=0.5*21;
                         val+=0.5*(vit/2);
                     }else{
                         val=120;
                     }
                     stats.put("Equip Load", (int) (val*10));
                 }
                 case "Slot":{
                    int val = 0;
                    int atn = attributePanel.get("ATN");
                    if(atn>=10){
                        if(atn<13){
                            val = 1;
                        }else if(atn<16){
                            val = 2;
                        }else if(atn<20){
                            val = 3;
                        }else if(atn<25){
                            val = 4;
                        }else if(atn<30){
                            val = 5;
                        }else if(atn<40){
                            val = 6;
                        }else if(atn<50){
                            val = 7;
                        }else if(atn<60){
                            val = 8;
                        }else if(atn<75){
                            val = 9;
                        }else{
                            val = 10;
                        }
                        stats.put("Slot", val);
                    }
                 }
                 case "Cast Speed":{
                    int val = 35;
                    int atn = attributePanel.get("ATN");
                    int INT = attributePanel.get("INT");
                    int fth = attributePanel.get("FTH");
                    int W = (atn+(INT/2)+(fth/2))/3;
                    if(W<=80){
                        val+=2*(W/2);
                    }else if(W<=100){
                        val=115;
                        val+=(W-80)/2;
                    }else{
                        val=125;
                        val+=(W-100)/4;
                    }
                    stats.put("Cast Speed", val);
                 }
                 case "ATK Str":{
                    int str = attributePanel.get("STR");
                    int val = statConverter.getStrengthBonus(str);
                    stats.put("ATK Str",val);
                 }
                 case "ATK Dex":{
                     int dex = attributePanel.get("DEX");
                     int val = statConverter.getDexBonus(dex);
                     stats.put("ATK Dex",val);
                 }
                 case "Magic BNS":{
                     int INT = attributePanel.get("INT");
                     int val = statConverter.getMagicBonus(INT);
                     stats.put("Magic BNS",val);
                 }
                 case "Fire BNS":{
                     stats.put("Fire BNS",statConverter.getFireBonus(attributePanel.get("INT"),attributePanel.get("FTH")));
                 }
                 case "Lghtng BNS":{
                    stats.put("Lghtng BNS",statConverter.getLightningBonus(attributePanel.get("FTH")));
                 }
                 case "Dark BNS":{
                     stats.put("Dark BNS",statConverter.getDarkBonus(attributePanel.get("INT"),attributePanel.get("FTH")));
                 }
                 case "Poison BNS":{
                    stats.put("Poison BNS",statConverter.getPoisonBonus(attributePanel.get("DEX"),attributePanel.get("ADP")));
                 }
                 case "Bleed BNS":{
                    stats.put("Bleed BNS",statConverter.getBleedBonus(attributePanel.get("DEX"),attributePanel.get("FTH")));
                 }
                 case "Phys DEF":{
                     int vit = attributePanel.get("VIT");
                     int end = attributePanel.get("END");
                     int str = attributePanel.get("STR");
                     int dex = attributePanel.get("DEX");
                    stats.put("Phys DEF",statConverter.getPhysDef(vit, end, str, dex));
                 }
                 case "Magic DEF":{
                    int INT = attributePanel.get("INT");
                    stats.put("Magic DEF", statConverter.getMagicDef(INT));
                 }
                 case "Fire DEF":{
                     int INT = attributePanel.get("INT");
                     int fth = attributePanel.get("FTH");
                     stats.put("Fire DEF",statConverter.getFireDef(INT, fth));
                 }
                 case "Lghtng DEF":{
                    int fth = attributePanel.get("FTH");
                    stats.put("Lghtng DEF",statConverter.getLightningDef(fth));
                 }
                 case "Dark DEF":{
                     int INT = attributePanel.get("INT");
                     int fth = attributePanel.get("FTH");
                     stats.put("Dark DEF",statConverter.getDarkDef(INT, fth));
                 }
                 case "Poison RES":{

                 }
                 case "Bleed RES":{

                 }
                 case "Petrify RES":{

                 }
                 case "Curse RES":{

                 }
                 case "AGL":{

                 }
                 case "Poise":{

                 }
            }
        }
    }
}
