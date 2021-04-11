//@@author CharlesLee01
package dog.pawbook.logic.commands;

import static dog.pawbook.commons.util.CollectionUtil.requireAllNonNull;
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
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import dog.pawbook.commons.util.CollectionUtil;
import dog.pawbook.logic.commands.exceptions.CommandException;
import dog.pawbook.model.Model;
import dog.pawbook.model.managedentity.Entity;
import dog.pawbook.model.managedentity.IdMatchPredicate;
import dog.pawbook.model.managedentity.Name;
import dog.pawbook.model.managedentity.tag.Tag;

/**
 * Edits the details of an existing owner in the database.
 */
public abstract class EditEntityCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the entity identified "
            + "by the ID number. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters:\n "
            + "1) For Owner: ID "
            + "[" + PREFIX_NAME + "OWNER_NAME] "
            + "[" + PREFIX_PHONE + "PHONE_NUMBER] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "2) For Dog: ID "
            + "[" + PREFIX_NAME + "DOG_NAME] "
            + "[" + PREFIX_BREED + "BREED] "
            + "[" + PREFIX_DOB + "DATE_OF_BIRTH] "
            + "[" + PREFIX_SEX + "SEX] "
            + "[" + PREFIX_OWNERID + "OWNER_ID] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "3) For Program: ID "
            + "[" + PREFIX_NAME + "PROGRAM_NAME] "
            + "[" + PREFIX_SESSION + "SESSION]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "ID must be a positive integer";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    protected final Integer id;
    protected final EditEntityDescriptor editEntityDescriptor;

    /**
     * @param id of the owner in the filtered owner list to edit
     * @param editEntityDescriptor details to edit the owner with
     */
    protected EditEntityCommand(Integer id, EditEntityDescriptor editEntityDescriptor) {
        requireAllNonNull(id, editEntityDescriptor);

        this.id = id;
        this.editEntityDescriptor = editEntityDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasEntity(id)) {
            throw new CommandException(getInvalidIdMessage());
        }

        Entity targetEntity = model.getEntity(id);
        Entity editedEntity = createEditedEntity(targetEntity, editEntityDescriptor);

        if (editedEntity.getClass() != targetEntity.getClass()) {
            throw new CommandException("Entity to edit does not match given entity type!");
        }

        if (!targetEntity.isSameAs(editedEntity) && model.hasEntity(editedEntity)) {
            throw new CommandException(getDuplicateEntityMessage());
        }

        model.setEntity(id, editedEntity);
        model.updateFilteredEntityList(new IdMatchPredicate(id));
        return new CommandResult(getSuccessMessage(editedEntity));
    }

    protected abstract String getDuplicateEntityMessage();

    protected abstract String getSuccessMessage(Entity editedEntity);

    protected abstract String getInvalidIdMessage();

    /**
     * Creates and returns an {@code Entity} with the details of {@code entityToEdit}
     * edited with {@code editEntityDescriptor}.
     */
    protected abstract Entity createEditedEntity(Entity entityToEdit, EditEntityDescriptor editEntityDescriptor)
            throws CommandException;

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEntityCommand)) {
            return false;
        }

        // state check
        EditEntityCommand e = (EditEntityCommand) other;
        return id.equals(e.id)
                && editEntityDescriptor.equals(e.editEntityDescriptor);
    }

    /**
     * Stores the details to edit the owner with. Each non-empty field value will replace the
     * corresponding field value of the owner.
     */
    public static class EditEntityDescriptor {
        protected Name name;
        protected Set<Tag> tags;

        public EditEntityDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEntityDescriptor(EditEntityDescriptor toCopy) {
            setName(toCopy.name);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isNoFieldEdited() {
            return CollectionUtil.isAllNull(name, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEntityDescriptor)) {
                return false;
            }

            // state check
            EditEntityDescriptor e = (EditEntityDescriptor) other;

            return getName().equals(e.getName())
                    && getTags().equals(e.getTags());
        }
    }
}
