## Problems
Grafana dashboard provisioning doesn't always get the data source right: If the dashboard JSON export uses the *Export the dashboard to use in another instance* option, the data source ID is set to `$DS_PROMETHEUS`. When the provisioned dashboard is imported, `$DS_PROMETHEUS` is not automatically replaced with an available Prometheus data source. This is a known Grafana problem (see [grafana/grafana#10786](https://github.com/grafana/grafana/issues/10786)). \
Workaround: Export the dashboard without the *Export the dashboard to use in another instance* option. This works for the provisioned Prometheus data source (apparently the IDs of provisioned data sources are reproducible).

## Prometheus
### Feature Flag: `created-timestamp-zero-ingestion`
By default, PromQL `increase({"my_counter"}[$__rate_interval])` queries (which are used to visualize counters) always output `0` for the first counter value recorded by Prometheus. \
The `created-timestamp-zero-ingestion` feature adjusts this behavior by injecting an initial counter value of `0` at the beginning of the time series.
