import unittest
from simulare_test.validation.validation import Validation
from simulare_test.repository.repository import Repository
from simulare_test.infrasctructure.services import Services
class Tests(unittest.TestCase):


    def run_tests(self):
        print("Am inceput testele.")
        self.__test_create_helicopter()
        self.__test_verifica_scop()
        self.__test_cerinta_1()
        self.__test_cerinta_2()
        print("Am terminat testele.")

    def __test_create_helicopter(self):
        """
        Functia testeaza daca elicopterul este creat cu succes.
        :return:
        """
        repo = Repository("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Probleme Pycharm\\simulare_test\\datas\datas")
        id = 15
        nume = "Ax99"
        scopuri = "vacanta militar"
        an = 1999
        helicopter = repo.create_helicopter(id, nume, scopuri, an)
        self.assertTrue(helicopter.get_id() == id)
        self.assertTrue(helicopter.get_nume() == nume)
        self.assertTrue(helicopter.get_scopuri() == scopuri)
        self.assertTrue(helicopter.get_an() == an)
        self.__test_add_helicopter(helicopter)

    def __test_add_helicopter(self, helicopter):
        """
        Functia testeaza daca elicopterul este adaugat in lista cu succes.
        :param helicopter:
        :return:
        """
        repo = Repository("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Probleme Pycharm\\simulare_test\\datas\datas")
        lista_helicopter = []
        lista_helicopter = repo.add_helicopter(lista_helicopter, helicopter)
        self.assertTrue(len(lista_helicopter) == 1)

    def __test_verifica_scop(self):
        """
        Functia verifica daca scopul este unul valid.
        :return:
        """
        validator = Validation()
        self.assertTrue(validator.verifica_scop("agricultura") == 1)
        self.assertRaises(ValueError, lambda: validator.verifica_scop(""))
        self.assertRaises(ValueError, lambda: validator.verifica_scop("asdasd"))
        self.assertRaises(ValueError, lambda: validator.verifica_scop("agricult"))

    def __test_cerinta_1(self):
        """
        Functia testeaza cerinta 1.
        :return:
        """
        repo = Repository("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Probleme Pycharm\\simulare_test\\datas\datas")
        services = Services(repo)
        self.assertTrue(len(services.cerinta_1("agricultura")) == 2)
        self.assertTrue(len(services.cerinta_1("livrare")) == 2)

    def __test_cerinta_2(self):
        """
        Functia testeaza cerinta 2.
        :return:
        """
        repo = Repository("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Probleme Pycharm\\simulare_test\\datas\datas")
        services = Services(repo)
        self.assertTrue(services.cerinta_2() == f"agricultura: 2014, 2020\nlivrare: 1998, 2014\nmedical: 2014, 1999, 2011\nmilitar: 1999, 1998, 2011\nvacanta: 2014, 2012\n")
