from simulare_test.entities.entities import Helicopter
class Repository(object):
    def __init__(self, filename):
        self.__filename = filename
        self.__lista_elicoptere = self.__loadfile()

    def get_all(self):
        return self.__lista_elicoptere

    def __str__(self):
        rezultat = ""
        for helicopter in self.__lista_elicoptere:
            rezultat += f"Id: {helicopter.get_id()} Nume: {helicopter.get_nume()} Scopuri: {helicopter.get_scopuri()} an: {helicopter.get_an()}\n"
        return rezultat

    def __loadfile(self):
        try:
            file = open(self.__filename, "r")
        except IOError:
            raise IOError

        lista_elicoptere = []
        lines = file.readlines()
        for line in lines:
            line = line.strip()
            line = line.split(",")
            id = line[0]
            nume = line[1]
            scopuri = line[2]
            scopuri = self.create_scopuri(scopuri)
            an = line[3]
            helicopter = self.create_helicopter(id, nume, scopuri, an)
            lista_elicoptere = self.add_helicopter(lista_elicoptere, helicopter)

        file.close()
        return lista_elicoptere



    def create_helicopter(self, id, nume, scopuri, an):
        """
        Functia creeaza un elicopter
        :param id:
        :param nume:
        :param scopuri:
        :param an:
        :return:
        """
        helicopter = Helicopter(id, nume, scopuri, an)
        return helicopter

    def add_helicopter(self, lista_elicoptere, helicopter):
        """
        Functia adauga un elicopter in lista.
        :param lista_elicoptere:
        :param helicopter:
        :return:
        """
        lista_elicoptere.append(helicopter)
        return lista_elicoptere

    def create_scopuri(self, scopuri):
        """
        Transforma stringul scopuri in lista de scop
        :param scopuri:
        :return:
        """
        lista_scopuri = []
        scopuri = scopuri.split(" ")
        for scop in scopuri:
            lista_scopuri.append(scop)
        return lista_scopuri
