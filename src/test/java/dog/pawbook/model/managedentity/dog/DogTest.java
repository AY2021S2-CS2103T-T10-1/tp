package dog.pawbook.model.managedentity.dog;

import static dog.pawbook.logic.commands.CommandTestUtil.VALID_BREED_BELL;
import static dog.pawbook.logic.commands.CommandTestUtil.VALID_DATEOFBIRTH_BELL;
import static dog.pawbook.logic.commands.CommandTestUtil.VALID_NAME_BELL;
import static dog.pawbook.logic.commands.CommandTestUtil.VALID_SEX_BELL;
import static dog.pawbook.logic.commands.CommandTestUtil.VALID_TAG_QUIET;
import static dog.pawbook.testutil.Assert.assertThrows;
import static dog.pawbook.testutil.TypicalEntities.APPLE;
import static dog.pawbook.testutil.TypicalEntities.BELL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

import dog.pawbook.model.managedentity.Name;
import dog.pawbook.model.managedentity.tag.Tag;
import dog.pawbook.testutil.DogBuilder;


public class DogTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Dog dog = new DogBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> dog.getTags().remove(0));
    }

    @Test
    public void getName() {
        assertEquals(new Name("Apple"), APPLE.getName());
        assertEquals(new Name("Bell"), BELL.getName());
    }

    @Test
    public void getBreed() {
        assertEquals(new Breed("Golden Retriever"), APPLE.getBreed());
        assertEquals(new Breed("Greyhound"), BELL.getBreed());
    }

    @Test
    public void getSex() {
        assertEquals(new Sex("female"), APPLE.getSex());
        assertEquals(new Sex("male"), BELL.getSex());
    }

    @Test
    public void getDob() {
        assertEquals(new DateOfBirth("11-02-2020"), APPLE.getDob());
        assertEquals(new DateOfBirth("15-04-2020"), BELL.getDob());
    }

    @Test
    public void getOwnerId() {
        assertEquals(APPLE.getOwnerId(), 1);
        assertEquals(BELL.getOwnerId(), 17);
    }

    @Test
    public void getTags() {
        assertEquals(Set.of(new Tag("friendly")), APPLE.getTags());
        assertEquals(Set.of(new Tag("friendly"), new Tag("quiet")), BELL.getTags());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Dog appleCopy = new DogBuilder(APPLE).build();
        assertEquals(appleCopy, APPLE);

        // same object -> returns true
        assertEquals(APPLE, APPLE);

        // null -> returns false
        assertNotEquals(APPLE, null);

        // different type -> returns false
        assertNotEquals(APPLE, 5);

        // different dog -> returns false
        assertNotEquals(BELL, APPLE);

        // different name -> returns false
        Dog editedApple = new DogBuilder(APPLE).withName(VALID_NAME_BELL).build();
        assertNotEquals(editedApple, APPLE);

        // different sex -> returns false
        editedApple = new DogBuilder(APPLE).withSex(VALID_SEX_BELL).build();
        assertNotEquals(editedApple, APPLE);

        // different breed -> returns false
        editedApple = new DogBuilder(APPLE).withBreed(VALID_BREED_BELL).build();
        assertNotEquals(editedApple, APPLE);

        // different date of birth -> returns false
        editedApple = new DogBuilder(APPLE).withDateOfBirth(VALID_DATEOFBIRTH_BELL).build();
        assertNotEquals(editedApple, APPLE);

        // different tags -> returns false
        editedApple = new DogBuilder(APPLE).withTags(VALID_TAG_QUIET).build();
        assertNotEquals(editedApple, APPLE);
    }
}
