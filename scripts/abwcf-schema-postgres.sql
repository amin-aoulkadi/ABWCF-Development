CREATE TABLE pages (
  url         TEXT,
  status      TEXT,
  crawl_depth INTEGER
);

CREATE INDEX pages_id_index ON pages USING HASH (url); -- The ABWCF executes many queries with "... WHERE url = ?".
