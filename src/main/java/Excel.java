import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Excel {


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

    public static List<Student> markingMultipleFile(List<String> links) {
        List<Student> result = new ArrayList<>();
        for (String link : links) {
            result.add(markingSingleFile(link));
        }
        return result;
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

    public static void writeRepo(List<Student> students) {

    }

    public static void writeResultWithoutFormat(List<Student> students) {

    }

    // Need to Fix
    public static void writeResultWithFormat(List<Student> students) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        // The content is within merge shell

        // Justify content to center
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // Font
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 24);
        font.setFontName("Courier New");
        font.setBold(true);
        cellStyle.setFont(font);


        cell.setCellValue("This is a test of merging");
        cell.setCellStyle(cellStyle);


        //Common Font
        Font commonFont = wb.createFont();
        commonFont.setBold(true);
        CellStyle commonCellStyle = wb.createCellStyle();
        commonCellStyle.setAlignment(HorizontalAlignment.LEFT);

        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                4, //last row  (0-based)
                0, //first column (0-based)
                11  //last column  (0-based)
        ));

        // Subject Name
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
        Row subjectRow = sheet.createRow(5);
        Cell subjectCell = subjectRow.createCell(0);
        Cell subjectDataCell = subjectRow.createCell(2);
        subjectCell.setCellValue("Môn học");
        subjectDataCell.setCellValue("Java");


        // Number of student
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
        Row numberOfStudentRow = sheet.createRow(6);
        Cell numberOfStudentCell = numberOfStudentRow.createCell(0);
        Cell numberOfStudentDataCell = numberOfStudentRow.createCell(2);
        numberOfStudentCell.setCellValue("Số lượng sinh viên");
        numberOfStudentDataCell.setCellValue(25);

        // Time
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 1));
        Row markedTimeRow = sheet.createRow(7);
        Cell markedTimeCell = markedTimeRow.createCell(0);
        Cell markedTimeDataCell = markedTimeRow.createCell(2);
        markedTimeCell.setCellValue("Thời gian chấm");
        markedTimeDataCell.setCellValue(LocalDate.now().toString());


        sheet.addMergedRegion(new CellRangeAddress(
                8, 8, 0, 1
        ));
        sheet.addMergedRegion(new CellRangeAddress(
                8, 8, 2, 4
        ));
        sheet.addMergedRegion(new CellRangeAddress(
                8, 8, 5, 6
        ));
        sheet.addMergedRegion(new CellRangeAddress(
                8, 8, 7, 8
        ));
        sheet.addMergedRegion(new CellRangeAddress(
                8, 8, 9, 11
        ));


        // ID NAME SUBJECT SCORE
        Row row8 = sheet.createRow(8);

        CellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle1.setFont(commonFont);


        Cell id = row8.createCell(0);
        Cell name = row8.createCell(2);
        Cell subjectName = row8.createCell(5);
        Cell score = row8.createCell(7);
        Cell note = row8.createCell(9);


        id.setCellStyle(cellStyle1);
        id.setCellValue("Student ID");

        name.setCellStyle(cellStyle1);
        name.setCellValue("Student Name");

        subjectName.setCellStyle(cellStyle1);
        subjectName.setCellValue("Subject Name");

        score.setCellStyle(cellStyle1);
        score.setCellValue("Score");


        note.setCellStyle(cellStyle1);
        note.setCellValue("Note");


        // Write data
        int startFrom = 9;
        //This data needs to be written (Object[])
        Map<Long, Object[]> data = new TreeMap<>();
        for (int i = 0; i < students.size(); i++) {
            data.put(Long.valueOf(i), new Object[]{
                    students.get(i).getStudentId(),
                    students.get(i).getStudentName(),
                    students.get(i).getSubjects().get(0).getSubjectName(),
                    students.get(i).getSubjects().get(0).getScore()});
        }

        //Iterate over data and write to sheet
        List<Long> longs = data.keySet().stream().collect(Collectors.toList());
        for (int i = startFrom; i < (startFrom + longs.size()); i++) {

            Row row1 = sheet.createRow(i);
            // Get data from map must be from 0
            int index = i - startFrom;
            // longs.get(index) get index for get value from map
            Object[] objArr = data.get(longs.get(index));
            //Mere cell
            for (int j = 0; j < objArr.length; j++) {
                {
                    if (j == 0 || j == 2 || j == 3) {
                        sheet.addMergedRegion(new CellRangeAddress(i, i, j, j + 1));
                        Cell cell1 = row1.createCell(j);
                        if (objArr[j] instanceof Long) { // Student ID
                            cell1.setCellValue((Long) objArr[j]);
                        } else if (objArr[j] instanceof String) { // Student Name
                            cell1.setCellValue((String) objArr[j]);
                        } else if (objArr[j] instanceof String) // Subject Name
                        {
                            cell1.setCellValue((String) objArr[j]);
                        } else if (objArr[j] instanceof Double) // Score
                        {
                            cell1.setCellValue((Double) objArr[j]);
                        }
                    } else {
                        sheet.addMergedRegion(new CellRangeAddress(i, i, j, j + 2));
                        Cell cell1 = row1.createCell(j);
                        if (objArr[j] instanceof Long) { // Student ID
                            cell1.setCellValue((Long) objArr[j]);
                        } else if (objArr[j] instanceof String) { // Student Name
                            cell1.setCellValue((String) objArr[j]);
                        } else if (objArr[j] instanceof String) // Subject Name
                        {
                            cell1.setCellValue((String) objArr[j]);
                        } else if (objArr[j] instanceof Double) // Score
                        {
                            cell1.setCellValue((Double) objArr[j]);
                        }
                    }

                }


            }

        }

        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("workbook.xlsx")) {
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
