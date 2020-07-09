package microservice.spring.io.moviecatalogservice.models;

public class Rating {

    private String name;
    private int rating;

    public Rating() {
    }

    public Rating(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
