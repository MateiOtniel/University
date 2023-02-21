from basket_coach.validation.validator import Validator
from basket_coach.entities.entities import Player
from basket_coach.repository.repository import Repository
import unittest


class Tests(unittest.TestCase):
    def run_all_tests(self):
        print("Am inceput testele...")
        self.test_valideaza_player()
        self.test_valideaza_date()
        self.test_adauga_player()
        print("Am terminat testele.")

    def test_valideaza_player(self):
        """
        Testeaza validatorul pt. player.
        :return: 
        """
        validator = Validator()
        prenumeinv = ""
        numeinv = ""
        postinv = "pivo"
        inaltimeinv = "asd"
        prenume = "Matei"
        nume = "Cordis"
        post = "pivot"
        inaltime = "180"
        self.assertRaises(AttributeError, lambda: validator.valideaza_player(prenumeinv, numeinv, postinv, inaltimeinv))
        self.assertRaises(AttributeError, lambda: validator.valideaza_player(prenumeinv, nume, postinv, inaltimeinv))
        validator.valideaza_player(prenume, nume, post, inaltime)

    def test_valideaza_date(self):
        """
        Functia testeaza validatorul pt. date.
        :return: 
        """
        validator = Validator()
        prenumeinv = ""
        numeinv = ""
        postinv = "pivo"
        inaltimeinv = "asd"
        prenume = "Matei"
        nume = "Cordis"
        post = "pivot"
        inaltime = "180"
        self.assertRaises(AttributeError, lambda: validator.valideaza_date(prenumeinv, numeinv, inaltimeinv))
        validator.valideaza_date(prenume, nume, inaltime)

    def test_adauga_player(self):
        """
        Functia testeaza adaugarea playerilor.
        :return:
        """
        repo = Repository("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Probleme Pycharm\\basket_coach\\datas\\playerstest")
        player = Player("da", "da", "extrema", "180")
        repo.add_player(player)
        self.assertTrue(len(repo.get_all()), 11)
