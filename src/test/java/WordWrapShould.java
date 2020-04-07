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
    public void result_must_be_empty() {
        assertThat(wordresultado("", 2)).isEqualTo("");
        assertThat(wordresultado(null, 2)).isEqualTo("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void result_must_be_Ilegal_Argument_Exception() {
        wordresultado("hola", 0);
    }

    @Test
    public void text_must_be_wrap() {
        assertThat(wordresultado("hola", 2)).isEqualTo("ho\nla");
        assertThat(wordresultado("hola", 3)).isEqualTo("hol\na");
        assertThat(wordresultado("hola mundo", 6)).isEqualTo("hola\nmundo");
        assertThat(wordresultado("hola mundodffdfdfdfdf", 6)).isEqualTo("hola\nmundodf\nfdfdfd\nfdf");
        assertThat(wordresultado("holaquetalestas", 2)).isEqualTo("ho\nla\nqu\net\nal\nes\nta\ns");
        assertThat(wordresultado("holaquetalestas", 3)).isEqualTo("hol\naqu\neta\nles\ntas");
    }

    private String wordresultado(String texto, int anchoColumna) {
        if (anchoColumna < 1) {
            throw new IllegalArgumentException("Column width must be one or more");
        }
        if (texto == null) {
            return "";
        }

        boolean textoMayorQueAnchoColumna = texto.length() > anchoColumna;
        if (textoMayorQueAnchoColumna) {
            String ajuste = "";
            for (int currentPosition = 0; currentPosition < texto.length(); currentPosition += anchoColumna) {
                boolean textoSePuedeAjustar = currentPosition < texto.length() - anchoColumna;
                if (textoSePuedeAjustar) {
                    ajuste += ajustarTexto(texto, anchoColumna, currentPosition);
                } else {
                    ajuste += agregarResto(texto, currentPosition);
                }
            }
            return ajuste;
        }
        
        return texto;
    }

    private String agregarResto(String texto, int currentPosition) {
        String ajuste;
        ajuste = texto.substring(currentPosition);
        return ajuste;
    }

    private String ajustarTexto(String texto, int anchoColumna, int currentPosition) {
        String ajuste;
        ajuste = texto.substring(currentPosition, currentPosition + anchoColumna);
        int whiteSpaceIndex = ajuste.indexOf(" ");
        if (whiteSpaceIndex != -1) {
            ajuste = ajuste.replace(" ", "\n");
        } else {
            ajuste += "\n";
        }
        return ajuste;
    }
}
