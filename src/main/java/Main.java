import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

            if (login(scanner)) {
                menu(scanner);
        }

    }

    public static void menu(Scanner scanner) {
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
                    if (checkFileName(file.getName())) {
                        System.out.println(markingSingleFile(filePath));
                    }
                    break inner;
                }
                case 2: {
                    System.out.println("You now are in marking multiple files. The report will be auto generated in the same directory: ");
                    System.out.println("Enter the file directory");

                    String filePath = scanner.nextLine();
                    scanner.nextLine();
                    File file = new File(filePath);
                    if (file.isDirectory()) {
                        String correctFileNameFormat = "[a-zA-Z]+_[0-9]+.xlsx";
                        List<String> files = getCorrectFileForm(correctFileNameFormat, filePath);
                        List<Student> students = markingMultipleFile(files);
                        writeResult(students);
                    }
                    break inner;
                }
                default:
                    break inner;


            }
            System.out.println("Do you want to continue ?");
            String ans2 = scanner.nextLine();
            if (!ans2.equalsIgnoreCase("yes")) {
                ans = false;
            }
        }
    }

    private static void writeResult(List<Student> students) {
    }


    public static boolean checkFileName(String fileName) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+_[0-9]+.xlsx");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }

    public static List<String> getCorrectFileForm(String correctFileFormRegex, String directory) {
        List<String> correctFileNameList = new ArrayList<>();
        try {
            correctFileNameList = list(directory).stream()
                    .map(x -> new File(x))
                    .filter(x -> x.getName().matches(correctFileFormRegex))
                    .map(x -> x.getAbsolutePath())
                    .collect(Collectors.toList());
            return correctFileNameList;
        } catch (Exception e) {
            System.err.println("Op! Something wrong");
        }
        return correctFileNameList;
    }

    public static List<Student> markingMultipleFile(List<String> links) {
        List<Student> result = new ArrayList<>();
        for (String link : links) {
            result.add(markingSingleFile(link));
        }
        return result;
    }

    public static List<Student> markingMultipleFile(String link) {
        List<Student> result = new ArrayList<>();
        result.add(markingSingleFile(link));
        return result;
    }

    public static void statistic(Map<Integer, Integer> list) {

    }

    public static Map<Double, Integer> countTheSameScore(Map<Integer, Double> list) {
        Map<Double, Integer> test = new HashMap<>();
        // Code
        return test;
    }

    public static Map<Integer, Integer> getCorrectAnswerSheet(String link) {
        Map<Integer, Integer> correctAnswer = new HashMap<>();
        correctAnswer.put(1, 1);
        correctAnswer.put(2, 2);
        correctAnswer.put(3, 2);
        correctAnswer.put(4, 3);
        correctAnswer.put(5, 4);
        correctAnswer.put(6, 1);
        correctAnswer.put(7, 1);
        correctAnswer.put(8, 2);
        correctAnswer.put(9, 1);
        correctAnswer.put(10, 1);
        correctAnswer.put(11, 3);
        correctAnswer.put(12, 4);
        correctAnswer.put(13, 1);
        correctAnswer.put(14, 1);
        correctAnswer.put(15, 2);
        correctAnswer.put(16, 1);
        correctAnswer.put(17, 1);
        correctAnswer.put(18, 3);
        correctAnswer.put(19, 4);
        correctAnswer.put(20, 1);
        correctAnswer.put(21, 1);
        correctAnswer.put(22, 2);
        correctAnswer.put(23, 1);
        correctAnswer.put(24, 1);
        correctAnswer.put(25, 1);
        return correctAnswer;
    }

    public static Student markingSingleFile(String link) {
        Student student = new Student();
        Subject subject = new Subject();
        Integer startFrom = 11;
        Map<Integer, Integer> correctAnswer = getCorrectAnswerSheet("test");

        Map<Integer, Integer> beforeFixIndex = new TreeMap<>();
        Map<Integer, Integer> afterFixIndex = new HashMap<>();

        try {
            FileInputStream file = new FileInputStream(link);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            student = getStudentInfo(sheet);
            subject = getSubjectInfo(sheet);


            for (int i = startFrom; i < startFrom + subject.getNumberOfQuestion(); i++) {
                Row row1 = sheet.getRow(i);
                int count = 0;
                for (int j = 1; j <= 4; j++) {
                    if (row1.getCell(j).getStringCellValue().equals("x")) {
                        count++;
                        beforeFixIndex.put(i, row1.getCell(j).getColumnIndex());
                    }
                }
                if (count != 1) {
                    beforeFixIndex.put(i, null);
                }
            }
            List<Integer> studentOnyAnswer = new ArrayList<>(beforeFixIndex.values());
            for (int i = 0; i < studentOnyAnswer.size(); i++) {
                afterFixIndex.put(i + 1, studentOnyAnswer.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(afterFixIndex);
        List<Object> scoreInfo = marking(afterFixIndex, correctAnswer);
        subject.setScore((Double) scoreInfo.get(0));
        subject.setNumberOfCorrectQuestions((int) scoreInfo.get(1));
        student.setSubjects(Arrays.asList(subject));
        return student;

    }

    public static List<Object> marking(Map<Integer, Integer> studentAnswer, Map<Integer, Integer> correctAnswer) {
        int numberOfCorrectAns = 0;

        if (studentAnswer.size() != correctAnswer.size()) {
            throw new IllegalArgumentException();
        } else {
            List<Integer> studentAnswerList = new ArrayList<>(studentAnswer.values());
            List<Integer> correctAnswerList = new ArrayList<>(correctAnswer.values());

            for (int i = 0; i < studentAnswer.values().size(); i++) {
                if (studentAnswerList.get(i) == correctAnswerList.get(i)) {
                    numberOfCorrectAns++;
                }
            }
        }
        return Arrays.asList((10.0 / Double.valueOf(correctAnswer.size())) * numberOfCorrectAns, numberOfCorrectAns);


    }

    private static Student getStudentInfo(Sheet sheet) {
        return Student.builder()
                .studentName(sheet.getRow(4).getCell(2).getStringCellValue())
                .studentId((long) sheet.getRow(4).getCell(4).getNumericCellValue())
                .build();
    }

    private static Subject getSubjectInfo(Sheet sheet) {
        return Subject.builder()
                .subjectName(sheet.getRow(0).getCell(1).getStringCellValue())
                .numberOfQuestion((int) sheet.getRow(2).getCell(3).getNumericCellValue())
                .build();
    }

    public static List<String> getInfoFromFile(File file) {
        List<String> fileName = Arrays.asList(file.getName().split("\\W|_"));
        return fileName.stream().map(item -> item.trim()).collect(Collectors.toList());

    }

    public static boolean login(Scanner scanner) {
        boolean ans = true;
        while(ans)
        {
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

    public static List<String> list(String file) throws Exception {
        try (Stream<Path> walk = Files.walk(Paths.get(file))) {
            List<String> result = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".xlsx")).collect(Collectors.toList());

            return result;
        } catch (IOException e) {
            throw new Exception("Something wrong!");
        }
    }


    public List<Student> findMax(List<Student> list) {
        List<Double> scores = list.stream().map(x -> x.getSubjects().get(0).getScore()).collect(Collectors.toList());
        Double max = Collections.max(scores);
        return list.stream()
                .filter(x -> x.getSubjects().get(0).getScore() == max).collect(Collectors.toList());
    }

    public List<Student> findMin(List<Student> list) {
        List<Double> scores = list.stream().map(x -> x.getSubjects().get(0).getScore()).collect(Collectors.toList());
        Double min = Collections.min(scores);
        return list.stream()
                .filter(x -> x.getSubjects().get(0).getScore() == min).collect(Collectors.toList());

    }

    public Map<Double, Integer> countTheSameScore(List<Student> lst) {
        List<Double> scores = new ArrayList<>();
        Map<Double, Integer> map = new TreeMap<>();
        for (Student student : lst) {
            if (!scores.contains(student.getSubjects().get(0).getScore())) {
                scores.add(student.getSubjects().get(0).getScore());
            }
        }

        for (int i = 0; i < scores.size(); i++) {
            int count = 0;
            for (int j = 0; j < lst.size(); j++) {
                if (scores.get(i) == lst.get(j).getSubjects().get(0).getScore()) {
                    count++;
                }

            }
            map.put(scores.get(i), count);
        }
        return map;
    }
}
