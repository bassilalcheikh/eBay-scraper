/*
SQL script creates table to upload results from the eBay search results.
*/
CREATE TABLE rawebaysearchresults (
    listing_number varchar(25) NOT NULL PRIMARY KEY,
    listing_title varchar(255) NOT NULL,
    listing_link varchar(255) NOT NULL,
    --brand varchar(50) NOT NULL,
    --model varchar(50) NOT NULL,
    sale_date_time varchar(20),
    bid_count integer,
    listing_format varchar(20) NOT NULL,
    price real NOT NULL,
    price_shipping real NOT NULL
    --seller_id varchar(50) NOT NULL
);
