package dog.pawbook.logic.autocomplete.graphs;

import static dog.pawbook.commons.util.ProviderUtil.extractPreamble;
import static dog.pawbook.commons.util.ProviderUtil.hasCompletePreamble;
import static dog.pawbook.commons.util.ProviderUtil.isValidIndex;
import static dog.pawbook.commons.util.ProviderUtil.populateValuesWithIndexes;
import static dog.pawbook.logic.parser.ParserUtil.parseIndex;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import dog.pawbook.commons.core.index.Index;
import dog.pawbook.logic.autocomplete.AutoCompleteResult;
import dog.pawbook.logic.parser.exceptions.ParseException;

/**
 * Represents a {@code Graph} that supports commands that accept both a preamble and arguments.
 */
public abstract class GraphWithStartNodeAndPreamble extends GraphWithStartNode {

    private final List<?> dataList;

    /**
     * @param dataList
     */
    public GraphWithStartNodeAndPreamble(List<?> dataList) {
        super();
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
            } else {
                String preamble = extractPreamble(input);
                try {
                    // parse to ensure it is a valid index
                    Index index = parseIndex(preamble);
                    boolean valid = isValidIndex(index, dataList);
                    if (valid) {
                        // if valid, extract arguments
                        String argString = input.stripLeading().substring(preamble.length());
                        // return autocomplete result from arguments
                        return super.process(argString);
                    }
                } catch (ParseException e) {
                    // preamble is invalid
                    // suggest nothing
                }
            }
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
