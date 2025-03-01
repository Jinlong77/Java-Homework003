package constant;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static constant.Config.*;
import static constant.Message.printError;

public class Validation {

    public static boolean validateChoice(String choice) {
        if (choice.matches(REGEX_TABLE_OPTION)) {
            return true;
        } else {
            printError("Invalid option, please enter number between [1-5]");
            return false;
        }
    }

    public static String getValidatedInput(Supplier<String> input, Predicate<String> validator, String errorMessage, String inputMessage) {
        String value;
        while (true) {
            System.out.print(inputMessage);
            value = input.get();
            if (validator.test(value)) return value;
            printError(errorMessage + " Try again.");
        }
    }

}
