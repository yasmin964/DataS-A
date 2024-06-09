//import java.util.*;
//
//// VISITOR interface
//interface ProductVisitor {
//    void visit(Book book);
//    void visit(Tea tea);
//    void visit(Coffee coffee);
//}
//
//// Element interface
//interface Product {
//    void accept(ProductVisitor visitor);
//}
//
//// Concrete Element: Book
//class Book implements Product {
//    @Override
//    public void accept(ProductVisitor visitor) {
//        visitor.visit(this);
//    }
//}
//
//// Concrete Element: Tea
//class Tea implements Product {
//    @Override
//    public void accept(ProductVisitor visitor) {
//        visitor.visit(this);
//    }
//}
//
//// Concrete Element: Coffee
//class Coffee implements Product {
//    @Override
//    public void accept(ProductVisitor visitor) {
//        visitor.visit(this);
//    }
//}
//
//// Concrete Visitor: DiscountVisitor
//class DiscountVisitor implements ProductVisitor {
//    @Override
//    public void visit(Book book) {
//        // No discount for books
//        System.out.println("No discount for books.");
//    }
//
//    @Override
//    public void visit(Tea tea) {
//        // Apply discount for tea
//        System.out.println("Discount applied for tea.");
//    }
//
//    @Override
//    public void visit(Coffee coffee) {
//        // Apply discount for coffee
//        System.out.println("Discount applied for coffee.");
//    }
//}
//
//// Object Structure
//class OnlineStore {
//    private List<Product> products = new ArrayList<>();
//
//    public void addProduct(Product product) {
//        products.add(product);
//    }
//
//    public void applyDiscount(ProductVisitor visitor) {
//        for (Product product : products) {
//            product.accept(visitor);
//        }
//    }
//}
//
//// Main class
//public class visitor {
//    public static void main(String[] args) {
//        // Create products
//        Book book = new Book();
//        Tea tea = new Tea();
//        Coffee coffee = new Coffee();
//
//        // Add products to the online store
//        OnlineStore onlineStore = new OnlineStore();
//        onlineStore.addProduct(book);
//        onlineStore.addProduct(tea);
//        onlineStore.addProduct(coffee);
//
//        // Apply discount using visitor pattern
//        ProductVisitor discountVisitor = new DiscountVisitor();
//        onlineStore.applyDiscount(discountVisitor);
//    }
//}
