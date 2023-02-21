from entities.entities import Concurenti, Participari
from repository.repository import RepoConcurenti, RepoParticipari
from infrastructure.services import Services
import unittest
class Tests(unittest.TestCase):

    def run_tests(self):
        print("Am inceput testele")
        self.__test_concurent_valid()
        self.__test_participare()
        self.__test_liste()
        self.__test_cauta_punctaj_dupa_id()
        self.__test_cerinta_1()
        self.__test_cerinta_2()
        print("Am terminat testele.")

    def __test_concurent_valid(self):
        """
        Testeaza daca se creeaza corect entitatea concurent.
        :return:
        """
        concurent = Concurenti("12", "Matei", "Romania", "12.12.2000")
        self.assertTrue(concurent.get_id() == 12)
        self.assertTrue(concurent.get_an() == 2000)
        self.assertTrue(concurent.get_nume() == "Matei")
        self.assertTrue(concurent.get_tara() == "Romania")

    def __test_participare(self):
        """
        Testeaza daca se creeaza corect entitatea participare.
        :return:
        """
        participare = Participari("13212", "1", "1200")
        self.assertTrue(participare.get_id() == 1)
        self.assertTrue(participare.get_cod_participare() == "13212")
        self.assertTrue(participare.get_punctaj() == 1200)

    def __test_liste(self):
        """
        Testeaza daca cele doua liste sunt create corect.
        :return:
        """
        repo_concurenti = RepoConcurenti("datas/concurenti.txt")
        self.assertTrue(len(repo_concurenti.get_all()) == 7)
        repo_participari = RepoParticipari("datas/participari.txt")
        self.assertTrue(len(repo_participari.get_all()) == 8)

    def __test_cauta_punctaj_dupa_id(self):
        """
        Functia testeaza daca se returneaza corect punctajul concurentului cu id-ul dat.
        :return:
        """
        repo_participari = RepoParticipari("datas/participari.txt")
        self.assertTrue(repo_participari.cauta_punctaj_dupa_id(1) == 1600)
        self.assertTrue(repo_participari.cauta_punctaj_dupa_id(7) == 200)

    def __test_cerinta_1(self):
        """
        Functia testeaza cerinta 1.
        :return:
        """
        repo_concurenti = RepoConcurenti("datas/concurenti.txt")
        repo_participari = RepoParticipari("datas/participari.txt")
        service = Services(repo_concurenti, repo_participari)
        self.assertTrue(service.cerinta_1(2001) == "Id: 1, Nume: Ionel, Tara: Romania, Data nasterii: 12.09.2002\nId: 2, Nume: Viorel, Tara: Romania, Data nasterii: 13.02.2007\nId: 3, Nume: Andrei, Tara: Romania, Data nasterii: 15.03.2015\n")

    def __test_cerinta_2(self):
        """
        Functia testeaza cerinta 2.
        :return:
        """
        repo_concurenti = RepoConcurenti("datas/concurenti.txt")
        repo_participari = RepoParticipari("datas/participari.txt")
        service = Services(repo_concurenti, repo_participari)
        self.assertFalse(service.cerinta_2() == "Finlanda: 1959\nRomania: 390\nRusia: 120\nUngaria: 10\n")