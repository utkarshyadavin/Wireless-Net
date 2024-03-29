# B.Tech Project 

## Daily Progress Update 
* **2/08/2019**
    * Finished reading dB unit conversion tutorials at [this](http://www.ittc.ku.edu/~jstiles/622/handouts/dB.pdf) and [this](http://www.rfcafe.com/references/electrical/decibel-tutorial.htm) link. 
* **3/08/2019** 
	* Understood basic functionality of how radio transmitter and antenna work using feeder lines to produce and receive radio waves. 
	* Basics of what is antenna gain and how it is calculated. [wiki](https://en.wikipedia.org/wiki/Antenna_gain)
	* Finished reading about link budget from [here](https://www.electronics-notes.com/articles/antennas-propagation/propagation-overview/radio-link-budget-formula-calculator.php) and [wiki](https://en.wikipedia.org/wiki/Link_budget)
* **5/08/2019** 
	* Finished reading about link budget form [Tranzeo Link Budget Whitepaper](http://www.tranzeo.com/allowed/Tranzeo_Link_Budget_Whitepaper.pdf)
	* Finished reading [The Sky Is Not the Limit: LTE for Unmanned Aerial Vehicles](https://ieeexplore.ieee.org/document/8337920) with few doubts. 
* **7/08/2019**
	* Completed reading about centrality from [Network Analysis and Centrality Measures (Part-I)](https://www.hackerearth.com/practice/notes/network-analysis-and-centrality-measures-part-i/). 
	* Read basics of Social network from this [paper](https://pdfs.semanticscholar.org/75a9/829c84e345595bc6d50322abb823fd8831eb.pdf).
* **8/08/2019**
	* Understood the basic intuition and calculations of degree, betweenness and closeness centrality from [here](https://cs.brynmawr.edu/Courses/cs380/spring2013/section02/slides/05_Centrality.pdf)
	* Finished reading Part A,B and C of the paper: [Analysing Human Mobility and Social Relationships from Cellular Data](https://ieeexplore.ieee.org/document/8256044). Confusion over some mathematical expressions. 
* **9/09/2019** 
	* Basics of [radiation pattern](https://www.tutorialspoint.com/antenna_theory/antenna_theory_radiation_pattern.htm)
* **10/08/2019**
	* Basics of Handoffs from [wiki](https://en.wikipedia.org/wiki/Handover). Read about when handoffs takes place, how they take place, soft and hard handoffs, practical implementation, causes of failure of handovers, handoff prioritisation, etc. 
	* The [youtube](https://www.youtube.com/watch?v=dhsphf0Mnhs&list=PLjGG94etKypKeb0nzyN9tSs_HCd5c4wXF&index=26) video covers basics of cell division, frequency reuse pattern, FDMA, TDMA and 2G GSM technology. 
* **13/08/2019** 
	* Complete writing a simple simulator that associates UEs to the nearest BaseStation.
* **15/08/2019**
	* Completed simulation for cell selection based on max received power.
	* Completed simulation for cell selection based on max available bandwidth.
	* Completed simulation for cell selection based on max received bitrate in bits per second.
* **17/08/2019**
	* Changed the simulator structure to include functions for various criteria based associations.
	* Added function for LeastDistanceAssociation of UEs. 
	* Added function for MaxRsrpBasedAssociation for UEs.
	* Added function for Max Bitrate based Association. 
* **1/9/2018 - 8/10/2019** 
	* Completed writing a basic simulator for collaborative(Operator & Social) Cellular network for the following scenarios: 
	 	* Zero operator and Social Collaboration
	 	* Partial Operator and Social Collaboration
	 	* Partial Operator and Zero Social Collaboration
	 	* Complete Operator and Zero Operator Collaboration
	 * Read the following set of research papers:
	 	* [On the Performance Analysis and Relay Algorithm Design in Social-Aware D2D Cooperated Communications](https://ieeexplore.ieee.org/document/7504379)
	 	* [A novel relay selection algorithm based on mobile social networks for device-to-device cooperative communications](https://ieeexplore.ieee.org/document/8393215)
	 	* [Social-aware relay selection for device to device communications in cooperative cellular networks](https://ieeexplore.ieee.org/document/8075812)
	 	* [Cooperative device-to-device communications in cellular networks](https://ieeexplore.ieee.org/abstract/document/7143335)



## Reading Resources 
* **2/08/2019**
	* [A load-conscious cell selection scheme for femto-assisted cellular networks](https://ieeexplore.ieee.org/document/6666543)
	* [The Sky Is Not the Limit: LTE for Unmanned Aerial Vehicles](https://ieeexplore.ieee.org/document/8337920)
	* [Femtocell: Indoor Cellular Communication Redefined](https://www.cse.wustl.edu/~jain/cse574-10/ftp/femto/index.html): A short document on the architecture and functionality of femtocelss. 
	* Tutorial on db conversions of Watt Power in cellular networks. [One](http://www.ittc.ku.edu/~jstiles/622/handouts/dB.pdf) [Two](http://www.rfcafe.com/references/electrical/decibel-tutorial.htm)
	* [Article on the current technology in use for drones using satellite communication](https://www.dronezon.com/learn-about-drones-quadcopters/what-is-drone-technology-or-how-does-drone-technology-work/)
	* [List of rules and regulations for the use Unmanned Aerial Vehicles in USA by Federal Aviation Administration](https://www.faa.gov/news/fact_sheets/news_story.cfm?newsId=22615): Seems very restrictive but this line:
	> "The FAA can issue waivers to certain requirements of Part 107 if an operator demonstrates they can fly safely under the waiver without endangering other aircraft or people and property on the ground or in the air" 
* **3/08/2019**
	* Basics of link budget calculation equations. [wiki](https://en.wikipedia.org/wiki/Link_budget) [two](https://www.electronics-notes.com/articles/antennas-propagation/propagation-overview/radio-link-budget-formula-calculator.php)
	* [Wireless link budget analysis](http://www.tranzeo.com/allowed/Tranzeo_Link_Budget_Whitepaper.pdf) 
	* [Overview of 3GPP Release-15 Study on Enhanced LTE Support for Connected Drones](https://arxiv.org/pdf/1805.00826.pdf)
	* Paper describing the [architecture of LTE equipment](https://www.fujitsu.com/global/documents/about/resources/publications/fstj/archives/vol48-1/paper11.pdf) (eNodeB)
* **5/08/2019**
	* [An Overview of 3GPP Release-15 Study on Enhanced LTE Support for Connected Drones](https://arxiv.org/abs/1805.00826)
	* [Mobile Networks Connected Drones: Field Trials, Simulations, and Design Insights](https://arxiv.org/abs/1801.10508)
	* [Interference Mitigation Methods for Unmanned Aerial Vehicles Served by Cellular Networks](https://arxiv.org/abs/1802.00223)
* **7/08/2019** 
	* [Social network representation basics](https://pdfs.semanticscholar.org/75a9/829c84e345595bc6d50322abb823fd8831eb.pdf)
	* [Types of Centrality in a graph](https://www.hackerearth.com/practice/notes/network-analysis-and-centrality-measures-part-i/)
* **9/08/2019**
	* Brief about Antenna Gain, Apperture Efficiency, Antenna Efficiency and Gain from [tutorials point](https://www.tutorialspoint.com/antenna_theory/antenna_theory_parameters.htm)
	* Brief on Impedence Matching and Voltage Standing Wave allocateBW();Ratio from [tutorials point](https://www.tutorialspoint.com/antenna_theory/antenna_theory_basic_parameters.htm)


## Doubt Section 
* **2/08/2019**
	* How does a femtocell connects to the main cellular network? How is it different from connecting a WiFi router to the internet?
* **3/08/2019** 
	* Are dBm and dBi actually different?
	* What is a dipole in an antenna?
	* Better understanding for antenna gain...
	* How antenna gain reduces interference?
* **5/08/2019** 
	* Need some more intuition on Sound to Noise Ratio.
* **9/08/2019** 
	* Need some intuition on the characterstics of near field and far feild on how they differ in wave propogation mechanism. 
	* What does it mean when we say "there is 50% percent resource utilisation in a network"
	* What is Interference over Thermal Noise(IoT). 
* **10/08/2019**
	* How exactly transferring voice different from transferring data in a cellular network given the fact that both of them are just z0s and 1s. 


## Random 
* **8/08/2019**
	* [Wiki](https://en.wikipedia.org/wiki/Near-me_area_network) of Near-me area Network(NAN). Explains the benefits of NAN over LAN
