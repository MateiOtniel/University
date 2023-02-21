class Validatori(object):
    def valideaza_an(self, an):
        """
        Functia valideaza daca anul este corect.
        :param an:
        :return:
        """
        try:
            an = int(an)
        except ValueError:
            raise ValueError