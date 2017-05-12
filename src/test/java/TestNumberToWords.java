import static org.junit.Assert.assertTrue;

import com.me4502.numberstowords.NumberToWords;
import org.junit.Test;

public class TestNumberToWords {

    @Test
    public void testSingleDigit() {
        NumberToWords converter = new NumberToWords("1");
        assertTrue(converter.convert().equals("ONE DOLLAR"));
        converter = new NumberToWords("9");
        assertTrue(converter.convert().equals("NINE DOLLARS"));
    }

    @Test
    public void testDoubleDigit() {
        NumberToWords converter = new NumberToWords("01");
        assertTrue(converter.convert().equals("ONE DOLLAR"));
        converter = new NumberToWords("99");
        assertTrue(converter.convert().equals("NINETY-NINE DOLLARS"));
        converter = new NumberToWords("18");
        assertTrue(converter.convert().equals("EIGHTEEN DOLLARS"));
        converter = new NumberToWords("47");
        assertTrue(converter.convert().equals("FORTY-SEVEN DOLLARS"));
        converter = new NumberToWords("30");
        assertTrue(converter.convert().equals("THIRTY DOLLARS"));
    }

    @Test
    public void testHundred() {
        NumberToWords converter = new NumberToWords("001");
        assertTrue(converter.convert().equals("ONE DOLLAR"));
        converter = new NumberToWords("011");
        assertTrue(converter.convert().equals("ELEVEN DOLLARS"));
        converter = new NumberToWords("199");
        assertTrue(converter.convert().equals("ONE HUNDRED AND NINETY-NINE DOLLARS"));
        converter = new NumberToWords("200");
        assertTrue(converter.convert().equals("TWO HUNDRED DOLLARS"));
        converter = new NumberToWords("807");
        assertTrue(converter.convert().equals("EIGHT HUNDRED AND SEVEN DOLLARS"));
        converter = new NumberToWords("230");
        assertTrue(converter.convert().equals("TWO HUNDRED AND THIRTY DOLLARS"));
    }

    @Test
    public void testCents() {
        NumberToWords converter = new NumberToWords("1.12");
        assertTrue(converter.convert().equals("ONE DOLLAR AND TWELVE CENTS"));
        converter = new NumberToWords("1.01");
        assertTrue(converter.convert().equals("ONE DOLLAR AND ONE CENT"));
        converter = new NumberToWords("0.20");
        assertTrue(converter.convert().equals("ZERO DOLLARS AND TWENTY CENTS"));
    }

    @Test
    public void testNegative() {
        NumberToWords converter = new NumberToWords("-1");
        assertTrue(converter.convert().equals("NEGATIVE ONE DOLLAR"));
    }
}
