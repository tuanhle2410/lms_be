package vn.edu.topica.edumall.tenant.core;

public class ThreadLocalStorage {
	private static ThreadLocal<String> tenant = new ThreadLocal<>();

	public static void setTenantName(String tenantName) {
		tenant.set(tenantName);
	}

	public static String getTenantName() {
		return tenant.get();
	}
}
