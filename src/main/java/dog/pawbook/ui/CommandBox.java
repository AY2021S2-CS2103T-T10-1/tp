package dog.pawbook.ui;

import java.util.List;
import java.util.stream.Collectors;

import dog.pawbook.logic.Logic;
import dog.pawbook.logic.autocomplete.AutoCompleteResult;
import dog.pawbook.logic.commands.CommandResult;
import dog.pawbook.logic.commands.exceptions.CommandException;
import dog.pawbook.logic.parser.exceptions.ParseException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;



/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final Logic logic;

    @FXML
    private AutoCompleteTextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        addListener();
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Adds a listener to extract the input text on change for autocomplete.
     */
    private void addListener() {
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // calls #setStyleToDefault() whenever there is a change to the text of the command box.
            commandTextField.setStyleToDefault();
            String enteredText = commandTextField.getText();
            // hide suggestions if no input
            if (enteredText == null || enteredText.isEmpty()) {
                commandTextField.getEntriesPopup().hide();
            } else {
                AutoCompleteResult result = logic.getAutoCompleteResult(enteredText);

                // add possible values
                commandTextField.getEntries().clear();
                commandTextField.getEntries().addAll(result.getValues());

                // filter
                String stringToCompare = result.getStringToCompare();
                List<String> filteredEntries = commandTextField.getEntries().stream()
                    .filter(e -> e.contains(stringToCompare))
                    .sorted((e1, e2) -> commandTextField.compareEntries(e1, e2, stringToCompare))
                    .collect(Collectors.toList());
                if (!filteredEntries.isEmpty() && !filteredEntries.contains(stringToCompare)) {
                    commandTextField.populatePopup(filteredEntries, stringToCompare);
                    commandTextField.refreshDropdown();
                } else {
                    commandTextField.getEntriesPopup().hide();
                }
            }
        });

        commandTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            commandTextField.getEntriesPopup().hide();
        }));
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see dog.pawbook.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }



}
