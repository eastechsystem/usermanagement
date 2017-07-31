package com.carma.usermanagement.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * The AbstractController class provides common functionality.
 * 
 */
public abstract class AbstractController {

	protected Map<String, String> errors = new HashMap<String, String>();
	public Map<String, String> getErrors() {
		return errors;
	}

	public Object getObjectFromContext(String maincomponentId,
			String subcomponentId) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		UIComponent form = viewRoot.findComponent(maincomponentId);
		UIData table = (UIData) form.findComponent(subcomponentId);
		Object currObject = table.getRowData();

		return currObject;
	}

	/**
	 * This method returns controller object from faces context.
	 * 
	 * @param controllerName
	 *            Specifies name of controller object.
	 * @param clazz
	 *            Specifies class of controller object.
	 * @return Controller object from faces context.
	 */
	public Object getControllerObject(String controllerName, Class<?> clazz) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELContext elcontext = facesContext.getELContext();
		ValueExpression ve = facesContext
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(elcontext, "#{" + controllerName + "}",
						clazz);
		return ve.getValue(elcontext);
	}

	/**
	 * This method returns request parameter value.
	 * 
	 * @param parameterName
	 *            Specifies name of parameter.
	 * @return Request parameter value.
	 */
	public Object getRequestParameter(String parameterName) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(parameterName);
	}

	/**
	 * This method returns initial parameter map from faces context.
	 * 
	 * @return Initial parameter map from faces context.
	 */
	public Map<?, ?> getInitialParameterMap() {
		Map<?, ?> initParameterMap = FacesContext.getCurrentInstance()
				.getExternalContext().getInitParameterMap();
		return initParameterMap;
	}

	/**
	 * This method returns session map from faces context.
	 * 
	 * @return Session map from faces context.
	 */
	public Map<?, ?> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap();
	}

	/**
	 * This method returns request map from faces context.
	 * 
	 * @return Request map from faces context.
	 */
	public Map<?, ?> getRequestMap() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestMap();
	}

	/**
	 * This method returns request parameter map from faces context.
	 * 
	 * @return Request parameter map from faces context.
	 */
	public Map<?, ?> getRequestParameterMap() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
	}

	/**
	 * This method returns user session object if it is already created.
	 * 
	 * @return User session object.
	 */
	public HttpSession getSession() {
		return getSession(false);
	}

	/**
	 * This method returns user session object. If create new flag is set then
	 * it creates new session if it is not created already.
	 * 
	 * @param createNewIfNotExit
	 *            Specifies whether to create a new session if it is not there
	 *            already.
	 * @return User session object.
	 */
	public HttpSession getSession(boolean createNewIfNotExit) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (HttpSession) facesContext.getExternalContext().getSession(
				createNewIfNotExit);
	}

	/**
	 * This method returns session id.
	 * 
	 * @return Session id.
	 */
	public String getSessionId() {
		HttpSession session = getSession();
		if (session == null) {
			return null;
		} else {
			return session.getId();
		}
	}

	/**
	 * This method returns external context.
	 * 
	 * @return External context.
	 */
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public ServletContext getServletContext() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext
				.getExternalContext().getContext();
		return servletContext;
	}

	public HttpServletRequest getRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest servletRequest = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		return servletRequest;
	}

	public HttpServletResponse getResponse() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse servletResponse = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		return servletResponse;
	}

	/**
	 * This method concatenate two objects.
	 * 
	 * @param object1
	 *            Specifies object1.
	 * @param object2
	 *            Specifies object2.
	 * @param separtor
	 *            Specifies separator between two objects.
	 * @return Concatenated string of two objects.
	 */
	public String concat(Object object1, Object object2, String separtor) {
		return (object1 != null ? object1.toString() : "")
				+ (separtor != null ? separtor : "")
				+ (object2 != null ? object2.toString() : "");
	}

	/**
	 * This method converts an integer to string form.
	 * 
	 * @param object
	 *            Specifies integer value.
	 * @return String from of integer value.
	 */
	public String convertToString(int object) {
		return object + "";
	}

	/**
	 * This method returns size of list.
	 * 
	 * @param list
	 *            Specifies list of items.
	 * @return Size of list.
	 */
	@SuppressWarnings("rawtypes")
	public int getSize(List list) {
		if (list == null) {
			return 0;
		}

		return list.size();
	}

	/**
	 * This method returns substring from the given string value.
	 * 
	 * @param input
	 *            Specifies input string value.
	 * @param length
	 *            Specifies the number of characters to be returned.
	 * @return Substring from the given string value.
	 */
	public String subString(String input, int length) {
		if (input == null) {
			return null;
		}

		if (input.length() > length)
			return input.substring(0, length);
		else
			return input;
	}

	/**
	 * This method returns substring from the given string value. Tailing dots
	 * are added at the end of string the actual string is larger than requested
	 * number of characters.
	 * 
	 * @param input
	 *            Specifies input string value.
	 * @param length
	 *            Specifies the number of characters to be returned.
	 * @return Substring from the given string value. Tailing dots are added at
	 *         the end of string the actual string is larger than requested
	 */
	public String subStringWithTailingDots(String input, int length) {
		if (input == null) {
			return null;
		}

		if (input.length() > length) {
			return input.substring(0, length) + "...";
		} else
			return input;
	}

	public void redirectToPage(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
