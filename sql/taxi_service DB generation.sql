SET FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS `person` CASCADE;

DROP TABLE IF EXISTS `role` CASCADE;

DROP TABLE IF EXISTS `trip` CASCADE;

DROP TABLE IF EXISTS `trip_status` CASCADE;

DROP TABLE IF EXISTS `location` CASCADE;

DROP TABLE IF EXISTS `car` CASCADE;

DROP TABLE IF EXISTS `car_model` CASCADE;

DROP TABLE IF EXISTS `car_category` CASCADE;

DROP TABLE IF EXISTS `car_status` CASCADE;

DROP TABLE IF EXISTS `m2m_order_car` CASCADE;

/* Create Tables */

CREATE TABLE `person`
(
	`p_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `p_phone` VARCHAR(50) NOT NULL,
	`p_password`CHAR(60) NOT NULL,
    `p_name` VARCHAR(50),
	`p_surname` VARCHAR(50),
	`p_role` INT NOT NULL DEFAULT 1,
	CONSTRAINT `PK_person` PRIMARY KEY (`p_id` ASC)
);

CREATE TABLE `role`
(
`r_id` INT NOT NULL AUTO_INCREMENT,
`r_name` VARCHAR(50) NOT NULL,
CONSTRAINT `PK_role` PRIMARY KEY (`r_id` ASC)
);

CREATE TABLE `trip`
(
`t_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`t_person` INT UNSIGNED NOT NULL,
`t_origin` INT UNSIGNED NOT NULL,
`t_destination` INT UNSIGNED NOT NULL,
`t_distance` DECIMAL(6,2) NOT NULL,
`t_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`t_bill` DECIMAL(6,2) NOT NULL,
`t_status` INT NOT NULL DEFAULT 0,
CONSTRAINT `PK_trip` PRIMARY KEY (`t_id` ASC)
);

CREATE TABLE `trip_status`
(
`ts_id` INT NOT NULL AUTO_INCREMENT,
`ts_name` VARCHAR(50) NOT NULL,
CONSTRAINT `PK_trip_status` PRIMARY KEY (`ts_id` ASC)
);

CREATE TABLE `car`
(
`c_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`c_reg_number` VARCHAR(50) NOT NULL,
`c_model` INT UNSIGNED NOT NULL,
`c_category` INT UNSIGNED NOT NULL,
`c_location` INT UNSIGNED NOT NULL,
`c_status` INT NOT NULL,
CONSTRAINT `PK_car` PRIMARY KEY (`c_id` ASC)
);

CREATE TABLE `car_model`
(
`cm_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`cm_brand` VARCHAR(50) NOT NULL,
`cm_name` VARCHAR(50) NOT NULL,
`cm_color` VARCHAR(50) NOT NULL,
`cm_year` SMALLINT UNSIGNED NOT NULL,
`cm_seat_count` SMALLINT UNSIGNED NOT NULL,
CONSTRAINT `PK_car_model` PRIMARY KEY (`cm_id` ASC)
);

CREATE TABLE `car_category`
(
`cc_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`cc_name` VARCHAR(50) NOT NULL,
`cc_price` DECIMAL(6,2) NOT NULL,
CONSTRAINT `PK_car_category` PRIMARY KEY (`cc_id` ASC)
);

CREATE TABLE `car_status`
(
`cs_id` INT NOT NULL AUTO_INCREMENT,
`cs_name` VARCHAR(50) NOT NULL,
CONSTRAINT `PK_car_status` PRIMARY KEY (`cs_id` ASC)
);

CREATE TABLE `m2m_trip_car`
(
`t_id` INT UNSIGNED NOT NULL,
`c_id` INT UNSIGNED NOT NULL,
CONSTRAINT `PK_m2m_trip_car` PRIMARY KEY (`t_id` ASC, `c_id` ASC)
);

CREATE TABLE `location`
(
`l_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`l_street_name` VARCHAR(50) NOT NULL,
`l_street_number` VARCHAR(50) NOT NULL,
`l_coordinates` POINT NOT NULL,
CONSTRAINT `PK_location` PRIMARY KEY (`l_id` ASC)
);

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE `person` 
 ADD CONSTRAINT `UQ_person_p_phone` UNIQUE (`p_phone` ASC);

ALTER TABLE `role` 
 ADD CONSTRAINT `UQ_role_r_name` UNIQUE (`r_name` ASC);
 
ALTER TABLE `trip_status` 
 ADD CONSTRAINT `UQ_trip_status_ts_name` UNIQUE (`ts_name` ASC);
 
ALTER TABLE `car` 
 ADD CONSTRAINT `UQ_car_c_reg_number` UNIQUE (`c_reg_number` ASC);
 
ALTER TABLE `car_category` 
 ADD CONSTRAINT `UQ_car_category_cc_name` UNIQUE (`cc_name` ASC);
 
ALTER TABLE `car_status` 
 ADD CONSTRAINT `UQ_car_status_cs_name` UNIQUE (`cs_name` ASC);
 
 ALTER TABLE `location` 
 ADD CONSTRAINT `UQ_location_address` UNIQUE (`l_street_name` ASC, `l_street_number` ASC);

/* Create Foreign Key Constraints */

ALTER TABLE `person` 
 ADD CONSTRAINT `FK_person_role`
	FOREIGN KEY (`p_role`) REFERENCES `role` (`r_id`) ON DELETE Cascade ON UPDATE Cascade;

ALTER TABLE `trip` 
 ADD CONSTRAINT `FK_trip_person`
	FOREIGN KEY (`t_person`) REFERENCES `person` (`p_id`) ON DELETE Cascade ON UPDATE Cascade;

ALTER TABLE `trip` 
 ADD CONSTRAINT `FK_trip_location_from`
	FOREIGN KEY (`t_origin`) REFERENCES `location` (`l_id`) ON DELETE Cascade ON UPDATE Cascade;

ALTER TABLE `trip` 
 ADD CONSTRAINT `FK_trip_location_to`
	FOREIGN KEY (`t_destination`) REFERENCES `location` (`l_id`) ON DELETE Cascade ON UPDATE Cascade;
    
ALTER TABLE `trip` 
 ADD CONSTRAINT `FK_trip_trip_status`
	FOREIGN KEY (`t_status`) REFERENCES `trip_status` (`ts_id`) ON DELETE Cascade ON UPDATE Cascade;

ALTER TABLE `car` 
 ADD CONSTRAINT `FK_car_car_model`
	FOREIGN KEY (`c_model`) REFERENCES `car_model` (`cm_id`) ON DELETE Cascade ON UPDATE Cascade;
    
ALTER TABLE `car` 
 ADD CONSTRAINT `FK_car_car_category`
	FOREIGN KEY (`c_category`) REFERENCES `car_category` (`cc_id`) ON DELETE Cascade ON UPDATE Cascade;
    
ALTER TABLE `car` 
 ADD CONSTRAINT `FK_car_location`
	FOREIGN KEY (`c_location`) REFERENCES `location` (`l_id`) ON DELETE Cascade ON UPDATE Cascade;
    
ALTER TABLE `car` 
 ADD CONSTRAINT `FK_car_status`
	FOREIGN KEY (`c_status`) REFERENCES `car_status` (`cs_id`) ON DELETE Cascade ON UPDATE Cascade;
    
ALTER TABLE `m2m_trip_car` 
 ADD CONSTRAINT `FK_trip_car_trip`
	FOREIGN KEY (`t_id`) REFERENCES `trip` (`t_id`) ON DELETE Cascade ON UPDATE Cascade;
    
ALTER TABLE `m2m_trip_car` 
 ADD CONSTRAINT `FK_trip_car_car`
	FOREIGN KEY (`c_id`) REFERENCES `car` (`c_id`) ON DELETE Cascade ON UPDATE Cascade;

SET FOREIGN_KEY_CHECKS=1;