package dog.pawbook.logic.autocomplete;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import dog.pawbook.logic.autocomplete.graphs.add.AddDogGraph;
import dog.pawbook.logic.autocomplete.graphs.add.AddOwnerGraph;
import dog.pawbook.logic.autocomplete.graphs.add.AddProgramGraph;
import dog.pawbook.logic.autocomplete.graphs.edit.EditDogGraph;
import dog.pawbook.logic.autocomplete.graphs.edit.EditOwnerGraph;
import dog.pawbook.logic.autocomplete.graphs.edit.EditProgramGraph;
import dog.pawbook.logic.autocomplete.providers.EmptyProvider;
import dog.pawbook.logic.autocomplete.providers.FindDogProvider;
import dog.pawbook.logic.autocomplete.providers.simpleproviders.DeleteDogProvider;
import dog.pawbook.logic.autocomplete.providers.simpleproviders.DeleteOwnerProvider;
import dog.pawbook.logic.autocomplete.providers.simpleproviders.DeleteProgramProvider;
import dog.pawbook.model.Model;

/**
 * Represents a generator for autocomplete support.
 */
public class AutoCompleteResultGenerator {

    private final Model model;
    private final Map<String, AutoCompleteResultProvider> providers;

    /**
     *
     * @param model
     */
    public AutoCompleteResultGenerator(Model model) {
        this.model = model;
        providers = new HashMap<>();

        initProviders();
    }

    /**
     * Instantiates supporting providers.
     */
    private void initProviders() {
        // Dog commands
        providers.put("add dog", new AddDogGraph(model));
        providers.put("delete dog", new DeleteDogProvider(model));
        providers.put("list dog", EmptyProvider.getInstance());
        providers.put("edit", new EditDogGraph(model));
        providers.put("find", new FindDogProvider(model));
        // Owner commands
        providers.put("add owner", new AddOwnerGraph(model));
        providers.put("delete owner", new DeleteOwnerProvider(model));
        providers.put("list owner", EmptyProvider.getInstance());
        providers.put("edit owner", new EditOwnerGraph(model));
        //providers.put("find", new FindOwnerProvider(model));
        // Program commands
        providers.put("add program", new AddProgramGraph(model));
        providers.put("delete program", new DeleteProgramProvider(model));
        providers.put("list program", EmptyProvider.getInstance());
        providers.put("edit program", new EditProgramGraph(model));
        //providers.put("find", new FindProgramProvider(model));
        // General commands
        providers.put("exit", EmptyProvider.getInstance());
        providers.put("help", EmptyProvider.getInstance());
        
    }

    private Optional<AutoCompleteResultProvider> getProvider(String commandWord) {
        return Optional.ofNullable(providers.get(commandWord));
    }

    public Set<String> getSupportedCommandWords() {
        return providers.keySet();
    }

    /**
     * Processes an input string and returns possible autocomplete results.
     * @param input A user input string.
     * @return An {@code AutoCompleteResult} containing possible autocomplete values.
     */
    public AutoCompleteResult process(String input) {
        int firstSpace = input.indexOf(" ");
        //indicating still typing command word
        if (firstSpace == -1 || firstSpace == 3 || firstSpace == 4 || firstSpace == 6) {
            SortedSet<String> values = new TreeSet<>(getSupportedCommandWords());
            return new AutoCompleteResult(values, input);
        } else { // there is at least one space, suggesting command word is present
            String commandWord = input.substring(0, firstSpace);
            Optional<AutoCompleteResultProvider> provider = getProvider(commandWord);
            if (provider.isPresent()) { // command word is supported
                String args = input.substring(firstSpace);
                return provider.get().process(args);
            } else { // command word not supported, return empty set
                return new AutoCompleteResult(Collections.emptySortedSet(), input);
            }
        }
    }

}
