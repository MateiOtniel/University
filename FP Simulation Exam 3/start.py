from infrastructure.services import Services
from repository.repository import RepoConcurenti, RepoParticipari
from tests.tests import Tests
from ui.console import Console
from validator.validator import Validatori
if __name__ == '__main__':
    repo_concurenti = RepoConcurenti("datas/concurenti.txt")
    repo_participari = RepoParticipari("datas/participari.txt")
    valid = Validatori()

    service = Services(repo_concurenti, repo_participari)

    test = Tests()
    test.run_tests()

    ui = Console(valid, service)
    ui.run()