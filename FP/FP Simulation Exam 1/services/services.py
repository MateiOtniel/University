from simulare.entities.entities import Carti, Imprumut
class Service(object):
    def __init__(self, repo_carte, repo_imprumut):
        self.__repo_carte = repo_carte
        self.__repo_imprumut = repo_imprumut

    def cerinta_1(self, sir):
        """
        Functia creeaza lista rezultat pe care mai apoi o sorteaza si returneaza rezultatul.
        :param sir:
        :return:
        """
        lista_rez = []
        for carte in self.__repo_carte.get_all():
            nume = carte.get_nume()
            if self.verifica_carte_sir_caractere(nume,sir):
                lista_rez.append(carte)

        lista_rez.sort(key=lambda x: x.get_an())

        return self.__creeaza_rezultat(lista_rez)

    def cerinta_2(self, durata_imprumut):
        """
        Functia creeza rezultatul daca imprumutul respectiv are durata data de la tastatura.
        :param durata_imprumut:
        :return:
        """
        rez = ""
        for imprumut in self.__repo_imprumut.get_all():
            if imprumut.get_durata() == durata_imprumut:
                rez += str(imprumut) + '\n'
        if not rez:
            rez = "Nu exista imprumuturi de asa durata."
        return rez

    def verifica_carte_sir_caractere(self, nume, sir):
        """
        Functia verifica daca sufixele numelui cartii si a sirului dat de la tastatura sunt egale.
        Returneaza 1 daca se respecta cerinta, 0 in caz contrar.
        :param nume:
        :param sir:
        :return:
        """
        ind = 0
        ind_nume = len(nume) - 1
        ind_sir = len(sir) - 1
        while True:
            if ind > ind_sir:
                return 1
            if ind > ind_nume and ind <=ind_sir:
                return 0
            if nume[ind_nume - ind] != sir[ind_sir - ind]:
                return 0
            ind += 1

    def __creeaza_rezultat(self, lista_rez):
        """
        Functia creeaza rezultatul pt. cerinta 1. Am realizat functia pt a pastra principiul singurei responsabilitati.
        :param lista_rez:
        :return:
        """
        rez = ""
        if not lista_rez:
            rez = "Nu exista carti care sa se termine cu acest sir de caractere."
        else:
            for carte in lista_rez:
                rez += str(carte) + '\n'
        return rez