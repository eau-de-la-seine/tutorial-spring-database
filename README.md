
## Les properties

Placer les properties dans *resources/application.properties*.

Ne précisez pas les quotes au niveau des valeurs, sinon cela va rajouter des quotes à la valeur.

Dans la classe de test, pour que le fichier *application.properties* soit prise en compte et qu'il soit possible d'injecter `@Value` :
    @ContextConfiguration(initializers = {
            ConfigFileApplicationContextInitializer.class
    })



## SQL

Dans la requête SQL, il est possible de préciser le nom de tous les champs à récupérer ou bien d'utiliser l'étoile `*`

Créez une classe de configuration SQL :
* Activer les transactions avec `@EnableTransactionManagement`
* Spring Data : Indiquer l'existence des classes Entity et Repository avec `@EnableJpaRepositories`



## DataSource

Deux moyens d'initialiser un datasource, en utilisant `@ConfigurationProperties` et `DataSourceBuilder` :
```
@Bean
@ConfigurationProperties(prefix="spring.datasource")
public DataSource dataSource() {
    return DataSourceBuilder.create().build();
}
```

Ou bien en utilisant l'implémentation `HikariDataSource` :
```
@Bean
public DataSource dataSource(
    @Value("${spring.datasource.url}") String jdbcUrl,
    @Value("${spring.datasource.username}") String username,
    @Value("${spring.datasource.password}") String password,
    @Value("${spring.datasource.driver-class-name}") String driverClassName
) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcUrl);
    config.setUsername(username);
    config.setPassword(password);
    config.setDriverClassName(driverClassName);
    return new HikariDataSource(config);
}
```




## Spring MVC

La variable `server.servlet.context-path` ne devrait pas impacter les tests.



## Autre

Démarrer le serveur PostgreSQL sous Windows:
     pg_ctl -D "C:/opt/postgresql/11/data" start

Configuration Spring JPA :
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html

En cours (à ne pas prendre en compte) :
Penser à ajouter l'annotation `@EnableConfigurationProperties` pour activer l'injection de properties via `@Value`