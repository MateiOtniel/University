// Matricea de numere sau imagini
var matrice = [1, 1, 2, 2, 3, 3, 4, 4];

// Amestecam elementele matricei
matrice.sort(function () {
    return 0.5 - Math.random();
});

// Variabile globale pentru a memora elementele selectate
var primaSelectie = null;
var aDouaSelectie = null;

// Variabila pentru a numara cate perechi au fost ghicite
var perechiGhicite = 0;

// Functia care verifica daca cele doua elemente selectate contin aceleasi numere
function verificarePereche() {
    var id1 = primaSelectie.id;
    var id2 = aDouaSelectie.id;

    if (matrice[id1] === matrice[id2]) {
        // Daca cele doua elemente selectate contin aceleasi numere,
        // le afisam permanent si le marcam ca fiind deja ghicite
        primaSelectie.removeEventListener("click", selectie);
        aDouaSelectie.removeEventListener("click", selectie);
        primaSelectie.style.backgroundColor = "green";
        aDouaSelectie.style.backgroundColor = "green";

        primaSelectie = null;
        aDouaSelectie = null;

        perechiGhicite++;

        if (perechiGhicite === matrice.length / 2) {
            alert("Ai castigat!");
        }
    } else {
        // Daca cele doua elemente selectate nu contin aceleasi numere, le ascundem dupa un interval de 2 secunde
        setTimeout(function () {
            primaSelectie.style.backgroundColor = "#CCC";
            aDouaSelectie.style.backgroundColor = "#CCC";
            primaSelectie.textContent = "";
            aDouaSelectie.textContent = "";

            primaSelectie = null;
            aDouaSelectie = null;
        }, 1000);
    }
}

// Functia care se apeleaza cand utilizatorul da click pe un element din matrice
function selectie() {
    // Daca utilizatorul a selectat deja doua elemente, nu se intampla nimic
    if (primaSelectie && aDouaSelectie) {
        return;
    }

    // Daca utilizatorul a selectat un element deja selectat, nu se intampla nimic
    if (this === primaSelectie) {
        return;
    }

    // Afisam numarul selectat
    this.textContent = matrice[this.id];

    // Daca este prima selectie, memoram elementul selectat
    if (!primaSelectie) {
        primaSelectie = this;
        return;
    }

    // Daca este a doua selectie, memoram elementul selectat si verificam daca cele doua elemente contin aceleasi numere
    aDouaSelectie = this;
    verificarePereche();
}

// Adaugam evenimentul de click pentru fiecare element din matrice
var elemente = document.getElementsByTagName("td");
for (var i = 0; i < elemente.length; i++) {
    elemente[i].addEventListener("click", selectie);
}