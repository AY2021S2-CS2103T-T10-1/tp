//@@author ZhangAnli
package dog.pawbook.logic.parser;

import static dog.pawbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dog.pawbook.commons.core.Messages.MESSAGE_NEGATIVE_ENTITY_ID;

import dog.pawbook.logic.commands.ViewCommand;
import dog.pawbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Check if argument is empty
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        // Check if integer is valid
        int id = ParserUtil.parseId(trimmedArgs);

        if (id < 1) {
            throw new ParseException(MESSAGE_NEGATIVE_ENTITY_ID);
        }

        return new ViewCommand(id);
    }
}
