from random import randint


class RealChromosome:
	def __init__(self, problParam):
		self.__problParam = problParam
		self.__fitness = 0.0
		n = self.__problParam['N']
		self.__repres = [randint(0, n - 1) for _ in range(n)]
	
	@property
	def repres(self):
		return self.__repres
	
	@property
	def fitness(self):
		return self.__fitness
	
	@repres.setter
	def repres(self, l=[]):
		self.__repres = l
	
	@fitness.setter
	def fitness(self, fit=0.0):
		self.__fitness = fit
	
	def crossover(self, c):
		r = randint(0, len(self.__repres) - 1)
		newrepres = []
		for i in range(r):
			newrepres.append(self.__repres[i])
		for i in range(r, len(self.__repres)):
			newrepres.append(c.__repres[i])
		offspring = RealChromosome(c.__problParam)
		offspring.repres = newrepres
		return offspring
	
	def mutation(self):
		N = self.__problParam['N']
		pos = randint(0, N - 1)
		self.__repres[pos] = self.__repres[randint(0, N - 1)]
	
	def __str__(self):
		return '\nChromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)
	
	def __repr__(self):
		return self.__str__()
	
	def __eq__(self, c):
		return self.__repres == c.__repres and self.__fitness == c.__fitness
