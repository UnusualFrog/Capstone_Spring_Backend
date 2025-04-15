package org.example.capstone.controllers;

/**
 * Store the API Path and REST Nouns as constants to make the REST Controller more robust
 *
 */
public class RESTNouns {

    /* *
     *  NON-PLURALS
     *
     * */

    public static final String VERSION_1 = "/v1";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String RESET = "/reset";
    public static final String ID = "/{id}";
    public static final String ADDRESS_ID = "/{address_id}";
    public static final String HOME_ID = "/{home_id}";
    public static final String CUSTOMER_ID = "/{customer_id}";
    public static final String AUTO_ID = "/{auto_id}";
    public static final String ACCIDENT_ID = "/{accident_id}";
    public static final String QUOTE_ID = "/{quote_id}";
    public static final String POLICY_ID = "/{policy_id}";

    /* *
     *  PLURALS
     *
     * */

    public static final String CUSTOMER = "/customers";
    public static final String EMPLOYEE = "/employees";
    public static final String ADMIN = "/admins";
    public static final String EMAIL = "/emails";
    public static final String NAME = "/names";
    public static final String HOME = "/homes";
    public static final String ADDRESS = "/addresses";
    public static final String HOME_QUOTE = "/home_quotes";
    public static final String HOME_POLICY = "/home_policies";
    public static final String AUTO = "/autos";
    public static final String ACCIDENT = "/accidents";
    public static final String AUTO_QUOTE = "/auto_quotes";
    public static final String AUTO_POLICY = "/auto_policies";
    public static final String ACTIVE = "/actives";

}
