import networkx as nx
import scipy as sp

class Graph:
	def __init__(self, gml_file_path):
		self._nx_graph = nx.read_gml(gml_file_path, label='id')
	
	def getParams(self):
		return {'nx_graph': self._nx_graph,
		        'noNodes': self._nx_graph.number_of_nodes(),
		        'mat': nx.adjacency_matrix(self._nx_graph).toarray().tolist(),
		        'degrees': [j for i, j in
		                    sorted(list(self._nx_graph.degree()), key=lambda x: x[0])],
		        'noEdges': self._nx_graph.number_of_edges(),
		        'edges': list(self._nx_graph.edges())}
	
	@property
	def nx_graph(self):
		return self._nx_graph
