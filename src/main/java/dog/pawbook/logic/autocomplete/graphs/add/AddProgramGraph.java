package dog.pawbook.logic.autocomplete.graphs.add;

import static dog.pawbook.logic.parser.CliSyntax.PREFIX_NAME;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_SESSION;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import dog.pawbook.logic.autocomplete.graphs.Edge;
import dog.pawbook.logic.autocomplete.graphs.GraphWithStartNode;
import dog.pawbook.logic.autocomplete.nodes.program.ProgramNameNode;
import dog.pawbook.logic.autocomplete.nodes.program.ProgramSessionSetNode;
import dog.pawbook.logic.autocomplete.nodes.program.ProgramTagNode;
import dog.pawbook.model.Model;
import dog.pawbook.model.managedentity.program.Program;


/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddPhoneCommand}.
 */
public class AddProgramGraph extends GraphWithStartNode {
    /**
     * Constructor
     * @param model
     */
    public AddProgramGraph(Model model) {
        super();
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Program> programList = model.getProgramList();
        ProgramNameNode programNameNode = new ProgramNameNode(programList);
        ProgramSessionSetNode programSessionSetNode = new ProgramSessionSetNode(programList);
        ProgramTagNode programTagNode = new ProgramTagNode(programList);

        addEdges(
            new Edge<>(PREFIX_NAME, startingNode, programNameNode),
            new Edge<>(PREFIX_SESSION, programNameNode, programSessionSetNode),
            new Edge<>(PREFIX_SESSION, programSessionSetNode, programSessionSetNode),
            new Edge<>(PREFIX_TAG, programSessionSetNode, programTagNode),
            new Edge<>(PREFIX_TAG, programTagNode, programTagNode)
        );
    }

}
