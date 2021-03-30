package dog.pawbook.logic.autocomplete.providers;

import static dog.pawbook.commons.util.ProviderUtil.hasCompletePreamble;
import static dog.pawbook.commons.util.ProviderUtil.populateValuesWithIndexes;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import dog.pawbook.logic.autocomplete.AutoCompleteResult;
import dog.pawbook.logic.autocomplete.AutoCompleteResultProvider;

/**
 * Represents a simple pointer to a list.
 */
public abstract class SimpleProvider implements AutoCompleteResultProvider {

    protected final List<?> dataList;

    protected SimpleProvider(List<?> dataList) {
        requireNonNull(dataList);
        this.dataList = dataList;
    }

    @Override
    public AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        String stringToCompare = input; // dummy value

        if (input.isBlank()) { // empty, all whitespaces, or null
            // suggest indexes
            populateValuesWithIndexes(values, dataList);
            // match any
            stringToCompare = "";
        } else {
            if (!hasCompletePreamble(input)) {
                // user is entering preamble
                // suggest indexes
                populateValuesWithIndexes(values, dataList);
                // match partially entered preamble
                stringToCompare = input.stripLeading();
            }
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
