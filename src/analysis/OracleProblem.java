package analysis;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Bobko <limit-speed@yandex.ru>.
 */
public class OracleProblem {
    /**
     * All possible sizes.
     */
    private String[] sizes = {"Large","Small"};
    /**
     * Even number or not.
     */
    private String[] evenOddEnum = {"Yes","No"};
    /**
     * All possible values for numbers presented semantic.
     */
    private String[] perimeterSizes = {"less10%2=0","less10%2=1","between%2=0","between%2=1","more200%2=1","more200%2=0"};
    private static final Integer MIN_VALUE = 1;
    private static final Integer MAX_VALUE = 250;
    Map<String,Map<String,Map<String,String>>> allPossibleResults = new HashMap<>();
    Map<String,Map<String,Map<String,Map<String,String>>>> allPossibleResultsWithShapes = new HashMap<>();

    /**
     * Function finds and populate allPossibleResults variable for further generation of test cases.
     */
    private void fillingAllPossibleResult() {
        for (final String size: sizes) {
            allPossibleResults.put(size,new HashMap<>());
            for (final String evenOdd: evenOddEnum) {
                allPossibleResults.get(size).put(evenOdd,new HashMap<>());
                for (final String ps: perimeterSizes) {
                    String yesno = "Yes";
                    if (evenOdd.equals("Yes") && ps.contains("%2=1")) {
                        yesno = "No";
                    } else if (evenOdd.equals("No") && ps.contains("%2=0")) {
                        yesno = "No";
                    } else if (size.equals("Large") && (ps.contains("between") || ps.contains("less10"))) {
                        yesno = "No";
                    } else if (size.equals("Small") && (ps.contains("between") || ps.contains("more200"))) {
                        yesno = "No";
                    }
                    allPossibleResults.get(size).get(evenOdd).put(ps,yesno);
                }
            }
        }
    }

    public OracleProblem() {
        fillingAllPossibleResult();
        fillingAllPossibleWithShapes();
    }

    private void fillingAllPossibleWithShapes() {


    }


    void printResultWeMustGet() {
        System.out.println("Size\tEvenOdd\tPerimeter\tResult");
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    System.out.println(m1.getKey() + "\t" + m2.getKey() + "\t\t" + m3.getKey() + "\t" + m3.getValue() );
                }
            }
        }
    }


    @SuppressWarnings("RedundantIfStatement")
    static private boolean validateValue(final String mustBe, final Integer value) {
        if (mustBe.equals("less10%2=0") && value < 10 && value % 2 == 0) return true;
        if (mustBe.equals("less10%2=1") && value < 10 && value % 2 == 1) return true;
        if (mustBe.equals("between%2=0") && value > 10 && value < 200 && value % 2 == 0) return true;
        if (mustBe.equals("between%2=1") && value > 10 && value < 200 && value % 2 == 1) return true;
        if (mustBe.equals("more200%2=0") && value > 200 && value % 2 == 0) return true;
        if (mustBe.equals("more200%2=1") && value > 200 && value % 2 == 1) return true;
        return false;
    }

    /**
     * Generate all possible line test cases.
     *
     * @return All test cases for line.
     */
    private Map<String,String> generateLineTestCases() {
        final Map<String,String> testValues = new HashMap<>();
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    String parameter = "Line," + m1.getKey() + "," + m2.getKey();
                    for (int  i = MIN_VALUE; i < MAX_VALUE; i++) {
                        if (validateValue(m3.getKey(),i)) {
                            parameter += "," + i;
                            testValues.put(parameter,m3.getValue());
                            break;
                        }
                    }
                }
            }
        }
        return testValues;
    }

    /**
     * Generate all possible circle test cases.
     *
     * @return All test cases for circle.
     */
    private Map<String,String> generateCircleTestCases() {
        final Map<String,String> testValues = new HashMap<>();
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    String parameter = "Circle," + m1.getKey() + "," + m2.getKey();
                    for (int i = MIN_VALUE; i < MAX_VALUE; i++) {
                        final Integer value = calculateCirclePerimeter(i);
                        if (validateValue(m3.getKey(),value)) {
                            parameter += "," + i + "," + i;
                            testValues.put(parameter,m3.getValue());
                            break;
                        }
                    }
                }
            }
        }
        return testValues;
    }

    /**
     * Generate all possible ellipse test cases.
     *
     * @return All test cases for ellipse.
     */
    private Map<String,String> generateEllipseTestCases() {
        final Map<String,String> testValues = new HashMap<>();
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    String parameter = "Ellipse," + m1.getKey() + "," + m2.getKey();
                    loop: for (int a = MIN_VALUE+1; a < MAX_VALUE; a++) {
                        for (int b  = MIN_VALUE+1; b < MAX_VALUE; b++) {
                            if (a != b) {
                                final Integer value = calculateEllipsePerimeter(a, b);
                                if (validateValue(m3.getKey(), value)) {
                                    parameter += "," + a + "," + b;
                                    testValues.put(parameter, m3.getValue());
                                    break loop;
                                }
                            }
                        }
                    }
                }
            }
        }
        return testValues;
    }

    /**
     * Generate all possible square test cases.
     *
     * @return All test cases for square.
     */
    private Map<String,String> generateSquareTestCases() {
        final Map<String,String> testValues = new HashMap<>();
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    String parameter = "Square," + m1.getKey() + "," + m2.getKey();
                    for (int i = MIN_VALUE; i < MAX_VALUE; i++) {
                        final Integer value = calculateRectanglePerimeter(i,i,i,i);
                        if (validateValue(m3.getKey(),value)) {

                            parameter += "," + i;
                            testValues.put(parameter,m3.getValue());

                            parameter += "," + i + "," + i + "," + i;

                            testValues.put(parameter,m3.getValue());
                            break;
                        }
                    }
                }
            }
        }
        return testValues;
    }

    /**
     * Generate all possible rectangle test cases.
     *
     * @return All test cases for rectangle.
     */
    private Map<String,String> generateRectangleTestCases() {
        final Map<String,String> testValues = new HashMap<>();
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    String parameter = "Rectangle," + m1.getKey() + "," + m2.getKey();
                    loop: for (int a = MIN_VALUE; a < MAX_VALUE; a++) {
                        for (int b = MIN_VALUE; b < MAX_VALUE; b++ ) {
                            if ( a != b) {
                                final Integer value = calculateRectanglePerimeter(a,a,b,b);
                                if (validateValue(m3.getKey(),value)) {
                                    parameter += "," + a + "," + a + "," + b + "," + b;
                                    testValues.put(parameter,m3.getValue());
                                    break loop;
                                }
                            }
                        }
                    }
                }
            }
        }
        return testValues;
    }

    /**
     * Generate all possible isosceles test cases.
     *
     * @return All test cases for isosceles.
     */
    private Map<String,String> generateIsoscelesTestCases() {
        final Map<String,String> testValues = new HashMap<>();
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    String parameter = "Isosceles," + m1.getKey() + "," + m2.getKey();
                    loop: for (int a = MIN_VALUE; a < MAX_VALUE; a++) {
                        for (int b = MIN_VALUE; b < MAX_VALUE; b++ ) {
                            for (int c = MIN_VALUE; c < MAX_VALUE; c++ ) {
                                if ( (a < (b+c)) && (b < (a + c)) && (c < (a+b))) {
                                    if ((a != b || b != c) && (a == b || a == c || b == c)) {
                                        final Integer value = calculateTrianglePerimeter(a, b, c);
                                        if (validateValue(m3.getKey(), value)) {
                                            parameter += "," + a + "," + b + "," + c;
                                            testValues.put(parameter, m3.getValue());
                                            break loop;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return testValues;
    }


    private Map<String,String> generateScaleneTestCases() {
        final Map<String,String> testValues = new HashMap<>();
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    String parameter = "Scalene," + m1.getKey() + "," + m2.getKey();
                    loop: for (int a = MIN_VALUE; a < MAX_VALUE; a++) {
                        for (int b = MIN_VALUE; b < MAX_VALUE; b++ ) {
                            for (int c = MIN_VALUE; c < MAX_VALUE; c++ ) {
                                if ( (a < (b+c)) && (b < (a + c)) && (c < (a+b))) {
                                    if (a!=b && a!=c && b!=c) {
                                        final Integer value = calculateTrianglePerimeter(a,b,c);
                                        if (validateValue(m3.getKey(),value)) {
                                            parameter += "," + a + "," + b + "," + c;
                                            testValues.put(parameter,m3.getValue());
                                            break loop;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return testValues;
    }


    private Map<String,String> generateEquilateralTestCases() {
        final Map<String,String> testValues = new HashMap<>();
        for (final Map.Entry<String,Map<String,Map<String,String>>> m1: allPossibleResults.entrySet()) {
            for (final Map.Entry<String,Map<String,String>> m2: m1.getValue().entrySet()) {
                for (final Map.Entry<String,String> m3: m2.getValue().entrySet()) {
                    String parameter = "Equilateral," + m1.getKey() + "," + m2.getKey();
                    loop: for (int a = MIN_VALUE; a < MAX_VALUE; a++) {
                        for (int b = MIN_VALUE; b < MAX_VALUE; b++ ) {
                            for (int c = MIN_VALUE; c < MAX_VALUE; c++ ) {
                                if ( (a < (b+c)) && (b < (a + c)) && (c < (a+b))) {
                                    if (a == b && b == c) {
                                        final Integer value = calculateTrianglePerimeter(a,b,c);
                                        if (validateValue(m3.getKey(),value)) {
                                            parameter += "," + a + "," + b + "," + c;
                                            testValues.put(parameter,m3.getValue());
                                            break loop;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return testValues;
    }

    @Test
    public void LineTestCase() {
        final Map<String,String> lineTestCases = generateLineTestCases();
        for (final Map.Entry<String,String> testCase: lineTestCases.entrySet()) {
            System.out.println(testCase.getKey() + "," + testCase.getValue());
        }
    }

    @Test
    public void CircleTestCase() {
        final Map<String,String> testCases = generateCircleTestCases();
        for (final Map.Entry<String,String> testCase: testCases.entrySet()) {
            System.out.println(testCase.getKey() + "," + testCase.getValue());
        }
    }


    @Test
    public void EllipseTestCase() {
        final Map<String,String> testCases = generateEllipseTestCases();
        for (final Map.Entry<String,String> testCase: testCases.entrySet()) {
            System.out.println(testCase.getKey() + "," + testCase.getValue());
        }
    }


    @Test
    public void SquareTestCase() {
        final Map<String,String> testCases = generateSquareTestCases();
        for (final Map.Entry<String,String> testCase: testCases.entrySet()) {
            System.out.println(testCase.getKey() + "," + testCase.getValue());
        }
    }

    @Test
    public void RectangleTestCase() {
        final Map<String,String> testCases = generateRectangleTestCases();
        for (final Map.Entry<String,String> testCase: testCases.entrySet()) {
            System.out.println(testCase.getKey() + "," + testCase.getValue());
        }
    }



    @Test
    public void IsoscelesTestCase() {
        final Map<String,String> testCases = generateIsoscelesTestCases();
        for (final Map.Entry<String,String> testCase: testCases.entrySet()) {
            System.out.println(testCase.getKey() + "," + testCase.getValue());
        }
    }

    @Test
    public void EquilateralTestCase() {
        final Map<String,String> testCases = generateEquilateralTestCases();
        for (final Map.Entry<String,String> testCase: testCases.entrySet()) {
            System.out.println(testCase.getKey() + "," + testCase.getValue());
        }
    }

    @Test
    public void ScaleneTestCase() {
        final Map<String,String> testCases = generateScaleneTestCases();
        for (final Map.Entry<String,String> testCase: testCases.entrySet()) {
            System.out.println(testCase.getKey() + "," + testCase.getValue());
        }
    }




    // P = 2 * PI *r
    public int calculateCirclePerimeter(int r) {
        return (int) (2 * Math.PI * r);
    }

    // P = 4 * s
    public int calculateSquarePerimeter(int side) {
        return 4 * side;
    }

    // P = 2l + 2w)
    public int calculateRectanglePerimeter(int side1, int side2, int side3, int side4) {
        if (side1 == side2) {
            return (2 * side1) + (2 * side3);
        }

        else if (side2 == side3) {
            return (2 * side1) + (2 * side2);
        }

        return 0;
    }

    // P = a + b + c
    public int calculateTrianglePerimeter(int side1, int side2 , int side3) {
        return side1 + side2 + side3;
    }

    // This is an approximation
    // PI(3(a+b) - sqrt((3a+b)(a+3b))
    public int calculateEllipsePerimeter(int a, int b) {
        return (int) ((int) Math.PI * (3 * ((double) a + (double) b) - Math.sqrt((3* (double) a + (double) b)*((double) a +3* (double) b))));
    }

    boolean isNormalTriangle(Integer a, Integer b, Integer c) {
        if ( (a < (b+c)) && (b < (a + c)) && (c < (a+b))) {
            if (a == b && b == c) {
                return true;
            } else if (a!=b && a!=c && b!=c) {
                return  true;
            } else {
                return true;
            }
        }
        return false;
    }

    boolean isNormalRectangle(Integer a,Integer b, Integer c, Integer d) {
        if (a == b && c == d) {
            if (a != c) {
                return true;
            }
            else
                return true;
        }
        else if (b == d && c == a) {
            return true;
        }
        else if (b == c && a == d) {
            return  true;
        }
        return  false;
    }

}
