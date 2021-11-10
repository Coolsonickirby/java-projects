import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.Collectors;

import javax.naming.spi.DirStateFactory.Result;

public class App extends Application {
    public static final ArrayList<Size> PIZZA_SIZES = new ArrayList<Size>();
    public static final ArrayList<Topping> PIZZA_TOPPINGS = new ArrayList<Topping>();
    public static final ObservableList<Pizza> PIZZAS = FXCollections.observableArrayList();
    public static String CASHIER_NAME = "";
    public static final NumberFormat FORMATTER = new DecimalFormat("#0.00");
    public static TableView<Pizza> TABLE_VIEW = new TableView<Pizza>();

    public static void main(String[] args) {
        setup();
        launch(args);
    }

    public static void setup() {
        // #region Setup Pizza Sizes
        PIZZA_SIZES.add(new Size("Small", 7.00));
        PIZZA_SIZES.add(new Size("Medium", 9.00));
        PIZZA_SIZES.add(new Size("Large", 15.00));
        PIZZA_SIZES.add(new Size("Extra Large", 19.00));
        // #endregion

        // #region Setup Toppings
        PIZZA_TOPPINGS.add(new Topping("Cheese", 0.00));
        PIZZA_TOPPINGS.add(new Topping("Olives", 1.00));
        PIZZA_TOPPINGS.add(new Topping("Meat", 1.00));
        PIZZA_TOPPINGS.add(new Topping("Pineapple", 1.00));
        PIZZA_TOPPINGS.add(new Topping("Peppers", 1.00));
        PIZZA_TOPPINGS.add(new Topping("Mushrooms", 1.00));
        // #endregion
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pizza Menu");

        Label lblCashier = new Label("Cashier Name:");
        TextField txtCashier = new TextField();
        txtCashier.setOnKeyReleased(ev -> CASHIER_NAME = txtCashier.getText());

        Button btnAdd = new Button("Add Pizza");
        Button btnEdit = new Button("Edit Pizza");
        Button btnDel = new Button("Remove Pizza");
        Button btnPrint = new Button("Print Recipt");

        TABLE_VIEW.setEditable(false);

        TableColumn<Pizza, String> pizzaID = new TableColumn<Pizza, String>("Pizza ID");
        pizzaID.setCellValueFactory(new PropertyValueFactory<Pizza, String>("idString"));

        TableColumn<Pizza, String> pizzaSize = new TableColumn<Pizza, String>("Pizza Size");
        pizzaSize.setCellValueFactory(new PropertyValueFactory<Pizza, String>("sizeString"));

        TableColumn<Pizza, String> pizzaToppings = new TableColumn<Pizza, String>("Pizza Toppings");
        pizzaToppings.setCellValueFactory(new PropertyValueFactory<Pizza, String>("toppingsString"));

        TableColumn<Pizza, String> pizzaExtras = new TableColumn<Pizza, String>("Extra Instructions");
        pizzaExtras.setCellValueFactory(new PropertyValueFactory<Pizza, String>("extras"));

        TableColumn<Pizza, String> pizzaPrice = new TableColumn<Pizza, String>("Pizza Price");
        pizzaPrice.setCellValueFactory(new PropertyValueFactory<Pizza, String>("priceString"));

        TABLE_VIEW.setItems(PIZZAS);
        TABLE_VIEW.getColumns().addAll(pizzaID, pizzaSize, pizzaToppings, pizzaExtras, pizzaPrice);
        TABLE_VIEW.getColumns().stream().forEach(column -> column.setMinWidth(120));

        btnAdd.setOnAction(ev -> showPizzaMenu(0));

        btnEdit.setOnAction(ev -> {
            if (TABLE_VIEW.getSelectionModel().selectedItemProperty().get() != null) {
                showPizzaMenu(TABLE_VIEW.getSelectionModel().selectedItemProperty().get().getId());
            }else {
                ShowMessage("Please select an item in the table first!");
            }
        });

        btnDel.setOnAction(ev -> {
            if (TABLE_VIEW.getSelectionModel().selectedItemProperty().get() != null) {
                int pizzaIdx = TABLE_VIEW.getSelectionModel().selectedIndexProperty().get();
                if(ShowPrompt(String.format("Are you sure you want to delete Pizza #%s?", PIZZAS.get(pizzaIdx).getId()))){
                  PIZZAS.remove(pizzaIdx);
                }
            }else{
                ShowMessage("Please select an item in the table first!");
            }
        });

        btnPrint.setOnAction(ev -> {
            if(TABLE_VIEW.getItems().size() > 0){
                PrintReceipt();
            }
        });

        HBox btns = new HBox(btnAdd, btnEdit, btnDel, btnPrint);
        btns.setSpacing(10);

        VBox container = new VBox();
        container.setPadding(new Insets(10, 10, 10, 10));
        container.setSpacing(10);
        container.getChildren().addAll(lblCashier, txtCashier, btns, TABLE_VIEW);

        primaryStage.setScene(new Scene(container, 630, 512));
        primaryStage.show();
    }

    public static void PrintReceipt() {
        FileChooser sfdRes = new FileChooser();
        sfdRes.setInitialFileName("receipt.txt");
        File location = sfdRes.showSaveDialog(null);
        if(location == null){ return; }

        StringBuilder result = new StringBuilder();
        DoubleAdder totalPrice = new DoubleAdder();
        if(!CASHIER_NAME.equals("")){ result.append("Cashier: " + CASHIER_NAME + "\n"); }
        PIZZAS.stream().forEach(pizza -> {
            result.append(String.format("Pizza #%s\n", pizza.getId()));
            result.append(String.format("\tSize:\n\t\t%s - %s\n", pizza.getPizzaSize().getSize(), FORMATTER.format(pizza.getPizzaSize().getPrice())));
            result.append("\tToppings:\n");
            pizza.getToppings().stream().forEach(topping -> result.append(String.format("\t\t%s - %s\n", topping.getType(), FORMATTER.format(topping.getPrice()))));
            result.append(String.format("\tNotes: %s\n", pizza.getExtras().replace("\n", "\n\t       ")));
            result.append(String.format("\t\tPrice: %s\n", pizza.getPriceString()));
            totalPrice.add(pizza.getPrice());
        });
        result.append("------------------------------------------------------------------------\n");
        result.append(String.format("\t\tTotal Price: %s\n", FORMATTER.format(totalPrice.doubleValue())));

        try {
            FileWriter writer = new FileWriter(location);
            writer.write(result.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showPizzaMenu(int id) {
        Pizza currentPizza = PIZZAS.stream().filter(entry -> entry.getId() == id).findFirst().orElse(null);

        Stage pizzaStage = new Stage();
        pizzaStage.setTitle("Pizza");

        Label lblSizes = new Label("Select a pizza size:");
        ListView<String> lvSizes = new ListView<String>(FXCollections
                .observableArrayList(PIZZA_SIZES.stream().map(entry -> entry.toString()).collect(Collectors.toList())));

        if (currentPizza != null) {
            lvSizes.getSelectionModel().select(currentPizza.getPizzaSize().toString());
        }

        Label lblToppings = new Label("Select the toppings you'd like:");

        FlowPane toppings = new FlowPane();
        toppings.setHgap(10);
        toppings.setVgap(10);
        toppings.setOrientation(Orientation.HORIZONTAL);

        ArrayList<String> selectedToppings = currentPizza != null ? new ArrayList<String>() : null;
        if (currentPizza != null) {
            currentPizza.getToppings().stream().forEach(entry -> selectedToppings.add(entry.toString()));
        }

        for (Topping topping : PIZZA_TOPPINGS) {
            CheckBox chkBox = new CheckBox(topping.toString());
            if (selectedToppings != null) {
                chkBox.setSelected(selectedToppings.contains(topping.toString()));
            }
            toppings.getChildren().add(chkBox);
        }

        Label lblExtras = new Label("Extra Instructions:");
        TextArea extras = new TextArea("");
        extras.setMinHeight(100);
        if (currentPizza != null) {
            extras.setText(currentPizza.getExtras());
        }

        Button btnSave = new Button("Save Pizza");
        Button btnCancel = new Button("Cancel Pizza");

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Save (re-assign currentPizza into a new variable so it can be edited in this
                // function)
                Pizza pizza = currentPizza;

                ArrayList<Topping> pizzaToppings = new ArrayList<Topping>();
                ObservableList<Node> topping = toppings.getChildren();

                for (int i = 0; i < topping.size(); i++) {
                    CheckBox chk = (CheckBox) topping.get(i);
                    if (chk.isSelected()) {
                        pizzaToppings.add(PIZZA_TOPPINGS.get(i));
                    }
                }

                if (pizza == null) { // Create new pizza
                    int id = PIZZAS.size() > 0
                            ? PIZZAS.stream().collect(Collectors.maxBy(Comparator.comparing(Pizza::getId))).get()
                                    .getId() + 1
                            : PIZZAS.size() + 1;
                    pizza = new Pizza(id, PIZZA_SIZES.get(lvSizes.getSelectionModel().getSelectedIndex()),
                            pizzaToppings, extras.getText());
                    PIZZAS.add(pizza);
                } else { // Edit current pizza
                    pizza.setPizzaSize(PIZZA_SIZES.get(lvSizes.getSelectionModel().getSelectedIndex()));
                    pizza.setToppings(pizzaToppings);
                    pizza.setExtras(extras.getText());
                }

                TABLE_VIEW.refresh(); // Refresh

                // Always close after saving
                pizzaStage.close();
            }
        });

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pizzaStage.close();
            }
        });

        FlowPane options = new FlowPane(btnSave, btnCancel);
        options.setHgap(10);
        options.setVgap(10);
        options.setOrientation(Orientation.HORIZONTAL);

        VBox root = new VBox();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setSpacing(5);
        root.getChildren().addAll(lblSizes, lvSizes, lblToppings, toppings, lblExtras, extras, options);
        pizzaStage.setScene(new Scene(root, 575, 350));
        pizzaStage.show();
    }

    public static void ShowMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.show();
    }

    public static boolean ShowPrompt(String message){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Pizza");
        alert.setHeaderText(message);
        alert.setContentText("Press OK to delete this item.");
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println(result.get());
        System.out.println(result.get() == ButtonType.OK);
        return result.isPresent() ? result.get() == ButtonType.OK : false; // Only works in jGrasp for some reason
    }
}