package dog.pawbook.logic.commands;

import static dog.pawbook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_BREED;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_DOB;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_NAME;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_OWNERID;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_SESSION;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_SEX;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_TAG;
import static dog.pawbook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import dog.pawbook.logic.commands.EditDogCommand.EditDogDescriptor;
import dog.pawbook.logic.commands.EditOwnerCommand.EditOwnerDescriptor;
import dog.pawbook.logic.commands.EditProgramCommand.EditProgramDescriptor;
import dog.pawbook.logic.commands.exceptions.CommandException;
import dog.pawbook.model.Database;
import dog.pawbook.model.Model;
import dog.pawbook.model.managedentity.Entity;
import dog.pawbook.testutil.EditDogDescriptorBuilder;
import dog.pawbook.testutil.EditOwnerDescriptorBuilder;
import dog.pawbook.testutil.EditProgramDescriptorBuilder;
import javafx.util.Pair;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String VALID_NAME_ASHER = "Asher";
    public static final String VALID_NAME_BELL = "Bell";
    public static final String VALID_SEX_ASHER = "male";
    public static final String VALID_SEX_BELL = "female";
    public static final String VALID_DATEOFBIRTH_ASHER = "16-4-2020";
    public static final String VALID_DATEOFBIRTH_BELL = "15-4-2020";
    public static final String VALID_BREED_ASHER = "Corgi";
    public static final String VALID_BREED_BELL = "Greyhound";
    public static final int VALID_OWNERID_9 = 9;
    public static final int VALID_OWNERID_10 = 10;
    public static final String VALID_TAG_FRIENDLY = "friendly";
    public static final String VALID_TAG_QUIET = "quiet";

    public static final String NAME_DESC_ASHER = " " + PREFIX_NAME + VALID_NAME_ASHER;
    public static final String NAME_DESC_BELL = " " + PREFIX_NAME + VALID_NAME_BELL;
    public static final String SEX_DESC_ASHER = " " + PREFIX_SEX + VALID_SEX_ASHER;
    public static final String SEX_DESC_BELL = " " + PREFIX_SEX + VALID_SEX_BELL;
    public static final String BREED_DESC_ASHER = " " + PREFIX_BREED + VALID_BREED_ASHER;
    public static final String BREED_DESC_BELL = " " + PREFIX_BREED + VALID_BREED_BELL;
    public static final String DATEOFBIRTH_DESC_ASHER = " " + PREFIX_DOB + VALID_DATEOFBIRTH_ASHER;
    public static final String DATEOFBIRTH_DESC_BELL = " " + PREFIX_DOB + VALID_DATEOFBIRTH_BELL;
    public static final String OWNERID_DESC_9 = " " + PREFIX_OWNERID + VALID_OWNERID_9;
    public static final String OWNERID_DESC_10 = " " + PREFIX_OWNERID + VALID_OWNERID_10;
    public static final String TAG_DESC_FRIENDLY = " " + PREFIX_TAG + VALID_TAG_FRIENDLY;
    public static final String TAG_DESC_QUIET = " " + PREFIX_TAG + VALID_TAG_QUIET;

    public static final String VALID_NAME_PROGRAM_A = "Obedience Training";
    public static final String VALID_NAME_PROGRAM_B = "Endurance Training";
    public static final String VALID_SESSION_PROGRAM_A = "1-2-2021 18:00";
    public static final String VALID_SESSION_PROGRAM_B = "12-12-2012 16:00";
    public static final String VALID_TAG_PUPPIES = "puppies";
    public static final String VALID_TAG_DOGS = "dogs";

    public static final String NAME_DESC_PROGRAM1 = " " + PREFIX_NAME + VALID_NAME_PROGRAM_A;
    public static final String NAME_DESC_PROGRAM2 = " " + PREFIX_NAME + VALID_NAME_PROGRAM_B;
    public static final String SESSION_DESC_PROGRAM1 = " " + PREFIX_SESSION + VALID_SESSION_PROGRAM_A;
    public static final String SESSION_DESC_PROGRAM2 = " " + PREFIX_SESSION + VALID_SESSION_PROGRAM_B;
    public static final String TAG_DESC_PUPPIES = " " + PREFIX_TAG + "puppies";
    public static final String TAG_DESC_DOGS = " " + PREFIX_TAG + "dogs";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SEX_DESC = " " + PREFIX_SEX + "Male1"; // '1' not allowed in sex
    public static final String INVALID_DATEOFBIRTH_DESC = " " + PREFIX_DOB + "a-a-2020"; // 'a' not
    // allowed in dates of birth
    public static final String INVALID_BREED_DESC = " " + PREFIX_BREED + "poodle!"; // '!' not allowed for breed
    public static final String INVALID_OWNERID_DESC = " " + PREFIX_OWNERID; // empty ownerID not allowed
    // session only allows mm-dd-yyyy hh:mm format and should only contain numbers
    public static final String INVALID_SESSION_DESC = " " + PREFIX_SESSION + "a-a-2020 18:00";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final String TAG_EMPTY = " " + PREFIX_TAG;

    public static final EditOwnerDescriptor DESC_AMY;
    public static final EditOwnerDescriptor DESC_BOB;
    public static final EditDogDescriptor DESC_ASHER;
    public static final EditDogDescriptor DESC_BELL;
    public static final EditProgramDescriptor DESC_PROGRAM_A;
    public static final EditProgramDescriptor DESC_PROGRAM_B;

    static {
        DESC_AMY = new EditOwnerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditOwnerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_ASHER = new EditDogDescriptorBuilder().withName(VALID_NAME_ASHER).withBreed(VALID_BREED_ASHER)
                .withDob(VALID_DATEOFBIRTH_ASHER).withSex(VALID_SEX_ASHER).withOwnerId(VALID_OWNERID_9)
                .withTags(VALID_TAG_FRIENDLY).build();
        DESC_BELL = new EditDogDescriptorBuilder().withName(VALID_NAME_BELL).withBreed(VALID_BREED_BELL)
                .withDob(VALID_DATEOFBIRTH_BELL).withSex(VALID_SEX_BELL).withOwnerId(VALID_OWNERID_10)
                .withTags(VALID_TAG_QUIET).build();

        DESC_PROGRAM_A = new EditProgramDescriptorBuilder().withName(VALID_NAME_PROGRAM_A)
                .withSessions(VALID_SESSION_PROGRAM_A).withTags(VALID_TAG_PUPPIES).build();
        DESC_PROGRAM_B = new EditProgramDescriptorBuilder().withName(VALID_NAME_PROGRAM_B)
                .withSessions(VALID_SESSION_PROGRAM_B).withTags(VALID_TAG_DOGS).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the database, filtered owner list and selected owner in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Database expectedAddressBook = new Database(actualModel.getDatabase());
        List<Pair<Integer, Entity>> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEntityList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getDatabase());
        assertEquals(expectedFilteredList, actualModel.getFilteredEntityList());
    }

}
