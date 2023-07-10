import networkx as nx
import scipy as sp
import tsplib95 as tsp

class Graph:
	def __init__(self, file_path):
		# --------------------------------------
		# self._nx_graph = nx.Graph()
		# with open(file_path, "r") as file:
		# 	size = int(file.readline().removesuffix('\n'))
		# 	for count in range(size):
		# 		self._nx_graph.add_node(count)
		# 	for row in range(size - 1):
		# 		line = file.readline().split(' ')
		# 		for column in range(size - 1):
		# 			if column == row:
		# 				continue
		# 			self._nx_graph.add_edge(row, column, weight=int(line[column]))
		# 		self._nx_graph.add_edge(row, size - 1,
		# 		                     weight=int(line[size - 1].removesuffix('\n')))
		# --------------------------------------
		self._nx_graph = nx.Graph()
		with open(file_path, "r") as file:
			for i in range(100000):
				node, longitude, latitude = file.readline().strip().split()
				self._nx_graph.add_node(int(node) - 1, x=int(longitude), y=int(latitude))
	
	def getParams(self):
		return {'nx_graph': self._nx_graph,
		        'noNodes': self._nx_graph.number_of_nodes(),
		        # 'mat': nx.adjacency_matrix(self._nx_graph).toarray().tolist(),
		        'degrees': [j for i, j in
		                    sorted(list(self._nx_graph.degree()), key=lambda x: x[0])],
		        'noEdges': self._nx_graph.number_of_edges(),
		        'edges': list(self._nx_graph.edges())}
	
	@property
	def nx_graph(self):
		return self._nx_graph
