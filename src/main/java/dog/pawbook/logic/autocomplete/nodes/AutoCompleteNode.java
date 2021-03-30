package dog.pawbook.logic.autocomplete.nodes;

import dog.pawbook.logic.autocomplete.AutoCompleteValueProvider;

/**
 * Represents a {@code Node} that implements {@code AutoCompleteValueProvider}.
 */
public abstract class AutoCompleteNode<V> extends Node<V> implements AutoCompleteValueProvider {

    public AutoCompleteNode(V pointer) {
        super(pointer);
    }

}
