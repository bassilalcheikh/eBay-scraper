/*
INSERT INTO searchlist VALUES
(1, 'Rolex 16750',                          2001, null, 'watch', 0 ),
(2, 'Rolex 16760',                          2001, null, 'watch', 0 ),
(3, 'Rolex 126710BLRO',                     5001, null, 'watch', 0 ),
(4, 'Rolex 16610',                          2001, null, 'watch', 0 ),
(5, 'Omega Moonwatch 311.30.42.30.01.005',  2001, null, 'watch', 0 );
*/

CREATE TABLE searchlist(
    search_id       int             NOT NULL PRIMARY KEY,
    search_keywords varchar(255)    NOT NULL,
    price_min       real,
    price_max       real,
    item_type       varchar(100),
    item_quantity   int
);
