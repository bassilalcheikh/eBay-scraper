CREATE TABLE IF NOT EXISTS searchlist(
    search_id int NOT NULL PRIMARY KEY,
    search_brand varchar(100) NOT NULL,
    search_model varchar(100) NOT NULL,
    omit_phrase varchar(500),	
    model_search_phrase varchar(600) NOT NULL,
    price_min real,
    price_max real,
    item_type varchar(100),
    item_quantity int
);

/*
INSERT INTO searchlist VALUES
(1,'Rolex', '1675', '+-custom', '1675+-custom', 6001, NULL, 'watch', 0),
(2,'Rolex', '16750', '+-custom', '16750+-custom', 5001, NULL, 'watch', 0),
(3,'Rolex', '16760', '+-custom', '16760+-custom', 5001, NULL, 'watch', 0),
(4,'Rolex', '16710', '+-custom', '16710+-custom', 4001, NULL, 'watch', 0),
(5,'Rolex', '16700', '+-custom+-pvd', '16700+-custom+-pvd', 4001, NULL, 'watch', 0),
(6,'Rolex', '126710', '+-custom+-pvd', '126710+-custom+-pvd', 6001, NULL, 'watch', 0),
(7,'Rolex', '6542', '+-custom', '6542+-custom', 9500, NULL, 'watch', 0),
(8,'Rolex', '16610', '+-custom+-pvd+-kermit+-hulk+-green+-blue', '16610+-custom+-pvd+-kermit+-hulk+-green+-blue', 3001, NULL, 'watch', 0),
(9,'Rolex', '16800', '+-custom+-pvd', '16800+-custom+-pvd', 2901, NULL, 'watch', 0),
(10,'Rolex', '168000', '+-custom+-pvd', '168000+-custom+-pvd', 3001, NULL, 'watch', 0),
(11,'Rolex', '116613', '+-custom+-pvd', '116613+-custom+-pvd', 5001, NULL, 'watch', 0),
(12,'Rolex', '116610 LN', '+-custom+-pvd+-kermit+-hulk+-green+-blue', '116610 LN+-custom+-pvd+-kermit+-hulk+-green+-blue', 5001, NULL, 'watch', 0),
(13,'Rolex', '116610 LV', '+-custom+-pvd+-blue', '116610 LV+-custom+-pvd+-blue', 5001, NULL, 'watch', 0),
(14,'Rolex', '216570', '+-custom+-pvd', '216570+-custom+-pvd', 4001, NULL, 'watch', 0),
(15,'Rolex', '116500', '+-custom+-pvd', '116500+-custom+-pvd', 10001, NULL, 'watch', 0),
(16,'Omega', '311.30.42.30.01.005', '+-custom', '311.30.42.30.01.005+-custom', 2001, NULL, 'watch', 0),
(17,'Tudor', '79830', '+-custom+-pvd', '79830+-custom+-pvd', 1200, NULL, 'watch', 0);
*/