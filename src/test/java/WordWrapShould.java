import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/* TO DO LIST
    "hola", 7 -> "hola"
    "hola", 2 -> "ho\nla"
    "hola mundo", 7 -> "hola\n mundo"
    "null", 2 -> ""
    "", 2 -> ""
    "hola", -3 -> ThrowException
 */
public class WordWrapShould {
    @Test
    public void consider_as_empty_texts() {
        assertThat(wordWrap("", 2)).isEqualTo("");
        assertThat(wordWrap(null, 2)).isEqualTo("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void not_accept_integers_smaller_than_one() {
        wordWrap("hola", 0);
    }

    @Test
    public void put_or_replace_wrap_lines() {
        assertThat(wordWrap("hola", 2)).isEqualTo("ho\nla");
        assertThat(wordWrap("hola", 3)).isEqualTo("hol\na");
        assertThat(wordWrap("holaqu", 2)).isEqualTo("ho\nla\nqu");
        assertThat(wordWrap("holaquetalestas", 2)).isEqualTo("ho\nla\nqu\net\nal\nes\nta\ns");

        assertThat(wordWrap("hola mundo", 6)).isEqualTo("hola\nmundo");
        assertThat(wordWrap("hola mundodffdfdfdfdf",6)).isEqualTo("hola\nmundodf\nfdfdfd\nfdf");
        assertThat(wordWrap("a lot of words", 10)).isEqualTo("a lot of\nwords");
        assertThat(wordWrap("a lot of words for a single line",10)).isEqualTo("a lot of\nwords for\na single\nline");
    }

    private String wordWrap(String text, int columnWidth) {
        if (columnWidth < 1) {
            throw new IllegalArgumentException("Column width must be one or more");
        }
        if (text == null || text.isEmpty()) {
            return "";
        }

        if (text.length() <= columnWidth) {
            return text;
        }
        String result = text.substring(0, columnWidth);
        int lastWhiteSpaceIndex = result.lastIndexOf(" ");
        String remainder = text.substring(columnWidth);
        if (lastWhiteSpaceIndex != -1) {
            result = changeWhiteSpaceToLineWrap(result, lastWhiteSpaceIndex);
            return result + wordWrap(remainder, columnWidth);
        }
        return result +"\n"+ wordWrap(remainder, columnWidth);
    }

    private String changeWhiteSpaceToLineWrap(String result, int lastWhiteSpaceIndex) {
        StringBuilder textWithWhiteSpace = new StringBuilder(result);
        textWithWhiteSpace.setCharAt(lastWhiteSpaceIndex, '\n');
        result=textWithWhiteSpace.toString();
        return result;
    }
}
