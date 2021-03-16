import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataUtils {

    public static boolean checkFileName(String fileName) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+_[0-9]+.xlsx");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }


    public static List<Student> findMax(List<Student> list) {
        List<Double> scores = list.stream().map(x -> x.getSubjects().get(0).getScore()).collect(Collectors.toList());
        Double max = Collections.max(scores);
        return list.stream()
                .filter(x -> x.getSubjects().get(0).getScore() == max).collect(Collectors.toList());
    }

    public static List<Student> findMin(List<Student> list) {
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
