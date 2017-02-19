CREATE TABLE property (
id INTEGER NOT NULL,
parent_id INTEGER,
name VARCHAR(250),
PRIMARY KEY (id)
);

create sequence PROPERTY_SEQUENCE;
