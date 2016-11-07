This is a simple (written in an afternoon) election simulator written in Scala. It:
1. Downloads the latest polls from Huffington Post
2. Weights the polls according to recency and sample size
3. Simulates polling error based on the number of undecided voters and the historical polling error
4. Simulates the election _n_ times

You can run a simulation with `sbt test`
