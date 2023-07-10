def modularity(communities, param):
    noNodes = param['noNodes']
    mat = param['mat']
    degrees = param['degrees']
    noEdges = param['noEdges']
    M = 2 * noEdges
    Q = 0.0
    for i in range(0, noNodes):
        for j in range(0, noNodes):
            if (communities[i] == communities[j]):
               Q += (mat[i][j] - degrees[i] * degrees[j] / M)
    return Q * 1 / M

def average_degree(communities, param):
    def average_degree_helper(communities, param, community_index):
        ms = sum([communities[edge[0]] == communities[edge[1]] and communities[edge[0]] == community_index for edge in
                  param['edges']])
        ns = sum([i == community_index for i in communities])
        return 2 * ms / ns

    return sum([average_degree_helper(communities, param, index) for index in set(communities)]) / len(set(communities))

def coverage_fitness(communities, param):
    total_edges = len(param['edges'])
    intra_community_edges = 0

    for community in communities:
        subgraph = param['nx_graph'].subgraph(community)
        intra_community_edges += len(subgraph.edges)

    return intra_community_edges / total_edges

def fitness(communties, param):
    return min(modularity(communties, param), coverage_fitness(communties, param),
               average_degree(communties, param))
