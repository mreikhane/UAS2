import java.util.Comparator;
import java.util.List;

public class ProductSorter {
    public static void sortByRating(List<Product> products) {
        products.sort(Comparator.comparingDouble(Product::getRating));
    }
}
