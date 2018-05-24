package UI.components.meetingPopUp;

import ACQ.*;
import UI.components.Component;
import UI.components.data_prompt.DataPromptController;
import UI.components.data_prompt.IDataPrompt;
import UI.components.dropdown_search.DropdownSearchController;
import UI.components.dropdown_search.IDropdownSearch;
import UI.components.dropdown_search.IDropdownSearchRequire;
import UI.components.dropdown_search.NameCheckboxCell;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class MeetingPopUpController extends Component implements IMeetingPopUp {

    private IMeetingPopUpRequire required;

    @FXML
    private StackPane container;

    @FXML
    private JFXDialog dialog;

    private JFXDialogLayout content;

    private Text subject;

    private Text message;

    private IDropdownSearch<IProfile> dropdownSearch;

    public MeetingPopUpController(IMeetingPopUpRequire required) {
        super("meetingPopUp.fxml");
        setRequired(required);
        subject = new Text();
        message = new Text();
        subject.getStyleClass().add("dialog_subject");
        message.getStyleClass().add("dialog_message");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content = new JFXDialogLayout();
        JFXButton button = new JFXButton("Indkald til møde");
        button.getStyleClass().add("dialog_button");
        button.setOnAction(event -> close());
        content.setActions(button);
        content.setHeading(subject);


        VBox innercontent = new VBox();

        AnchorPane datePickerWrapper = new AnchorPane();
        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.getStyleClass().add("datopicker");
        datePicker.setPromptText("Klik på kalenderen for at vælge dato");
        datePickerWrapper.getChildren().add(datePicker);
        datePickerWrapper.setTopAnchor(datePicker, .0);
        datePickerWrapper.setLeftAnchor(datePicker, .0);
        datePickerWrapper.setRightAnchor(datePicker, .0);
        datePickerWrapper.setBottomAnchor(datePicker, .0);

        VBox textfieldsWrapper = new VBox();

        dropdownSearch = new DropdownSearchController<>(new IDropdownSearchRequire<IProfile>() {
            @Override
            public JFXListCell getCellFactory() {
                return new NameCheckboxCell(){};
            }
        });

        IDataPrompt dataPrompt = new DataPromptController();

        Label dropdownLabel = new Label("Tilføj deltagere: ");

        textfieldsWrapper.getChildren().addAll(dataPrompt.getView(), dropdownLabel, dropdownSearch.getView());
        dataPrompt.setPrompt(null);
        dataPrompt.setButtonText(null);
        dataPrompt.addTextFields("Klokkeslæt (Tt:Mm)", "Information om mødet");


        innercontent.getChildren().addAll(datePickerWrapper, textfieldsWrapper);

        content.setBody(innercontent);
        dialog.setDialogContainer(content);
        dialog = new JFXDialog(container, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed(event -> {
            required.getParent().getChildren().remove(this.getView());
        });

        dropdownSearch.onDone(data -> {
            HBox attendees = new HBox();
            textfieldsWrapper.getChildren().add(attendees);
            for (IProfile profile : data) {
                attendees.getChildren().add(new Label(profile.getUser().getName()));
            }
        });

    }

    @Override
    public void show(String subject, String message) {

        this.subject.setText(subject);
        this.message.setText(message);
        if(!required.getParent().getChildren().contains(this.getView())) {
            required.getParent().getChildren().add(this.getView());
            required.getParent().setTopAnchor(this.getView(), .0);
            required.getParent().setLeftAnchor(this.getView(), .0);
            required.getParent().setRightAnchor(this.getView(), .0);
            required.getParent().setBottomAnchor(this.getView(), .0);
        }
        dialog.show();
    }


    @Override
    public void close() {
        dialog.close();
    }


    @Override
    public void setRequired(IMeetingPopUpRequire required) {
        this.required = required;
    }

    @Override
    public IDropdownSearch<IProfile> getDropdownSearch() {
        return dropdownSearch;
    }
}