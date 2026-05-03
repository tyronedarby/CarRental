package cars;

//FULL JAVA FX CAR RENTAL SYSTEM (SIMPLIFIED BUT COMPLETE)
//Project Structure (all in one file for simplicity; in real use separate files)

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;

//---------------------- MODELS ----------------------

abstract class Person implements Serializable {
 protected String id, name;

 public Person(String id, String name) {
     this.id = id;
     this.name = name;
 }
}

class Customer extends Person {
 public Customer(String id, String name) {
     super(id, name);
 }
}

abstract class Staff extends Person {
 public Staff(String id, String name) {
     super(id, name);
 }
}

class Clerk extends Staff {
 public Clerk(String id, String name) {
     super(id, name);
 }
}

class Manager extends Staff {
 public Manager(String id, String name) {
     super(id, name);
 }
}

class Car implements Serializable {
 String id, model;
 double rate;
 boolean available = true;

 public Car(String id, String model, double rate) {
     this.id = id;
     this.model = model;
     this.rate = rate;
 }

 public String toString() {
     return model + " ($" + rate + ")";
 }
}

class Rental implements Serializable {
 Car car;
 Customer customer;
 int days;
 LocalDate date;

 public Rental(Car car, Customer customer, int days) {
     this.car = car;
     this.customer = customer;
     this.days = days;
     this.date = LocalDate.now();
 }

 public double calculate(int actualDays) {
     double base = days * car.rate;
     if (actualDays > days) {
         return base + (actualDays - days) * car.rate * 1.5;
     }
     return base;
 }
}

//---------------------- FILE HANDLER ----------------------

class FileUtil {
 public static void save(String file, Object data) {
     try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
         out.writeObject(data);
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 public static Object load(String file) {
     try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
         return in.readObject();
     } catch (Exception e) {
         return null;
     }
 }
}

//---------------------- MAIN APP ----------------------

public class Main extends Application {

 ObservableList<Car> cars = FXCollections.observableArrayList();
 ObservableList<Rental> rentals = FXCollections.observableArrayList();

 @Override
 public void start(Stage stage) {

     loadData();

     TabPane tabs = new TabPane();

     Tab rentTab = new Tab("Rent Car", rentPane());
     Tab returnTab = new Tab("Return Car", returnPane());
     Tab manageTab = new Tab("Manage Cars", managePane());

     tabs.getTabs().addAll(rentTab, returnTab, manageTab);

     Scene scene = new Scene(tabs, 900, 600);
     stage.setTitle("Car Rental System");
     stage.setScene(scene);
     stage.show();
 }

 // ---------------------- RENT ----------------------

 private Pane rentPane() {
     VBox box = new VBox(10);
     box.setPadding(new Insets(20));

     ComboBox<Car> carBox = new ComboBox<>(cars);
     TextField name = new TextField();
     name.setPromptText("Customer Name");

     TextField days = new TextField();
     days.setPromptText("Days");

     Label result = new Label();

     Button rentBtn = new Button("Rent");
     rentBtn.setOnAction(e -> {
         Car car = carBox.getValue();
         if (car != null && car.available) {
             Customer c = new Customer("C" + Math.random(), name.getText());
             Rental r = new Rental(car, c, Integer.parseInt(days.getText()));
             rentals.add(r);
             car.available = false;
             result.setText("Car rented!");
             saveData();
         }
     });

     box.getChildren().addAll(new Label("Select Car"), carBox, name, days, rentBtn, result);
     return box;
 }

 // ---------------------- RETURN ----------------------

 private Pane returnPane() {
     VBox box = new VBox(10);
     box.setPadding(new Insets(20));

     ComboBox<Rental> rentalBox = new ComboBox<>(rentals);
     TextField actualDays = new TextField();
     actualDays.setPromptText("Actual Days Used");

     Label result = new Label();

     Button returnBtn = new Button("Return Car");
     returnBtn.setOnAction(e -> {
         Rental r = rentalBox.getValue();
         if (r != null) {
             double cost = r.calculate(Integer.parseInt(actualDays.getText()));
             r.car.available = true;
             rentals.remove(r);
             result.setText("Total Cost: $" + cost);
             saveData();
         }
     });

     box.getChildren().addAll(new Label("Select Rental"), rentalBox, actualDays, returnBtn, result);
     return box;
 }

 // ---------------------- MANAGE ----------------------

 private Pane managePane() {
     VBox box = new VBox(10);
     box.setPadding(new Insets(20));

     TextField model = new TextField();
     model.setPromptText("Car Model");

     TextField rate = new TextField();
     rate.setPromptText("Daily Rate");

     ListView<Car> list = new ListView<>(cars);

     Button add = new Button("Add Car");
     add.setOnAction(e -> {
         cars.add(new Car("CAR" + Math.random(), model.getText(), Double.parseDouble(rate.getText())));
         saveData();
     });

     Button remove = new Button("Remove Selected");
     remove.setOnAction(e -> {
         cars.remove(list.getSelectionModel().getSelectedItem());
         saveData();
     });

     box.getChildren().addAll(model, rate, add, remove, list);
     return box;
 }

 // ---------------------- FILE ----------------------

 private void saveData() {
     FileUtil.save("cars.dat", new java.util.ArrayList<>(cars));
     FileUtil.save("rentals.dat", new java.util.ArrayList<>(rentals));
 }

 private void loadData() {
     Object c = FileUtil.load("cars.dat");
     if (c != null) cars.addAll((java.util.List<Car>) c);

     Object r = FileUtil.load("rentals.dat");
     if (r != null) rentals.addAll((java.util.List<Rental>) r);
 }

 public static void main(String[] args) {
     launch();
 }
}
