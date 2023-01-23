class Services(object):

    def __init__(self, repo_concurenti, repo_participari):
        self.__repo_concurenti = repo_concurenti
        self.__repo_participari = repo_participari

    def cerinta_1(self, an):
        """
        Functia rezolva cerinta 1 si returneaza fiecare concurent care corespunde cerintei.
        :param an:
        :return:
        """
        rez = ""
        lista_concurenti = self.__repo_concurenti.get_all()
        for concurent in lista_concurenti:
            if concurent.get_an() >= an:
                rez += str(concurent) + '\n'
        if not rez:
            rez = "Nu exista concurenti de afisat. :("
        return rez

    def cerinta_2(self):

        """
        Functia rezolva cerinta 2 si returneaza clasamentul tarilor in functie de punctaj.
        :return:
        """
        lista_concurenti = self.__repo_concurenti.get_all()
        lista_concurenti.sort(key=lambda x: x.get_tara())
        tara = ""
        punctaj = 0
        lista_rez = []
        for concurent in lista_concurenti:
            if tara == "":
                tara = concurent.get_tara()
            if tara == concurent.get_tara():
                punctaj += self.__repo_participari.cauta_punctaj_dupa_id(concurent.get_id())
            else:
                lista_rez.append([tara, punctaj])
                tara = concurent.get_tara()
                punctaj = self.__repo_participari.cauta_punctaj_dupa_id(concurent.get_id())
        lista_rez.append([tara, punctaj])
        return self.sortare_lista_rez(lista_rez)

    def sortare_lista_rez(self, lista_rez):
        """
        Functia sorteaza lista cu clasamentul descrescator.
        :param lista_rez:
        :return:
        """
        lista_rez.sort(key=lambda x: x[1], reverse=True)
        return lista_rez