from simulare.entities.entities import Carti, Imprumut
class RepositoryCarti(object):
    def __init__(self, filename):
        self._filename = filename
        self.__lista_carti = self.__loadfile()

    def __loadfile(self):
        """
        Functia ia toate datele din fisier si le adauga in lista.
        :return:
        """
        file = 0
        try:
            file = open(self._filename, "r")
        except IOError:
            raise IOError

        lista_carti = []
        lines = file.readlines()
        for line in lines:
            line = line.strip()
            line = line.split(",")
            carte = Carti(line[0], line [1], line[2], line[3])
            lista_carti.append(carte)
        file.close()
        return lista_carti

    def get_all(self):
        return self.__lista_carti

class RepositoryImprumut(object):
    def __init__(self, filename):
        self._filename = filename
        self.__lista_imprumut = self.__loadfile()

    def __loadfile(self):
        """
        Functia ia toate datele din fisier si le adauga in lista.
        :return:
        """
        file = 0
        try:
            file = open(self._filename, "r")
        except IOError:
            raise IOError

        lista_imprumuturi = []
        lines = file.readlines()
        for line in lines:
            line = line.strip()
            line = line.split(",")
            imprumut = Imprumut(line[0], line [1], line[2], line[3])
            lista_imprumuturi.append(imprumut)
        file.close()
        return lista_imprumuturi

    def get_all(self):
        return self.__lista_imprumut