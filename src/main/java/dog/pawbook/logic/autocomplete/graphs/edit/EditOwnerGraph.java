package dog.pawbook.logic.autocomplete.graphs.edit;

import static dog.pawbook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_DOGID;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_NAME;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static dog.pawbook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import dog.pawbook.logic.autocomplete.graphs.Edge;
import dog.pawbook.logic.autocomplete.graphs.GraphWithStartNodeAndPreamble;
import dog.pawbook.logic.autocomplete.nodes.owner.AddressNode;
import dog.pawbook.logic.autocomplete.nodes.owner.EmailNode;
import dog.pawbook.logic.autocomplete.nodes.owner.OwnerDogSetNode;
import dog.pawbook.logic.autocomplete.nodes.owner.OwnerNameNode;
import dog.pawbook.logic.autocomplete.nodes.owner.OwnerTagNode;
import dog.pawbook.logic.autocomplete.nodes.owner.PhoneNode;
import dog.pawbook.model.Model;
import dog.pawbook.model.managedentity.owner.Owner;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code EditCustomerCommand}.
 */
public class EditOwnerGraph extends GraphWithStartNodeAndPreamble {
    /**
     *
     * @param model
     */
    public EditOwnerGraph(Model model) {
        super(model.getFilteredOwnerList());
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Owner> ownerList = model.getOwnerList();
        OwnerNameNode ownerNameNode = new OwnerNameNode(ownerList);
        PhoneNode phoneNode = new PhoneNode(ownerList);
        EmailNode emailNode = new EmailNode(ownerList);
        AddressNode addressNode = new AddressNode(ownerList);
        OwnerDogSetNode ownerDogSetNode = new OwnerDogSetNode(ownerList);
        OwnerTagNode ownerTagNode = new OwnerTagNode(ownerList);

        addEdges(
            new Edge<>(PREFIX_NAME, startingNode, ownerNameNode),
            new Edge<>(PREFIX_PHONE, ownerNameNode, phoneNode),
            new Edge<>(PREFIX_EMAIL, phoneNode, emailNode),
            new Edge<>(PREFIX_ADDRESS, emailNode, addressNode),
            new Edge<>(PREFIX_DOGID, addressNode, ownerDogSetNode),
            new Edge<>(PREFIX_DOGID, ownerDogSetNode, ownerDogSetNode),
            new Edge<>(PREFIX_TAG, ownerDogSetNode, ownerTagNode),
            new Edge<>(PREFIX_TAG, ownerTagNode, ownerTagNode),
            new Edge<>(PREFIX_NAME, startingNode, ownerNameNode),
            new Edge<>(PREFIX_PHONE, ownerNameNode, phoneNode),
            new Edge<>(PREFIX_EMAIL, phoneNode, emailNode),
            new Edge<>(PREFIX_ADDRESS, emailNode, addressNode),
            new Edge<>(PREFIX_DOGID, addressNode, ownerDogSetNode),
            new Edge<>(PREFIX_DOGID, ownerDogSetNode, ownerDogSetNode),
            new Edge<>(PREFIX_TAG, ownerDogSetNode, ownerTagNode),
            new Edge<>(PREFIX_TAG, ownerTagNode, ownerTagNode)
        );
    }

}
