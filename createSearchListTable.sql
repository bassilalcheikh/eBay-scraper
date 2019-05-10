CREATE TABLE IF NOT EXISTS searchlist(
    search_id int NOT NULL PRIMARY KEY,
    search_brand varchar(100) NOT NULL,
    search_model varchar(100) NOT NULL,
    price_min real,
    price_max real,
    item_type varchar(100),
    item_quantity int
);

/*
INSERT INTO searchlist VALUES
(1, 'Rolex', '16750',      3001, null, 'watch', 0 ),
(2, 'Rolex', '16760',      3001, null, 'watch', 0 ),
(3, 'Rolex', '126710BLRO', 6001, null, 'watch', 0 ),
(4, 'Rolex', '16610',      3001, null, 'watch', 0 ),
(5, 'Omega', '311.30.42.30.01.005', 2001, null, 'watch', 0 ),
(6, 'Tudor', '79830RB', 1000, null, 'watch', 0 );
*/