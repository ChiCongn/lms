package edu.lms.services;

import io.github.cdimascio.dotenv.Dotenv;
public class Config {
    public static Dotenv dotenv = Dotenv.load();

    public static final String GG_BOOKS_API_KEY = dotenv.get("GG_BOOKS_API_KEY");

    public static final String FROM_EMAIL = dotenv.get("FROM_EMAIL");
    public static final String EMAIL_PASSWORD = dotenv.get("EMAIL_PASSWORD");

    public static final String DATABASE_USERNAME = dotenv.get("DATABASE_USERNAME");
    public static final String DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");
    public static final String DATABASE_ENDPOINT = dotenv.get("DATABASE_ENDPOINT");
}
