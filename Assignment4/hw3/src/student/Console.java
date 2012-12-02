package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Console {

    private WebFilter myFilter;
    private Scanner scan;

    public static void main(String[] args) {
        Console console = new Console();
        while (true) {
            System.out.println("Please enter a command or \"help\" for a list of commands.");
            console.handleCommand();
        }
    }

    void handleCommand() {
	String command = scan.next();
        if (command.equals("clearFilter")) clearBlacklist();
        else if (command.equals("addBlacklist")) addBlacklist();
        else if (command.equals("filter")) filter();
        else if (command.equals("perf")) perf();
	else if (command.equals("help")) printHelp();
        else System.out.println(command + " is not a valid command.");
    }

    public Console() {
        scan = new Scanner(System.in);
        myFilter = new WebFilter();
    }

    private void clearBlacklist() {
        myFilter.clearBlacklist();
    }

    private void addBlacklist() {
        myFilter.addToFilterFromFile(scan.next());
    }

    private void filter() {
        try {
            Scanner inputFile = new Scanner(new FileReader(new File(scan.next())));
            FileWriter outputFile = new FileWriter(new File(scan.next()));
            while (inputFile.hasNext()) {
                String url = inputFile.nextLine();
                if (myFilter.isBlacklisted(url)) {
                    outputFile.append(url);
                    outputFile.append("\n");
                }
            }
            inputFile.close();
            outputFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("The given file does not exist.");
        } catch (IOException e) {
            System.out.println("Error printing to the given output file.");
        }
    }

    private void perf() {
        try {
            Scanner inputFile = new Scanner(new FileReader(new File(scan.next())));
            int numUrlsToRead = scan.nextInt();
            String[] urls = new String[numUrlsToRead];
            int i = 0;
            while (inputFile.hasNext() && i < numUrlsToRead) {
                urls[i] = inputFile.nextLine();
                i++;
            }
            int numUrlsFromFile = i;
            while (i < numUrlsToRead) {
                urls[i] = urls[i - numUrlsFromFile];
                i++;
            }
            long start = System.currentTimeMillis();
            int numPassedFile = 0;
            for (int j = 0; j < urls.length; j++) {
                if (myFilter.isBlacklisted(urls[j])) {
                    ++numPassedFile;
                }
            }
            long total = System.currentTimeMillis() - start;
            System.out.println("Of " + numUrlsToRead + " URLs, " + numPassedFile + " passed the " + 
                    "web filter in " + total + " milliseconds.");
            inputFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("The given file does not exist.");
        }
    }

    private void printHelp() {
        System.out.println("clearFilter : empty the web filter blacklist");
        System.out.println("addBlacklist <blacklist_file> : add the URLs from the file " +
                "specified to the web filter");
        System.out.println("filter <input_urls> <filtered_urls> : read URLs from the \"input_urls\" file " +
                "and add all blacklisted URLs to the file specified by \"filtered_urls"); 
        System.out.println("perf <input_urls> <n> : prints the number of URLs that are not blacklisted " + 
                "as well as the amount of time taken in milliseconds to test \"n\" URLs");
    }
}
