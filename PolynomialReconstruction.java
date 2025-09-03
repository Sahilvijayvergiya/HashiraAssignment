import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
public class PolynomialReconstruction {
   private static int convertToDecimal(String value, int base) {
       return Integer.parseInt(value, base);
   }
   private static double lagrangeInterpolation(List<int[]> points) {
       double result = 0.0;
       for (int i = 0; i < points.size(); i++) {
           int xi = points.get(i)[0];
           int yi = points.get(i)[1];
           double term = yi;
           for (int j = 0; j < points.size(); j++) {
               if (j != i) {
                   int xj = points.get(j)[0];
                   term *= (0 - xj) * 1.0 / (xi - xj); // Evaluate at x=0
               }
           }
           result += term;
       }
       return result;
   }
   public static void main(String[] args) throws Exception {
       String jsonInput = "{\n" +
               "    \"keys\": {\"n\": 4, \"k\": 3},\n" +
               "    \"1\": {\"base\": \"10\", \"value\": \"4\"},\n" +
               "    \"2\": {\"base\": \"2\", \"value\": \"111\"},\n" +
               "    \"3\": {\"base\": \"10\", \"value\": \"12\"},\n" +
               "    \"6\": {\"base\": \"4\", \"value\": \"213\"}\n" +
               "}";
       ObjectMapper mapper = new ObjectMapper();
       JsonNode root = mapper.readTree(jsonInput);
       int n = root.get("keys").get("n").asInt();
       int k = root.get("keys").get("k").asInt();
       List<int[]> points = new ArrayList<>();
       // Extract all points
       Iterator<String> fieldNames = root.fieldNames();
       while (fieldNames.hasNext()) {
           String field = fieldNames.next();
           if (field.equals("keys")) continue;
           int x = Integer.parseInt(field);
           int base = Integer.parseInt(root.get(field).get("base").asText());
           String valueStr = root.get(field).get("value").asText();
           int y = convertToDecimal(valueStr, base);
           points.add(new int[]{x, y});
       }
       // Pick first k points (any k points would work)
       List<int[]> chosenPoints = points.subList(0, k);
       double secret = lagrangeInterpolation(chosenPoints);
    //    System.out.println("Chosen points: ");
    //    for (int[] p : chosenPoints) {
    //        System.out.println("(" + p[0] + ", " + p[1] + ")");
    //    }
       System.out.println("Constant C value is: " + secret);
   }
}
