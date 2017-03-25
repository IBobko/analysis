package analysis;

import org.junit.Before;

/**
 * @author Igor Bobko <limit-speed@yandex.ru>.
 */

public class ShapeClassifierTest {
    /**
     * Tested class.
     */
    private ShapeClassifierWithFixed shapeClassifier;



    @Before
    public void before() {
        shapeClassifier = new ShapeClassifierWithFixed();

    }

    //@Test
    public void whiteBoxTesting() {
        String s1 = "Line,Small,Yes,6";
        shapeClassifier.evaluateGuess(s1);

        String s2 = "Line,Large,No,201";

        shapeClassifier.evaluateGuess(s2);

        String s3 = "Square,Large,Yes,202";

        shapeClassifier.evaluateGuess(s3);

        String s4 = "Ellipse,Small,Yes,0,1";

        shapeClassifier.evaluateGuess(s4);



        String s6 = "Ellipse,Small,Yes,2,1";

        shapeClassifier.evaluateGuess(s6);

        String s5 = "Circle,Small,Yes,2,2";

        shapeClassifier.evaluateGuess(s5);


        String s7 = "Ellipse,Small,Yes,1,0";

        shapeClassifier.evaluateGuess(s7);

        String s8 = "Isosceles,Small,No,2,3,3";

        shapeClassifier.evaluateGuess(s8);

        String s9 = "Isosceles,Small,Yes,2,3,3";

        shapeClassifier.evaluateGuess(s9);


        String s10 = "Isosceles,Small,Yes,2,3,3";

        shapeClassifier.evaluateGuess(s10);

        String s11 = "Equilateral,Small,Yes,2,2,2";

        shapeClassifier.evaluateGuess(s11);


        String s12 = "Scalene,Large,Yes,3,99,100";

        shapeClassifier.evaluateGuess(s12);

        String s13 = "Scalene,Large,Yes,10,2,1";

        shapeClassifier.evaluateGuess(s13);


        String s14 = "Rectangle,Small,Yes,1,1,2,2";

        shapeClassifier.evaluateGuess(s14);


        String s15 = "Rectangle,Small,Yes,1,1,1,1";

        shapeClassifier.evaluateGuess(s15);

        String s16 = "Rectangle,Small,Yes,1,2,1,2";

        shapeClassifier.evaluateGuess(s16);

        String s17 = "Rectangle,Small,Yes,1,2,2,1";

        shapeClassifier.evaluateGuess(s17);

        String s18 = "Rectangle,Small,Yes,1,2,3,4";

        shapeClassifier.evaluateGuess(s18);

    }

    // P = 2 * PI *r
    private int calculateCirclePerimeter(int r) {
        return (int) (2 * Math.PI * r);
    }

    // P = 4 * s
    private int calculateSquarePerimeter(int side) {
        return 4 * side;
    }

    // P = 2l + 2w)
    private int calculateRectanglePerimeter(int side1, int side2, int side3, int side4) {
        if (side1 == side2) {
            return (2 * side1) + (2 * side3);
        }

        else if (side2 == side3) {
            return (2 * side1) + (2 * side2);
        }

        return 0;
    }

    // P = a + b + c
    private int calculateTrianglePerimeter(int side1, int side2 , int side3) {
        return side1 + side2 + side3;
    }

    // This is an approximation
    // PI(3(a+b) - sqrt((3a+b)(a+3b))
    private int calculateEllipsePerimeter(int a, int b) {
        double da = a, db = b;
        return (int) ((int) Math.PI * (3 * (da+db) - Math.sqrt((3*da+db)*(da+3*db))));
    }


}
