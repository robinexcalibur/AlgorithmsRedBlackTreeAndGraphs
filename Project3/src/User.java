import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class User {
    int id;
    //ArrayList<Integer> friends;
    HashMap<Integer, Integer> artists;

    public User (int id) {
        this.id = id;
        //friends = new ArrayList<Integer>();
        artists = new HashMap<Integer, Integer>();
    }

    //adds friend to friend list by their ID
//    public void addFriend(int id) {
//        friends.add(id);
//    }

    //adds an artist
    public void addArtist(int id, int weight) {
        artists.put(id, weight);
    }

    public HashMap<Integer, Integer> getArtistWeight() {
        return artists;
    }

    public HashSet<Integer> getArtists() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.addAll(artists.keySet());
        return set;
    }
}
