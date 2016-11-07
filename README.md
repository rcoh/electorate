This is a simple (written in an afternoon) election simulator written in Scala. It's extremely simple, I was mostly curious how much it would take to get plausible results. The most valuable part of it is probably the section that loads the recent polling data from the Huffington Post API (don't worry, these aren't only HuffPost polls). It:

1. Downloads the latest polls from Huffington Post
2. Weights the polls according to recency and sample size
3. Simulates polling error based on the number of undecided voters and the historical polling error
4. Simulates the election _n_ times

You can run a simulation with `sbt test`

At time of writing, here are the results:
```
SimulationResult(
  Map(Trump -> 0.0619, Clinton -> 0.9381),
  Map(
    Connecticut -> Map(Trump -> 0.0774, Clinton -> 0.9226),
    California -> Map(Trump -> 0.0158, Johnson -> 1.0E-4, Stein -> 2.0E-4, Clinton -> 0.9839),
    Nevada -> Map(Trump -> 0.4134, Clinton -> 0.5866),
    Washington -> Map(Trump -> 0.0991, Johnson -> 1.0E-4, Clinton -> 0.9008),
    Florida -> Map(Trump -> 0.3009, Clinton -> 0.6991),
    NewMexico -> Map(Trump -> 0.2289, Johnson -> 1.0E-4, Clinton -> 0.771),
    NewYork -> Map(Trump -> 0.0174, Stein -> 3.0E-4, Johnson -> 2.0E-4, Clinton -> 0.9821),
    Delaware -> Map(Trump -> 0.0685, Clinton -> 0.9315),
    Arkansas -> Map(Trump -> 0.9298, Johnson -> 3.0E-4, Stein -> 4.0E-4, Clinton -> 0.0695),
    Alaska -> Map(Trump -> 0.8899, Johnson -> 1.0E-4, Clinton -> 0.11),
    Texas -> Map(Trump -> 0.7939, Clinton -> 0.2061),
    Tennessee -> Map(Trump -> 0.8628, Johnson -> 1.0E-4, Clinton -> 0.1371),
    Minnesota -> Map(Trump -> 0.1692, Clinton -> 0.8308),
    Utah -> Map(Trump -> 0.7985, McMullin -> 0.0493, Johnson -> 4.0E-4, Clinton -> 0.1518),
    Georgia -> Map(Trump -> 0.649, Clinton -> 0.351),
    Missouri -> Map(Trump -> 0.8167, Johnson -> 1.0E-4, Clinton -> 0.1832),
    Wisconsin -> Map(Trump -> 0.2577, Clinton -> 0.7423),
    Maine -> Map(Trump -> 0.2343, Stein -> 4.0E-4, Johnson -> 6.0E-4, Clinton -> 0.7647),
    Oklahoma -> Map(Trump -> 0.9695, Johnson -> 7.0E-4, Clinton -> 0.0298),
    Pennsylvania -> Map(Trump -> 0.2729, Clinton -> 0.7271),
    Virginia -> Map(Trump -> 0.1712, Clinton -> 0.8288),
    Massachusetts -> Map(Trump -> 0.0128, Stein -> 4.0E-4, Johnson -> 9.0E-4, Clinton -> 0.9859),
    Alabama -> Map(Trump -> 0.963, Clinton -> 0.037),
    WestVirginia -> Map(Trump -> 0.9851, Johnson -> 7.0E-4, Stein -> 3.0E-4, Clinton -> 0.0139),
    Indiana -> Map(Trump -> 0.886, Johnson -> 2.0E-4, Clinton -> 0.1138),
    SouthCarolina -> Map(Trump -> 0.7041, Clinton -> 0.2959),
    Kentucky -> Map(Trump -> 0.9794, Johnson -> 1.0E-4, Clinton -> 0.0205),
    Ohio -> Map(Trump -> 0.6428, Clinton -> 0.3572),
    Illinois -> Map(Trump -> 0.0681, Stein -> 1.0E-4, Clinton -> 0.9318),
    Wyoming -> Map(Trump -> 0.9947, Johnson -> 2.0E-4, Clinton -> 0.0051),
    Vermont -> Map(Trump -> 0.0055, Johnson -> 1.0E-4, Clinton -> 0.9944),
    Kansas -> Map(Trump -> 0.9106, Johnson -> 1.0E-4, Clinton -> 0.0893),
    NorthCarolina -> Map(Trump -> 0.3455, Clinton -> 0.6545),
    Maryland -> Map(Trump -> 0.0122, Johnson -> 1.0E-4, Clinton -> 0.9877),
    Iowa -> Map(Trump -> 0.7412, Clinton -> 0.2588),
    Arizona -> Map(Trump -> 0.6428, Clinton -> 0.3572),
    Louisiana -> Map(Trump -> 0.8877, Stein -> 1.0E-4, Johnson -> 3.0E-4, Clinton -> 0.1119),
    Colorado -> Map(Trump -> 0.2443, Clinton -> 0.7557),
    Michigan -> Map(Trump -> 0.299, Stein -> 1.0E-4, Johnson -> 2.0E-4, Clinton -> 0.7007),
    NewHampshire -> Map(Trump -> 0.2824, Clinton -> 0.7176),
    Oregon -> Map(Trump -> 0.1279, Stein -> 3.0E-4, Johnson -> 7.0E-4, Clinton -> 0.8711),
    Idaho -> Map(Trump -> 0.9593, Stein -> 7.0E-4, Johnson -> 0.0016, Clinton -> 0.0352, McMullin -> 0.0032),
    NewJersey -> Map(Trump -> 0.0647, Stein -> 1.0E-4, Clinton -> 0.9352)
  )
)```
