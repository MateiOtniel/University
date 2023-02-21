from basket_coach.entities.entities import Player
import random


class Service:

    def __init__(self, repository):
        self.repo = repository

    def creaza_player(self, prenume, nume, post, inaltime):
        """
        Functia creeaza playerul si il trimite in repo pt. a fi adaugat.
        :param prenume:
        :param nume:
        :param post:
        :param inaltime:
        :return:
        """
        player = Player(prenume, nume, post, inaltime)
        self.repo.add_player(player)

    def modifica_player(self, prenume, nume, inaltime):
        """
        Functia trimite datele de modificare spre repo pt. a putea fi modificata inaltimea playerului.
        :param prenume:
        :param nume:
        :param inaltime:
        :return:
        """
        self.repo.modifica_player(prenume, nume, inaltime)

    def filtrare(self):
        """
        Functia filtreaza din toti jucatorii pe aceia care indeplinesc conditiile din cerinta.
        :return:
        """
        max_fundas1 = 0
        max_fundas2 = 0
        max_pivot = 0
        max_extrema1 = 0
        max_extrema2 = 0
        lista_playeri = self.repo.get_all()
        """
        Prima data se afla inaltimile maxime ale jucatorilor din fiecare categorie.
        """
        for player in lista_playeri:
            if player.get_post() == "fundas":
                if player.get_inaltime() > max_fundas1:
                    max_fundas2, max_fundas1 = max_fundas1, player.get_inaltime()
                elif player.get_inaltime() > max_fundas2:
                    max_fundas2 = player.get_inaltime()
            elif player.get_post() == "extrema":
                if player.get_inaltime() > max_extrema1:
                    max_extrema2, max_extrema1 = max_extrema1, player.get_inaltime()
                elif player.get_inaltime() > max_extrema2:
                    max_extrema2 = player.get_inaltime()
            elif player.get_post() == "pivot":
                if player.get_inaltime() > max_pivot:
                    max_pivot = player.get_inaltime()
        return self.creaza_rezultat(lista_playeri, max_fundas1, max_fundas2, max_pivot, max_extrema1, max_extrema2)

    def creaza_rezultat(self, lista_playeri, max_fundas1, max_fundas2, max_pivot, max_extrema1, max_extrema2):
        """
        Stiind toate inaltimile maxime gasite anterior in filtrare, acuma doar se cauta jucatorii (folosindu-ne de valorile maxime)
        care au inaltimile si postul corespunzator cerintei.
        :param lista_playeri:
        :param max_fundas1:
        :param max_fundas2:
        :param max_pivot:
        :param max_extrema1:
        :param max_extrema2:
        :return:
        """
        rez = ""
        nrf = 0
        nrp = 0
        nre = 0
        for player in lista_playeri:
            if player.get_post() == "fundas":
                if player.get_inaltime() == max_fundas1 or player.get_inaltime() == max_fundas2 and nrf <= 1:
                    rez += f"{str(player)}\n"
                    nrf += 1
            elif player.get_post() == "extrema":
                if player.get_inaltime() == max_extrema1 or player.get_inaltime() == max_extrema2 and nre <= 1:
                    rez += f"{str(player)}\n"
                    nre += 1
            elif player.get_post() == "pivot":
                if player.get_inaltime() == max_pivot and nrp <= 0:
                    rez += f"{str(player)}\n"
                    nrp += 1
        if nrp != 1 or nre != 2 or nrf != 2:
            rez = "Nu exista echipa valida."
        return rez

    def adauga_playeri_fisier(self, fisier):
        nr = 0
        with open(fisier, "r") as f:
            lista_playeri = self.repo.get_all()
            lines = f.readlines()
        for line in lines:
            nume, prenume = [token.strip() for token in line.split(";")]
            inaltime = random.randint(130, 200)
            post = random.randint(1, 3)
            if post == 1:
                post = "pivot"
            elif post == 2:
                post = "extrema"
            else:
                post = "fundas"
            player = Player(prenume, nume, post, inaltime)
            if self.repo.verificare_in_lista(player):
                lista_playeri.append(player)
                nr += 1
        self.repo._save_to_file(lista_playeri)
        f.close()
        print(nr)
