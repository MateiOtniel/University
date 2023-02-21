from simulare.repository.repository import RepositoryCarti, RepositoryImprumut
from simulare.services.services import Service
from simulare.test.test import Tests
from ui.ui import Console
if __name__ == '__main__':
    repo_carte = RepositoryCarti("datas/carti.txt")
    repo_imprumut = RepositoryImprumut("datas/imprumuturi.txt")

    service = Service(repo_carte, repo_imprumut)

    test = Tests()
    test.run_teste()

    ui = Console(service)
    ui.run()