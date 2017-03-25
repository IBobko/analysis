package analysis;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.mappers.CsvWithHeaderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Bobko <limit-speed@yandex.ru>.
 */
@RunWith(JUnitParamsRunner.class)
public class WhiteBoxShapeClassfierTest {

    @Test
    @FileParameters(value = "/src/analysis/all_singles.csv",mapper = CsvWithHeaderMapper.class)
    public void testAllSingles (String Shape, String Size, String Even, String Param1, String Param2, String Param3, String Param4 ) {

        System.out.println();



    }

}
