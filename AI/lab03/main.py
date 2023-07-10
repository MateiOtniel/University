import networkx as nx
import matplotlib.pyplot as plt
from GA import GA
from Graph import Graph
import sys
import numpy as np

from FitnessFunctions import modularity, average_degree, coverage_fitness, fitness


def plotGraph(G, communities):
	np.random.seed(1)  # to freeze the graph's view (networks uses a random view)
	pos = nx.spring_layout(G)  # compute graph layout
	plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
	nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu,
	                       node_color=communities)
	nx.draw_networkx_edges(G, pos, alpha=0.3)
	plt.show()


def getCommunities(comms):
	communities = {}
	for i in range(len(comms)):
		if str(comms[i]) in communities:
			communities[str(comms[i])].append(i + 1)
		else:
			communities[str(comms[i])] = [i + 1]
	return communities


if __name__ == "__main__":
	file_path = './data/dolphins.gml'
	graph = Graph(file_path)
	#print(graph.getParams())
	
	# initialise de GA parameters
	gaParam = {'popSize': 10, 'noGen': 300}
	# problem parameters
	problParam = {'N': graph.getParams()['noNodes'] + min(graph.nx_graph.nodes()),
	              'function': lambda x: fitness(x, graph.getParams())}
	
	# store the best/average solution of each iteration (for a final plot used to anlyse the GA's convergence)
	allBestFitnesses = []
	allAvgFitnesses = []
	generations = []
	
	ga = GA(gaParam, problParam)
	ga.initialisation()
	ga.evaluation()
	
	for g in range(gaParam['noGen']):
		generations.append(g)
		# logic alg
		ga.oneGeneration()
		
		bestChromo = ga.bestChromosome()
		# print('Best solution in generation ' + str(g) + ' is: x = ' + str(
		# 	bestChromo.repres) + ' f(x) = ' + str(
		# 	bestChromo.fitness))
	
	#plotGraph(graph.nx_graph, ga.bestChromosome().repres[min(graph.nx_graph):])
	
	communities = getCommunities(bestChromo.repres)
	print(len(communities))
	print(communities)
