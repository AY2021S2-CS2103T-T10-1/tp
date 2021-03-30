package dog.pawbook.logic.autocomplete.nodes.program;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import dog.pawbook.logic.autocomplete.nodes.AutoCompleteNode;
import dog.pawbook.model.managedentity.program.Program;

/**
 * Represents a {@code Node} tracking {@code Phone} {@code Cost} for autocompletion.
 */
public class ProgramSessionSetNode extends AutoCompleteNode<List<Program>> {

    public ProgramSessionSetNode(List<Program> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(program ->
            program.getSessions().forEach(session ->
                values.add(session.toString().replaceAll("\\[|\\]", ""))));
        return values;
    }

}

