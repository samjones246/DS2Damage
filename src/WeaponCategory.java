import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeaponCategory implements Serializable{
    private String name;
    private List<Weapon[]> members;

    WeaponCategory(String name){
        this.name = name;
        members = new ArrayList<>();
    }

    public void addMember(Weapon weapon){
        Weapon[] addTo = null;
        for(Weapon[] type : members){
            for(Weapon wep : type){
                if(wep!=null&&wep.getName().equals(weapon.getName())){
                    addTo = type;
                }
            }
        }
        int index = View.infusions.get(weapon.getInfusion());
        if(addTo==null){
            addTo=new Weapon[7];
            members.add(addTo);
        }
        addTo[index] = weapon;
    }

    public String getName() {
        return name;
    }
    public List<Weapon[]> getMembers() {
        return members;
    }
    public boolean equals(Object object){
        return object instanceof WeaponCategory && ((WeaponCategory) object).name.equals(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
