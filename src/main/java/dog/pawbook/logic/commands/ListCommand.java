package dog.pawbook.logic.commands;

import static dog.pawbook.model.Model.PREDICATE_SHOW_ALL_OWNERS;
import static java.util.Objects.requireNonNull;

import dog.pawbook.model.Model;

/**
 * Lists all owners in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all owners";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOwnerList(PREDICATE_SHOW_ALL_OWNERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
