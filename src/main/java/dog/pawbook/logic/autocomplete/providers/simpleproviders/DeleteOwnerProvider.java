package dog.pawbook.logic.autocomplete.providers.simpleproviders;

import dog.pawbook.logic.autocomplete.providers.SimpleProvider;
import dog.pawbook.model.Model;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code DeleteCustomerCommand}.
 */
public class DeleteOwnerProvider extends SimpleProvider {

    public DeleteOwnerProvider(Model model) {
        super(model.getFilteredOwnerList());
    }

}
