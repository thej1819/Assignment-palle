package mohan;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*Importing the jsoup libraries inorder to read the data from the html file */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class palle {
    /**
     * @param args
     * @param code
     * @param model
     * 
     * @param cat
     */

    public static void main(String[] args, String code, String model, String ca, String cat) {
        String url = "https://www.quill.com/hanging-file-folders/cbl/4378.html";
        List<Product> productList = new ArrayList<Product>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements products = doc.select("div.row.grid.grid-2.grid-md-3.grid-lg-4 > div");

            for (Element product : products) {
                String name = product.select("div.description > h3 > a").text().trim();
                String rating = product.select("div.description > div.rating > span > span").attr("style");
                String price = product.select("div.price").text().trim();

                Product p = new Product(name, rating, price, code, model, cat);
                productList.add(p);
            }

            Collections.sort(productList, new Comparator<Product>() {
                public int compare(Product p1, Product p2) {
                    float r1 = Float.parseFloat(p1.getRating().split(":")[1].trim().replace("%", ""));
                    float r2 = Float.parseFloat(p2.getRating().split(":")[1].trim().replace("%", ""));
                    return Float.compare(r1, r2);
                }
            });
            if (productList.size() > 10) {
            } else {
            }

            /*
             * Creating a file name : product_details.csv
             * inorder to retrive the data into the set of fields.
             * 
             */

            FileWriter writer = new FileWriter("product_details.csv");
            writer.append("Name,Rating,Price\n");
            for (Product product : productList) {
                writer.append(product.getName() + ",");
                writer.append(product.getRating() + ",");
                writer.append(product.getPrice() + ",");
                writer.append(product.getCode() + ",");
                writer.append(product.getModel() + ",");
                writer.append(product.getCategory() + ",");
                writer.append(product.getDescription() + "\n");
            }
            writer.close();
            System.out.println(productList.size());
            System.out.println("Top 10 products exported to product_details.csv successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/* product class */

class Product {
    private String name;
    private String rating;
    private String price;
    private String code;
    private String model;
    private String cat;
    private String description;

    /**
     * @param name
     * @param rating
     * @param price
     * @param code
     * @param model
     * @param cat
     */

    /* Constructor for the product class */

    public Product(String name, String rating, String price, String code, String model, String cat) {
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.code = code;
        this.model = model;
        this.cat = cat;
    }
    /* List of methods to retrive data through getter() method */

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return cat;
    }

    public String getDescription() {
        return description;
    }
}