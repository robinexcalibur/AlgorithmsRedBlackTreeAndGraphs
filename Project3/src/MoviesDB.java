import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MoviesDB {

    private String fileName;
    private Map<String, RBT<String, HashSet<Integer>>> indexTreeMap =
            new HashMap<String, RBT<String, HashSet<Integer>>>();
    private ArrayList<Movie> db;
    private int n;

    //enums
    private final String IMDB_SCORE = "score";
    private final String YEAR = "year";
    private final String CONTENT_RATING = "content";
    private final String LANGUAGE = "language";

    //these are the indexes of this info when we split the line array
    private final int IMDB_SCORE_DATA = 13;
    private final int YEAR_DATA = 12;
    private final int CONTENT_RATING_DATA = 11;
    private final int LANGUAGE_DATA = 9;



    //loads data into an array from the file and builds the hashmap/RBTs.
    public MoviesDB(String fileName) throws FileNotFoundException {
        this.fileName = fileName;

        db = new ArrayList<Movie>();

        indexTreeMap.put(IMDB_SCORE, new RBT<String, HashSet<Integer>>());
        indexTreeMap.put(YEAR, new RBT<String, HashSet<Integer>>());
        indexTreeMap.put(CONTENT_RATING, new RBT<String, HashSet<Integer>>());
        indexTreeMap.put(LANGUAGE, new RBT<String, HashSet<Integer>>());

        File file = new File(fileName);
        Scanner scanner = new Scanner(file, "UTF-8");
        scanner.nextLine(); //skip first line
        int index = 0;
        while(scanner.hasNextLine()) {
            //build the movie object. There's 14 aspects to a movie.
            String line = scanner.nextLine();
            String[] s = line.split(",");
            try {
                Movie m = new Movie(Integer.parseInt(s[0]), s[1], s[2], Integer.parseInt(s[3]), s[4], s[5], s[6], s[7],
                        s[8], s[9], s[10], s[11], Integer.parseInt(s[12]), Double.parseDouble(s[13]));

                //place in array list and the hash map
                db.add(m);
                putInRBT(IMDB_SCORE, s[IMDB_SCORE_DATA], index);
                putInRBT(YEAR, s[YEAR_DATA], index);
                putInRBT(CONTENT_RATING, s[CONTENT_RATING_DATA], index);
                putInRBT(LANGUAGE, s[LANGUAGE_DATA], index);

                index++;
            } catch (NumberFormatException e) {
                //Do nothing
            }

        }
        scanner.close();
    }

    private void putInRBT(String tree, String data, int index) {
        RBT<String, HashSet<Integer>> rbt = indexTreeMap.get(tree);
        HashSet<Integer> hashSet = rbt.get(data);
        if (hashSet == null) { //makes a new node with the data and given index
            HashSet<Integer> set = new HashSet<Integer>();
            set.add(index);
            rbt.put(data, set);
        } else { //sticks the index in the already existing hash set
            hashSet.add(index);
        }
    }

    public void printDB() {
        for (int i = 0; i <db.size(); i++) {
            System.out.println(db.get(i).toString());
            System.out.println("---------");
        }
    }

    public void printID(int id) {
        System.out.println(db.get(id).toString());
        System.out.println("---------");
    }

    public HashSet<Integer> searchByScore(double score) {
        return indexTreeMap.get(IMDB_SCORE).get(score + "");
    }

    public HashSet<Integer> searchByYear(int year) {
        return indexTreeMap.get(YEAR).get(year + "");
    }

    public HashSet<Integer> searchByContentRating(String rating) {
        return indexTreeMap.get(CONTENT_RATING).get(rating);
    }

    public HashSet<Integer> seeachByLanguage(String language) {
        return indexTreeMap.get(LANGUAGE).get(language);
    }

    public static void main (String args[]) throws FileNotFoundException {
        MoviesDB moviesDB = new MoviesDB("movie_metadata.csv");
        System.out.println("Note: the CSV file contains some bad data. For the purposes of this program, it's been skipped.");
        System.out.println("Movies with content rating R in the year 2010");
        //moviesDB.printDB();
        HashSet<Integer> setRating = moviesDB.searchByContentRating("R");
        HashSet<Integer> setYear = moviesDB.searchByYear(2010);

        setRating.retainAll(setYear);

        if (setRating != null) {
            System.out.println(setRating);
        }
        Iterator<Integer> idIterator = setRating.iterator();
        while (idIterator.hasNext()) {
            int id = idIterator.next();
            moviesDB.printID(id);
        }

    }




}
