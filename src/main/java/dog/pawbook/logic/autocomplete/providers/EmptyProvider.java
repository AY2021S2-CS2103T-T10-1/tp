package dog.pawbook.logic.autocomplete.providers;

import java.util.Collections;

import dog.pawbook.logic.autocomplete.AutoCompleteResult;
import dog.pawbook.logic.autocomplete.AutoCompleteResultProvider;

/**
 * Represents a {@code AutoCompleteResultProvider} that returns no results.
 */
public class EmptyProvider implements AutoCompleteResultProvider {

    private static EmptyProvider theOne;

    private EmptyProvider() {}

    public static EmptyProvider getInstance() {
        if (theOne == null) {
            theOne = new EmptyProvider();
        }
        return theOne;
    }

    @Override
    public AutoCompleteResult process(String input) {
        return new AutoCompleteResult(Collections.emptySortedSet(), "");
    }

}
