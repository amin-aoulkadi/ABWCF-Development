CREATE TABLE pages (
  url            TEXT,
  status         TEXT,
  crawl_depth    INTEGER,
  crawl_priority BIGINT
);

-- Indexes to speed up common ABWCF queries:
CREATE INDEX pages_id_index ON pages USING hash (url); -- For queries with "WHERE url = ?".
CREATE INDEX pages_crawl_priority_index ON pages USING btree (crawl_priority); -- For queries with "ORDER BY crawl_priority".
