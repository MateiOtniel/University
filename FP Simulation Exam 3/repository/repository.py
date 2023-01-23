from entities.entities import Concurenti, Participari
class RepoConcurenti(object):
    def __init__(self, filename):
        self.__filename = filename
        self.__lista_concurenti = self.__loadfile()

    def __loadfile(self):
        """
        Creeaza lista concurentilor din fisierul txt.
        :return:
        """
        f = 0
        try:
            f = open(self.__filename, "r")
        except IOError:
            print("Nu s-a putut deschide fisierul!")

        lista_concurenti = []
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            line = line.split(",")
            concurent = Concurenti(line[0], line[1], line[2], line[3])
            lista_concurenti.append(concurent)
        f.close()
        return lista_concurenti

    def get_all(self):
        return self.__lista_concurenti

class RepoParticipari(object):
    def __init__(self, filename):
        self.__filename = filename
        self.__lista_participari = self.__loadfile()

    def __loadfile(self):
        """
        Creeaza lista participarilor din fisierul txt.
        :return:
        """
        f = 0
        try:
            f = open(self.__filename, "r")
        except IOError:
            print("Nu s-a putut deschide fisierul!")

        lista_participari= []
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            line = line.split(",")
            participare = Participari(line[0], line[1], line[2])
            lista_participari.append(participare)
        f.close()
        return lista_participari

    def cauta_punctaj_dupa_id(self, id):
        """
        Functia cauta punctajul unui concurent din lista de participari.
        :param id:
        :return:
        """
        suma = 0
        for participare in self.__lista_participari:
            if id == participare.get_id():
                suma += participare.get_punctaj()
        return suma

    def get_all(self):
        return self.__lista_participari