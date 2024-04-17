import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calc {
    public static double euclideanDistance(Double[] arr1, Double[] arr2) {
        double sum = 0;
        for (int i = 0; i < arr1.length; i++) {
            double diff = arr1[i] - arr2[i];
            sum += diff * diff;
        }
        return sum;
    }
    public static String findMostRepeatedString(Map<Double, String> map, int k) {

            HashMap<String, Integer> frequencyMap = new HashMap<>();

            int count = 0;
            for (String label : map.values()) {
                if (count >= k) {
                    break;
                }
                if (frequencyMap.containsKey(label)) { //Checks if there is already in map given label
                    frequencyMap.put(label, frequencyMap.get(label) + 1); //Increment
                } else {
                    frequencyMap.put(label, 1); //Add if not
                }
                count++;
            }

            String mostRepeatedString = null;
            int highestFrequency = 0;

            for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
                if (entry.getValue() > highestFrequency) {
                    highestFrequency = entry.getValue();
                    mostRepeatedString = entry.getKey();
                }
            }

            return mostRepeatedString;
        }

    }

