// definim variabilele necesare
const imageList = document.getElementById('image-list');
const listItems = imageList.getElementsByTagName('li');
listItems[0].style.display = 'none';
let currentIndex = 0;
let intervalId = null;

// functia care afiseaza urmatorul element
function showNext() {
    // ascundem elementul curent
    listItems[currentIndex].style.display = 'none';
    // trecem la urmatorul element
    currentIndex = (currentIndex + 1) % listItems.length;
    // afisam urmatorul element
    listItems[currentIndex].style.display = 'block';
}

// functia care afiseaza elementul anterior
function showPrevious() {
    // ascundem elementul curent
    listItems[currentIndex].style.display = 'none';
    // trecem la elementul anterior
    currentIndex = (currentIndex - 1 + listItems.length) % listItems.length;
    // afisam elementul anterior
    listItems[currentIndex].style.display = 'block';
}

// functia care porneste tranzitia automata
function startInterval() {
    // setam un interval de 3 secunde pentru afisarea urmatorului element
    intervalId = setInterval(showNext, 3000);
}

// functia care opreste tranzitia automata
function stopInterval() {
    clearInterval(intervalId);
}

// atasam functiile de afisare a elementelor la butoanele Next si Previous
const nextButton = document.getElementById('next-button');
nextButton.addEventListener('click', showNext);
const previousButton = document.getElementById('previous-button');
previousButton.addEventListener('click', showPrevious);

// pornim tranzitia automata
startInterval();