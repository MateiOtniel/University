from simulare.services.services import Service
from simulare.repository.repository import RepositoryCarti, RepositoryImprumut
import unittest
class Tests(unittest.TestCase):

    def run_teste(self):
        self.__test_cerinta_1()
        self.__test_cerinta_2()
        self.__test_verifica_carte_sir_caractere()

    def __test_cerinta_1(self):
        """
        Testeaza cerinta 1 cu diferite cazuri care ar putea fi date de la tastatura.
        :return:
        """
        repo_carte = RepositoryCarti("datas/cartitest.txt")
        repo_imprumut = RepositoryImprumut("datas/imprumuturitest.txt")
        service = Service(repo_carte, repo_imprumut)
        self.assertTrue(service.cerinta_1("neu") == "1 Zori la Semineu Petre Ispirescu 1990\n")
        self.assertTrue(service.cerinta_1("u") == "7 Deu Petre Ispirescu 1803\n1 Zori la Semineu Petre Ispirescu 1990\n2 Catelul meu Claudiu Costea 2021\n")
        self.assertTrue(service.cerinta_1("asd") == "Nu exista carti care sa se termine cu acest sir de caractere.")

    def __test_cerinta_2(self):
        """
        Testeaza cerinta 2 cu diferite cazuri care ar putea fi date de la tastatura.
        :return:
        """
        repo_carte = RepositoryCarti("datas/cartitest.txt")
        repo_imprumut = RepositoryImprumut("datas/imprumuturitest.txt")
        service = Service(repo_carte, repo_imprumut)
        self.assertTrue(service.cerinta_2(13) == "1 1 11.12.2021 13\n8 2 10.10.2020 13\n9 2 10.10.2020 13\n")
        self.assertTrue(service.cerinta_2(2) == "Nu exista imprumuturi de asa durata.")
        self.assertTrue(service.cerinta_2(16) == "10 2 10.10.2020 16\n")

    def __test_verifica_carte_sir_caractere(self):
        """
        Testeaza functia care verifica sufixele numelui cartii si a sirului de caractere.
        :return:
        """
        repo_carte = RepositoryCarti("datas/cartitest.txt")
        repo_imprumut = RepositoryImprumut("datas/imprumuturitest.txt")
        service = Service(repo_carte, repo_imprumut)
        self.assertTrue(service.verifica_carte_sir_caractere("neu","e") == 0)
        self.assertTrue(service.verifica_carte_sir_caractere("neu", "") == 1)
        self.assertTrue(service.verifica_carte_sir_caractere("neu", "eu") == 1)