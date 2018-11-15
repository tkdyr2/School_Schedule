
package cookandroid.com.schoolschedule;

import java.util.ArrayList;
import java.util.UUID;

// Entity class
public class Item {
    private long id;
    private String title;
    private ArrayList items_array;

    public Item(String title){
        this.items_array = new ArrayList<String>();
        id = Math.abs(UUID.randomUUID().hashCode());
        this.title = title;
    }

    public void add_Items_array(String item_content) {
        items_array.add(item_content);
        this.items_array = items_array;
    }

    public ArrayList getItems_array(){
        return items_array;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
