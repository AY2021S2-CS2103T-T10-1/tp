package dog.pawbook.logic.autocomplete.providers;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import dog.pawbook.logic.autocomplete.AutoCompleteResult;
import dog.pawbook.logic.autocomplete.AutoCompleteResultProvider;
import dog.pawbook.model.Model;
import dog.pawbook.model.managedentity.dog.Dog;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code FindCustomerCommand}.
 */
public class FindDogProvider implements AutoCompleteResultProvider {

    private List<Dog> dogList;

    public FindDogProvider(Model model) {
        this.dogList = model.getDogList();
    }

    @Override
    public AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        List<String> nameList = dogList.stream()
            .map(dog -> dog.getName().toString())
            .collect(Collectors.toList());
        List<String> dateOfBirthList = dogList.stream()
            .map(dog -> dog.getDob().toString())
            .collect(Collectors.toList());
        List<String> sexList = dogList.stream()
            .map(dog -> dog.getSex().toString())
            .collect(Collectors.toList());
        List<String> breedList = dogList.stream()
            .map(dog -> dog.getBreed().toString())
            .collect(Collectors.toList());
        List<String> tagList = dogList.stream()
            .flatMap(dog -> dog.getTags().stream()
                .map(tag -> tag.toString().replaceAll("\\[|\\]", "")))
            .collect(Collectors.toList());
        values.addAll(nameList);
        values.addAll(breedList);
        values.addAll(dateOfBirthList);
        values.addAll(sexList);
        values.addAll(tagList);
        String stringToCompare;
        if (input.endsWith(" ")) {
            stringToCompare = "";
        } else {
            stringToCompare = input.substring(input.lastIndexOf(" ") + 1);
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
