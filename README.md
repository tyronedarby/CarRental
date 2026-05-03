# Vantage Car Rental System 🚗

A robust, Java-based desktop application designed for streamlined car rental management. This project demonstrates core **Object-Oriented Programming (OOP)** principles, role-based access control, and persistent data storage using **Binary Serialization**.

---

## 🛠️ Key Features

* **Role-Based Access Control:** Distinct interfaces for **Clerks** (rentals, returns, fines) and **Managers** (inventory control, user management).
* **Dynamic Cost Engine:** Automated calculation of rental fees with built-in penalty logic for late returns.
* **Persistent Storage:** Uses **Binary I/O** (`.dat` files) to save the state of cars, users, and rental history without an external database.
* **OOP Architecture:** Implements **Inheritance** (User/Car hierarchies) and **Polymorphism** for specialized car category logic.
* **Modern UI:** A clean, responsive interface built with **JavaFX** and CSS.

---

## 🏗️ System Architecture

### Class Hierarchy
* **Users:** `User` (Abstract) → `Clerk` → `Manager`
* **Vehicles:** `Car` (Abstract) → `EconomyCar`, `LuxuryCar`, `SUV`

### Cost Calculation Formula
The system handles late returns using the following logic:
$$Total = (Days_{Planned} \times Rate) + (Days_{Late} \times (Rate + Penalty))$$

---

## 🚀 Getting Started

### Prerequisites
* **JDK 17** or higher
* **JavaFX SDK** (configured in your IDE)
* An IDE like **IntelliJ IDEA**, **Eclipse**, or **VS Code**

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/CarRental.git
   ```
2. Add the JavaFX library to your project structure.
3. Configure VM Options:
   ```bash
   --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
   ```
4. Run `Main.java`.

---

## 📂 Project Structure
* `src/models/`: Contains the Serializable data classes.
* `src/database/`: Handles Binary File I/O operations.
* `src/view/`: JavaFX UI layouts and styling.
* `data/`: Directory where `rental_data.dat` is stored.

---

## 📝 License
This project is for educational purposes under the **MIT License**.
