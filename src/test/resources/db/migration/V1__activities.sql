CREATE TABLE activities (id INT AUTO_INCREMENT, start_time DATETIME, activity_desc VARCHAR(250), activity_type VARCHAR(10), PRIMARY KEY (id), UNIQUE KEY `start_time` (`start_time`));
INSERT INTO `activities` (`id`, `start_time`, `activity_desc`, `activity_type`) VALUES
	(1, '2022-04-01 10:00:00', 'egy kis futás', 'RUNNING'),
	(2, '2022-04-02 11:00:00', 'egy masik futas', 'RUNNING'),
	(3, '2022-04-02 11:10:00', 'egy kis biciklizés', 'BIKING');