package cw;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class Config {
	@Bean
    public static PropertyPlaceholderConfigurer properties(){
      PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
      ClassPathResource[] resources = new ClassPathResource[ ]
        { new ClassPathResource( "cw.properties" ) };
      ppc.setLocations( resources );
      ppc.setIgnoreUnresolvablePlaceholders( true );
      return ppc;
    }
     
    @Value( "${jdbc.url}" ) private String jdbcUrl;
    @Value( "${jdbc.driverClassName}" ) private String driverClassName;
    @Value( "${jdbc.username}" ) private String username;
    @Value( "${jdbc.password}" ) private String password;
    
    @Bean(name="sqliteDataSource")
    public DataSource sqliteDataSource() {
    	DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(jdbcUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }
    
    @Autowired
    @Qualifier("sqliteDataSource")
    @Bean(name="sqliteJdbcTemplate")
    public JdbcTemplate sqliteJdbcTemplate(DataSource dataSource){
    	return new JdbcTemplate(dataSource);
    }
}
