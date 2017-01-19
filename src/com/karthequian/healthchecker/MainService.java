package com.karthequian.healthchecker;

import org.skife.jdbi.v2.DBI;

import com.karthequian.healthchecker.health.DatabaseHealthCheck;
import com.karthequian.healthchecker.resources.HtmlPageResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.jdbi.DBIFactory;
import com.yammer.dropwizard.migrations.MigrationsBundle;

public class MainService extends Service<MainConfiguration> {

	public static void main(String[] args) throws Exception {
		new MainService().run(args);
	}

	@Override
	public void initialize(Bootstrap<MainConfiguration> bootstrap) {
		bootstrap.setName("hello-world");

		bootstrap.addBundle(new MigrationsBundle<MainConfiguration>() {

			public DatabaseConfiguration getDatabaseConfiguration(MainConfiguration configuration) {
				return configuration.getDatabaseConfiguration();
			}

		});

		bootstrap.addBundle(new AssetsBundle("/assets/"));

	}

	@Override
	public void run(MainConfiguration configuration, Environment environment) throws Exception {
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, configuration.getDatabaseConfiguration(), "oracle");

		environment.addResource(new HtmlPageResource());

		environment.addHealthCheck(
				new DatabaseHealthCheck(jdbi, configuration.getDatabaseConfiguration().getValidationQuery()));
	}

}
