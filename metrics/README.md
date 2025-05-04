## Problems
PromQL `increase({"my_counter"}[$__rate_interval])` queries (which are used to visualize counters) always output `0` for the first counter value recorded by Prometheus. This is a known Prometheus problem (see [prometheus/prometheus#13570](https://github.com/prometheus/prometheus/issues/13570)). \
The `created-timestamp-zero-ingestion` [feature flag](https://prometheus.io/docs/prometheus/latest/feature_flags/) doesn't seem to work with Prometheus' OTLP receiver yet.

Grafana dashboard provisioning doesn't always get the data source right: If the dashboard JSON export uses the *Export the dashboard to use in another instance* option, the data source ID is set to `$DS_PROMETHEUS`. When the provisioned dashboard is imported, `$DS_PROMETHEUS` is not automatically replaced with an available Prometheus data source. This is a known Grafana problem (see [grafana/grafana#10786](https://github.com/grafana/grafana/issues/10786)). \
Workaround: Export the dashboard without the *Export the dashboard to use in another instance* option. This works for the provisioned Prometheus data source (apparently the IDs of provisioned data sources are reproducible).
