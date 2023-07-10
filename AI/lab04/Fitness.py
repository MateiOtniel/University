import math


def fitness2(graph, repres):
    cost = 0
    for i in range(len(repres)-1):
        cost+=graph.get_edge_data(repres[i],repres[i+1])['weight']
    cost += graph.get_edge_data(repres[len(repres) - 1], repres[0])['weight']
    return cost

def fitness(graph, repres):
    cost = 0
    for i in range(len(repres)-2):
        node1 = graph.nodes[repres[i]]
        node2 = graph.nodes[repres[i+1]]
        cost += math.sqrt((node1['x'] - node2['x']) ** 2 + (node1['y'] - node2['y'])
                          ** 2)
    return cost
