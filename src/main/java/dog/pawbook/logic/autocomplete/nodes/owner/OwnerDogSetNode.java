package dog.pawbook.logic.autocomplete.nodes.owner;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import dog.pawbook.logic.autocomplete.nodes.AutoCompleteNode;
import dog.pawbook.model.managedentity.owner.Owner;

/**
 * Represents a {@code Node} tracking {@code Customer} {@code Tag} for autocompletion.
 */
public class OwnerDogSetNode extends AutoCompleteNode<List<Owner>> {

    public OwnerDogSetNode(List<Owner> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(customer ->
            customer.getDogIdSet().forEach(dogId ->
                values.add(dogId.toString().replaceAll("\\[|\\]", ""))));
        return values;
    }

}
