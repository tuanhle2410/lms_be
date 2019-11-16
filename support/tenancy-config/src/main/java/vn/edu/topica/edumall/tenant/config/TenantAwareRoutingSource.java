package vn.edu.topica.edumall.tenant.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import vn.edu.topica.edumall.tenant.core.ThreadLocalStorage;

public class TenantAwareRoutingSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return ThreadLocalStorage.getTenantName();
	}

}
