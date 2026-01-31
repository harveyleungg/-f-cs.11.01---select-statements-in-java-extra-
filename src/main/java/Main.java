import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Current Path: " + new java.io.File("sakila_master.db").getAbsolutePath());
        // jdbc url
        String url = "jdbc:sqlite:/Users/harveyleung/IdeaProjects/-F-CS.11.01---SELECT-statements-in-Java-Extra-/sakila_master.db";
        // array of all queries
        String[] queries = {
                "SELECT * FROM film;",                                          // 1.1
                /* "SELECT first_name, last_name FROM actor;",                     // 1.2
                "SELECT title, release_year FROM film;",                        // 1.3
                "SELECT * FROM customer LIMIT 5;",                              // 2.1
                "SELECT * FROM actor LIMIT 3;",                                 // 2.2
                "SELECT film_id, title AS film_title FROM film;",           // 3.1
                "SELECT category_id, name as category_name FROM category;",     // 3.2
                "SELECT rental_id, customer_id, amount AS payment_amount FROM payment;", // 3.3
                "SELECT DISTINCT city_id FROM address;",                        // 4.1
                "SELECT DISTINCT category_id FROM film_category;",              // 4.2
                "SELECT DISTINCT rating FROM film;",                            // 4.3
                "SELECT DISTINCT film_id, actor_id FROM film_actor;",           // 5.1
                "SELECT DISTINCT customer_id, store_id FROM customer;",         // 5.2
                "SELECT DISTINCT category_id, film_id FROM film_category;",     // 5.3
                "SELECT DISTINCT title AS film_title FROM film LIMIT 10;",      // Combined 1
                "SELECT DISTINCT first_name AS firstname, last_name as lastname FROM actor LIMIT 5;"*/ // Combined 2
        };

        // print each result based on each query
        try (Connection conn = DriverManager.getConnection(url)) {
            for (String sql : queries) {
                runAndPrintQuery(conn, sql);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void runAndPrintQuery(Connection conn, String query) {
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            ResultSetMetaData data = result.getMetaData();
            int columnsNumber = data.getColumnCount();

            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnName = data.getColumnLabel(i);
                    String columnValue = result.getString(i);
                    System.out.print(columnName + ": " + columnValue + (i < columnsNumber ? " | " : ""));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
        }
    }
}