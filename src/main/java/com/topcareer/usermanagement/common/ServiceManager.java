package com.topcareer.usermanagement.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Jahanzaib Ali
 * @since May 21, 2017
 *
 */
public final class ServiceManager {

	static {
		try {
			System.out.println("############CLass static block loading############");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private ServiceManager() {

	}

	private static Map<String, Properties> jndiProperties = new ConcurrentHashMap<String, Properties>(0);

	private static Map<Class<?>, String> serviceNamesRemote = new ConcurrentHashMap<Class<?>, String>(0);

	private static Map<String, InitialContext> jndiContexts = new ConcurrentHashMap<String, InitialContext>(0);

	private static Map<String, Date> jndiContextsTimes = new ConcurrentHashMap<String, Date>(0);

	private static final String SERVICE_NAME_POSTFIX_REMOTE = "Remote";
	private static final String SERVICE_NAME_POSTFIX_LOCAL = "Local";
	public static final String APP_NAME_USER_MANAGEMENT = "usermanagement";

	private static Properties getJNDIProperties(String appName) {
		if (!jndiProperties.containsKey(appName)) {
			if (appName.equalsIgnoreCase(getAppContext())) {
				// LocalService Call Using Remote Naming
				String propertiesFileName = "jndi.properties";
				System.out.println("Loading properties file: " + propertiesFileName);
				try {

					InputStream inputStream = ServiceManager.class.getClassLoader().getResourceAsStream(propertiesFileName);
					Properties jndiProps = new Properties();
					jndiProps.load(inputStream);
					inputStream.close();
					jndiProperties.put(appName, jndiProps);

				} catch (IOException exception) {
					exception.printStackTrace();
				}
			} else { // RemoteService Call using EJB Client API

				Properties jndiProps = new Properties();
				jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

				jndiProperties.put(appName, jndiProps);
			}
		}
		return jndiProperties.get(appName);
	}

	private static String getAppContext() {
		return APP_NAME_USER_MANAGEMENT;
	}

	public static Object getService(final Class<?> clazz) {
		return getService(clazz, getAppContext());
	}

	public static Object getService(final Class<?> clazz, final String appName) {
		// System.out.println("########### Class: " + clazz.getName());
		// System.out.println("########### AppName: " + appName);
		String serviceName = getGlobalJndiName(clazz, appName);
		// System.out.println("########### Service Lookup Path: " +
		// serviceName);
		return getServiceByJndiName(serviceName, appName);
	}

	private static Object getServiceByJndiName(final String serviceName, final String appName) {
		try {
			// System.out.println("########### AppName 2: " + appName);
			InitialContext ctx = getInitialContext(appName);
			return ctx.lookup(serviceName);
		} catch (NamingException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	private static String getGlobalJndiName(Class<?> clazz, String appName) {

		String jndiName = null;

		String testServicePrefix = getAppContext() + "/";

		final String remoteServicePrefix = "ejb:/" + appName + "//";
		final String localServicePrefix = "java:global/" + appName + "/";

		String defaultprefix = "java:global/" + appName + "/";
		String prefix = defaultprefix;

		if (!appName.equalsIgnoreCase(getAppContext())) {
			if (appName.startsWith("test")) { // For Test Service Calls
				prefix = testServicePrefix;
			} else {
				prefix = remoteServicePrefix; // For Remote Service Calls
			}
		} else if (appName.equalsIgnoreCase(getAppContext())) {
			prefix = localServicePrefix; // For Local Service Calls

		}

		jndiName = serviceNamesRemote.get(clazz);
		if (jndiName == null) {
			if (clazz.getSimpleName().endsWith(SERVICE_NAME_POSTFIX_REMOTE)) {
				jndiName = prefix + clazz.getSimpleName().substring(0, clazz.getSimpleName().lastIndexOf(SERVICE_NAME_POSTFIX_REMOTE)) + "!" + clazz.getName();

			} else if (clazz.getSimpleName().endsWith(SERVICE_NAME_POSTFIX_LOCAL)) {

				jndiName = prefix + clazz.getSimpleName().substring(0, clazz.getSimpleName().lastIndexOf(SERVICE_NAME_POSTFIX_LOCAL)) + "!" + clazz.getName();
			}
		}
		serviceNamesRemote.put(clazz, jndiName);

		return jndiName;
	}

	private static InitialContext getInitialContext(final String appName) {
		try {
			Properties p = getJNDIProperties(appName);
			InitialContext ctx;
			if (jndiContexts.containsKey(appName) && jndiContexts.get(appName) != null) {
				ctx = jndiContexts.get(appName);
				Date time = jndiContextsTimes.get(appName);
				// System.out.println("JNDI Context for AppName["+appName+"] is running since: "+time.toString());
			} else {
				ctx = new InitialContext(p);
				jndiContexts.put(appName, ctx);
				jndiContextsTimes.put(appName, new Date());
			}
			return ctx;
		} catch (NamingException e) {
			try {
				throw new Exception("Unable to get InitialContext for JNDI. AppName[" + appName + "]", e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;

	}

	/**
	 * This method gets JNDI InitialContext for local services
	 * 
	 * @return Returns the JNDI Initial Context for local services
	 */
	public static InitialContext getInitialContext() {
		return getInitialContext(getAppContext());
	}
}
