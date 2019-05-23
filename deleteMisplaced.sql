DELETE FROM rawebaysearchresults 
WHERE instr( listing_link, model ) = 0 AND instr( listing_title, model ) = 0;
