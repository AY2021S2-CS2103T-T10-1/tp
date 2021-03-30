package dog.pawbook.logic.autocomplete.graphs;

import dog.pawbook.logic.autocomplete.AutoCompleteResultProvider;
import dog.pawbook.logic.autocomplete.nodes.AutoCompleteNode;

/**
 * Represents a {@code Graph} that implements {@code AutoCompleteResultProvider}.
 */
public abstract class AutoCompleteGraph extends Graph<AutoCompleteNode<?>> implements AutoCompleteResultProvider {}
