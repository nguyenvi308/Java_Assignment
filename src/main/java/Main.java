import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {


        // Statistic: Loan Va Thuy
          /* // MSV      Score
        Map<Integer, Double> input = new HashMap<>();

        input.put(11185570,6.0);
        input.put(11185572,7.0);
        input.put(11185575,7.0);

        // {6.0=1,7.0=2}


        // Score    Count
        Map<Double, Integer> countTheSameScore = countTheSameScore(input);
        System.out.println(countTheSameScore);

*/

        // Hoang
        /*User login in*/
        // Hung
        /*Write Repo*/
        // Test

        List<String> list = Arrays.asList("C:\\Users\\NguyenVi\\Desktop\\test1.xlsx","C:\\Users\\NguyenVi\\Desktop\\test2.xlsx");


        System.out.println("Test 1: ");
        System.out.println(markingSingleFile("C:\\Users\\NguyenVi\\Desktop\\test1.xlsx"));
        System.out.println("Test 2: ");
        System.out.println(markingSingleFile("C:\\Users\\NguyenVi\\Desktop\\test2.xlsx"));

        System.out.println("Multiple File: ");
        System.out.println(markingMultipleFile(list));



    }


    public boolean login(String userName, String pass) {
        Map<String, String> accounts = new HashMap<>();
        boolean isAuthenticated = false;
        // Logic
        return isAuthenticated;
    }

    public  static  List<Map<Integer,Double>> markingMultipleFile(List<String> links) {
        List<Map<Integer,Double>> result = new ArrayList<>();
        for (String link : links)
        {
            result.add(markingSingleFile(link));
        }
        return result;
    }

    public static void statistic(Map<Integer, Integer> list) {


        //  Double highestScore;
        //  Double lowestScore;

        //  Score at 5 have 5 student
        //  Score at 6 have 3 student


    }

    public double findMax(Map<Integer, Double> list) {
        return 10.6;

    }

    public double findMin(Map<Integer, Double> list) {
        return 10.6;

    }

    public static Map<Double, Integer> countTheSameScore(Map<Integer, Double> list) {
        Map<Double, Integer> test = new HashMap<>();
        // Code
        return test;
    }


    // Get from database
    // Get from CSV
    // Get from excel
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
        return correctAnswer;
    }

    // Single File without Random Ma De
    public static Map<Integer, Double> markingSingleFile(String link) {


        Map<Integer, Integer> correctAnswer = getCorrectAnswerSheet("test");
        Integer studentId = null;
        Double score;
        Map<Integer, Double> finalRecord = new HashMap<>();


        Map<Integer, Integer> map1 = new TreeMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        try {
            FileInputStream file = new FileInputStream(link);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            studentId = (int) (sheet.getRow(0).getCell(1).getNumericCellValue());

            for (int i = 7; i <= sheet.getLastRowNum(); i++) {
                Row row1 = sheet.getRow(i);
                int count = 0;
                for (int j = row1.getFirstCellNum() + 1; j < row1.getLastCellNum(); j++) {
                    if (row1.getCell(j).getStringCellValue().equals("x")) {
                        count++;
                        map1.put(i, row1.getCell(j).getColumnIndex());
                    }
                }
                if (count != 1) {
                    map1.put(i, null);
                }
            }
            List<Integer> studentOnyAnswer = new ArrayList<>(map1.values());
            for (int i = 0; i < studentOnyAnswer.size(); i++) {
                map2.put(i + 1, studentOnyAnswer.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        score = marking(map2, correctAnswer);
        finalRecord.put(studentId, score);
        return finalRecord;

    }

    public static double marking(Map<Integer, Integer> studentAnswer, Map<Integer, Integer> correctAnswer) {
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
        return (10 / correctAnswer.size()) * numberOfCorrectAns;


    }


}
