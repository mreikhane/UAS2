import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductDataFetcher dataFetcher = new ProductDataFetcher();
        List<Product> products = dataFetcher.fetchData();

        if (!products.isEmpty()) {
            ProductSorter sorter = new ProductSorter();
            sorter.sortByRating(products);

            for (Product product : products) {
                System.out.println("Rating: " + product.getRating() + ", Name: " + product.getTitle());
            }
        } else {
            System.out.println("No data available.");
        }
    }
}
