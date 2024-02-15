package task2.util;

import java.util.Arrays;
import java.util.List;

public class ArgParser {
    private String inputFilename;
    private String inputParserMsg;
    private boolean isInputOK = false;

    public ArgParser(String[] strings) {
        this.parse(strings);
    }

    public boolean isOk() {
        return isInputOK;
    }
    public boolean hasFileName() {
        return inputFilename != null;
    }

    public String getFileName() {
        return  this.inputFilename;
    }

    public String getMessage() {
        return this.inputParserMsg;
    }

    public void parse(String[] inputStrings) {
        isInputOK = false;
        if (inputStrings.length > 1) {
            inputParserMsg = "Too many arguments provided";
            return;
        }

//        List<String> args = Arrays.asList(inputStrings);
//        this.reverseFlag = args.contains("-r") || args.contains("--reverse");
//        this.caseSensitivityFlag = args.contains("-c") || args.contains("--case");

        if (inputStrings.length == 1) {
            this.inputFilename = inputStrings[0];
        }
        this.isInputOK = true;
    }
}

