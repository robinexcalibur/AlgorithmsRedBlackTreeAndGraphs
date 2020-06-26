import java.util.HashMap;
import java.util.HashSet;

public class Artist {
    int id;
    String name;
    HashMap<Integer, Integer> listeners; //Hashset of <user name, listening weight>
    int listeningRating; //how much they're listened to

    public Artist (int id, String name) {
        this.id = id;
        this.name = name;
        listeningRating = 0;
        listeners = new HashMap<Integer, Integer>();
    }

    public void addListener(int id, int weight) {
        listeners.put(id, weight);
        listeningRating+=weight;
    }

    public void removeListener(int id) {
        if (hasListener(id)) {
            listeningRating -= listeners.get(id);
        }
    }

    public boolean hasListener(int id) {
        return listeners.containsKey(id);
    }

    public int getListeningRating() {
        return listeningRating;
    }

    public String getName() {
        return name;
    }
}
