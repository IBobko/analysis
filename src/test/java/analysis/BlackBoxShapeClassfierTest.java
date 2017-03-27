package analysis;


import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.mappers.CsvWithHeaderMapper;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Bobko <limit-speed@yandex.ru>.
 */
@RunWith(JUnitParamsRunner.class)
public class BlackBoxShapeClassfierTest {
    private ShapeClassifierWithFixed shapeClassifier;
    private OracleProblem oracleProblem;

    private Integer getValue(String i) {
        if (i.equals("between")) {
            return (int) (Math.random() * 4095);
        }
        return Integer.parseInt(i);
    }


    @Before
    public void initialization() {
        oracleProblem = new OracleProblem();
    }

    private List<String> generateParams(String Shape, String Param1, String Param2, String Param3, String Param4) {
        List<String> parameters = new ArrayList<>();
        if (!Param1.equals("Empty")) {
            parameters.add(getValue(Param1).toString());
        }
        if (!Param2.equals("Empty") && (Shape.equals("Circle") || Shape.equals("Ellipse") || Shape.equals("Equilateral") || Shape.equals("Isosceles") || Shape.equals("Scalene") || Shape.equals("Rectangle") || Shape.equals("Square"))) {
            parameters.add(getValue(Param2).toString());
        }

        if (!Param3.equals("Empty") && (Shape.equals("Equilateral") || Shape.equals("Isosceles") || Shape.equals("Scalene") || Shape.equals("Rectangle") || Shape.equals("Square"))) {
            parameters.add(getValue(Param3).toString());
        }


        if (!Param4.equals("Empty") && (Shape.equals("Rectangle") || Shape.equals("Square"))) {
            parameters.add(getValue(Param4).toString());

        }
        return parameters;
    }

    private String getResult(String Shape, List<String> parameters, String Even, String Size) {
        boolean isNormal = false;
        Integer perimeter = 0;

        try {
            switch (Shape) {
                case "Line":
                    isNormal = true;
                    break;
                case "Circle":
                    isNormal = true;
                    break;
                case "Ellipse":
                    isNormal = true;
                    break;
                case "Rectangle":
                    isNormal = oracleProblem.isNormalRectangle(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)));
                    break;
                case "Square":
                    isNormal = oracleProblem.isNormalRectangle(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)));
                    break;
                case "Equilateral":
                    isNormal = oracleProblem.isNormalTriangle(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    break;
                case "Isosceles":
                    isNormal = oracleProblem.isNormalTriangle(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    break;
                case "Scalene":
                    isNormal = oracleProblem.isNormalTriangle(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    break;
            }


            String actualShape = "";
            switch (Shape) {
                case "Line":
                    perimeter = Integer.parseInt(parameters.get(0));
                    actualShape = "Line";
                    break;
                case "Circle":
                    perimeter = oracleProblem.calculateCirclePerimeter(Integer.parseInt(parameters.get(0)));
                    ;
                    actualShape = oracleProblem.classify2Parameters(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
                    break;
                case "Ellipse":
                    perimeter = oracleProblem.calculateEllipsePerimeter(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
                    ;
                    actualShape = oracleProblem.classify2Parameters(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
                    break;
                case "Rectangle":
                    perimeter = oracleProblem.calculateRectanglePerimeter(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)));
                    actualShape = oracleProblem.classify4Parameters(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)));
                    break;
                case "Square":
                    perimeter = oracleProblem.calculateRectanglePerimeter(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)));
                    actualShape = oracleProblem.classify4Parameters(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)));
                    break;
                case "Equilateral":
                    perimeter = oracleProblem.calculateTrianglePerimeter(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    actualShape = oracleProblem.classify3Parameters(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    break;
                case "Isosceles":
                    perimeter = oracleProblem.calculateTrianglePerimeter(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    actualShape = oracleProblem.classify3Parameters(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    break;
                case "Scalene":
                    perimeter = oracleProblem.calculateTrianglePerimeter(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    actualShape = oracleProblem.classify3Parameters(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                    break;
            }


            if (!Shape.equals(actualShape)) {
                isNormal = false;
            }

        } catch (IndexOutOfBoundsException e) {
            isNormal = false;
        }


        if (isNormal) {


            String s = "";
            if (perimeter < 10 && perimeter % 2 == 0) {
                s = "less10%2=0";
            }
            if (perimeter < 10 && perimeter % 2 == 1) {
                s = "less10%2=1";
            }

            if (perimeter > 10 && perimeter < 200 && perimeter % 2 == 0) {
                s = "between%2=0";
            }

            if (perimeter > 10 && perimeter < 200 && perimeter % 2 == 1) {
                s = "more200%2=1";
            }

            if (perimeter > 200 && perimeter % 2 == 0) {
                s = "more200%2=0";
            }

            if (perimeter > 200 && perimeter % 2 == 1) {
                s = "more200%2=1";
            }

            //System.out.println(s);

            for (final Map.Entry<String, Map<String, String>> m1 : oracleProblem.allPossibleResults.get(Size).entrySet()) {
                for (final Map.Entry<String, String> m2 : m1.getValue().entrySet()) {
                    if (Even.equals(m1.getKey()) && m2.getKey().equals(s)) {
                        return m2.getValue();
                    }
                }
            }
        }
        return "No";
    }

    //@Test
    @FileParameters(value = "/home/igor/An3/src/test/java/all_singles.csv", mapper = CsvWithHeaderMapper.class)
    public void testAllSingles(String Shape, String Size, String Even, String Param1, String Param2, String Param3, String Param4) {

        if (Even.equals("true")) {
            Even = "Yes";
        } else {
            Even = "No";
        }
        List<String> parameters = generateParams(Shape, Param1, Param2, Param3, Param4);
        String params = StringUtils.join(parameters, ",");
        String testLine = Shape + "," + Size + "," + Even;// + Param1 + "
        String result = getResult(Shape, parameters, Even, Size);
        shapeClassifier = new ShapeClassifierWithFixed();
        //System.out.println(testLine + params + " " + shapeClassifier.evaluateGuess(testLine + params) + " == " + result);
        Assert.assertEquals(shapeClassifier.evaluateGuess(testLine + params),result);
    }

    //@Test
    @FileParameters(value = "/home/igor/An3/src/test/java/all_doubles.csv", mapper = CsvWithHeaderMapper.class)
    public void testAllPairs(String Shape, String Size, String Even, String Param1, String Param2, String Param3, String Param4) {
        if (Even.equals("true")) {
            Even = "Yes";
        } else {
            Even = "No";
        }
        List<String> parameters = generateParams(Shape, Param1, Param2, Param3, Param4);
        String params = StringUtils.join(parameters, ",");
        String testLine = Shape + "," + Size + "," + Even;// + Param1 + "
        String result = getResult(Shape, parameters, Even, Size);
        shapeClassifier = new ShapeClassifierWithFixed();
        Assert.assertEquals(shapeClassifier.evaluateGuess(testLine + params),result);
    }

    @Test
    @FileParameters(value = "/home/igor/An3/src/test/java/all_triples.csv", mapper = CsvWithHeaderMapper.class)
    public void testAllTriples(String Shape, String Size, String Even, String Param1, String Param2, String Param3, String Param4) {
        if (Even.equals("true")) {
            Even = "Yes";
        } else {
            Even = "No";
        }
        List<String> parameters = generateParams(Shape, Param1, Param2, Param3, Param4);
        String params = StringUtils.join(parameters, ",");
        String testLine = Shape + "," + Size + "," + Even;// + Param1 + "
        String result = getResult(Shape, parameters, Even, Size);
        shapeClassifier = new ShapeClassifierWithFixed();
        System.out.println(testLine + "," + params + "," + shapeClassifier.evaluateGuess(testLine + "," + params) + "," + result);
    }


}
