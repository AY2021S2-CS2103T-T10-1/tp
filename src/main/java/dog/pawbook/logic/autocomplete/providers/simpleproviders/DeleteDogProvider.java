package dog.pawbook.logic.autocomplete.providers.simpleproviders;

import dog.pawbook.logic.autocomplete.providers.SimpleProvider;
import dog.pawbook.model.Model;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code DeleteDogCommand}.
 */
public class DeleteDogProvider extends SimpleProvider {

    public DeleteDogProvider(Model model) {
        super(model.getFilteredDogList());
    }

}
