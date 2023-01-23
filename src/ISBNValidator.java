import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class ISBNValidator {
    private String[] validNums;
    private String[] invalidNums;
    private String filename;

    public static void main(String[] args) {
        ISBNValidator app = new ISBNValidator();
        System.out.println("* ISBN Validator Program *");
        System.out.println("...Importing data...");
        app.importData();
        app.runProgram();
        System.out.println("* End of Program *");
    }

    public ISBNValidator() {
        filename = "isbn_files/isbn1.dat";
        int lines = 0;
        try {
            Scanner in = new Scanner(new File(filename));
            while(in.hasNext()) {
                lines++;
                in.nextLine();
            }
            in.close();
        }
        catch(Exception e)  {
            System.out.println(e.toString());
        }
        validNums = new String[lines];
        invalidNums = new String[lines];
    }

    public void importData() {
        try {
            Scanner in = new Scanner(new File(filename));
            int validCount = 0;
            int invalidCount = 0;
            while(in.hasNext()) {
                String isbn = in.nextLine().trim().strip();
                if(isValidISBN(isbn))
                    validNums[validCount++] = isbn;
                else
                    invalidNums[invalidCount++] = isbn;
            }
            in.close();
        }
        catch(Exception e)  {
            System.out.println(e.toString());
        }
    }

    public boolean isValidISBN(String isbn) {
        if (isbn.startsWith("978") || isbn.startsWith("979")) {
            return true;
        }
        isbn = isbn.replaceAll("-", "");
        int sum = 0;
        for (int i = 1; i < 14; i++) {
            int num = Integer.parseInt(isbn.substring(i-1, i));
            if (i % 2 == 0)
                sum += 3*num;
            else {
                sum += num;
            }
        }
        return sum % 10 == 0 ;
    }

    public void runProgram() {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(Arrays.toString(validNums));

            System.out.println("All ISBN data has been imported and validated. Would you like to:");
            System.out.println("1) View all valid ISBN numbers");
            System.out.println("2) View all invalid ISBN numbers");
            System.out.println("3) Quit");
            System.out.print("Your Selection: ");
            String option = in.nextLine();
            if (option.equals("3"))
                break;
            else if (option.equals("1")) {
                for (int i = 0; i < validNums.length && validNums[i] != null; i++)
                    System.out.println(validNums[i]);
            }
            else if (option.equals("2")){
                for (int i = 0; i < invalidNums.length && invalidNums[i] != null; i++)
                    System.out.println(invalidNums[i]);
            }
            else {
                System.out.println("Invalid choice, try again");
            }
        }
    }
}
