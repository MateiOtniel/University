class Services(object):

    def __init__(self, repo):
        self.__repo = repo

    def cerinta_1(self, scop):
        """
        Functia cauta fiecare elicopter dupa scop, iar daca indeplineste cerinta, se va adauga in lista rezultat.
        :param scop:
        :return:
        """
        lista_rez = []
        for helicopter in self.__repo.get_all():
            lista_scopuri = helicopter.get_scopuri()
            for scopcurent in lista_scopuri:
                if scopcurent == scop:
                    lista_rez.append(helicopter)
        lista_rez =  self.__sorted_list_by_name(lista_rez)
        return lista_rez

    def cerinta_2(self):
        """Functia afiseaza anii fiecarui elicopter dupa scopul sau."""
        lista_elicoptere = self.__repo
        lista_scopuri = ["agricultura", "livrare", "medical", "militar", "vacanta"]
        rez_final = ""
        for scopcurent in lista_scopuri:
            rez = ""
            for helicopter in lista_elicoptere.get_all():
                for scophelicopter in helicopter.get_scopuri():
                    if scopcurent == scophelicopter:
                        if rez:
                            rez += ", "
                        rez += f"{helicopter.get_an()}"
            rez_final += f"{scopcurent}: {rez}\n"
        return rez_final

    def __sorted_list_by_name(self, lista_rez):
        """
        Functia sorteaza litsa rezultat descrescator dupa nume.
        :param lista_rez:
        :return:
        """
        for ind1 in range(len(lista_rez) - 1):
            for ind2 in range(ind1, len(lista_rez)):
                helicopter1 = lista_rez[ind1]
                helicopter2 = lista_rez[ind2]
                if helicopter1.get_nume() > helicopter2.get_nume():
                    lista_rez[ind1], lista_rez[ind2] = lista_rez[ind2], lista_rez[ind1]
        return lista_rez

    def __sorted_litsa_by_scop(self, lista_rez):
        """for ind1 in range(len(lista_rez) - 1):
            for ind2 in range(ind1, len(lista_rez)):
                helicopter1 = lista_rez[ind1]
                helicopter2 = lista_rez[ind2]
                if helicopter1.get_scop() > helicopter2.get_scop():
                    lista_rez[ind1], lista_rez[ind2] = lista_rez[ind2], lista_rez[ind1]
        """
        return lista_rez.sort(key = lambda x: x.get_scop(), reversed = True)
