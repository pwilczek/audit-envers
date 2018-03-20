package name.wilu.auditenvers;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;


@Configuration
class Database {

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setName("db").setType(H2).build();
    }

    @Bean
    Server httpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9997").start();
    }

    @Bean  // jdbc:h2:tcp://localhost:9997/mem:db
    Server webServer() throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "9998").start();
    }

}

@Configuration
public class Jpa {

    @Bean
    HibernateJpaVendorAdapter vendor() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(org.springframework.orm.jpa.vendor.Database.H2);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                JpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean f = new LocalContainerEntityManagerFactoryBean();
        f.setDataSource(dataSource);
        f.setJpaVendorAdapter(adapter);
        f.setPackagesToScan(this.getClass().getPackage().getName());
        return f;
    }

    @Bean
    JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean factory) {
        JpaTransactionManager txm = new JpaTransactionManager();
        txm.setEntityManagerFactory(factory.getObject());
        return txm;
    }
}