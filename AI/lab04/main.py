import networkx as nx
import matplotlib.pyplot as plt
from GA import GA
from Graph import Graph
import sys
import numpy as np
import tsplib95 as tsp

from Fitness import fitness


def plotGraph(G):
	np.random.seed(1)  # to freeze the graph's view (networks uses a random view)
	pos = nx.spring_layout(G)  # compute graph layout
	plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
	nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu)
	nx.draw_networkx_edges(G, pos, alpha=0.3)
	plt.show()


def insertOneForEach(repres):
	bestChromoList = []
	for i in repres:
		bestChromoList.append(i + 1)
	return bestChromoList


if __name__ == "__main__":
	file_name = 'mona-lisa100k'
	input_path = 'tsp/' + file_name + '.tsp'
	output_path = 'tsp/' + file_name + '.out'
	
	graph = Graph(input_path)
	
	# initialise de GA parameters
	gaParam = {'popSize': 20, 'noGen': 20}
	# problem parameters
	problParam = {'N': graph.getParams()['noNodes'] + min(graph.nx_graph.nodes()),
	              'function': lambda x, y: fitness(x, y), 'nx_graph':
		              graph.nx_graph}
	
	# store the best/average solution of each iteration (for a final plot used to anlyse the GA's convergence)
	allBestFitnesses = []
	allAvgFitnesses = []
	generations = []
	
	ga = GA(gaParam, problParam)
	ga.initialisation()
	ga.evaluation()
	
	allBestChromosomes = []
	bestFitness = 9999999999
	
	for g in range(gaParam['noGen']):
		generations.append(g)
		ga.oneGeneration()
		bestChromo = ga.bestChromosome()
		
		bestChromoRepres = insertOneForEach(bestChromo.repres)
		
		if bestChromo.fitness == bestFitness:
			if bestChromoRepres not in allBestChromosomes:
				allBestChromosomes.append(bestChromoRepres)
		elif bestChromo.fitness < bestFitness:
			bestFitness = bestChromo.fitness
			allBestChromosomes = [bestChromoRepres]
	
	# open file to write
	f = open(output_path, "w")
	bestChromoList = []
	for i in bestChromo.repres:
		bestChromoList.append(i + 1)
	
	outputString = ""
	
	for list in allBestChromosomes:
		outputString += " ".join(str(x) for x in list) + "\n"
		
	f.write(str(len(bestChromo.repres)) + "\n" + " ".join(str(x) for x in
	                                                      bestChromoList) + "\n" +
	        str(bestChromo.fitness) + "\n" + outputString)
