class Validator(object):

    def valideaza_player(self, prenume:str, nume:str, post:str, inaltime:str):
        """
        Functia valideaza in parte fiecare componenta din obiectul player si ridica o eroare pt. fiecare componenta necorespunzatoare.
        :param prenume:
        :param nume:
        :param post:
        :param inaltime:
        :return:
        """
        err = ""
        if not prenume:
            err += "prenume invalid\n"
        if not nume:
            err += "nume invalid\n"
        if post != "fundas" and post != "pivot" and post != "extrema":
            err += "post nu poate sa fie doar: fundas, pivot, extrema\n"
        try:
            inaltime = int(inaltime)
            if inaltime < 0:
                raise ValueError
        except ValueError:
            err += "inaltime invalida\n"
        if err:
            raise AttributeError(str(err))

    def valideaza_date(self, prenume, nume, inaltime):
        """
        Functia valideaza datele necesare pt. modificarea playerului si ridica o erpare pt. fiecare componenta necorespunzatoare.
        :param prenume:
        :param nume:
        :param inaltime:
        :return:
        """
        err = ""
        if not prenume:
            err += "prenume invalid\n"
        if not nume:
            err += "nume invalid\n"
        try:
            inaltime = int(inaltime)
            if inaltime < 0:
                raise ValueError
        except ValueError:
            err += "inaltime invalida\n"
        if err:
            raise AttributeError(str(err))
