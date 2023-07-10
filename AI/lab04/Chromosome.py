from random import randint


class Chromosome:
	def __init__(self, problParam):
		self.__problParam = problParam
		self.__fitness = 0.0
		n = self.__problParam['N']
		self.__repres = self.getRandomPermutation(n)
	
	def getRandomPermutation(self, n):
		perm = [i for i in range(n)]
		pos1 = randint(0, n - 1)
		pos2 = randint(0, n - 1)
		perm[pos1], perm[pos2] = perm[pos2], perm[pos1]
		return perm
	
	def gerPermutationOfThreeElements(self, n):
		perm = [i for i in range(n)]
		pos1 = randint(0, n - 1)
		pos2 = randint(0, n - 1)
		perm[pos1], perm[pos2] = perm[pos2], perm[pos1]
		return perm[:3]
	
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
		offSpring= []
		pos = randint(0, len(self.__repres) // 2)
		length = randint(1, len(self.__repres) // 2)
		for i in range(0, pos):
			offSpring.append(0)
		for i in range(0, length):
			if pos >= len(self.__repres):
				pos = 0
			offSpring.append(self.__repres[pos])
			pos += 1
		
		for i in range(pos, len(self.__repres)):
			if c.__repres[i] not in offSpring:
				offSpring.append(c.__repres[i])
				pos += 1
		
		for i in range(0, len(c.__repres)):
			if pos >= len(c.__repres) - 1 or len(offSpring) < len(
					c.__repres):
				pos = 0
			if c.__repres[i] not in offSpring and len(
					offSpring) < len(c.__repres):
				offSpring.append(c.__repres[i])
			elif c.__repres[i] not in offSpring:
				offSpring[pos] = c.__repres[i]
				pos += 1
		
		newChromosome = Chromosome(c.__problParam)
		newChromosome.representation = offSpring
		return newChromosome
	
	def crossover2(self, c):
		newChromosome = self
		r = randint(1, len(self.__repres) - 2)
		for i in range(r):
			newChromosome.mutation()
		return newChromosome
	
	def getCommonPositions(self, __repres, __repres1):
		list = []
		for i in range(len(__repres)):
			if i < len(__repres1):
				if __repres[i] == __repres1[i]:
					list.append(i)
		return list
	
	def mutation(self):
		n = self.__problParam['N']
		pos1 = randint(0, len(self.__repres) - 1)
		pos2 = randint(0, len(self.__repres) - 1)
		self.__repres[pos1], self.__repres[pos2] = self.__repres[pos2], self.__repres[
			pos1]
	
	def __str__(self):
		return '\nChromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)
	
	def __repr__(self):
		return self.__str__()
	
	def __eq__(self, c):
		return self.__repres == c.__repres and self.__fitness == c.__fitness
