/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech
 *******************************************************************************/
package org.eclipse.kura.web.client.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.kura.web.client.messages.Messages;
import org.eclipse.kura.web.shared.GwtKuraErrorCode;
import org.eclipse.kura.web.shared.GwtKuraException;
import org.gwtbootstrap3.client.ui.Modal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.StatusCodeException;

/**
 * Handles GwtExceptions from RCP calls.
 *
 * @author mcarrer
 *
 */
public class FailureHandler {

    private static final Messages CMSGS = GWT.create(Messages.class);
    // private static final ValidationMessages MSGS = GWT.create(ValidationMessages.class);

    private static Logger logger = Logger.getLogger("ErrorLogger");
    private static Modal popup;

    public static void handle(Throwable caught, String name) {
        printMessage(caught, name);
    }

    public static void handle(Throwable caught) {
        printMessage(caught, "");
    }

    private static void printMessage(Throwable caught, String name) {
        if (caught instanceof GwtKuraException) {

            GwtKuraException gee = (GwtKuraException) caught;
            GwtKuraErrorCode code = gee.getCode();
            switch (code) {

            default:
                logger.log(Level.INFO, name + ": " + caught.getLocalizedMessage(), caught);
                popup.show();
                break;
            }
        } else if (caught instanceof StatusCodeException && ((StatusCodeException) caught).getStatusCode() == 0) {
            // the current operation was interrupted as the user started a new one
            // or navigated away from the page.
            // we can ignore this error and do nothing.
        } else {
            logger.log(Level.INFO, name + ": " + caught.getLocalizedMessage(), caught);
            popup.show();
        }
    }

    public static void setPopup(Modal uiElement) {
        popup = uiElement;
    }

    /*
     * @SuppressWarnings("unchecked")
     * public static boolean handleFormException(FormPanel form, Throwable caught) {
     *
     * boolean isWarning = false;
     * if (caught instanceof GwtKuraException) {
     *
     * List<Field<?>> fields = form.getFields();
     * GwtKuraException gee = (GwtKuraException) caught;
     * GwtKuraErrorCode code = gee.getCode();
     * switch (code) {
     *
     * case DUPLICATE_NAME:
     * boolean fieldFound = false;
     * String duplicateFieldName = gee.getArguments()[0];
     * for (Field<?> field : fields) {
     * if (duplicateFieldName.equals(field.getName())) {
     * TextField<String> textField = (TextField<String>) field;
     * textField.markInvalid(MSGS.duplicateValue());
     * fieldFound = true;
     * break;
     * }
     * }
     * if (!fieldFound) {
     * Info.display(CMSGS.error(), caught.getLocalizedMessage());
     * }
     * break;
     *
     * case ILLEGAL_NULL_ARGUMENT:
     * String invalidFieldName = gee.getArguments()[0];
     * for (Field<?> field : fields) {
     * if (invalidFieldName.equals(field.getName())) {
     * TextField<String> textField = (TextField<String>) field;
     * textField.markInvalid(MSGS.invalidNullValue());
     * break;
     * }
     * }
     * break;
     *
     * case ILLEGAL_ARGUMENT:
     * String invalidFieldName1 = gee.getArguments()[0];
     * for (Field<?> field : fields) {
     * if (invalidFieldName1.equals(field.getName())) {
     * TextField<String> textField = (TextField<String>) field;
     * textField.markInvalid(gee.getCause().getMessage());
     * break;
     * }
     * }
     * break;
     *
     * case INVALID_RULE_QUERY:
     * for (Field<?> field : fields) {
     * if ("query".equals(field.getName())) {
     * TextArea statement = (TextArea) field;
     * statement.markInvalid(caught.getLocalizedMessage());
     * break;
     * }
     * }
     * break;
     *
     * case WARNING:
     * isWarning = true;
     * Info.display(CMSGS.warning(), caught.getLocalizedMessage());
     * break;
     *
     * default:
     * Info.display(CMSGS.error(), caught.getLocalizedMessage());
     * caught.printStackTrace();
     * break;
     * }
     * }
     * else {
     *
     * Info.display(CMSGS.error(), caught.getLocalizedMessage());
     * caught.printStackTrace();
     * }
     *
     * return isWarning;
     * }
     */
}
