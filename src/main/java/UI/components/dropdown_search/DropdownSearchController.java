package UI.components.dropdown_search;

import UI.components.Component;
import UI.components.IEventListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.*;

public class DropdownSearchController<T> extends Component implements IDropdownSearch<T> {

    private List<IEventListener<String>> onTypeSubscribers = new ArrayList<>();
    private List<IEventListener<Set<T>>> onDoneSubscribers = new ArrayList<>();

    private IDropdownSearchRequire<T> required;

    // This list holds the reference to the search results.
    private ObservableList<T> searchResults;

    @FXML
    private TextField inputField;

    @FXML
    private JFXListView<T> results;

    @FXML
    private HBox buttonContainer;

    private JFXButton addButton;

    public DropdownSearchController(IDropdownSearchRequire required) {
        super("dropdown_search.fxml");
        setRequired(required);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        results.setCellFactory(param -> required.getCellFactory());
        addButton = new JFXButton("Tilføj sagsbehandler");
        addButton.getStyleClass().addAll("flat-button", "flat-button_outlined");
        addButton.setOnAction(clickEvent -> {
            done();
        });
        collapse();
    }

    @FXML
    void search(KeyEvent event) {
        if(inputField.getText().length() < 1){
            collapse();
            removeButton();
        } else{
            expand();
            onTypeSubscribers.forEach(listener -> listener.onAction(inputField.getText()));
            displayButton();
        }
    }

    void done() {
        Set<T> selectedItems = new HashSet<>();
        for (T t : results.getSelectionModel().getSelectedItems()) {
            selectedItems.add(t);
        }
        onDoneSubscribers.forEach(listener -> {
            listener.onAction(selectedItems);
        });
        collapse();
        addButton.setVisible(false);
        inputField.clear();
    }

    @Override
    public void updateList(List<T> searchResults) {
        this.searchResults = FXCollections.observableArrayList(searchResults);
        results.setItems(this.searchResults);
    }

    @Override
    public void onType(IEventListener<String> listener) {
        onTypeSubscribers.add(listener);
    }

    @Override
    public void onDone(IEventListener<Set<T>> listIEventListener) {
        onDoneSubscribers.add(listIEventListener);
    }

    @Override
    public void expand() {
        results.setMaxHeight(300);
        results.setVisible(true);
    }

    @Override
    public void collapse() {
        results.setMaxHeight(0);
        results.setVisible(false);
    }

    private void displayButton(){
        if(!buttonContainer.getChildren().contains(addButton)) buttonContainer.getChildren().add(addButton);
    }

    private void removeButton(){
        buttonContainer.getChildren().remove(addButton);
    }

    @Override
    public void setRequired(IDropdownSearchRequire required) {
        this.required = required;
    }

}
