package analysis;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.mappers.CsvWithHeaderMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.Random;

/**
 * @author Igor Bobko <limit-speed@yandex.ru>.
 */
@RunWith(JUnitParamsRunner.class)
public class BlackBoxShapeClassfierTest {
    private ShapeClassifier shapeClassifier;
    private OracleProblem oracleProblem;

    Integer getValue(String i) {
        if (i.equals("between")) {
            return (int) (Math.random() * 4095);
        }
        return Integer.parseInt(i);
    }


    @Before
    public void initialization() {
        shapeClassifier = new ShapeClassifier();

        oracleProblem = new OracleProblem();
    }

    @Test
    @Ignore
    @FileParameters(value = "src/analysis/all_singles.csv",mapper = CsvWithHeaderMapper.class)
    public void testAllSingles(String Shape, String Size, String Even, String Param1, String Param2, String Param3, String Param4) {

        String params = "";

        Integer param1 = 0;
        Integer param2 = 0;
        Integer param3 = 0;
        Integer param4 = 0;

        String testLine = Shape + "," + Size + "," + Even;// + Param1 + "

        if (!Param4.equals("Empty")) {
            params = "," + Param4 + params;
            param4 = Integer.parseInt(Param4);

        }

        if (!Param3.equals("Empty")) {
            params = "," + Param3 + params;
            param3 = Integer.parseInt(Param3);
        }

        if (!Param2.equals("Empty")) {
            params = "," + Param2 + params;
            param2 = Integer.parseInt(Param2);
        }

        if (!Param1.equals("Empty")) {
            params = "," + Param1 + params;
            param1 = Integer.parseInt(Param1);
        }


        System.out.println("Size\tEvenOdd\tPerimeter\tResult");


        Integer perimeter = 0;
        boolean isNormal = false;

        String result = "Yes";

        switch(Shape) {
            case "Line": isNormal = true;
            case "Circle": isNormal = true;
            case "Ellipse": isNormal = true;
            case "Rectangle": isNormal = oracleProblem.isNormalRectangle(param1,param2,param3,param4);
            case "Square": isNormal = oracleProblem.isNormalRectangle(param1,param1,param1,param1);
            case "Equilateral": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
            case "Isosceles": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
            case "Scalene": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
        }


        switch(Shape) {
            case "Line": perimeter = param1;
            case "Circle": perimeter = oracleProblem.calculateCirclePerimeter(param1);
            case "Ellipse": perimeter = oracleProblem.calculateEllipsePerimeter(param1,param2);
            case "Rectangle": perimeter = oracleProblem.calculateRectanglePerimeter(param1,param2,param3,param4);
            case "Square": perimeter = oracleProblem.calculateSquarePerimeter(param1);
            case "Equilateral": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
            case "Isosceles": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
            case "Scalene": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
        }

        if (!isNormal) {
            result = "No";
        } else {


            String s = "";
            if (perimeter < 10 && perimeter % 2 == 0) {
                s = "less10%2=0";
            }
            if (perimeter < 10 && perimeter % 2 == 1) {
                s = "less10%2=0";
            }

            if (perimeter > 10 && perimeter < 200) {
                s = "between%2=0";
            }

            if (perimeter > 10 && perimeter < 200) {
                s = "more200%2=1";
            }

            if (perimeter > 200 && perimeter % 2 == 0) {
                s = "more200%2=0";
            }

            if (perimeter > 200 && perimeter % 2 == 1) {
                s = "more200%2=1";
            }

            //System.out.println(s);

            for (final Map.Entry<String,Map<String,String>> m1: oracleProblem.allPossibleResults.get(Size).entrySet()) {
                for (final Map.Entry<String,String> m2: m1.getValue().entrySet()) {
                    if (Even.equals(m1.getKey()) && m2.getKey().equals(s)) {
                        result = m2.getValue();
                    }
                }
            }
        }


        System.out.println(testLine + params + " " + shapeClassifier.evaluateGuess(testLine + params) + " == " + result);


    }

    @Test
    @Ignore
    @FileParameters(value = "src/analysis/all_doubles.csv",mapper = CsvWithHeaderMapper.class)
    public void testAllPairs(String Shape, String Size, String Even, String Param1, String Param2, String Param3, String Param4) {

        if (Even.equals("true")) {
            Even = "Yes";
        } else {
            Even = "No";
        }


        String params = "";

        Integer param1 = 0;
        Integer param2 = 0;
        Integer param3 = 0;
        Integer param4 = 0;

        String testLine = Shape + "," + Size + "," + Even;// + Param1 + "

        if (!Param4.equals("Empty")) {
            param4 = getValue(Param4);
            params = "," + param4 + params;
        }

        if (!Param3.equals("Empty")) {
            param3 = getValue(Param3);
            params = "," + param3 + params;
        }

        if (!Param2.equals("Empty")) {
            param2 = getValue(Param2);
            params = "," + param2 + params;
        }

        if (!Param1.equals("Empty")) {
            param1 = getValue(Param1);
            params = "," + param1 + params;
        }





        Integer perimeter = 0;
        boolean isNormal = false;

        String result = "Yes";

        switch(Shape) {
            case "Line": isNormal = true;
            case "Circle": isNormal = true;
            case "Ellipse": isNormal = true;
            case "Rectangle": isNormal = oracleProblem.isNormalRectangle(param1,param2,param3,param4);
            case "Square": isNormal = oracleProblem.isNormalRectangle(param1,param1,param1,param1);
            case "Equilateral": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
            case "Isosceles": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
            case "Scalene": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
        }


        switch(Shape) {
            case "Line": perimeter = param1;
            case "Circle": perimeter = oracleProblem.calculateCirclePerimeter(param1);
            case "Ellipse": perimeter = oracleProblem.calculateEllipsePerimeter(param1,param2);
            case "Rectangle": perimeter = oracleProblem.calculateRectanglePerimeter(param1,param2,param3,param4);
            case "Square": perimeter = oracleProblem.calculateSquarePerimeter(param1);
            case "Equilateral": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
            case "Isosceles": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
            case "Scalene": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
        }

        if (!isNormal) {
            result = "No";
        } else {


            String s = "";
            if (perimeter < 10 && perimeter % 2 == 0) {
                s = "less10%2=0";
            }
            if (perimeter < 10 && perimeter % 2 == 1) {
                s = "less10%2=0";
            }

            if (perimeter > 10 && perimeter < 200) {
                s = "between%2=0";
            }

            if (perimeter > 10 && perimeter < 200) {
                s = "more200%2=1";
            }

            if (perimeter > 200 && perimeter % 2 == 0) {
                s = "more200%2=0";
            }

            if (perimeter > 200 && perimeter % 2 == 1) {
                s = "more200%2=1";
            }

            //System.out.println(s);

            for (final Map.Entry<String,Map<String,String>> m1: oracleProblem.allPossibleResults.get(Size).entrySet()) {
                for (final Map.Entry<String,String> m2: m1.getValue().entrySet()) {
                    if (Even.equals(m1.getKey()) && m2.getKey().equals(s)) {
                        result = m2.getValue();
                    }
                }
            }
        }


        System.out.println(testLine + params + " " + shapeClassifier.evaluateGuess(testLine + params) + " == " + result);


    }

    @Test
    @FileParameters(value = "src/analysis/all_triples.csv",mapper = CsvWithHeaderMapper.class)
    public void testAllTriples(String Shape, String Size, String Even, String Param1, String Param2, String Param3, String Param4) {

        if (Even.equals("true")) {
            Even = "Yes";
        } else {
            Even = "No";
        }


        String params = "";

        Integer param1 = 0;
        Integer param2 = 0;
        Integer param3 = 0;
        Integer param4 = 0;

        String testLine = Shape + "," + Size + "," + Even;// + Param1 + "

        if (!Param4.equals("Empty")) {
            param4 = getValue(Param4);
            params = "," + param4 + params;
        }

        if (!Param3.equals("Empty")) {
            param3 = getValue(Param3);
            params = "," + param3 + params;
        }

        if (!Param2.equals("Empty")) {
            param2 = getValue(Param2);
            params = "," + param2 + params;
        }

        if (!Param1.equals("Empty")) {
            param1 = getValue(Param1);
            params = "," + param1 + params;
        }





        Integer perimeter = 0;
        boolean isNormal = false;

        String result = "Yes";

        switch(Shape) {
            case "Line": isNormal = true;
            case "Circle": isNormal = true;
            case "Ellipse": isNormal = true;
            case "Rectangle": isNormal = oracleProblem.isNormalRectangle(param1,param2,param3,param4);
            case "Square": isNormal = oracleProblem.isNormalRectangle(param1,param1,param1,param1);
            case "Equilateral": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
            case "Isosceles": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
            case "Scalene": isNormal = oracleProblem.isNormalTriangle(param1,param2,param3);
        }


        switch(Shape) {
            case "Line": perimeter = param1;
            case "Circle": perimeter = oracleProblem.calculateCirclePerimeter(param1);
            case "Ellipse": perimeter = oracleProblem.calculateEllipsePerimeter(param1,param2);
            case "Rectangle": perimeter = oracleProblem.calculateRectanglePerimeter(param1,param2,param3,param4);
            case "Square": perimeter = oracleProblem.calculateSquarePerimeter(param1);
            case "Equilateral": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
            case "Isosceles": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
            case "Scalene": perimeter = oracleProblem.calculateTrianglePerimeter(param1,param2,param3);
        }

        if (!isNormal) {
            result = "No";
        } else {


            String s = "";
            if (perimeter < 10 && perimeter % 2 == 0) {
                s = "less10%2=0";
            }
            if (perimeter < 10 && perimeter % 2 == 1) {
                s = "less10%2=0";
            }

            if (perimeter > 10 && perimeter < 200) {
                s = "between%2=0";
            }

            if (perimeter > 10 && perimeter < 200) {
                s = "more200%2=1";
            }

            if (perimeter > 200 && perimeter % 2 == 0) {
                s = "more200%2=0";
            }

            if (perimeter > 200 && perimeter % 2 == 1) {
                s = "more200%2=1";
            }

            //System.out.println(s);

            for (final Map.Entry<String,Map<String,String>> m1: oracleProblem.allPossibleResults.get(Size).entrySet()) {
                for (final Map.Entry<String,String> m2: m1.getValue().entrySet()) {
                    if (Even.equals(m1.getKey()) && m2.getKey().equals(s)) {
                        result = m2.getValue();
                    }
                }
            }
        }


        System.out.println(testLine + params + " " + shapeClassifier.evaluateGuess(testLine + params) + " == " + result);


    }


}
