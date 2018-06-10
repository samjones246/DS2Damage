import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeaponCategory implements Serializable{
    private String name;
    private List<Weapon> members;

    WeaponCategory(String name){
        this.name = name;
        members = new ArrayList<>();
    }

    public void addMember(Weapon weapon){
        members.add(weapon);
    }

    public String getName() {
        return name;
    }
    public List<Weapon> getMembers() {
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
