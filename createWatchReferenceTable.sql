CREATE TABLE rawebaysearchresults (

    listing_number  varchar(25)     NOT NULL PRIMARY KEY,
    listing_title   varchar(255)    NOT NULL,
    listing_link    varchar(255)    NOT NULL,
    sale_date_time  varchar(20)             ,
    bid_count       integer                 ,
    listing_format  varchar(20)     NOT NULL,
    price           real            NOT NULL,
    price_shipping  real            NOT NULL,
    brand           varchar(50)     NOT NULL, --keep
    model           varchar(50)     NOT NULL, --keep
    gender          varchar(10)             , --keep
    case_size_mm    int                     , --keep
);
