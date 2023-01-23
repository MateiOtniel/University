class Player(object):

    def __init__(self, prenume, nume, post, inaltime):
        self._prenume = prenume
        self._nume = nume
        self._post = post
        self._inaltime = inaltime

    def get_prenume(self):
        return self._prenume

    def get_nume(self):
        return self._nume

    def get_post(self):
        return self._post

    def get_inaltime(self):
        return int(self._inaltime)

    def set_inaltime(self, value):
        self._inaltime = value

    def __str__(self):
        return f"{self.get_prenume()} {self.get_nume()} {self.get_post()} {self.get_inaltime()}"