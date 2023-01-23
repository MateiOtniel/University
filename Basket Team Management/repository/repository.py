from basket_coach.entities.entities import Player


class Repository(object):

    def __init__(self, filename):
        self._filename = filename
        self._repo = self._load_from_file()

    def get_all(self):
        return self._repo

    def _load_from_file(self):
        """
        Functia incarca lista cu fiecare data din fisier.
        :return:
        """
        with open(self._filename, "r") as f:
            lines = f.readlines()
            lista_playeri = []
            for line in lines:
                prenume, nume, post, inaltime = [token.strip() for token in line.split(";")]
                player = Player(prenume, nume, post, inaltime)
                lista_playeri.append(player)
            f.close()
            return lista_playeri

    def _save_to_file(self, lista_playeri):
        """
        Functia salveaza in fisier orice modificare a listei.
        :param lista_playeri:
        :return:
        """
        with open(self._filename, "w") as f:
            for player in lista_playeri:
                player_string = f"{player.get_prenume()};{player.get_nume()};{player.get_post()};{str(player.get_inaltime())}\n"
                f.write(player_string)

    def add_player(self, player):
        """
        Functia adauga playerul creat.
        :param player:
        :return:
        """
        lista_playeri = self._repo
        lista_playeri.append(player)
        self._save_to_file(lista_playeri)
        print("Jucator adaugat cu succes!")

    def modifica_player(self, prenume, nume, inaltime):
        """
        Functia modifica playerul cu prenumele si numele dat de la tastatura.
        :param prenume:
        :param nume:
        :param inaltime:
        :return:
        """
        gasit = 0
        lista_playeri = self._repo
        for player in lista_playeri:
            if player.get_nume() == nume and player.get_prenume() == prenume:
                player.set_inaltime(inaltime)
                gasit = 1
                print("Player modificat cu succes!")
                break
        if not gasit:
            print("Playerul nu a fost gasit.")
        self._save_to_file(lista_playeri)

    def verificare_in_lista(self, player):
        """
        Functia verifica daca playerul este unul nou.
        :param player:
        :return:
        """
        lista_playeri = self._repo
        for playercurent in lista_playeri:
            if playercurent.get_nume() == player.get_nume() and player.get_prenume() == playercurent.get_prenume():
                return 0
        return 1
