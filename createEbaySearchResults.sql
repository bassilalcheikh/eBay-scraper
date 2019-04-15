CREATE TABLE ebaysearchresults (
    listing_number varchar(25) NOT NULL PRIMARY KEY,
    listing_link varchar(100) NOT NULL,
    listing_title varchar(255) NOT NULL,
    brand varchar(25) NOT NULL,
    model varchar(25) NOT NULL,
    sale_date_time varchar(20),
    price real NOT NULL,
    price_shipping real NOT NULL,
    listing_format varchar(10), 
    seller_id varchar(50) NOT NULL
);
