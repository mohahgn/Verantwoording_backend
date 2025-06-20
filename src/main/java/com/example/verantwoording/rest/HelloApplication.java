package com.example.verantwoording.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends Application {
    // lege klasse, alleen voor configuratie van base path /api
}
