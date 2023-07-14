import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class ProductDataFetcher {
    private static final String API_URL = "https://dummyjson.com/product";
    private static final String X_CONS_ID_HEADER = "X-Cons-ID";
    private static final String X_CONS_ID_VALUE = "123456";
    private static final String USER_KEY_HEADER = "user_key";
    private static final String USER_KEY_VALUE = "faY738sH";

    public List<Product> fetchData() {
        List<Product> products = new ArrayList<>();

        try {
            // Membangun URL dengan header akses
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty(X_CONS_ID_HEADER, X_CONS_ID_VALUE);
            connection.setRequestProperty(USER_KEY_HEADER, USER_KEY_VALUE);

            // Mengecek kode respons HTTP
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Membaca response dari server
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Mengurai data JSON
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("products");

                // Mendapatkan data produk dari array JSON
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonProduct = jsonArray.getJSONObject(i);
                    int id = jsonProduct.getInt("id");
                    String title = jsonProduct.getString("title");
                    String description = jsonProduct.getString("description");
                    double price = jsonProduct.getDouble("price");
                    double discountPercentage = jsonProduct.getDouble("discountPercentage");
                    double rating = jsonProduct.getDouble("rating");
                    int stock = jsonProduct.getInt("stock");
                    String brand = jsonProduct.getString("brand");
                    String category = jsonProduct.getString("category");
                    String thumbnail = jsonProduct.getString("thumbnail");
                    JSONArray jsonImages = jsonProduct.getJSONArray("images");
                    String[] images = new String[jsonImages.length()];
                    for (int j = 0; j < jsonImages.length(); j++) {
                        images[j] = jsonImages.getString(j);
                    }

                    Product product = new Product(id, title, description, price, discountPercentage, rating, stock, brand, category, thumbnail, images);
                    products.add(product);
                }
            } else {
                System.out.println("Failed to fetch data. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
