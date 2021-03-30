package dog.pawbook.logic.autocomplete.nodes.dog;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import dog.pawbook.logic.autocomplete.nodes.AutoCompleteNode;
import dog.pawbook.model.managedentity.dog.Dog;

/**
 * Represents a {@code Node} tracking {@code Customer} {@code Tag} for autocompletion.
 */
public class DogTagNode extends AutoCompleteNode<List<Dog>> {

    public DogTagNode(List<Dog> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(dog ->
            dog.getTags().forEach(tag ->
                values.add(tag.toString().replaceAll("\\[|\\]", ""))));
        return values;
    }

}
