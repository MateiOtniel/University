class Concurenti(object):

    def __init__(self, id, nume, tara, data_nasterii):
        self.__id = id
        self.__nume = nume
        self.__tara = tara
        self.__data_nasterii = data_nasterii

    def get_id(self):
        return int(self.__id)

    def get_nume(self):
        return self.__nume

    def get_tara(self):
        return self.__tara

    def get_an(self):
        data = self.__data_nasterii.strip()
        data = data.split(".")
        return int(data[2])

    def __str__(self):
        return f"Id: {self.get_id()}, Nume: {self.get_nume()}, Tara: {self.get_tara()}, Data nasterii: {self.__data_nasterii}"

class Participari(object):

    def __init__(self, cod_participare, id, punctaj):
        self.__cod_participare = cod_participare
        self.__id = id
        self.__punctaj = punctaj

    def get_cod_participare(self):
        return self.__cod_participare

    def get_id(self):
        return int(self.__id)

    def get_punctaj(self):
        return int(self.__punctaj)