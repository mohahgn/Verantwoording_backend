package com.example.verantwoording.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.SQLException;
import java.util.List;

@Path("/users")
public class UserController {

    private final UserService userService = new UserService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getUserEmails() throws SQLException {
        return userService.getUserEmails();
    }
}
