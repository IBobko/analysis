package analysis;

import org.junit.Assert;
import org.junit.Before;

/**
 * @author Igor Bobko <limit-speed@yandex.ru>.
 */

public class WhiteBoxShapeClassifierTest {
    /**
     * Tested class.
     */
    private ShapeClassifierOriginal shapeClassifier;



    @Before
    public void before() {
        //shapeClassifier = new ShapeClassifierOriginal();

    }

    //@Test
    //@Ignore
    //PATH 1: 23,26,29,33,35,36-39,63-65,68,69,75,76-77,85,86-87,95,96-97
    public void testCaseForPath1() {
        shapeClassifier = new ShapeClassifierOriginal();
        String s = "Line,Large,Yes,202";
        Assert.assertEquals(shapeClassifier.evaluateGuess(s),"Yes");
    }


    //@Test
    //@Ignore
    //PATH 9: 23,26,29,33,35,36-39,63-65,68,69,75,76-77,85,86-87,95,96-97
    public void testCaseForPath9() {
        shapeClassifier = new ShapeClassifierOriginal();
        String s = "Isosceles,Large,Yes,2,100,100";
        Assert.assertEquals(shapeClassifier.evaluateGuess(s),"Yes");
    }

    //@Test
    //@Ignore
    //PATH 12: 23,26,29,33,54,55,56-57,63-65,68,69,75,78,79-80,85,86-87,95,96-97
    public void testCaseForPath12() {
        shapeClassifier = new ShapeClassifierOriginal();
        String s = "Rectangle,Small,Yes,1,1,1,1";
        Assert.assertEquals(shapeClassifier.evaluateGuess(s),"Yes");
    }


    //@Test
    //@Ignore
    //PATH14: 23,26,29,33,35,36-39,63-65,68,69,75,76-77,85,88,89-90,95,96-97
    public void testCaseForPath14() {
        shapeClassifier = new ShapeClassifierOriginal();
        String s = "Line,Large,No,201";
        Assert.assertEquals(shapeClassifier.evaluateGuess(s),"Yes");
    }

}
