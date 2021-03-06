package dog.pawbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dog.pawbook.commons.core.Messages;
import dog.pawbook.commons.core.index.Index;
import dog.pawbook.logic.commands.exceptions.CommandException;
import dog.pawbook.model.Model;
import dog.pawbook.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteOwnerCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public DeleteOwnerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteOwnerCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteOwnerCommand) other).targetIndex)); // state check
    }
}
