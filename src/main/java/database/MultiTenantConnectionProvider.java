package database;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {
    private ConnectionProvider connectionProvider;

    public MultiTenantConnectionProvider() throws SQLException {
        try {
            this.connectionProvider = initConnectionProvider();
        } catch (Exception e) {
            System.err.println("MultitenantConnectionProvider constructor error: " + e.getMessage());
        }
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return connectionProvider;
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = super.getConnection(tenantIdentifier);
        connection.createStatement().execute(String.format("SET search_path TO %s;", tenantIdentifier));
        return connection;
    }

    private ConnectionProvider initConnectionProvider() throws IOException {
        Configuration configuration = new HibernateConfiguration().getConfiguration().configure();
        DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();

        Properties properties = configuration.getProperties();
        connectionProvider.configure(properties);
        return connectionProvider;
    }
}
