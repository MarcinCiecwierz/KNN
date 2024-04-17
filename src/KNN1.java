import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KNN1 {

    public static void main(String[] args) {

        int k;
        List<Double[]> listTest = new ArrayList<>();
        List<Double[]> listTrain = new ArrayList<>();
        List<String> testLabels = new ArrayList<>();
        List<String> trainLabels = new ArrayList<>();
        Double[] trainValues;
        Double[] testValues;
        String speciesTest;
        String speciesTrain;
        int countLines = 0;
        int count = 0;

        System.out.println("Provide k number (0 to exit) ");

        Scanner scanner = new Scanner(System.in);
        k = scanner.nextInt();

        String option;

        System.out.println("Do you want to provide your vector or work on test.txt provide/test");
        option = scanner.next();

        try {
            BufferedReader brTest = new BufferedReader(new FileReader("data/test.txt"));
            BufferedReader brTrain = new BufferedReader(new FileReader("data/train.txt"));


            String line;

            while ((line = brTest.readLine()) != null) {
                countLines++;
                String[] splitLine = line.split(",");
                testValues = new Double[splitLine.length - 1];           //Read from test.txt
                speciesTest = splitLine[splitLine.length - 1];

                for (int i = 0; i < testValues.length; i++) {
                    testValues[i] = Double.parseDouble(splitLine[i]);
                }
                listTest.add(testValues);
                testLabels.add(speciesTest);
            }

            while ((line = brTrain.readLine()) != null) {
                String[] splitLine = line.split(",");
                trainValues = new Double[splitLine.length - 1];      //Read from train.txt
                speciesTrain = splitLine[splitLine.length - 1];

                for (int i = 0; i < trainValues.length; i++) {
                    trainValues[i] = Double.parseDouble(splitLine[i]);
                }

                listTrain.add(trainValues);
                trainLabels.add(speciesTrain);
            }

            if (option.equals("test")){

                for (Double[] testSample : listTest) {
                    Map<Double, String> resultMap = new HashMap<>();
                    for (int i = 0; i < listTrain.size(); i++) {
                        Double[] trainSample = listTrain.get(i);
                        double distance = Calc.euclideanDistance(testSample, trainSample);      //Calculate distance and map labels to
                        String label = trainLabels.get(i);                                      //Its result
                        resultMap.put(distance, label);
                    }
                    Map<Double, String> sortedMap = new TreeMap<>(resultMap);

                    String predictedLabel = Calc.findMostRepeatedString(sortedMap, k);

                    String testLabel = testLabels.get(listTest.indexOf(testSample)); //Get label of train txt

                    if(predictedLabel.equals(testLabel)){
                        count++;
                    }

                    System.out.println("Test sample: " + Arrays.toString(testSample) + " " + testLabel + ", Predicted: " + predictedLabel);
                }
                double percentage = (count*100/countLines);

                System.out.println("Accuracy: " + percentage + "%");

        }else if (option.equals("provide")){
                System.out.println("Provide your vector e.g: 'double,double,double,double'");
                String read = scanner.next();
                String[] split = read.split(",");
                Double[] values = new Double[split.length];

                for(int i = 0; i < values.length; i++){
                    values[i] = Double.parseDouble(split[i]);
                }
                Map<Double, String> resultMap = new HashMap<>();
                for(int i = 0; i < listTrain.size(); i++){
                    Double[] trainSample = listTrain.get(i);
                    double distance = Calc.euclideanDistance(trainSample, values);
                    resultMap.put(distance, trainLabels.get(i));
                }

                Map<Double, String> sortedMap = new TreeMap<>(resultMap); // sort by distance
                String predictedLabel = Calc.findMostRepeatedString(sortedMap, k);

                System.out.println("Test sample: " + Arrays.toString(values) + ", Predicted: " + predictedLabel);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}