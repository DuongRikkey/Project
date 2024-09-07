package business.features.impl.productImpl.User;

import business.entity.Category;
import business.entity.Product;
import business.features.impl.CategoryImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.Colors;

import java.util.Comparator;

public class ProductFeaturesForUser {
    public static void displayAllProductsUser() {
        if (ProductImpl.productList.isEmpty()){
            System.err.println("Danh sách trống");
            return;
        }
        if (ProductImpl.productList != null && !ProductImpl.productList.isEmpty()) {
            System.out.println(Colors.CYAN+"**************************LIST PRODUCTS ***************************"+Colors.RESET);
            ProductImpl.productList.stream()
                    .filter(product ->  product.getCategory().isStatus()) // Ensure category is not null
                    .forEach(Product::displayDataForUser); // Display data of products that belong to active categories
        } else {
            System.out.println("No products available.");
        }

    }

    public static void showNewstProducts() {
        if (ProductImpl.productList.isEmpty()){
            System.err.println("Danh sách trống");
            return;
        }
        System.out.println(Colors.CYAN+"**************** PRODUCTS NEWEST ****************"+Colors.RESET);
        ProductImpl.productList.stream().filter(product ->  product.getCategory().isStatus()).
                sorted(Comparator.comparing(Product::getCreatedAt).reversed()).forEach(Product::displayDataForUser);
        System.out.println(Colors.CYAN+"*************************************************"+Colors.RESET);
    }

    public static void showProductByCategory() {

    }

    public static void displayBestSelling() {
    }
}
