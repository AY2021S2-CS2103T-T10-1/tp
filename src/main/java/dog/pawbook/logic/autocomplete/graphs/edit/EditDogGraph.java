package dog.pawbook.logic.autocomplete.graphs.edit;

import static dog.pawbook.logic.parser.CliSyntax.PREFIX_BREED;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_DOB;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_NAME;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_SEX;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import dog.pawbook.logic.autocomplete.graphs.Edge;
import dog.pawbook.logic.autocomplete.graphs.GraphWithStartNodeAndPreamble;
import dog.pawbook.logic.autocomplete.nodes.dog.BreedNode;
import dog.pawbook.logic.autocomplete.nodes.dog.DateOfBirthNode;
import dog.pawbook.logic.autocomplete.nodes.dog.DogNameNode;
import dog.pawbook.logic.autocomplete.nodes.dog.DogTagNode;
import dog.pawbook.logic.autocomplete.nodes.dog.SexNode;
import dog.pawbook.model.Model;
import dog.pawbook.model.managedentity.dog.Dog;


/**
 * Represents a {@code Graph} used to support autocomplete for {@code EditCustomerCommand}.
 */
public class EditDogGraph extends GraphWithStartNodeAndPreamble {
    /**
     *
     * @param model
     */
    public EditDogGraph(Model model) {
        super(model.getFilteredDogList());
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Dog> dogList = model.getDogList();
        BreedNode breedNode = new BreedNode(dogList);
        DateOfBirthNode dateOfBirthNode = new DateOfBirthNode(dogList);
        DogNameNode dogNameNode = new DogNameNode(dogList);
        SexNode sexNode = new SexNode(dogList);
        DogTagNode dogTagNode = new DogTagNode(dogList);
        addEdges(
            new Edge<>(PREFIX_NAME, startingNode, dogNameNode),
            new Edge<>(PREFIX_BREED, dogNameNode, breedNode),
            new Edge<>(PREFIX_DOB, breedNode, dateOfBirthNode),
            new Edge<>(PREFIX_SEX, dateOfBirthNode, sexNode),
            new Edge<>(PREFIX_TAG, sexNode, dogTagNode),
            new Edge<>(PREFIX_TAG, dogTagNode, dogTagNode),
            new Edge<>(PREFIX_NAME, startingNode, dogNameNode),
            new Edge<>(PREFIX_BREED, dogNameNode, breedNode),
            new Edge<>(PREFIX_DOB, breedNode, dateOfBirthNode),
            new Edge<>(PREFIX_SEX, dateOfBirthNode, sexNode),
            new Edge<>(PREFIX_TAG, sexNode, dogTagNode),
            new Edge<>(PREFIX_TAG, dogTagNode, dogTagNode)
        );
    }

}
