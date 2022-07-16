package com.ludo.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ludo.ecommerce.entity.Product;
import com.ludo.ecommerce.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager entityManager) {
		entityManager = this.entityManager;
	}

	
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		
		HttpMethod[] unsupportedActions = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
		
		// disable HTTP method for Product: POST, PUT, DELETE
		config.getExposureConfiguration()
			.forDomainType(Product.class)
			.withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
			.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));
	
	
	// disable HTTP method for ProductCategory: POST, PUT, DELETE
	config.getExposureConfiguration()
		.forDomainType(ProductCategory.class)
		.withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
		.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));
	
	// call an internal helper method
	config.exposeIdsFor(ProductCategory.class, Product.class);
	//exposeIds(config);
	
	}



	private void exposeIds(RepositoryRestConfiguration config) {
		// expose entity ids
		
		// - get a list of all entity classes from the entity manager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

		
		// create an array of the entity types
		List<Class> entityClasses = new ArrayList<>();

		entities.forEach(entity -> entityClasses.add(entity.getJavaType()));

		// expose the entity ids for the array of entity/domain types
		Class[] domainTypes = entityClasses.toArray(Class[]::new);

		config.exposeIdsFor(domainTypes);
		
	}
}

	
	


