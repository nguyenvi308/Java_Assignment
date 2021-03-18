import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {


        Scanner scanner = new Scanner(System.in);

        // Main Function
//        if (login(scanner)) {
//            menu(scanner);
//        }

        List<Student> arr = Arrays.asList(
                new Student(11181170l, "Nguyen Van Vi", Arrays.asList(new Subject("Java", 5.6, 27, 7))),
                new Student(11181340l, "Nguyen Hoang Minh", Arrays.asList(new Subject("Java", 7.0, 27, 10))),
                new Student(11143440l, "Nguyen Cong Minh", Arrays.asList(new Subject("Java", 9.0, 27, 10))),
                new Student(11343340l, "Tran Thanh Loan", Arrays.asList(new Subject("Java", 6.5, 27, 10))),
                new Student(16771340l, "Nguyen Thi Kieu Thuy", Arrays.asList(new Subject("Java", 5.5, 27, 10))),
                new Student(11147540l, "Nguyen Thu Hien", Arrays.asList(new Subject("Java", 7.6, 27, 10))),
                new Student(15361340l, "Le Quang Liem", Arrays.asList(new Subject("Java", 8.2, 27, 10))),
                new Student(11646460l, "Nguyen Van Dung", Arrays.asList(new Subject("Java", 5.5, 27, 10))),
                new Student(11756540l, "Le Mai Anh", Arrays.asList(new Subject("Java", 7.5, 27, 10))),
                new Student(11186440l, "Nguyen Thi Thuy Ha", Arrays.asList(new Subject("Java", 9.0, 27, 10))));

        System.out.println("Max List  " + DataUtils.findMax(arr));
        System.out.println("Min List " + DataUtils.findMin(arr));
        System.out.println("Count the same " + DataUtils.countTheSameScore(arr));
        Excel.writeResultWithFormat(arr);
    }

    public static void menu(Scanner scanner) throws IOException {
        boolean ans = true;
        while (ans) {
            System.out.println("Enter the number of option you want to go: ");
            System.out.println("1. Marking single file");
            System.out.println("2. Marking multiple file");
            System.out.println("3. Exit");
            int ansNumber = scanner.nextInt();
            scanner.nextLine();
            inner:
            switch (ansNumber) {
                case 1: {
                    System.out.println("You now are in marking single file. So the output will be printed out console : ");
                    System.out.println("Enter the file path");
                    String filePath = scanner.nextLine();
                    File file = new File(filePath);
                    if (DataUtils.checkFileName(file.getName())) {
                        System.out.println(Excel.markingSingleFile(filePath));
                    }
                    break inner;
                }
                case 2: {
                    System.out.println("You now are in marking multiple files. The report will be auto generated in the same directory: ");
                    System.out.println("Enter the file directory");

                    String filePath = scanner.nextLine();
                    File file = new File(filePath);
                    if (file.isDirectory()) {
                        String correctFileNameFormat = "[a-zA-Z]+_[0-9]+.xlsx";
                        List<String> files = Excel.getCorrectFileForm(correctFileNameFormat, filePath);
                        List<Student> students = Excel.markingMultipleFile(files);
                        Excel.writeResultWithFormat(students);
                        break inner;
                    }
                    break inner;
                }
                default:
                    break inner;


            }
            System.out.println("Do you want to continue ? Yes or No");
            String ans2 = scanner.nextLine();
            if (ans2.equalsIgnoreCase("Yes") || ans2.equalsIgnoreCase("y")) {
                ans = true;
            } else if (ans2.equalsIgnoreCase("No") || ans2.equalsIgnoreCase("no")) {
                ans = false;
            } else {
                System.out.println("I don't know what you mean so we continue...");
            }
        }
    }


    public static boolean login(Scanner scanner) {
        boolean ans = true;
        while (ans) {
            System.out.println("Enter User Name: ");
            String userName = scanner.nextLine();
            System.out.println("Enter password: ");
            String pass = scanner.nextLine();
            Map<String, String> accounts = new HashMap<>();
            accounts.put("admin", "admin");
            if (accounts.containsKey(userName.trim())) {
                if (accounts.get(userName.trim()).equals(pass.trim())) {
                    ans = false;

                } else {
                    System.err.println("Password is not correct");
                    ans = true;

                }
            } else {
                System.err.println("Username is not correct!");
                ans = true;

            }
        }
        return true;

    }


}
