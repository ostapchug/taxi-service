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

DROP TABLE IF EXISTS `car_category_translation` CASCADE;

DROP TABLE IF EXISTS `language` CASCADE;

DROP TABLE IF EXISTS `car_status` CASCADE;

DROP TABLE IF EXISTS `m2m_trip_car` CASCADE;

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
`c_category` INT NOT NULL,
`c_location` INT UNSIGNED NOT NULL,
`c_status` INT NOT NULL DEFAULT 0,
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
`cc_id` INT NOT NULL AUTO_INCREMENT,
`cc_name` VARCHAR(50) NOT NULL,
`cc_price` DECIMAL(6,2) NOT NULL,
CONSTRAINT `PK_car_category` PRIMARY KEY (`cc_id` ASC)
);

CREATE TABLE `car_category_translation`
(
`cct_id` INT NOT NULL AUTO_INCREMENT,
`cct_car_category` INT NOT NULL,
`cct_name` VARCHAR(50) NOT NULL,
`cct_lang` INT NOT NULL,
CONSTRAINT `PK_car_category_translation` PRIMARY KEY (`cct_id` ASC)
);

CREATE TABLE `language`
(
`lang_id` INT NOT NULL AUTO_INCREMENT,
`lang_name` VARCHAR(50) NOT NULL,
CONSTRAINT `PK_language` PRIMARY KEY (`lang_id` ASC)
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

CREATE TABLE `location_translation`
(
`lt_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`lt_location` INT UNSIGNED NOT NULL,
`lt_street_name` VARCHAR(50) NOT NULL,
`lt_street_number` VARCHAR(50) NOT NULL,
`lt_lang` INT NOT NULL,
CONSTRAINT `PK_location_translation` PRIMARY KEY (`lt_id` ASC)
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
 
ALTER TABLE `car_category_translation` 
 ADD CONSTRAINT `UQ_car_category_translation_cct_name` UNIQUE (`cct_name` ASC);
 
ALTER TABLE `language` 
 ADD CONSTRAINT `UQ_language_lang_name` UNIQUE (`lang_name` ASC);
 
ALTER TABLE `car_status` 
 ADD CONSTRAINT `UQ_car_status_cs_name` UNIQUE (`cs_name` ASC);
 
ALTER TABLE `location` 
 ADD CONSTRAINT `UQ_location_address` UNIQUE (`l_street_name` ASC, `l_street_number` ASC);
 
ALTER TABLE `location_translation` 
 ADD CONSTRAINT `UQ_location_translation_address` UNIQUE (`lt_street_name` ASC, `lt_street_number` ASC);

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
    
ALTER TABLE `car_category_translation` 
 ADD CONSTRAINT `FK_car_category_translation_car_category`
	FOREIGN KEY (`cct_car_category`) REFERENCES `car_category` (`cc_id`) ON DELETE Cascade ON UPDATE Cascade;

ALTER TABLE `car_category_translation` 
 ADD CONSTRAINT `FK_car_category_translation_language`
	FOREIGN KEY (`cct_lang`) REFERENCES `language` (`lang_id`) ON DELETE Cascade ON UPDATE Cascade;

ALTER TABLE `location_translation` 
 ADD CONSTRAINT `FK_location_translation_location`
	FOREIGN KEY (`lt_location`) REFERENCES `location` (`l_id`) ON DELETE Cascade ON UPDATE Cascade;
    
ALTER TABLE `location_translation` 
 ADD CONSTRAINT `FK_location_translation_language`
	FOREIGN KEY (`lt_lang`) REFERENCES `language` (`lang_id`) ON DELETE Cascade ON UPDATE Cascade;
    
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

DELIMITER $$
CREATE TRIGGER `check_car_status`
BEFORE INSERT ON `m2m_trip_car`
FOR EACH ROW
BEGIN
	IF (SELECT `c_status` FROM `car` WHERE `c_id` = NEW.c_id) != 0 THEN
		BEGIN
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Wrong car_status';
		END;
	END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `lock_car`
AFTER INSERT ON `m2m_trip_car`
FOR EACH ROW
BEGIN
	UPDATE `car` SET `c_status` = 1 WHERE `c_id` = NEW.c_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `unlock_car`
AFTER UPDATE ON `trip`
FOR EACH ROW
BEGIN
DECLARE done INT DEFAULT 0;
DECLARE cs INT UNSIGNED;
DECLARE curs CURSOR FOR SELECT `c_id` FROM `m2m_trip_car` WHERE `t_id` = NEW.t_id;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
OPEN curs;
	read_loop: LOOP
		FETCH curs INTO cs;
    
		IF done THEN
			LEAVE read_loop;
		END IF;
        
		IF NEW.t_status = 2 OR NEW.t_status = 3 THEN
			BEGIN
				UPDATE `car` SET `c_status` = 0
				WHERE `c_id` = cs;
			END;
		END IF;
        
	END LOOP read_loop;
CLOSE curs;
END$$
DELIMITER ;

DELIMITER $$ 
CREATE PROCEDURE `get_cars`(IN capacity INT, IN category INT)
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE cur_count INT;
	DECLARE count_size INT DEFAULT capacity;
    DECLARE cur_id INT UNSIGNED;
    DECLARE id INT UNSIGNED;
    DECLARE curs CURSOR FOR SELECT `c_id`, `cm_seat_count` FROM `car` INNER JOIN `car_model` ON `c_model` = `cm_id` WHERE `c_status` = 0 AND `c_category` = category ORDER BY `cm_seat_count` DESC;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
	CREATE TABLE IF NOT EXISTS `temp_car`
	(
	`c_id` INT UNSIGNED NOT NULL,
	`c_reg_number` VARCHAR(50) NOT NULL,
	`c_model` INT UNSIGNED NOT NULL,
	`c_category` INT NOT NULL,
	`c_location` INT UNSIGNED NOT NULL,
	`c_status` INT NOT NULL
	);
    
	OPEN curs;
    
	read_loop: LOOP
        FETCH curs INTO cur_id, cur_count;
        
		IF done THEN
			SET id = (SELECT `c_id` FROM `temp_car` WHERE `c_id` = cur_id);
            
			IF count_size > 0 AND id IS NULL THEN
				INSERT INTO `temp_car` (`c_id`, `c_reg_number`, `c_model`, `c_category`, `c_location`, `c_status`)
				(
					SELECT * FROM `car` WHERE `c_id` = cur_id 
				);
			ELSEIF count_size > 0 AND id IS NOT NULL THEN
				DELETE FROM `temp_car`;
			END IF;
            
			LEAVE read_loop;
		END IF;

        IF count_size - cur_count >= -cur_count/3 THEN
			INSERT INTO `temp_car` (`c_id`, `c_reg_number`, `c_model`, `c_category`, `c_location`, `c_status`)
			(
				SELECT * FROM `car` WHERE `c_id` = cur_id 
			); 
            SET count_size = count_size - cur_count;
        END IF;
        
	END LOOP read_loop;
    
    CLOSE curs;
 
	SELECT * FROM `temp_car`;
	DROP TABLE IF EXISTS `temp_car`;

END $$
DELIMITER ;

SET FOREIGN_KEY_CHECKS=1;