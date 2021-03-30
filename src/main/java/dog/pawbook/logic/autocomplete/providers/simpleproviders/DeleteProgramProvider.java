package dog.pawbook.logic.autocomplete.providers.simpleproviders;

import dog.pawbook.logic.autocomplete.providers.SimpleProvider;
import dog.pawbook.model.Model;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code DeleteCustomerCommand}.
 */
public class DeleteProgramProvider extends SimpleProvider {

    public DeleteProgramProvider(Model model) {
        super(model.getFilteredProgramList());
    }

}
