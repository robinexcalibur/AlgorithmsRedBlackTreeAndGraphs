import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LastFMReconmenderTest {

    LastFMReconmender reconmender;

    @BeforeEach
    void setUp() {
        reconmender = new LastFMReconmender();
        reconmender.addArtist(new Artist(0, "Zero"));
        reconmender.addArtist(new Artist(1, "One"));
        reconmender.addArtist(new Artist(2, "Two"));
        reconmender.addArtist(new Artist(3, "Three"));
        reconmender.addArtist(new Artist(4, "Four"));
        reconmender.addArtist(new Artist(5, "Five"));
        reconmender.addArtist(new Artist(6, "Six"));
        reconmender.addArtist(new Artist(7, "Seven"));
        reconmender.addArtist(new Artist(8, "Eight"));
        reconmender.addArtist(new Artist(9, "Nine"));
        reconmender.addArtist(new Artist(10, "Ten"));

        reconmender.addUser(new User(0));
        reconmender.addUser(new User(1));
        reconmender.addUser(new User(2));
        reconmender.addUser(new User(3));
        reconmender.addUser(new User(4));
        reconmender.addUser(new User(5));

        reconmender.makeFriendList();

        reconmender.addFriend(0, 1);
        reconmender.addFriend(0, 2);
        reconmender.addFriend(0, 3);
        reconmender.addFriend(0, 4);
        reconmender.addFriend(0, 5);
        reconmender.addFriend(1, 2);

        reconmender.addArtistEdge(0, 0, 100);
        reconmender.addArtistEdge(0, 1, 100);
        reconmender.addArtistEdge(0, 2, 100);
        reconmender.addArtistEdge(0, 3, 100);
        reconmender.addArtistEdge(0, 4, 100);
        reconmender.addArtistEdge(0, 5, 100);
        reconmender.addArtistEdge(0, 6, 100);
        reconmender.addArtistEdge(0, 7, 100);
        reconmender.addArtistEdge(0, 8, 100);
        reconmender.addArtistEdge(0, 9, 100);
        reconmender.addArtistEdge(0, 10, 100); //0 is the only person who listens to 10

        reconmender.addArtistEdge(1, 0, 100);
        reconmender.addArtistEdge(2, 1, 110);
        reconmender.addArtistEdge(3, 2, 120);
        reconmender.addArtistEdge(4, 3, 130);
        reconmender.addArtistEdge(5, 4, 140);
        reconmender.addArtistEdge(1, 5, 150);
        reconmender.addArtistEdge(2, 6, 160);
        reconmender.addArtistEdge(3, 7, 170);
        reconmender.addArtistEdge(4, 8, 180);
        reconmender.addArtistEdge(5, 9, 190);
    }

    @Test
    void listFriends() {
        ArrayList<Integer> resultArray = reconmender.listFriends(1);
        ArrayList<Integer> expectedArray = new ArrayList<Integer>();
        resultArray.add(0);
        resultArray.add(2);
        assertTrue(expectedArray.equals(expectedArray));
    }

    @Test
    void commonFriends() {
        ArrayList<Integer> resultArray = reconmender.commonFriends(0, 1);
        ArrayList<Integer> expectedArray = new ArrayList<Integer>();
        expectedArray.add(2);
        assertArrayEquals(expectedArray.toArray(), resultArray.toArray());
    }

    @Test
    void listTop10() {
        ArrayList<String> resultArray = reconmender.listTop10();
        ArrayList<String> expectedArray = new ArrayList<String>();
        expectedArray.add("Nine");
        expectedArray.add("Eight");
        expectedArray.add("Seven");
        expectedArray.add("Six");
        expectedArray.add("Five");
        expectedArray.add("Four");
        expectedArray.add("Three");
        expectedArray.add("Two");
        expectedArray.add("One");
        expectedArray.add("Zero");
        assertTrue(expectedArray.equals(resultArray));
    }

    @Test
    void reconmend10() {
        ArrayList<String> resultArray = reconmender.reconmend10(0);
        ArrayList<String> expectedArray = new ArrayList<String>();
        expectedArray.add("Nine");
        expectedArray.add("Eight");
        expectedArray.add("Seven");
        expectedArray.add("Six");
        expectedArray.add("Five");
        expectedArray.add("Four");
        expectedArray.add("Three");
        expectedArray.add("Two");
        expectedArray.add("One");
        expectedArray.add("Zero");
        assertArrayEquals(expectedArray.toArray(), resultArray.toArray());
    }
}