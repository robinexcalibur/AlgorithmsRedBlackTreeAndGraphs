public class Movie {
    private int id;
    private String color;
    private String movieTitle;
    private int duration;
    private String directorName;
    private String actor1;
    private String actor2;
    private String actor3;
    private String movieImdbLink;
    private String language;
    private String country;
    private String contentRating;
    private int year;
    private double imdbScore;

    public Movie (int id, String color, String movieTitle, int duration, String directorName, String actor1, String actor2, String actor3,
        String movieImdbLink, String language, String country, String contentRating, int year, double imdbScore) {

        this.id = id;
        this.color = color;
        this.movieTitle = movieTitle;
        this.duration = duration;
        this.directorName = directorName;
        this.actor1 = actor1;
        this.actor2 = actor2;
        this.actor3 = actor3;
        this.movieImdbLink = movieImdbLink;
        this.language = language;
        this.country = country;
        this.contentRating = contentRating;
        this.year = year;
        this.imdbScore = imdbScore;
    }

    public int getYear() {
        return year;
    }

    public String getLanguage() {
        return language;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public String getContentRating() {
        return contentRating;
    }

    public String toString() {
        String string = "id: " + id +
                "\ncolor: " + color +
                "\nMovie Title: " + movieTitle +
                "\nDuration: " + duration +
                "\nDirector Name: " + directorName +
                "\nActor 1: " + actor1 +
                "\nActor 2: " + actor2 +
                "\nActor 3: " + actor3 +
                "\nIMDB Link: " + movieImdbLink +
                "\nLanguage: " + language +
                "\nCountry: " + country +
                "\nContent Rating: " + contentRating +
                "\nYear: " + year +
                "\nIMDB Score: " + imdbScore;

        return string;
    }


}
