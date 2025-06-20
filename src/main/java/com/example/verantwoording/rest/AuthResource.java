package com.example.verantwoording.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Path("/auth")
public class AuthResource {

    private final String DB_URL = "jdbc:postgresql://85.214.67.145:5432/postgres";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "rBHuygBSuewHR7BgllzIV8CvsX0Yc9SHnZ3HVguYr9xZiswlzuo34tVcSI8NFxIY";

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        try {
            Class.forName("org.postgresql.Driver");  // JDBC driver laden
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String checkSql = "SELECT COUNT(*) FROM users_new WHERE email = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, user.email);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        return Response.status(Response.Status.CONFLICT)
                                .entity(Map.of("error", "Email bestaat al"))
                                .build();
                    }
                }

                String sql = "INSERT INTO users_new (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, user.firstname);
                    stmt.setString(2, user.lastname);
                    stmt.setString(3, user.email);
                    stmt.setString(4, user.password);  // Pas wachtwoord hashing nog toe!
                    stmt.executeUpdate();
                }

                return Response.ok(Map.of("message", "Registratie gelukt")).build();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "JDBC Driver niet gevonden"))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Server error"))
                    .build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        try {
            Class.forName("org.postgresql.Driver");  // JDBC driver laden
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT password FROM users_new WHERE email = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, user.email);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        if (storedPassword.equals(user.password)) {
                            Map<String, String> token = new HashMap<>();
                            token.put("token", "dummy-token");
                            return Response.ok(token).build();
                        }
                    }
                }
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("error", "Ongeldige login"))
                        .build();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "JDBC Driver niet gevonden"))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Server error"))
                    .build();
        }
    }

    public static class User {
        public String firstname;
        public String lastname;
        public String email;
        public String password;
    }
}
