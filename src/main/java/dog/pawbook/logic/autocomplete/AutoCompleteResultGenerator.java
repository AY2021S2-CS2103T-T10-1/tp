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
        //providers.put("switch-c", EmptyProvider.getInstance());
        providers.put("add dog", new AddDogGraph(model));
        //providers.put("delete-c", new DeleteCustomerProvider(model));
        //providers.put("find-c", new FindCustomerProvider(model));
        //providers.put("list-c", EmptyProvider.getInstance());
        //providers.put("clear-c", EmptyProvider.getInstance());
        //providers.put("edit-c", new EditCustomerGraph(model));
        //providers.put("copy-c", new CopyCustomerProvider(model));

        /*
        // Phone commands
        providers.put("switch-p", EmptyProvider.getInstance());
        providers.put("add-p", new AddPhoneGraph(model));
        providers.put("delete-p", new DeletePhoneProvider(model));
        providers.put("find-p", new FindPhoneProvider(model));
        providers.put("list-p", EmptyProvider.getInstance());
        providers.put("clear-p", EmptyProvider.getInstance());
        providers.put("edit-p", new EditPhoneGraph(model));
        providers.put("copy-p", new CopyPhoneProvider(model));*/

        /*
        // Order commands
        providers.put("switch-o", EmptyProvider.getInstance());
        providers.put("add-o", new AddOrderGraph(model));
        providers.put("find-o", new FindOrderProvider(model));
        providers.put("complete", new CompleteOrderProvider(model));
        providers.put("cancel", new CancelOrderProvider(model));
        providers.put("list-o", EmptyProvider.getInstance());
        providers.put("clear-o", EmptyProvider.getInstance());
        providers.put("edit-o", new EditOrderGraph(model));
        providers.put("copy-o", new CopyOrderProvider(model));

        // Schedule commands
        providers.put("switch-s", EmptyProvider.getInstance());
        providers.put("schedule", new ViewScheduleGraph(model));
        providers.put("add-s", new AddScheduleGraph(model));
        providers.put("delete-s", new DeleteScheduleProvider(model));
        providers.put("edit-s", new EditScheduleGraph(model));
        providers.put("clear-s", EmptyProvider.getInstance());

        // Archived order commands
        providers.put("switch-a", EmptyProvider.getInstance());
        providers.put("clear-a", EmptyProvider.getInstance());

        // General commands
        providers.put("undo", EmptyProvider.getInstance());
        providers.put("redo", EmptyProvider.getInstance());
        providers.put("history", EmptyProvider.getInstance());
        providers.put("generate-s", new GenerateStatsGraph(model));
        providers.put("exit", EmptyProvider.getInstance());
        providers.put("help", EmptyProvider.getInstance());
        providers.put("export", EmptyProvider.getInstance()); */
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
