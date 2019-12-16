package br.com.projeto.agenda.util.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.text.MaskFormatter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Classe com funções utilitárias
 *

 */
public class Util {

    //formatadores
    public static final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat formatoMesAno = new SimpleDateFormat("MM/yyyy");
    private static final SimpleDateFormat formatoMesAnoExtenso = new SimpleDateFormat("MMMM/yyyy");
    private static final SimpleDateFormat formatoDiaMes = new SimpleDateFormat("dd/MMM");
    private static final SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final SimpleDateFormat formatoDataSQL = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat formatoDataHoraSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat formatoDataHoraArquivo = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
    private static final SimpleDateFormat formatoDataJson = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
    private static final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
    private static final DecimalFormat formatoValor = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    private static final DecimalFormat formatoValorDistancia = new DecimalFormat("#0.###", new DecimalFormatSymbols(new Locale("pt", "BR")));
    private static final DecimalFormat formatoValorSemDecimal = new DecimalFormat("#,##0");

    //Log
    private static Log log = LogFactory.getLog(Util.class);

    //mensagens padrão
    public static final String ACESSO_RESTRITO = "Acesso restrito! Você não tem permissão para acessar essa página.";
    public static final String PAGINA_NAO_ENCONTRADA = "Erro 404! A página que você procurava não foi encontrada.";

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000")
                || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0         
                // (48 eh a posicao de '0' na tabela ASCII)         
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static boolean isCNPJ(String CNPJ) {
// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111")
                || CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333")
                || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
                || CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777")
                || CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999")
                || (CNPJ.length() != 14)) {
            return (false);
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

// "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char) ((11 - r) + 48);
            }

// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char) ((11 - r) + 48);
            }

// Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    //rotinas utilitárias
    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        } else if (s.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static String trimAll(String s) {
        if (s != null) {
            String string = s.trim();

            while (string.contains("  ")) {
                string = string.replaceAll("  ", " ");
            }
            return string;
        }
        return "";
    }

    public static boolean isZero(Integer i) {
        if (i == null) {
            return true;
        } else if (i == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotZero(Integer i) {
        return !isZero(i);
    }

    public static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String colocaWhereOuAnd(String s) {
        if (s == null) {
            return "";
        }

        if (s.contains("WHERE")) {
            return " AND ";
        } else {
            return " WHERE ";
        }
    }

    public static String zerosAEsquerda(String str, Integer qtde) {
        if (str == null) {
            return "";
        }

        String s = str.trim();
        while (s.length() < qtde) {
            s = '0' + s;
        }

        s = s.substring(0, qtde);

        return s;
    }

    public static String espacosAdireita(String str, Integer qtde) {
        String s = str.trim();
        while (s.length() < qtde) {
            s = s + ' ';
        }
        s = s.substring(0, qtde);
        return s;
    }

    public static String espacosAesquerda(String str, Integer qtde) {
        String s = str.trim();
        while (s.length() < qtde) {
            s = ' ' + s;
        }
        s = s.substring(0, qtde);
        return s;
    }

    public static String completaComEspacoDireita(String str, Integer qtde) {
        if (str == null || str.equals("null")) {
            str = "";
        }
        String s = str.trim();
        while (s.length() < qtde) {
            s = s + ' ';
        }
        s = s.substring(0, qtde);
        return s;
    }

    public static String completaComEspacoEsquerda(String str, Integer qtde) {
        String s = str.trim();
        while (s.length() < qtde) {
            s = ' ' + s;
        }
        s = s.substring(0, qtde);
        return s;
    }

    public static String repeteCarectere(String caractere, Integer qtde) {
        String s = caractere;
        while (s.length() < qtde) {
            s = caractere + s;
        }
        s = s.substring(0, qtde);
        return s;
    }

    public static String alteraEncodingString(String fraseAntiga, String encode) {
        try {
            //Pega string como array de bytes
            byte[] bytes = fraseAntiga.getBytes();

            //tranforma no encode desejado
            return new String(bytes, encode);

        } catch (UnsupportedEncodingException ex) {
            log.error("Erro de sistema" + ex);
            return null;
        }
    }

    //Função para criar hash md5
    public static String calculaHashMD5(String data) {
        try {
            byte[] mybytes = data.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5digest = md5.digest(mybytes);

            return Util.bytesToHex(md5digest);

        } catch (NoSuchAlgorithmException ex) {
            log.error("Erro de sistema" + ex);
            return null;
        }
    }

    private static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            sb.append((Integer.toHexString((b[i] & 0xFF) | 0x100)).substring(1, 3));
        }
        return sb.toString();
    }

    private static String aplicaMascara(String pattern, Object value) throws ParseException {
        MaskFormatter mask = new MaskFormatter(pattern);
        mask.setValueContainsLiteralCharacters(false);
        return mask.valueToString(value);
    }

    public static String removeMascara(String mascarado) {
        String desmascara = mascarado.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/", "").replaceAll("\\(", "").replaceAll("\\)", "");
        return desmascara;
    }

    public static String formataCPF(String cpf) {
        try {
            cpf = Util.removeMascara(cpf);
            cpf = Util.zerosAEsquerda(cpf, 11);
            return Util.aplicaMascara("###.###.###-##", cpf);

        } catch (ParseException ex) {
            log.error("Erro de sistema - formatar CPF" + ex);
            return cpf;
        }
    }

    public static String formataNIF(String nif) {
        try {
            nif = Util.removeMascara(nif);
            nif = Util.zerosAEsquerda(nif, 9);
            return Util.aplicaMascara("###.###.###", nif);

        } catch (ParseException ex) {
            log.error("Erro de sistema - formatar NIF" + ex);
            return nif;
        }
    }

    public static String formataCNPJ(String cnpj) {
        try {
            cnpj = Util.removeMascara(cnpj);
            cnpj = Util.zerosAEsquerda(cnpj, 14);
            return Util.aplicaMascara("##.###.###/####-##", cnpj);

        } catch (ParseException ex) {
            log.error("Erro de sistema - formatar CNPJ" + ex);
            return cnpj;
        }
    }

    public static String formataCEP(String cep) {
        try {
            cep = Util.removeMascara(cep);
            cep = Util.zerosAEsquerda(cep, 8);
            return Util.aplicaMascara("#####-###", cep);

        } catch (ParseException ex) {
            log.error("Erro de sistema - formatar CEP" + ex);
            return cep;
        }
    }

    public static String formataTelefone(String telefone) {
        telefone = Util.removeMascara(telefone);
        telefone = Util.espacosAesquerda(telefone, 9);

        String telFormatado = telefone.substring(0, 5) + "-" + telefone.substring(5, 9) + " ";

        return telFormatado;
    }

    public static String formataTelefoneComDDD(String telefone, Integer ddd) {
        if (Util.isBlank(telefone)) {
            return "";
        }
        if (ddd == null) {
            ddd = 0;
        }

        ddd = Integer.valueOf(Util.removeMascara(ddd.toString()));
        telefone = Util.removeMascara(telefone);
        telefone = Util.espacosAesquerda(telefone, 9);

        String telFormatado = "(" + ddd + ") " + telefone.substring(0, 5) + "-" + telefone.substring(5, 9) + " ";

        return telFormatado;
    }

    // Expressão regex que remove tudo que estvier entre < >
    public static String removeTagsHtml(String htmlString) {
        String noTagString = htmlString.replaceAll("\\<.*?\\>", "");
        return noTagString;
    }

    public static String removeAcentuacao(String passa) {
        passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");
        passa = passa.replaceAll("[âãàáä]", "a");
        passa = passa.replaceAll("[ÊÈÉË]", "E");
        passa = passa.replaceAll("[êèéë]", "e");
        passa = passa.replaceAll("[ÎÍÌÏ]", "I");
        passa = passa.replaceAll("[îíìï]", "i");
        passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");
        passa = passa.replaceAll("[ôõòóö]", "o");
        passa = passa.replaceAll("[ÛÙÚÜ]", "U");
        passa = passa.replaceAll("[ûúùü]", "u");
        passa = passa.replaceAll("Ç", "C");
        passa = passa.replaceAll("ç", "c");
        passa = passa.replaceAll("[ýÿ]", "y");
        passa = passa.replaceAll("Ý", "Y");
        passa = passa.replaceAll("ñ", "n");
        passa = passa.replaceAll("Ñ", "N");
        passa = passa.replaceAll("['<>/]", "");
        passa = passa.replace("\"", "");
        passa = passa.replace("`", "");
        return passa;
    }

    public static boolean temPeloMenosUmCaractereNaoAlfabetico(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char caractere = str.charAt(i);

            if (caractere >= 65 && caractere <= 122) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    public static String preencheZerosAEsquerda(String str, Integer qtde) {
        if (str == null) {
            return "";
        }

        String s = str.trim();
        while (s.length() < qtde) {
            s = '0' + s;
        }

        s = s.substring(0, qtde);

        return s;
    }

   

    public static String formataValor(Double valor) {
        if (valor == null) {
            return "0";
        } else {
            return formatoValor.format(valor);
        }
    }

    public static String formataData(Date data) {
        if (data == null) {
            return "00/00/0000";
        }
        return formatoData.format(data);
    }

    public static String formataMesAno(Date data) {
        return formatoMesAno.format(data);
    }

    public static String formataMesAnoExtenso(Date data) {
        return formatoMesAnoExtenso.format(data);
    }

    public static String formataDiaMes(Date data) {
        return formatoDiaMes.format(data);
    }

    public static String formataDataHora(Date data) {
        if (data == null) {
            return "00/00/0000 00:00";
        }
        return formatoDataHora.format(data);
    }

    public static String formataDataSQL(Date data) {
        return formatoDataSQL.format(data);
    }

    public static String formataDataJson(Date data) {
        return formatoDataJson.format(data);
    }

    public static String formataHora(Date data) {
        return formatoHora.format(data);
    }

    public static String formataDataHoraSQL(Date data) {
        return formatoDataHoraSQL.format(data);
    }

    public static Date formataDataHoraSQL(String string) {
        try {
            return formatoDataHoraSQL.parse(string);
        } catch (ParseException ex) {
            System.out.println("Data inválida em Util.formataDataHoraSQL(string) " + string);
            return null;
        }
    }

    public static String formataDataHoraArquivo(Date data) {
        return formatoDataHoraArquivo.format(data);
    }

    public static String formataDataHoraInicioSQL(Date data) {
        return formatoDataSQL.format(data) + " 00:00:00.000";
    }

    public static String formataDataHoraFinalSQL(Date data) {
        return formatoDataSQL.format(data) + " 23:59:59.999";
    }

    public static String formataValorDistancia(Double valor) {
        return formatoValorDistancia.format(valor) + " Km";
    }

    public static String formataValorDecimaisVariavel(Double valor) {
        return formatoValorDistancia.format(valor);
    }

    public static String formataValorSemDecimais(Double valor) {
        return formatoValorSemDecimal.format(valor);
    }

    public static String quebraLinhaBancoHtml(String stringBanco) {
        String stringHtml = stringBanco.replace("\r\n", "<br>");
        return stringHtml;
    }

    public static String quebraLinhaHtmlBanco(String stringHtml) {
        String stringBanco = stringHtml.replace("<br>", "\r\n");
        return stringBanco;
    }

    public static String primeiroNome(String nomeCompleto) {
        int index = nomeCompleto.indexOf(" ");

        String primeiro = nomeCompleto;

        if (index > -1) {
            primeiro = nomeCompleto.substring(0, index);
        }

        return primeiro;
    }

    public static void poiDeixaLinhaNegrito(Workbook wb, Row row) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
        }
    }

    public static String formataDataSQL(LocalDate data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return data.format(formato);
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate dateToLocalDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        return LocalDate.of(year, month, day);
    }

    public static LocalDate primeiroDiaMes() {
        LocalDate now = LocalDate.now();
        return now.withDayOfMonth(1);
    }

    public static LocalDate ultimoDiaMes() {
        LocalDate now = LocalDate.now();
        return now.withDayOfMonth(now.lengthOfMonth());
    }

    public static LocalDate primeiroDiaSemana() {
        LocalDate now = LocalDate.now();

        //primeiro dia da semana                        
        LocalDate primeiroDiaSemana = now.with(DayOfWeek.MONDAY);

//        // Print the dates of the current week
//        LocalDate printDate = primeiroDiaSemana;        
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy");
//        
//        for (int i = 0; i < 7; i++) {
//            System.out.println(printDate.format(formatter));
//            printDate = printDate.plusDays(1);
//        }
        return primeiroDiaSemana;
    }

    public static LocalDate ultimoDiaSemana() {
        LocalDate now = LocalDate.now();

        //primeiro dia da semana                        
        LocalDate primeiroDiaSemana = now.with(DayOfWeek.MONDAY);

        //ultimo dia da semana                        
        LocalDate ultimoDiaSemana = primeiroDiaSemana.plusDays(6);

        return ultimoDiaSemana;
    }

    public static String codificaBase64Encoder(String str) {
        return new Base64().encodeToString(str.getBytes());
    }

    public static String decodificaBase64Decoder(String str) {
        return new String(new Base64().decode(str));
    }

    public static String formataSite(String site) {
        if (site == null) {
            return "";
        }
        if (site.contains("http://")) {
            return site.trim();
        } else if (site.contains("https://")) {
            return site.trim();
        } else {
            return "http://" + site;
        }
    }

    public static String formataSiteSemHttp(String site) {
        if (site == null) {
            return "";
        }
        //tira facebook
        site = site.replace("www.facebook.com", "");
        site = site.replace("?fref=ts", "");

        //tira ultima barra se houver
        if (site.charAt(site.length() - 1) == '/') {
            site = site.substring(0, site.length() - 1);
        }

        return site.replaceAll("http://", "").replace("https://", "");
    }

    public static String montaUrlParaEmbedarVideo(String urlNormal) {
        if (urlNormal == null) {
            return "";
        }

        //Youtube - Url normal
        if (urlNormal.contains("youtube")) {
            //pega o código do video
            int pos = urlNormal.indexOf("watch?v=");
            if (pos > -1) {
                urlNormal = urlNormal.substring(pos + 8);
            }

            //retira os parâmetros depois do código
            pos = urlNormal.indexOf("&");
            if (pos > -1) {
                urlNormal = urlNormal.substring(0, pos);
            }

            if (Util.isNotBlank(urlNormal)) {
                return "https://www.youtube.com/embed/" + urlNormal.trim();
            }
        } //Youtube - URL encurtada
        else if (urlNormal.contains("youtu.be")) {
            //trata url encurtada
            urlNormal = urlNormal.replaceAll("https://youtu.be/", "");
            urlNormal = urlNormal.replaceAll("http://youtu.be/", "");

            if (Util.isNotBlank(urlNormal)) {
                return "https://www.youtube.com/embed/" + urlNormal.trim();
            }
        }

        //Vimeo - Url normal
        if (urlNormal.contains("vimeo")) {
            //pega o código do video            
            urlNormal = urlNormal.replaceAll("https://vimeo.com/channels/staffpicks/", "");
            urlNormal = urlNormal.replaceAll("http://vimeo.com/channels/staffpicks/", "");

            urlNormal = urlNormal.replaceAll("https://vimeo.com/", "");
            urlNormal = urlNormal.replaceAll("http://vimeo.com/", "");

            if (Util.isNotBlank(urlNormal)) {
                return "https://player.vimeo.com/video/" + urlNormal.trim();
            }
        }

        return urlNormal;
    }

    public static int calculaIdade(Date dataNasc) {
        LocalDate hoje = LocalDate.now();
        LocalDate nascimento = Util.dateToLocalDate(dataNasc);

        Period dif = nascimento.until(hoje);
        return dif.getYears();
    }

    public static int calculaIdadeEmData(Date dataNasc, Date dataBase) {
        LocalDate hoje = Util.dateToLocalDate(dataBase);
        LocalDate nascimento = Util.dateToLocalDate(dataNasc);

        Period dif = nascimento.until(hoje);
        return dif.getYears();
    }

    public static String validaHoraString(String horaStringCom2Pontos) {

        String[] divideHora = horaStringCom2Pontos.split(":");

        Integer intHora = Integer.parseInt(divideHora[0]);
        Integer intMinuto = Integer.parseInt(divideHora[1]);

        if (intHora > 24) {
            return "Erro: Horário inválido (hora precisa ser menor ou igual a 24).";
        }
        if (intMinuto > 59) {
            return "Erro: Horário inválido (minuto precisa ser menor ou igual a 59).";
        }

        return null;
    }

    public static boolean emailValido(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static Double deixaSoNumeros(String string) {
        try {
            //verifica se palavra chave esta em metros ou km
            boolean emMetros = true;
            if (string.toLowerCase().contains("km")) {
                emMetros = false;
            } else if (string.toLowerCase().contains("m")) {
                emMetros = true;
            }

            //Troca tudo que não for dígito por vazio
            string = string.replaceAll("\\D", "").trim();

            //Divide por 1000 se for em metros
            if (emMetros) {
                return Double.valueOf(string) / 1000;
            } else {
                return Double.valueOf(string);
            }

        } catch (NumberFormatException e) {
            return null;
        }
    }

   

}
