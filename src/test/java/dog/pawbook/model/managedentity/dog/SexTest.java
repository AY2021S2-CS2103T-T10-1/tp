package dog.pawbook.model.managedentity.dog;

import static dog.pawbook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SexTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sex(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Sex(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Sex.isValidSex(null));

        // invalid addresses
        assertFalse(Sex.isValidSex("")); // empty string
        assertFalse(Sex.isValidSex(" ")); // spaces only
        assertFalse(Sex.isValidSex("m"));
        assertFalse(Sex.isValidSex("f"));

        // valid addresses
        assertTrue(Sex.isValidSex("Male"));
        assertTrue(Sex.isValidSex("Female"));
        assertTrue(Sex.isValidSex("male"));
        assertTrue(Sex.isValidSex("female"));
        assertTrue(Sex.isValidSex("M"));
        assertTrue(Sex.isValidSex("F"));
    }
}
