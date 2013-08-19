package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import fi.helsinki.cs.okkopa.main.Settings;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaDatabaseConnectionSource extends JdbcConnectionSource {

    @Autowired
    public OkkopaDatabaseConnectionSource(Settings settings) throws SQLException {
        super(settings.getProperty("database.h2.url"),
                settings.getProperty("database.h2.user"),
                settings.getProperty("database.h2.password"));
    }
}
