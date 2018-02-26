-- liquibase formatted sql
-- changeset jgoodwin:1
CREATE TABLE recipes (
  id int unsigned NOT NULL AUTO_INCREMENT,
  name varchar(256) NOT NULL,
  link varchar(1024) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE components (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(256) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE recipe_entries (
  recipeId int UNSIGNED NOT NULL,
  ingredientId int UNSIGNED NOT NULL,
  measurement VARCHAR(100) NOT NULL,
  amount DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (recipeId, ingredientId, amount, measurement)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
