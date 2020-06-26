import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class LastFMReconmender {

    private ArrayList<Artist> artists;
    private ArrayList<User> users;
    private ListGraph friendLists;
    private int[] topArtists;

    public LastFMReconmender() {
        artists = new ArrayList<Artist>();
        users = new ArrayList<User>();
    }

    public void makeData() throws FileNotFoundException {
        //build array list of artists from artists.dat
        File file = new File("artists.dat");
        Scanner s1 = new Scanner(file, "UTF-8");
        s1.nextLine();
        while (s1.hasNextLine()) {
            String line = s1.nextLine();
            Scanner lineScanner = new Scanner(line);
            int id = lineScanner.nextInt();
            String name = lineScanner.next();
            String holder = lineScanner.next();
            while (!holder.startsWith("http://www")) {
                name = name + " " + holder;
                holder = lineScanner.next();
            }
            lineScanner.close();

            //accounts for missing artist IDs in data
            if (id > artists.size()) {
                while (artists.size() < id) {
                    artists.add(new Artist(artists.size(), "No Name Data Found"));
                }
            }
            artists.add(new Artist(id, name)); //the Id will be the same as the index
        }
        s1.close();

        //build users list from user_artist.dat
        File file2 = new File("user_artists.dat");
        Scanner s2 = new Scanner(file2, "UTF-8");
        s2.nextLine();
        while (s2.hasNextLine()) {
            String line = s2.nextLine();
            Scanner lineScanner = new Scanner(line);
            int id = lineScanner.nextInt();
            int artistId = lineScanner.nextInt();
            int weight = lineScanner.nextInt();
            if (users.size() <= id) { //the user ID is the same as its place in the array list.
                while (users.size() <= id) { //accounts for missing user IDs in the data
                    users.add(new User(users.size()));
                }
            }
            lineScanner.close();

            users.get(id).addArtist(artistId, weight);
            artists.get(artistId).addListener(id, weight);
        }
        s2.close();

        //build friends list from user_friends.dat
        friendLists = new ListGraph(users.size());
        File file3 = new File("user_friends.dat");
        Scanner s3 = new Scanner(file3, "UTF-8");
        s3.nextLine();
        while (s3.hasNextLine()) {
            String line = s3.nextLine();
            Scanner lineScanner = new Scanner(line);
            int id = lineScanner.nextInt();
            int friendId = lineScanner.nextInt();
            friendLists.addEdge(id, friendId);
            lineScanner.close();
        }

        //finally, build the sorted array of top artists
        topArtists = new int[artists.size()];
        for (int i = 0; i < topArtists.length; i++) {
            topArtists[i] = i;
        }
        selectionSortByWeight(topArtists);
    }

    /*
    TEST CASE METHODS, used in the JUnit testing
     */
    //called from test classes who don't use the whole "make data" method. Called after all the users have been made.
    public void makeFriendList() {
        friendLists = new ListGraph(users.size());

        topArtists = new int[artists.size()];
        for (int i = 0; i < topArtists.length; i++) {
            topArtists[i] = i;
        }
    }

    public void addFriend(int a, int b) {
        if (!friendLists.hasEdge(a, b)) {
            friendLists.addEdge(a, b);
        }
    }

    public void addArtistEdge(int id, int artistId, int weight) {
        users.get(id).addArtist(artistId, weight);
        artists.get(artistId).addListener(id, weight);
        selectionSortByWeight(topArtists);
    }

    public void addArtist(Artist a) {
        artists.add(a);
    }

    public void addUser(User u) {
        users.add(u);
    }

    /*
    END TEST CASE METHODS
     */

    private void selectionSortByWeight(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i; //holds index, not value
            int minWeight = artists.get(array[min]).getListeningRating();

            //search the array
            for (int j = i+1; j < array.length; j++) {
                if (minWeight > artists.get(array[j]).getListeningRating()) {
                    min = j;
                    minWeight = artists.get(array[min]).getListeningRating();
                }
            }

            //swap if needed
            if (min != i) {
                int holder = array[i];
                array[i] = array[min];
                array[min] = holder;
            }
        }
    }

    public ArrayList<Integer> listFriends(int user) {
        return friendLists.getEdges(user);
    }

    public ArrayList<Integer> commonFriends(int a, int b) {
        ArrayList<Integer> aFriends = friendLists.getEdges(a);
        ArrayList<Integer> bFriends = friendLists.getEdges(b);
        aFriends.retainAll(bFriends);
        return aFriends;
    }

    public ArrayList<String> listTop10() {
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 1; i <= 10; i++) {
            list.add(artists.get(topArtists[topArtists.length - i]).getName());
        }
        return list;
    }

    //Tries to recommend 10 artists based on what the user and their friends listen to.
    //Note: it will recommend less than 10 if
    public ArrayList<String> reconmend10(int user) {
        ArrayList<Integer> friends = friendLists.getEdges(user);
        ArrayList<String> reconmendation = new ArrayList<String>();
        HashSet<Integer> userArtists = users.get(user).getArtists();
        HashSet<Integer> friendArtists = new HashSet<Integer>();

        //iterates through friend IDs, getting the artists of each and compiling them into a set.
        for (int i = 0; i < friends.size(); i++) {
            int friendID = friends.get(i);
            friendArtists.addAll(users.get(friendID).getArtists());
        }

        friendArtists.retainAll(userArtists); //friendArtists is now the intersection of userArtists and friendArtists
        int i = topArtists.length - 1;
        while (reconmendation.size() < 10 && i >= 0) {
            if (friendArtists.contains(topArtists[i])) {
                reconmendation.add(artists.get(topArtists[i]).getName());
            }
            i--;
        }

        return reconmendation;
    }

    public static void main(String[] args) throws FileNotFoundException {
        LastFMReconmender reconmender = new LastFMReconmender();
        reconmender.makeData();

        System.out.println("Friends of User 2:");
        printArray(reconmender.listFriends(2));
        System.out.println("\nTop 10 artists:");
        printArray(reconmender.listTop10());
        System.out.println("\nCommon friends of user 2 and 124:");
        printArray(reconmender.commonFriends(2, 124));
        System.out.println("\nReconmend 10 from user 2:");
        printArray(reconmender.reconmend10(2));

    }

    public static void printArray(ArrayList a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i));
        }
    }


}
