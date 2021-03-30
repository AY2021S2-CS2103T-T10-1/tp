package dog.pawbook.logic.autocomplete.nodes.dog;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import dog.pawbook.logic.autocomplete.nodes.AutoCompleteNode;
import dog.pawbook.model.managedentity.dog.Dog;

/**
 * Represents a {@code Node} tracking {@code Phone} {@code Cost} for autocompletion.
 */
public class DateOfBirthNode extends AutoCompleteNode<List<Dog>> {

    public DateOfBirthNode(List<Dog> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(dog -> values.add(dog.getDob().toString()));
        return values;
    }

}
