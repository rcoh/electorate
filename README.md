This is a simple (written in an afternoon) election simulator written in Scala. It's extremely simple. The most valuable part of it is probably the section that loads the recent polling data from the Huffington Post API (don't worry, these aren't only HuffPost polls). It:

1. Downloads the latest polls from Huffington Post
2. Weights the polls according to recency and sample size
3. Simulates polling error based on the number of undecided voters and the historical polling error
4. Simulates the election _n_ times

You can run a simulation with `sbt test`
