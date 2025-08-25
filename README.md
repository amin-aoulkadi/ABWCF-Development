# ABWCF Development
This repository contains various development resources for the [ABWCF](https://github.com/amin-aoulkadi/ABWCF).

## Getting Started
### Cloning
This repository contains a Git Submodule and should therefore be cloned like so:
```shell
git clone --recurse-submodules git@github.com:amin-aoulkadi/ABWCF-Development.git
```

### Seed URLs
The main method expects a `seed-urls.conf` file in `src/main/resources`. This file contains an array of seed URLs:
```hocon
seed-urls = [
  "https://www.something.example/",
  "https://something-else.example/welcome"
]
```
