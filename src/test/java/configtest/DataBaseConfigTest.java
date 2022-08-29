package configtest;

import com.softwify.libraryapp.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DataBaseConfigTest extends DataBaseConfig {

    private static final Logger logger = LogManager.getLogger(DataBaseConfigTest.class.getSimpleName());

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/librarytest?serverTimezone = UTC","root","rootroot");
    }

}
