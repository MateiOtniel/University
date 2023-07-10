const imageList = $('#image-list');
const listItems = imageList.find('li');
listItems.eq(0).hide();
let currentIndex = 0;
let intervalId = null;

function showNext() {
    listItems.eq(currentIndex).hide();
    currentIndex = (currentIndex + 1) % listItems.length;
    listItems.eq(currentIndex).show();
}

function showPrevious() {
    listItems.eq(currentIndex).hide();
    currentIndex = (currentIndex - 1 + listItems.length) % listItems.length;
    listItems.eq(currentIndex).show();
}

function startInterval() {
    intervalId = setInterval(showNext, 3000);
}

$('#next-button').click(showNext);
$('#previous-button').click(showPrevious);

startInterval();