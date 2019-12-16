package br.com.projeto.agenda.util.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Rogério
 */
public class DataSource {

    //constantes de conexão
    private static final String DRIVER = "org.postgresql.Driver";

    //local
    private static final String IP_BANCO_LOCAL = "192.168.0.51";
    private static final String NOME_BANCO_LOCAL = "sipfree";
    private static final String USUARIO_BANCO_LOCAL = "profsyst";
    private static final String SENHA_BANCO_LOCAL = "g82otori";
    private static final String URL_COMPLETA_LOCAL = "jdbc:postgresql://" + IP_BANCO_LOCAL + ":5432/" + NOME_BANCO_LOCAL;

    //integrator
    private static final String IP_BANCO_INTEGRATOR = "174.142.48.81";
    private static final String NOME_BANCO_INTEGRATOR = "prof01";
    private static final String USUARIO_BANCO_INTEGRATOR = "ps01";
    private static final String SENHA_BANCO_INTEGRATOR = "g82otori";
    private static final String URL_COMPLETA_INTEGRATOR = "jdbc:postgresql://" + IP_BANCO_INTEGRATOR + ":5432/" + NOME_BANCO_INTEGRATOR;

    //conexão com o banco Postgree
    private static Connection conexaoBanco;

    //cria ou recupera uma conexão com o Postgree
    public static Connection getConexao() throws SQLException {
        String sOname = System.getProperty("os.name");
        System.out.println("Sistema Operacional Relatório->" + sOname);
        
        if (conexaoBanco == null || conexaoBanco.isClosed()) {
            try {
                Class.forName(DRIVER);
                
                if (sOname.contains("Windows")) {                
                    conexaoBanco = DriverManager.getConnection(URL_COMPLETA_LOCAL, USUARIO_BANCO_LOCAL, SENHA_BANCO_LOCAL);//Windows - Profsyst
                } else {                    
                    conexaoBanco = DriverManager.getConnection(URL_COMPLETA_INTEGRATOR, USUARIO_BANCO_INTEGRATOR, SENHA_BANCO_INTEGRATOR); //Linux - Intregrator
                }
            } catch (ClassNotFoundException ex) {
                System.out.println("O driver não pôde ser carregado: " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
        return conexaoBanco;
    }

    //fecha conexão com o Postgree
    public static void closeConnection() {
        try {
            conexaoBanco = null;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}