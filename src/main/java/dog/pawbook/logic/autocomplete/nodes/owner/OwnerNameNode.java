package dog.pawbook.logic.autocomplete.nodes.owner;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import dog.pawbook.logic.autocomplete.nodes.AutoCompleteNode;
import dog.pawbook.model.managedentity.owner.Owner;

/**
 * Represents a {@code Node} tracking {@code Phone} {@code Cost} for autocompletion.
 */
public class OwnerNameNode extends AutoCompleteNode<List<Owner>> {

    public OwnerNameNode(List<Owner> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(owner -> values.add(owner.getName().toString()));
        return values;
    }

}

