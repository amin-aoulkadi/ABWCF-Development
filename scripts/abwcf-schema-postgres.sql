CREATE TABLE pages (
  url            TEXT,
  status         TEXT,
  crawl_depth    INTEGER,
  crawl_priority BIGINT
);

CREATE TABLE hosts (
  scheme_and_authority TEXT,
  robot_rules          TEXT,
  crawl_delay          BIGINT,
  valid_until          TIMESTAMP WITH TIME ZONE
);

-- Indexes to speed up common ABWCF queries:
CREATE INDEX pages_id_index ON pages USING hash (url); -- For queries with "WHERE url = ?".
CREATE INDEX pages_crawl_priority_index ON pages USING btree (crawl_priority); -- For queries with "ORDER BY crawl_priority".

CREATE INDEX hosts_id_index ON hosts USING hash (scheme_and_authority); -- For queries with "WHERE scheme_and_authority = ?".
