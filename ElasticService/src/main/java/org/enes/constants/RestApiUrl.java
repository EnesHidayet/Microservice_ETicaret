package org.enes.constants;

public class RestApiUrl {
    private static final String VERSION = "/v1";
    private static final String DEV = "/dev";
    private static final String ELASTIC = "/elastic";
    private static final String ROOT = DEV+VERSION+ELASTIC;
    public static final String USER = ROOT + "/user";

    public static final String ADD = "/add";
    public static final String GET_ALL = "/get-all";
    public static final String GET_BY_ID = "/get-by-id";
    public static final String DELETE_BY_ID = "/delete-by-id";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String UPDATE = "/update";
}
