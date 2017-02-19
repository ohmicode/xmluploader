package com.example.config;

import javax.sql.DataSource;

public interface DBConnection {
    DataSource getDataSource();
}
