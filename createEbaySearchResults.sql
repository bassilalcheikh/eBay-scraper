
/*
SQL script creates table to upload results from the eBay search results.
*/
CREATE TABLE rawebaysearchresults (
    listing_number  varchar(25)     NOT NULL PRIMARY KEY,
    listing_title   varchar(255)    NOT NULL,
    listing_link    varchar(255)    NOT NULL,
    sale_date_time  varchar(20),
    bid_count       integer,
    listing_format  varchar(20)     NOT NULL,
    condition       varchar(50),
    price           real            NOT NULL,
    price_shipping  real            NOT NULL,
    brand           varchar(50)     NOT NULL,
    model           varchar(50)     NOT NULL
);



    --{"String", "String", "String", "String", "int", "String", "String", "double", "double", "String", "String"}

